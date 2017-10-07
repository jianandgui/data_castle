package cn.edu.swpu.cins.data_castle.dao;

import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    int addUser(UserInfo userInfo);
    int updateUser(@Param("mail") String mail, @Param("flag") int flag);
    UserInfo getUser(String mail);
}
