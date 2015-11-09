package org.spring4scala.transaction
import org.springframework.transaction.support._
import org.springframework.transaction._
import scala.util._
import scala.language.implicitConversions

package object support {
  type TxCallback[T] = TransactionStatus => T
}
