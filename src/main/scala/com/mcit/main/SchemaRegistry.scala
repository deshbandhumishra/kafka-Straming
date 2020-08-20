package com.mcit.main

import java.io.{File, PrintWriter}
import java.util.Properties
import java.util.concurrent.TimeUnit

import com.mcit.util.{Avro, AvroSchemaImport, Base, Block}
import com.sksamuel.avro4s.AvroSchema
import io.circe.generic.encoding.DerivedAsObjectEncoder.deriveEncoder
import io.circe.syntax.EncoderOps
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

object SchemaRegistry extends App with Base{
  val inner = AvroSchema[Block].toString(false)
  val schema = AvroSchemaImport(inner).asJson.noSpaces//son.noSpaces
  val writer = new PrintWriter(new File("block.json"))
  writer.write(schema)
  writer.close()

  val config = new Properties()
  config.put(StreamsConfig.APPLICATION_ID_CONFIG, "application_id3")
  config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")

  implicit val url: Avro.SchemaRegistryUrl = "http://localhost:8081"

  val builder = new StreamsBuilder
  val blocks  = Avro.stream[Block](builder, "test_3")
  val streams  = new KafkaStreams(builder.build(), config)

  streams.start()

  sys.ShutdownHookThread {
    streams.close(10, TimeUnit.SECONDS)
  }

}
