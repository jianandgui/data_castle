package cn.edu.swpu.cins.data_castle.async.handler;


import cn.edu.swpu.cins.data_castle.async.EventHandler;
import cn.edu.swpu.cins.data_castle.async.EventModel;
import cn.edu.swpu.cins.data_castle.async.EventType;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MailHandler implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
    @Autowired
    JedisAdapter jedisAdapter;



    @Override
    public void doHandle(EventModel model) {
        try{
            String status=model.getExts().get("status");
            switch (status) {

        }
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }


    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.MAIL);
    }


}
