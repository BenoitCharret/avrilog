organization := "com.avricot.avrilog"

name := "avrilog-server"

version := "0.1"

scalaVersion := "2.9.1"

resolvers ++= Seq(
"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
"Sonatype OSS Releases" at  "http://oss.sonatype.org/content/repositories/releases/" )

libraryDependencies ++= Seq(
	"com.rabbitmq" % "amqp-client" % "2.8.7" withSources()  ,
	"com.typesafe.akka" % "akka-actor" % "2.0.3" withSources() ,
	"commons-codec" % "commons-codec" % "1.7" withSources() ,
//	"com.github.scala-incubator.io" %% "scala-io-core" % "0.4.1-seq" withSources() ,
	"org.msgpack" % "msgpack" % "0.6.6" withSources() ,
	"com.avricot" %% "horm" % "0.1.1" withSources())
           
EclipseKeys.withSource := true
