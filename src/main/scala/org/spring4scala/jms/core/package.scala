package org.spring4scala.jms
import org.springframework.jms.core._
import javax.jms._
import scala.language.implicitConversions

package object core {
  type BrowserCallbackFunction[T] = (Session, QueueBrowser) => T
  type PostProcessingFunction = Message => Message

  class BrowserCallbackAdapter[T](callback: BrowserCallbackFunction[T]) extends BrowserCallback[T] {
    def doInJms(session: Session, browser: QueueBrowser): T = callback(session, browser)
  }

  class PostProcessorAdapter(callback: PostProcessingFunction) extends MessagePostProcessor {
    def postProcessMessage(msg: Message): Message = callback(msg)
  }

  implicit def asMessagePostProcessor(callback: PostProcessingFunction): MessagePostProcessor = new PostProcessorAdapter(callback)

  implicit def asBrowserCallback[T](callback: BrowserCallbackFunction[T]): BrowserCallback[T] = new BrowserCallbackAdapter(callback)

}
