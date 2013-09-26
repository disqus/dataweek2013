package disqus.dataweek.spout

import java.util.Map;

import backtype.storm.Config;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;


/**
    Trivial spout implementation that emits a random element from a sequence of words
*/
class WordSpout extends BaseRichSpout {
    var _collector: SpoutOutputCollector = _
    val words: List[String] = List("George", "Jason", "James", "Pedro", "David", "Stork", "Matt")

    def open(conf: java.util.Map[_,_], context: TopologyContext, collector: SpoutOutputCollector) {
        _collector = collector;
    }

    override def close() { }

    def nextTuple() {
        Utils sleep 100
        val idx: Int = util.Random nextInt words.length
        _collector.emit(new Values(words(idx)));
    }

    override def ack(msgId: Object ) { }

    override def fail(msgId: Object ) { }

    def declareOutputFields(declarer: OutputFieldsDeclarer ) {
        declarer.declare(new Fields("word"))
    }

    override def getComponentConfiguration(): java.util.Map[String, Object] = {
        null
    }
}


// vim: set ts=4 sw=4 et:
