package org.artembogomolova.demo.webapp.main.dao.util

import org.hibernate.dialect.Dialect
import org.hibernate.dialect.function.SQLFunctionTemplate
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.dialect.function.VarArgsSQLFunction
import org.hibernate.dialect.pagination.AbstractLimitHandler
import org.hibernate.dialect.pagination.LimitHandler
import org.hibernate.engine.spi.RowSelection
import org.hibernate.type.StringType
import java.sql.Types

/**
 * from https://fullstackdeveloper.guru/2020/05/01/how-to-integrate-sqlite-database-with-spring-boot/
 */
class SQLite3Dialect : Dialect() {
    override fun getLimitHandler(): LimitHandler {
        return object : AbstractLimitHandler() {
            override fun supportsLimit(): Boolean {
                return true
            }

            override fun processSql(sql: String, selection: RowSelection): String {
                return sql + if (this.supportsLimitOffset()) " limit ? offset ?" else " limit ?"
            }
        }
    }

    companion object {
        private const val SUBSTRING_FUNCTION = "substr"
        private const val INTEGER_TYPE = "integer"
        private const val BLOB_TYPE = "blob"
    }

    init {
        registerColumnType(Types.BIT, INTEGER_TYPE)
        registerColumnType(Types.TINYINT, "tinyint")
        registerColumnType(Types.SMALLINT, "smallint")
        registerColumnType(Types.INTEGER, INTEGER_TYPE)
        registerColumnType(Types.BIGINT, "bigint")
        registerColumnType(Types.FLOAT, "float")
        registerColumnType(Types.REAL, "real")
        registerColumnType(Types.DOUBLE, "double")
        registerColumnType(Types.NUMERIC, "numeric")
        registerColumnType(Types.DECIMAL, "decimal")
        registerColumnType(Types.CHAR, "char")
        registerColumnType(Types.VARCHAR, "varchar")
        registerColumnType(Types.LONGVARCHAR, "longvarchar")
        registerColumnType(Types.DATE, "date")
        registerColumnType(Types.TIME, "time")
        registerColumnType(Types.TIMESTAMP, "timestamp")
        registerColumnType(Types.BINARY, BLOB_TYPE)
        registerColumnType(Types.VARBINARY, BLOB_TYPE)
        registerColumnType(Types.LONGVARBINARY, BLOB_TYPE)
        registerColumnType(Types.BLOB, BLOB_TYPE)
        registerColumnType(Types.CLOB, "clob")
        registerColumnType(Types.BOOLEAN, INTEGER_TYPE)
        registerFunction("concat", VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""))
        registerFunction("mod", SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"))
        registerFunction(SUBSTRING_FUNCTION, StandardSQLFunction(SUBSTRING_FUNCTION, StringType.INSTANCE))
        registerFunction("substring", StandardSQLFunction(SUBSTRING_FUNCTION, StringType.INSTANCE))
    }
}