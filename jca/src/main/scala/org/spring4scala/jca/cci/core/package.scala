package org.spring4scala.jca.cci
import org.springframework.jca.cci._
import org.springframework.jca.cci.core._
import javax.resource.cci._
import scala.language.implicitConversions

package object core {
  type ConnectionCallbackFunction[T] = (Connection, ConnectionFactory) => T
  type InteractionCallbackFunction[T] = (Interaction, ConnectionFactory) => T
  type RecordCreatingFunction = RecordFactory => Record
  type RecordExtractingFunction[T] = Record => T

  class ConnectionCallbackAdapter[T](callback: ConnectionCallbackFunction[T]) extends ConnectionCallback[T] {
    def doInConnection(connection: Connection, connectionFactory: ConnectionFactory): T = callback(connection, connectionFactory)
  }

  class InteractionCallbackAdapter[T](callback: InteractionCallbackFunction[T]) extends InteractionCallback[T] {
    def doInInteraction(interaction: Interaction, connectionFactory: ConnectionFactory): T = callback(interaction, connectionFactory)
  }

  class RecordCreatorAdapter(callback: RecordCreatingFunction) extends RecordCreator {
    def createRecord(factory: RecordFactory): Record = callback(factory)
  }

  class RecordExtractorAdapter[T](extractor: RecordExtractingFunction[T]) extends RecordExtractor[T] {
    def extractData(record: Record): T = extractor(record)
  }

  implicit def asConnectionCallback[T](callback: ConnectionCallbackFunction[T]): ConnectionCallback[T] =
    new ConnectionCallbackAdapter(callback)

  implicit def asInteractionCallback[T](callback: InteractionCallbackFunction[T]): InteractionCallback[T] =
    new InteractionCallbackAdapter(callback)

  implicit def asRecordCreator(callback: RecordCreatingFunction): RecordCreator =
    new RecordCreatorAdapter(callback)

  implicit def asRecordExtractor[T](extractor: RecordExtractingFunction[T]): RecordExtractor[T] =
    new RecordExtractorAdapter(extractor)

}
