package domain;

import lombok.Data;

/*
@Subject:用户实体类
@Author:叶兆威
@Time:2023/6/4
*/
@Data
public class User {
    private String userName;//用户名
    private String password;//密码
    private String score;//积分

}
