package cn.liuguangjie.config.redis;

import cn.liuguangjie.core.redis.RedisOperation;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

/**
 * @Author ms.liu
 * ~~Email liuguangj@dingtalk.com
 * @Date 2018-03-04 上午8:17
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


    @Bean
    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
        CacheManager cacheManager = new RedisCacheManager(redisTemplate);
        return cacheManager;
    }


    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public RedisOperation redisOperation(RedisConnectionFactory factory) {
        RedisOperation redisOperation = new RedisOperation();
        redisOperation.setConnectionFactory(factory);
        return redisOperation;
    }

    /**redis 发布订阅*/


    /**
     * Redis消息监听器容器
     * 这个容器加载了RedisConnectionFactory和消息监听器
     */

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("sprinboot-redis-messaage"));
        return container;
    }
    /**
     * 将Receiver注册为一个消息监听器，并指定消息接收的方法（receiveMessage）
     * 如果不指定消息接收的方法，消息监听器会默认的寻找Receiver中的handleMessage这个方法作为消息接收的方法
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    /**
     * Receiver实例 接受消息
     */
    @Bean
    public Receiver receiver(CountDownLatch latch){
        return new Receiver(latch);
    }

    @Bean
    public CountDownLatch latch(){
        return new CountDownLatch(1);
    }


    /**
     * Redis Template 用来发送消息
     */
    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

}
