package sg.nus.iss.ssfassessment.repositories;

//import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
//remember to instantiate redis templates
public class NewsRepository {
    // @Value("{new.cache.duration}")
    // private Long cacheTime;

    @Autowired
    @Qualifier("redislab")//redis template 
    private RedisTemplate<String, String> redisTemplate; 
    //NOTE FOR FUTURE JJ: I THINK IT MIGHT BE <OBJECT, OBJECT>, IF SOMETHIGN GOES WRONG , TRY AGAIN


    //this is to set a standardized cache time in case you need it
     public void save(String id, String title, String payload){
        ValueOperations<String,String> valueOp = redisTemplate.opsForValue();
        valueOp.set(id.toLowerCase(), payload);
     }

    public Optional<String> get(String id, Optional<String> result){
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        String value = valueOp.get(id.toLowerCase());
        if(null == value)
        return Optional.empty();
    return Optional.of(value);
    }

    public JsonObject get(String id) {
        return null;
    }

    public Optional<String> get(String title, String id) {
        return null;
    }

    
}
