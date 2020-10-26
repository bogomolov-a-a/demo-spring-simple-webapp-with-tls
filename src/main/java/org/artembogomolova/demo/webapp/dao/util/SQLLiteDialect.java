package org.artembogomolova.demo.webapp.dao.util;

import java.sql.Types;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StringType;

/**
 * from https://fullstackdeveloper.guru/2020/05/01/how-to-integrate-sqlite-database-with-spring-boot/
 */
public class SQLLiteDialect extends Dialect {

  public SQLLiteDialect() {
    registerColumnType(Types.BIT, "integer");
    registerColumnType(Types.TINYINT, "tinyint");
    registerColumnType(Types.SMALLINT, "smallint");
    registerColumnType(Types.INTEGER, "integer");
    registerColumnType(Types.BIGINT, "bigint");
    registerColumnType(Types.FLOAT, "float");
    registerColumnType(Types.REAL, "real");
    registerColumnType(Types.DOUBLE, "double");
    registerColumnType(Types.NUMERIC, "numeric");
    registerColumnType(Types.DECIMAL, "decimal");
    registerColumnType(Types.CHAR, "char");
    registerColumnType(Types.VARCHAR, "varchar");
    registerColumnType(Types.LONGVARCHAR, "longvarchar");
    registerColumnType(Types.DATE, "date");
    registerColumnType(Types.TIME, "time");
    registerColumnType(Types.TIMESTAMP, "timestamp");
    registerColumnType(Types.BINARY, "blob");
    registerColumnType(Types.VARBINARY, "blob");
    registerColumnType(Types.LONGVARBINARY, "blob");
    registerColumnType(Types.BLOB, "blob");
    registerColumnType(Types.CLOB, "clob");
    registerColumnType(Types.BOOLEAN, "integer");
    registerFunction("concat", new VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""));
    registerFunction("mod", new SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"));
    registerFunction("substr", new StandardSQLFunction("substr", StringType.INSTANCE));
    registerFunction("substring", new StandardSQLFunction("substr", StringType.INSTANCE));
  }

  @Override
  public org.hibernate.dialect.pagination.LimitHandler getLimitHandler() {
    return new org.hibernate.dialect.pagination.AbstractLimitHandler() {
      @Override
      public boolean supportsLimit() {
        return true;
      }

      @Override
      public String processSql(String sql, org.hibernate.engine.spi.RowSelection selection) {
        return sql + (this.supportsLimitOffset() ? " limit ? offset ?" : " limit ?");
      }
    };
  }

  @Override
  public String getForUpdateString() {
    return "";
  }

}
