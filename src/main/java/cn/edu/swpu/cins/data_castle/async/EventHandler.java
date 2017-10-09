package cn.edu.swpu.cins.data_castle.async;

import java.util.List;

public interface EventHandler {

    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
