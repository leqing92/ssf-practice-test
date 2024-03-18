package sg.edu.nus.iss.practicetest.Configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Utils.Util;

@Configuration
public class RedisConfig {
    //inject application properties into this configuration
    @Value ("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;
    
    @Value("${spring.data.redis.username}")
    private String redisUser;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);

        if(!"NOT_SET".equals(redisUser.trim())){
            redisConfig.setUsername(redisUser);
            redisConfig.setPassword(redisPassword);
        }

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisConnFac = new JedisConnectionFactory(redisConfig, jedisClient);
        jedisConnFac.afterPropertiesSet();

        return jedisConnFac;
    }

    @Bean(Util.REDIS_ONE)
    public RedisTemplate<String, String> redisStringTemplate(){

        RedisTemplate <String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        //value and list operation
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        //if using hash (Map operation)
        template.setHashKeySerializer(new StringRedisSerializer()); 
        template.setHashValueSerializer(new StringRedisSerializer());
        
        return template;
    }
}
