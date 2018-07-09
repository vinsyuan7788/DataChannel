package application.io.spring.common.utils.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

@MappedJdbcTypes({JdbcType.OTHER})
@MappedTypes({Object.class})
public class JSONTypeHandler extends BaseTypeHandler<Object> {

	private static final PGobject jsonObject = new PGobject();
	
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		jsonObject.setType("jsonb");
		jsonObject.setValue(parameter.toString());
		ps.setObject(i, jsonObject);
	}
  
	public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getString(columnIndex);
	}
  
	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex);
	}
  
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getString(columnName);
	}
}
