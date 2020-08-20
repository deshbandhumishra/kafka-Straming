package com.mcit.util
import scala.collection.JavaConverters._
import com.sksamuel.avro4s.{FromRecord, RecordFormat, ToRecord}
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerializer
import io.confluent.kafka.streams.serdes.avro.GenericAvroDeserializer
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream.KStream

object Avro {
  type SchemaRegistryUrl = String

  def stream[T: ToRecord : FromRecord]
  (builder: StreamsBuilder, topic: String)
  (implicit url: SchemaRegistryUrl): KStream[String, T] = {

    val config = Map("schema.registry.url" -> url).asJava
    implicit def stringSerde: Serde[SchemaRegistryUrl] = Serdes.String()
    implicit def genericAvroSerde: Serde[GenericRecord] = Serdes.serdeFrom({
      val ser = new GenericAvroSerializer()
      ser.configure(config, false)
      ser
    }, {
      val de = new GenericAvroDeserializer()
      de.configure(config, false)
      de
    })

    builder
      .stream[String, GenericRecord](topic)
      .mapValues(v => RecordFormat[T].from(v))
  }
}
