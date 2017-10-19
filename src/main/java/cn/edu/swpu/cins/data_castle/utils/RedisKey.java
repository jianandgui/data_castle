package cn.edu.swpu.cins.data_castle.utils;


public class RedisKey {

    private static String DATACASTLE_REGISTER = "USER_REGISTER";
    private static String SPLIT = ":";
    private static String DATACASTLE_LOGIN = "USER_LOGIN";
    private static String DADACASTLE_EVENTQUEUE = "EVENT_QUEUE";
    private static String UPLOAD_COUNT = "UPLOAD_COUNT";

    public static String getDatacastleRegister(String mail) {
        return DATACASTLE_REGISTER + SPLIT + mail;
    }
    public static String getDatacastleLogin(String mail) {
        return DATACASTLE_LOGIN + SPLIT + mail;
    }
    public static String getDadacastleEventqueue() {
        return DADACASTLE_EVENTQUEUE;
    }
    public static String getUploadCount(int teamId) {
        return UPLOAD_COUNT + SPLIT + teamId;
    }
}
