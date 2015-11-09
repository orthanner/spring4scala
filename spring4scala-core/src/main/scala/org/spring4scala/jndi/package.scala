package org.spring4scala

import org.springframework.jndi._
import javax.naming._
import scala.language.implicitConversions

package object jndi {
  type JndiCallbackFunction[T] = Context => T

  private[jndi] class JndiCallbackAdapter[T](callback: JndiCallbackFunction[T]) extends JndiCallback[T] {
    def doInContext(context: Context): T = callback(context)
  }

  implicit def asJndiCallback[T](callback: JndiCallbackFunction[T]): JndiCallback[T] = new JndiCallbackAdapter(callback)

}
