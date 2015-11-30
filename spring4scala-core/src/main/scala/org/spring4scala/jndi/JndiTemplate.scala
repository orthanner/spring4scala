package org.spring4scala.jndi

import javax.naming._
import java.util.Properties
import org.springframework.jndi.{ JndiTemplate => JavaTemplate }

class JndiTemplate extends JavaTemplate {
  override def bind(name: String, `object`: Any): Unit = super.bind(name, `object`.asInstanceOf[Object])

  def apply[T](callback: JndiCallbackFunction[T]): T = execute(callback)
}

object JndiTemplate {
  def apply(env: Properties): JndiTemplate = {
    val template = new JndiTemplate
    template.setEnvironment(env)
    template
  }
}
