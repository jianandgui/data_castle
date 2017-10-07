package cn.edu.swpu.cins.data_castle.dao;

import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void addUser() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("Mr.yang").setMail("test").setPwd("test");
        System.out.println(userDao.addUser(userInfo));

    }

    @Test
    public void updateUser() throws Exception {
//        System.out.println(userDao.updateUserEnable("test"));
        String mail = "879604213@qq.com";
        int flag = 789;
        userDao.updateUser(mail, flag);
    }

}