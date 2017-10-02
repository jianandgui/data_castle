package cn.edu.swpu.cins.data_castle.service;

public interface TimeService {
    long getCurrentTimeMillis();

    String getDate();

    long getMatchExpireTime();
}
