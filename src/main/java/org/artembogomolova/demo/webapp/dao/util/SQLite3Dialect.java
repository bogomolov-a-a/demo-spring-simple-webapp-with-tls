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
public class SQLite3Dialect extends Dialect {

  private static final String SUBSTRING_FUNCTION = "substr";
  private static final String INTEGER_TYPE = "integer";
  private static final String BLOB_TYPE = "blob";
  public static final String FOREIGN_KEY_COLUMN_DEFINITION = "bigint";
  public static final String IDENTITY_COLUMN_DEFINITION = "integer not null primary key autoincrement";

  public SQLite3Dialect() {
    registerColumnType(Types.BIT, INTEGER_TYPE);
    registerColumnType(Types.TINYINT, "tinyint");
    registerColumnType(Types.SMALLINT, "smallint");
    registerColumnType(Types.INTEGER, INTEGER_TYPE);
    registerColumnType(Types.BIGINT, FOREIGN_KEY_COLUMN_DEFINITION);
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
    registerColumnType(Types.BINARY, BLOB_TYPE);
    registerColumnType(Types.VARBINARY, BLOB_TYPE);
    registerColumnType(Types.LONGVARBINARY, BLOB_TYPE);
    registerColumnType(Types.BLOB, BLOB_TYPE);
    registerColumnType(Types.CLOB, "clob");
    registerColumnType(Types.BOOLEAN, INTEGER_TYPE);
    registerFunction("concat", new VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""));
    registerFunction("mod", new SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"));
    registerFunction(SUBSTRING_FUNCTION, new StandardSQLFunction(SUBSTRING_FUNCTION, StringType.INSTANCE));
    registerFunction("substring", new StandardSQLFunction(SUBSTRING_FUNCTION, StringType.INSTANCE));
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
