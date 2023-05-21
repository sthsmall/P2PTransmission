package Tracker.Presentation;
import java.awt.*;

public class TrackerForm extends Thread{
    //用户界面
    private Frame frame = new Frame("Tracker");
    //用户界面的宽度
    private int width = 800;
    //用户界面的高度
    private int height = 600;
    //用户界面的位置
    private int x = 300;
    private int y = 100;
    //用户界面的布局
    private BorderLayout borderLayout = new BorderLayout();
    //用户界面的菜单栏
    private MenuBar menuBar = new MenuBar();
    //用户界面的菜单
    private Menu menu = new Menu("File");
    //用户界面的菜单项
    private MenuItem menuItem = new MenuItem("Exit");
    //用户界面的列表
    private List list = new List();
    //用户界面的按钮
    private Button button = new Button("Start");
    //用户界面的文本框
    private TextField textField = new TextField();
    //用户界面的标签
    private Label label = new Label("Tracker");
    public TrackerForm(){

    }

    @Override
    public void run() {
        //设置用户界面的位置
        frame.setBounds(x, y, width, height);
        //设置用户界面的布局
        frame.setLayout(borderLayout);
        //设置用户界面的菜单栏
        frame.setMenuBar(menuBar);
        //设置用户界面的菜单
        menuBar.add(menu);
        //设置用户界面的菜单项
        menu.add(menuItem);
        //设置用户界面的列表
        frame.add(list, BorderLayout.CENTER);
        //设置用户界面的按钮
        frame.add(button, BorderLayout.SOUTH);
        //设置用户界面的文本框
        frame.add(textField, BorderLayout.NORTH);
        //设置用户界面的标签
        frame.add(label, BorderLayout.WEST);
        //设置用户界面的可见性
        frame.setVisible(true);
    }
}
