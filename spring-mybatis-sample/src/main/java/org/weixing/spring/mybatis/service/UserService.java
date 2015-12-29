package org.weixing.spring.mybatis.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weixing.spring.mybatis.domain.User;

import java.util.List;

/**
 * @author weixing
 */
@Component
public class UserService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public User getUserById(String id) {
        return sqlSessionTemplate.selectOne("getUserById", id);
    }

    public List<User> getUsers() {
        return sqlSessionTemplate.selectList("getUsers");
    }
}
