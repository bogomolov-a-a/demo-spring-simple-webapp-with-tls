package org.artembogomolova.demo.webapp.dao.util;

import java.sql.Types;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
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

  public boolean supportsTemporaryTables() {
    return true;
  }

  public String getCreateTemporaryTableString() {
    return "create temporary table if not exists";
  }

  public boolean dropTemporaryTableAfterUse() {
    return false;
  }

  @Override
  public boolean supportsCurrentTimestampSelection() {
    return true;
  }

  @Override
  public boolean isCurrentTimestampSelectStringCallable() {
    return false;
  }

  @Override
  public String getCurrentTimestampSelectString() {
    return "select current_timestamp";
  }

  @Override
  public boolean supportsUnionAll() {
    return true;
  }

  @Override
  public boolean hasAlterTable() {
    return false; // As specify in NHibernate dialect
  }

  @Override
  public boolean dropConstraints() {
    return false;
  }

  @Override
  public String getAddColumnString() {
    return "add column";
  }

  @Override
  public String getForUpdateString() {
    return "";
  }

  @Override
  public boolean supportsOuterJoinForUpdate() {
    return false;
  }

  @Override
  public String getDropForeignKeyString() {
    throw new UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect");
  }

  @Override
  public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
      String[] primaryKey, boolean referencesPrimaryKey) {
    throw new UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect");
  }

  @Override
  public String getAddPrimaryKeyConstraintString(String constraintName) {
    throw new UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect");
  }

  @Override
  public boolean supportsIfExistsBeforeTableName() {
    return true;
  }

  @Override
  public boolean supportsCascadeDelete() {
    return false;
  }

  @Override
  public IdentityColumnSupport getIdentityColumnSupport() {
    return new IdentityColumnSupportImpl()
    {
      @Override
      public boolean supportsIdentityColumns() {
        return true;
      }

      @Override
      public String getIdentitySelectString(String table, String column, int type)
          throws MappingException {
        return "select last_insert_rowid()";
      }

      @Override
      public String getIdentityColumnString(int type) throws MappingException {
        switch(type)
        {
          case Types.BIGINT:
          {
            return "bigint";
          }
          case Types.INTEGER: {
            return "integer primary key autoincrement";
          }
        }
        return "integer";
      }
    };
  }
}
