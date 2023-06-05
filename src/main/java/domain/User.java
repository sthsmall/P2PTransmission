package domain;

import lombok.Data;

//用户实体类
@Data
public class User {
    private String userName;//用户名
    private String password;//密码
    private String score;//积分
}
