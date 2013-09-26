package disqus.dataweek

import backtype.storm.{Config, LocalCluster, StormSubmitter}
import backtype.storm.tuple.{Tuple, Fields, Values}
import backtype.storm.topology.{BasicOutputCollector, OutputFieldsDeclarer, TopologyBuilder}
import backtype.storm.topology.base.BaseBasicBolt

import disqus.dataweek.spout.WordSpout


object DemoTopology {
  /**
   * Generic bolt template.
   */
  class CapitalizeBolt extends BaseBasicBolt {
    override def execute(tuple: Tuple, collector: BasicOutputCollector) {
      val capitalizedWord = tuple.getString(0).toUpperCase
      collector.emit(new Values(capitalizedWord))
    }

    override def declareOutputFields(declarer: OutputFieldsDeclarer) {
      declarer.declare(new Fields("capitalizedWord"))
    }
  }

  def main(args: Array[String]) {
    /* Create topology configuration object */
    val conf: Config = new Config()
    conf.setMaxSpoutPending(1)
    conf.setDebug(true)

    /* Build the topology */
    val builder: TopologyBuilder = new TopologyBuilder()
    builder.setSpout("0-word-spout", new WordSpout())
    builder.setBolt("1-capitalize", new CapitalizeBolt(), 1)
      .shuffleGrouping("0-word-spout")

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
