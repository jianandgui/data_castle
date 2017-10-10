/*
package cn.edu.swpu.cins.data_castle.service;

import cn.edu.swpu.cins.data_castle.dao.UserDao;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.service.Impl.UserServiceImpl;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplService {

    @Mock
    JedisAdapter jedisAdapter;
    @Mock
    UserDao userDao;
    @Mock
    MailFormatService formatService;
    @Mock
    MailService mailService;
    @Mock
    URLCoderService urlCoderService;
    @Mock
    PasswordEncoderService passwordEncoderService;
    private UserService service;


    @Before
    public void setUp() {
        service = new UserServiceImpl(userDao, mailService, jedisAdapter, formatService, urlCoderService, passwordEncoderService);
    }

    @Test
    public void test_userLogin_success() {

        UserInfo user = mock(UserInfo.class);
        when(userDao.getUser(anyString())).thenReturn(user);

    }


}
*/
