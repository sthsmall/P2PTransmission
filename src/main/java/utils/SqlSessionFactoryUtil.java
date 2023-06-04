package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/*
@Subject:sql会话工厂工具类
@Author:叶兆威
@Time:2023/6/4
*/
public class SqlSessionFactoryUtil {
    //采用单例模式创建SqlSessionFactory
    //mybatis配置文件
    private static final String resource = "mybatis-config.xml";
    private static InputStream inputStream;
    static {
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //创建SqlSessionFactory(不可改变)
    private static final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    private SqlSessionFactoryUtil() {
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
