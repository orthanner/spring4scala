package org.spring4scala.jdbc
import org.springframework.dao.EmptyResultDataAccessException
import java.sql._
import scala.language.postfixOps
import scala.util._
import scala.collection.JavaConverters._
import org.springframework.jdbc.core._
import scala.language.implicitConversions

package object core {
	type RowMappingFunction[T] = (ResultSet, Integer) => T
	type CallableStatementCallbackFunction[T] = CallableStatement => T
	type ValueSetter[T] = (PreparedStatement, T) => Unit
	type PreparedStatementCallbackFunction[T] = PreparedStatement => T
	type ResultSetExtractingFunction[T] = ResultSet => T
	type StatementCallbackFunction[T] = Statement => T
	type ParameterizedPreparedStatementSettingFunction[T] = (PreparedStatement, T) => Unit

	class ResultSetExtractorAdapter[T](callback: ResultSetExtractingFunction[T]) extends ResultSetExtractor[Option[T]] {
	  def extractData(rs: ResultSet): Option[T] = if (rs.next) Option(callback(rs)) else None
	}

	class RowMapperAdapter[T](mapper: RowMappingFunction[T]) extends RowMapper[T] {
	  def mapRow(rs: ResultSet, n: Int): T = mapper(rs, n)
	}

	class CallableStatementCallbackAdapter[T](callback: CallableStatementCallbackFunction[T]) extends CallableStatementCallback[T] {
		def doInCallableStatement(cs: CallableStatement): T = callback(cs)
	}

	class StatementCallbackAdapter[T](callback: StatementCallbackFunction[T]) extends StatementCallback[T] {
		def doInStatement(cs: Statement): T = callback(cs)
	}

	class ParameterizedPreparedStatementSetterAdapter[T](callback: ParameterizedPreparedStatementSettingFunction[T]) extends ParameterizedPreparedStatementSetter[T] {
		def setValues(ps: PreparedStatement, arg: T): Unit = callback(ps, arg)
	}

	implicit def asRowMapper[T](mapper: RowMappingFunction[T]): RowMapper[T] = new RowMapperAdapter(mapper)

	implicit def asResultSetExtractor[T](callback: ResultSetExtractingFunction[T]): ResultSetExtractor[Option[T]] = new ResultSetExtractorAdapter(callback)

	implicit def asCallableStatementCallback[T](callback: CallableStatementCallbackFunction[T]): CallableStatementCallback[T] =
		new CallableStatementCallbackAdapter(callback)

	implicit def asStatementCallback[T](callback: StatementCallbackFunction[T]): StatementCallback[T] =
			new StatementCallbackAdapter(callback)

	implicit def ParameterizedPreparedStatementSetterAdapter[T](callback: ParameterizedPreparedStatementSettingFunction[T]): ParameterizedPreparedStatementSetter[T] =
		new ParameterizedPreparedStatementSetterAdapter(callback)

}
