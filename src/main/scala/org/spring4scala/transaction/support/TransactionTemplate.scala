package org.spring4scala.transaction.support
import org.springframework.transaction.support.{ TransactionTemplate => JavaTemplate, _ }
import org.springframework.transaction._
import scala.util._

class TransactionTemplate extends JavaTemplate {
  private def transactionCallback[T](callback: TxCallback[T]): TransactionCallback[T] = new TransactionCallback[T]() {
    def doInTransaction(status: TransactionStatus): T = callback(status)
  }

  def apply[T](callback: TxCallback[T]): Try[T] = Try {
    execute(transactionCallback(callback))
  }
}

object TransactionTemplate {
  def apply(txManager: PlatformTransactionManager): TransactionTemplate = {
    val txTemplate = new TransactionTemplate()
    txTemplate.setTransactionManager(txManager)
    txTemplate
  }

  def apply(txManager: PlatformTransactionManager, txDefinition: TransactionDefinition): TransactionTemplate = {
    val txTemplate = apply(txManager)
    txTemplate.setIsolationLevel(txDefinition.getIsolationLevel)
    txTemplate.setName(txDefinition.getName)
    txTemplate.setPropagationBehavior(txDefinition.getPropagationBehavior)
    txTemplate.setTimeout(txDefinition.getTimeout)
    txTemplate.setReadOnly(txDefinition.isReadOnly)
    txTemplate
  }
}
