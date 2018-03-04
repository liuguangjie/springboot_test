package cn.liuguangjie.service.impl;

import cn.liuguangjie.mapper.UserMapper;
import cn.liuguangjie.model.User;
import cn.liuguangjie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ms.liu
 * ~~Email liuguangj@dingtalk.com
 * @Date 2018-03-04 上午9:30
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(User user) {
        User info = userMapper.selectOneUser(user);


        /**更新最后登录时间 unix 时间戳*/

        return info;
    }

}
