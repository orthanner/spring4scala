package org.spring4scala.jdbc
import org.springframework.jdbc.support._
import java.sql._
import javax.sql._
import scala.language.implicitConversions

package object support {
  type DatabaseMetaDataCallbackFunction = DatabaseMetaData => AnyRef

  class DBMetadataExtractor(callback: DatabaseMetaDataCallbackFunction) extends DatabaseMetaDataCallback {
    def processMetaData(dbmd: DatabaseMetaData): AnyRef = callback(dbmd)
  }

  implicit def asDatabaseMetaDataCallback(callback: DatabaseMetaDataCallbackFunction): DatabaseMetaDataCallback =
    new DBMetadataExtractor(callback)
}
