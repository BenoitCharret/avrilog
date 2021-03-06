package org.msgpack
import scala.collection.JavaConversions._
import org.msgpack.ScalaMessagePack._
import org.msgpack.conversion.ValueConversions
import com.avricot.avrilog.serialize.template.MutableMapTemplateTest
import org.msgpack.template.ScalaTemplateRegistry
import org.msgpack.template.AnyTemplate
import org.msgpack.template.ScalaTemplateRegistry
import org.msgpack.template.TemplateRegistry
import org.msgpack.template.StringTemplate
import org.msgpack.`type`.Value
import org.joda.time.DateTime
import com.avricot.avrilog.serialize.template.DateTimeTemplate
import com.avricot.avrilog.model.ClientTrace
import com.avricot.avrilog.model.User
import com.avricot.avrilog.model.ClientTrace
import com.avricot.avrilog.model.Trace
import com.avricot.avrilog.model.TraceContent

object AvrilogMPack extends ScalaMessagePackWrapper with ValueConversions {
  val template = new TemplateRegistry(null);
  val messagePack = new MessagePack()
  val sp = StringTemplate.getInstance()
  messagePack.register(classOf[scala.collection.mutable.Map[String, String]], new MutableMapTemplateTest(sp, sp))
  messagePack.register(classOf[DateTime], DateTimeTemplate.getInstance())
  messagePack.register(classOf[User])
  messagePack.register(classOf[ClientTrace])
}