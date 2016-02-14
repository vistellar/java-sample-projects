package org.weixing.spring.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.weixing.spring.mybatis.domain.Level;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LevelTypeHandler extends BaseTypeHandler<Level> {
    public static Map<String, Level> levelMap = new HashMap<String, Level>(Level.values().length);

    static {
        for (Level level : Level.values()) {
            levelMap.put(level.getValue(), level);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Level parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public Level getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return levelMap.get(rs.getString(columnName));
    }

    @Override
    public Level getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return levelMap.get(rs.getString(columnIndex));
    }

    @Override
    public Level getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return levelMap.get(cs.getString(columnIndex));
    }
}