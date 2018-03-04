package cn.liuguangjie.api;

import cn.liuguangjie.core.redis.RedisOperation;
import cn.liuguangjie.mapper.UserMapper;
import cn.liuguangjie.model.User;
import cn.liuguangjie.result.ResponseResult;
import cn.liuguangjie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * @Author ms.liu
 * ~~Email liuguangj@dingtalk.com
 * @Date 2018-03-03 下午6:03
 */
@RestController
@RequestMapping("/user")
public class UserContorller {


    @Autowired
    private UserService userService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisOperation redisOperation;
    /**
     * 实现账号密码输入错误
     * 登录次数限制
     * @param user
     * @return
     */
    @RequestMapping("/userLogin")
    public ResponseResult<String> userLogin(User user) {
        final ResponseResult<String> result;
        try {
            User info = userService.login(user);
            result = ResponseResult.buildSuccess(UUID.randomUUID().toString());
            final String username = user.getUsername() == null ? "" : user.getUsername();
            long ttl = redisOperation.ttl(username);
            if (info != null && ttl <= 0) {
                redisOperation.del(username);
                return result;
            }

            Object count = redisOperation.get(username);
            boolean lock = count != null && count.toString().equals("0");

            if (lock) {
                result.setData("账号已锁定剩余" + ttl + "s后重试");
                return result;
            } else {
                Integer key = count == null? 9 : Integer.parseInt(count.toString()) - 1;
                long lastTtl = 1800;
                if (key == 0) {
                    redisOperation.set(username, String.valueOf(key));
                    redisOperation.expire(username, lastTtl);
                }
                if (key > 0) {
                    redisOperation.set(username, String.valueOf(key));
                    result.setData("账号或密码错误剩余" + key + "次,将锁定.");
                } else {
                    result.setData("账号已锁定剩余" + lastTtl + "s后重试");
                }
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.buildError("");
        }

    }
}
