package dao;

import domain.User;
import org.apache.ibatis.annotations.Param;


//mapper实体类
public interface UserMapper {
    //登录
    User login(@Param("userName") String userName,@Param("password") String password);

    //注册
    void register(User user);

    //修改积分
    void updateScore(@Param("userName") String userName,@Param("score") String score);

    //查询积分
    String selectScore(@Param("userName") String userName);

    //查询用户是否存在
    User selectUser(@Param("userName") String userName);
}
