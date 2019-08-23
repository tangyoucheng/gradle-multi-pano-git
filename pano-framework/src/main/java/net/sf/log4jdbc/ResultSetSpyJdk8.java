package net.sf.log4jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Wraps a ResultSet and reports method calls, returns and exceptions。 <br>
 * JDBC 4 version。
 *
 * @author 唐友成
 * @date 2019-07-12
 */
public class ResultSetSpyJdk8 extends ResultSetSpy {

  public ResultSetSpyJdk8(StatementSpy parent, ResultSet realResultSet) {
    super(parent, realResultSet);
  }

  // 20190712 add START
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    return getRealResultSet().getObject(columnIndex, type);
  }

  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return getRealResultSet().getObject(columnLabel, type);
  }
  // 20190712 add END

}
