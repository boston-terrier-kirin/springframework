package com.example.spring_jdbc.repository;

import com.example.spring_jdbc.entity.Test;
import com.example.spring_jdbc.rowmappers.TestRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class TestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * デフォルトのRowMapperがある。
     */
    public List<Test> findAll() {
        return jdbcTemplate.query("select * from TEST", new BeanPropertyRowMapper(Test.class));
    }

    /**
     * SQLログがでない。
     */
    public List<Test> findMsgsWithMessage(String message) {
        String sql = "SELECT * FROM TEST WHERE MESSAGE LIKE ?";

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, message);
            }
        }, new TestRowMapper());
    }

    /**
     * queryForObjectは、カラム指定の場合に使うらしい。
     * https://note.com/yukoro/n/n1b0347250345
     */
    public Test findById(int id) {
        return jdbcTemplate.queryForObject("select * from test where id=?",
                new Object[] { id },
                new int[] {Types.INTEGER},
                new BeanPropertyRowMapper<Test>(Test.class));
    }

    /**
     * 戻りの型がObjectなので使いにくい
     */
    public Map<String, Object> findByMap(int id) {
        return jdbcTemplate.queryForMap("select * from test where id=?",
                                            new Object[] { id });
    }

    /**
     * 単純にこれが良いのでは？
     * https://qiita.com/suema0331/items/d7ccfd5aa2c983edbeab
     */
    public List<Test> findByNamedParameter(int id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("message", "%test%");

        return namedParameterJdbcTemplate.query("select * from test where id >= :id and message like :message",
                                                        params,
                                                        new BeanPropertyRowMapper<Test>(Test.class));
    }

    public int saveTest(Test test){
        String sql = "INSERT INTO TEST (NAME, MESSAGE, CREATED_AT, CREATED_BY) VALUES (?,?,?,?)";

        return jdbcTemplate.update(sql,
                test.getName(), test.getMessage(), test.getCreatedAt(), test.getCreatedBy());
    }

    /**
     * SQLログがでない。
     */
    public int updateTest(int id, String message, String updatedBy) {
        String sql = "UPDATE TEST SET MESSAGE = ?, UPDATED_BY = ?,UPDATED_AT =? WHERE ID = ?";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, message);
                preparedStatement.setString(2, updatedBy);
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(4, id);
            }
        });
    }
}
