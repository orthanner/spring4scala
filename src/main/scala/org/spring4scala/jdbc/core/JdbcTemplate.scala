package org.spring4scala.jdbc.core
import org.springframework.dao.EmptyResultDataAccessException
import java.sql._
import scala.language.postfixOps
import scala.util._
import scala.collection.JavaConverters._
import javax.sql._
import org.springframework.jdbc.core.{ JdbcTemplate => JavaTemplate, _ }

class JdbcTemplate(dataSource: DataSource, lazyInit: Boolean) extends JavaTemplate(dataSource, lazyInit) {

  def this(dataSource: DataSource) = this(dataSource, false)

  def this() = this(null, false)

	def get[T](queryString: String, args: Object*)(callback: ResultSetExtractingFunction[T]): Try[Option[T]] = Try {
		super.query(queryString, args.toArray, callback)
	} recover {
		case e: EmptyResultDataAccessException => None
	}

	def query[T](queryString: String, args: Object*)(callback: RowMappingFunction[T]): Seq[T] = super.query(queryString, args.toArray, callback) asScala

	def queryForObject[T](queryString: String, args: (Any, Int)*)(callback: RowMappingFunction[T]): Try[Option[T]] = Try {
		Some(queryForObject(queryString, args.map { _._1.asInstanceOf[Object] } toArray, args.map { _._2 } toArray, callback))
	} recover {
		case e: EmptyResultDataAccessException => None
	}
}
