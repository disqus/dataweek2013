package disqus.dataweek.spout

import java.net.URL
import java.util.{Map, Random}
import java.util.zip.GZIPInputStream

import backtype.storm.Config
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values
import backtype.storm.utils.Utils

import scala.io.Source


/**
    Trivial spout implementation that emits a random element from a sequence of words
*/
class GnipSpout extends BaseRichSpout {
    var _collector: SpoutOutputCollector = _
    val _cred: String = new sun.misc.BASE64Encoder().encode("drskippy27@gmail.com:gnopster33".getBytes)
    val _endpoints: Array[String] = Array(
        "https://stream.gnip.com:443/accounts/DrSkippy/publishers/disqus/streams/comment/dev1.json",
        "https://stream.gnip.com:443/accounts/DrSkippy/publishers/disqus/streams/comment/dev2.json",
        "https://stream.gnip.com:443/accounts/DrSkippy/publishers/disqus/streams/comment/dev3.json",
        "https://stream.gnip.com:443/accounts/DrSkippy/publishers/disqus/streams/comment/dev4.json"
    )
    var _stream: Iterator[Char] = _

    def open(conf: java.util.Map[_,_], context: TopologyContext, collector: SpoutOutputCollector) {
        _collector = collector
        val url: String = _endpoints(new Random().nextInt(_endpoints.length))
        val connection = new URL(url).openConnection
        connection.setRequestProperty("Authorization", "Basic " + _cred)
        connection.setRequestProperty("Accept-Encoding", "deflate gzip")
        _stream = Source.fromInputStream(new GZIPInputStream(connection.getInputStream))
    }

    override def close() { }

    def nextTuple() {
        Utils sleep 100
        var chars:List[Char] = List[Char]()
        var c: Char = '\0'
        do {
            c = _stream.next()
            chars = c :: chars
        } while (c != '\n')
        _collector.emit(new Values(new String(chars.reverse.toArray)))
    }

    override def ack(msgId: Object ) { }

    override def fail(msgId: Object ) { }

    def declareOutputFields(declarer: OutputFieldsDeclarer ) {
        declarer.declare(new Fields("line"))
    }

    override def getComponentConfiguration(): java.util.Map[String, Object] = {
        null
    }
}


// vim: set ts=4 sw=4 et:
