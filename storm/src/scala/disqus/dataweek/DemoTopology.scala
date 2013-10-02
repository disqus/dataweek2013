package disqus.dataweek

import backtype.storm.{Config, LocalCluster, StormSubmitter}
import backtype.storm.tuple.{Tuple, Fields, Values}
import backtype.storm.topology.{BasicOutputCollector, OutputFieldsDeclarer, TopologyBuilder}
import backtype.storm.topology.base.BaseBasicBolt

import scala.collection.mutable.HashMap
import scala.util.parsing.json.JSON

import disqus.dataweek.spout.GnipSpout


object DemoTopology {
  val _map: HashMap[String,Int] = HashMap[String,Int]()
  val wordBank: Set[String] = Set("shutdown", "shut down", "government", "politicians", "republicans", "democrats", "politics", "obama", "congress", "congressional", "obamacare", "white house", "Nancy Pelosi", "Harry Reid", "Mitch McConnell", "John Boehner", "aca", "affordable care act", "washington")
  var obamacare: Double = 0
  var total: Double = 0

  /**
   * Generic bolt template.
   */
  class Message extends BaseBasicBolt {
    override def execute(tuple: Tuple, collector: BasicOutputCollector) {
      val json: Map[String,Any] = JSON.parseFull(tuple.getString(0)) match {
        case Some(m) => m.asInstanceOf[Map[String,Any]]
        case _ => null
      }

      if (json == null)
        return

      val tag: String = json("id").asInstanceOf[String]
      if (tag.split(":")(1) != "gnip.disqus.com")
          return

      val message: String = json("body").asInstanceOf[String]

      collector.emit(new Values(message))
    }

    override def declareOutputFields(declarer: OutputFieldsDeclarer) {
      declarer.declare(new Fields("body"))
    }
  }

  class Count extends BaseBasicBolt {
    override def execute(tuple: Tuple, collector: BasicOutputCollector) {
        val message: String = tuple.getString(0)
        var aboutObamacare: Boolean = false
        for (word <- message.split(" ")) {
          if (wordBank contains word.toLowerCase)
            aboutObamacare = true
        }
        if (aboutObamacare)
          obamacare += 1
        total += 1

        collector.emit(new Values(100*obamacare/total: java.lang.Double))
    }

    override def declareOutputFields(declarer: OutputFieldsDeclarer) { 
        declarer.declare(new Fields("word"))
    }
  }

  def main(args: Array[String]) {
    /* Create topology configuration object */
    val conf: Config = new Config()
    conf.setMaxSpoutPending(1)
    conf.setDebug(true)

    /* Build the topology */
    val builder: TopologyBuilder = new TopologyBuilder()
    builder.setSpout("0-word-spout", new GnipSpout())
    builder.setBolt("1-message", new Message(), 1)
      .shuffleGrouping("0-word-spout")
    builder.setBolt("2-count", new Count(), 1)
      .shuffleGrouping("1-message")

    /* Run topology in either local or remote mode */
    if (args.length == 0) {
      val cluster: LocalCluster = new LocalCluster()
      cluster.submitTopology("demo", conf, builder.createTopology())
      Thread.sleep(100000)
    } else {
      conf.setNumWorkers(1)
      StormSubmitter.submitTopology(args(0), conf, builder.createTopology())
    }
  }
}

// vim: set ts=2 sw=2 et:
