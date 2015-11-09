package org.springframework.core
import java.util.concurrent.Callable

package object task {

  implicit def asRunnable(func: => Unit): Runnable = new Runnable {
    def run: Unit = func
  }

  implicit def asCallable[T](func: => T): Callable[T] = new Callable[T] {
    def call: T = func
  }
}
