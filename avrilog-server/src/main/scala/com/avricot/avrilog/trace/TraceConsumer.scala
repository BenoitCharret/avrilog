package com.avricot.avrilog.trace

import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.duration._
import com.avricot.avrilog.model.Trace
import com.avricot.avrilog.model.ClientTrace
import org.msgpack.AvrilogMPack
import com.avricot.avrilog.model.TraceContent
import org.slf4j.LoggerFactory
import java.io.IOException
import akka.actor.actorRef2Scala
import com.avricot.avrilog.rabbitmq.ConsumerManager
import com.avricot.avrilog.rabbitmq.RabbitMQConfig
import com.avricot.avrilog.rabbitmq.Message
import com.avricot.avrilog.crypto.sign.Sign
import com.avricot.avrilog.crypto.timestamp.Timestamping
import com.avricot.avrilog.rabbitmq.Start

class TraceConsumer {
  val logger = LoggerFactory.getLogger(classOf[TraceConsumer])

  def handleTrance(msg: Message): Unit = {
    try {
      try {
        msg.sendAck();
      } catch {
        case e: IOException => logger.error("error trying to ack the msg. rabbitMQ is probably down.", e); return
      }
      val clientTrace = AvrilogMPack.read[ClientTrace](msg.body);
      val traceContent = TraceContent(clientTrace)
      val traceContentBytes = traceContent.toJson.getBytes()
      try {
        val trace = clientTrace match {
          case c if c.sign && c.horodate => Trace(traceContent, Sign.signWithRemoteTimestamp(traceContentBytes), null)
          case c if c.sign => Trace(traceContent, Sign.sign(traceContentBytes), null)
          case c if c.horodate => Trace(traceContent, null, Timestamping.timestamp(traceContentBytes))
        }
        try {
          Trace.save(trace)
        } catch {
          case e: Throwable => logger.error("error while trying to save the trace in db. Trace content : " + trace.toJson, e)
        }
      } catch {
        case e: Throwable => logger.error("error while trying to horodate/sign trace. Trace content : " + traceContent.toJson, e)
      }
    } catch {
      case e: IOException => logger.error("IO exception. Your incoming message might not verify the protocol (field name much match to read trace using messagePack). body: " + msg.body + ", delivery Tag: " + msg.deliveryTag, e);
    }
  }
  val system = ActorSystem()
  system.actorOf(Props(new ConsumerManager(handleTrance, RabbitMQConfig.queue))) ! Start
}