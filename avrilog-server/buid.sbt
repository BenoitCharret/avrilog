organization := "com.avricot.avrilog"

name := "avrilog-server"

version := "0.1"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"com.rabbitmq" % "amqp-client" % "2.8.7" withSources() ,
	"com.typesafe" % "config" % "0.6.0" withSources() ,
	"com.typesafe.akka" % "akka-actor" % "2.0.3" withSources() 
)

EclipseKeys.withSource := true

