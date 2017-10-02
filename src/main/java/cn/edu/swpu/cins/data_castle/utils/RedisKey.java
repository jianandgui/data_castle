package cn.edu.swpu.cins.data_castle.utils;


public class RedisKey {

    private static String DATACASTLE_REGISTER = "USER_REGISTER";
    private static String SPLIT = ":";
    private static String DATACASTLE_LOGIN = "USER_LOGIN";

    public static String getDatacastleRegister(String mail) {
        return DATACASTLE_REGISTER + SPLIT + mail;
    }
    public static String getDatacastleLogin(String mail) {
        return DATACASTLE_LOGIN + SPLIT + mail;
    }
}
