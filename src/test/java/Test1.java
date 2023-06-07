/*
@Subject:
@Author:叶兆威
@Time:2023/6/3
*/

import utils.PeerMG;
import dao.UserMapper;
import domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import utils.SqlSessionFactoryUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test1 {
    @Test
    public void test1() {
        File file = new File("E:\\81.png");
        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.add(file);
        PeerMG.getInstance().MakeTorrentFromFile(arrayList);

    }

    //模拟登录
    @Test
    public void login() throws IOException {

        String userName1 = "小叶";
        String password1 = "123";
        //1.获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

        //2.获取SqlSession,创建会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3.获取mapper接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //4.执行sql，登录
        User user = mapper.login(userName1,password1);
        if (user != null) {
            System.out.println("登录成功");
        }

        //5.释放资源
        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {
        ArrayList<File> files = new ArrayList<>();
        File source = new File("./Torrent");
        if(source.isDirectory()){
            source.list();
        }
        for (String s : source.list()) {
            System.out.println(s);
        }

    }
}
