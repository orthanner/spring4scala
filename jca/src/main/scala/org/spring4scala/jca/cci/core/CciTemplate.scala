package org.spring4scala.jca.cci.core
import org.springframework.jca.cci.core.{ CciTemplate => JavaTemplate, _ }
import javax.resource.cci._

class CciTemplate extends JavaTemplate {
  def execute(spec: InteractionSpec)(inputCreator: RecordCreatingFunction): Record =
    execute(spec, inputCreator)

  def execute[T](spec: InteractionSpec)(inputCreator: RecordCreatingFunction)(extractor: RecordExtractingFunction[T]): T =
    execute(spec, inputCreator, extractor)

  def execute[T](spec: InteractionSpec, input: Record)(extractor: RecordExtractingFunction[T]): T =
    execute(spec, input, extractor)

  override def getDerivedTemplate(connectionSpec: ConnectionSpec): CciTemplate = {
    val template = new CciTemplate
    template.setOutputRecordCreator(getOutputRecordCreator)
    template.setConnectionFactory(getConnectionFactory)
    template.setConnectionSpec(connectionSpec)
    template
  }
}

object CciTemplate {
  def apply(connectionFactory: ConnectionFactory): CciTemplate = {
    val template = new CciTemplate
    template.setConnectionFactory(connectionFactory)
    template
  }

  def apply(connectionFactory: ConnectionFactory, connectionSpec: ConnectionSpec): CciTemplate = {
    val template = new CciTemplate
    template.setConnectionFactory(connectionFactory)
    template.setConnectionSpec(connectionSpec)
    template
  }
}
