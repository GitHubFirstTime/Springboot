package com.rlc.midServer;

import com.rlc.midServer.common.redis.service.RedisHelperImpl;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * TODO
 * ClassName:TestRedis <br/>
 * Function: redis测试类 ADD FUNCTION. <br/>
 * Reason:	 redis测试类 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/11/6 16:19
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest

@WebAppConfiguration
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisHelperImpl redisHelper;

    @Test
    public void test() throws Exception{
//        基本写法
//        stringRedisTemplate.opsForValue().set("aaa","111");
//        Assert.assertEquals("111",stringRedisTemplate.opsForValue().get("aaa"));
//        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
        Author user=new Author();
        user.setName("Alex");
        user.setIntro_l("不会做饭的程序员不是好男人");
        redisHelper.valuePut("aaa",user);
        redisHelper.valuePut("bbb","teststs");
        System.out.println(redisHelper.getValue("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        Author user=new Author();
        user.setName("Jerry");
        user.setIntro_l("不会做饭的程序员不是好男人!");

        ValueOperations<String, Author> operations=redisTemplate.opsForValue();
        operations.set("502", user);
        Thread.sleep(500);
        boolean exists=redisTemplate.hasKey("502");
        if(exists){
            System.out.println(redisTemplate.opsForValue().get("502"));
        }else{
            System.out.println("exists is false");
        }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}