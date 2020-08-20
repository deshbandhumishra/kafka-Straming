version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.3.1"
libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "io.circe" %% "circe-generic" % "0.13.0"
resolvers += "confluent.io" at "http://packages.confluent.io/maven/"
libraryDependencies ++=
  Seq("org.apache.kafka" %% "kafka-streams-scala" % "2.0.0",
    "io.confluent" % "kafka-streams-avro-serde" % "5.0.0",
    "com.sksamuel.avro4s" %% "avro4s-core" % "1.9.0")

val sparkVersion = "3.0.0"

//libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.3.3"
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion
