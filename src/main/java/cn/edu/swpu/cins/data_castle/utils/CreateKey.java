package cn.edu.swpu.cins.data_castle.utils;

import cn.edu.swpu.cins.data_castle.enums.ExceptionEnum;
import cn.edu.swpu.cins.data_castle.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateKey {

    public String getKey(String type, String root) throws UserException {

        String key;
        switch (type) {
            case "register":
                key = RedisKey.getDatacastleRegister(root);
                break;
            case "login":
                key = RedisKey.getDatacastleLogin(root);
                break;

            default:
                throw new UserException(ExceptionEnum.INTERNAL_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return key;
    }
}
