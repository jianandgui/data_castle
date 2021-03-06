package cn.edu.swpu.cins.data_castle.async;



import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import cn.edu.swpu.cins.data_castle.utils.RedisKey;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKey.getDadacastleEventqueue();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


}
