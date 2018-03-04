package cn.liuguangjie.core.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author ms.liu
 * ~~Email liuguangj@dingtalk.com
 * @Date 2018-03-04 下午3:46
 */
public class RedisOperation extends RedisTemplate<String, String> {





    /**获取key的有效时间*/
    public Long ttl(final String key) {

        return super.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ttl(key.getBytes());
            }
        });
    }

    /**设置值*/
    public void set(final String key, final String value) {

        super.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key.getBytes(), value.getBytes());
                return null;
            }
        });
    }

    /**获取值*/
    public String get(final String key) {

        return super.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.get(key.getBytes());

                return value == null ? null : new String(value);
            }
        });
    }

    /**设置有效时间*/
    public void expire (final String key, final long seconds) {

        super.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.expire(key.getBytes(), seconds);
                return null;
            }
        });
    }

    public void del(final String key) {

        super.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(key.getBytes());
                return null;
            }
        });
    }



}
