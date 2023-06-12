package service.Peer.page;

import com.formdev.flatlaf.FlatLightLaf;
import utils.PeerMG;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Register extends JFrame {


    private final JPanel contentPane;
    private final JTextField ID;
    private final JPasswordField M1;
    private final JPasswordField M2;
    private static Register frame;
    private final JLabel userWarning;
    private final JLabel switichLogin;
    private final JLabel passwordWarning;
    private final JButton viewBtn1;
    private final JButton viewHideBtn1;
    private final JButton viewBtn2;
    private final JButton viewHideBtn2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Register register = PeerMG.getInstance().getRegister();

                register.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

//
//	public void paint(Graphics g) {// g=画笔
//        try {
//            // 加载图片
//            BufferedImage bg = ImageIO.read(this.getClass().getResourceAsStream("1.jpg"));
//            // 绘制图片
//
//            g.drawImage(bg, 0, 0, null);
//            } catch (IOException e) {
//                System.out.println("加载图片失败");
//              }
//      }


    /**
     * Create the frame.
     */
    public Register() {

        //设置界面
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置窗口大小不可变
        setResizable(false);
        setLocationRelativeTo(null);
        FlatLightLaf.setup();
        setTitle("注册界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 685, 504);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //窗口居中显示
        setLocationRelativeTo(contentPane);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("P2P文件传输系统注册界面");
        lblNewLabel.setForeground(Color.GRAY);
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        lblNewLabel.setBounds(150, 21, 424, 63);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("\u8BF7\u8F93\u5165\u7528\u6237\u540D\uFF1A");
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(55, 94, 140, 63);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("\u8BF7\u8F93\u5165\u5BC6\u7801\uFF1A");
        lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel_2.setBounds(55, 167, 129, 63);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("\u518D\u6B21\u8F93\u5165\u5BC6\u7801\uFF1A");
        lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel_3.setBounds(55, 242, 112, 63);
        contentPane.add(lblNewLabel_3);

        JButton Register = new JButton("\u7ACB\u5373\u6CE8\u518C");
        Register.addActionListener(new RegisterActionListener());
        Register.setForeground(Color.BLACK);

        Register.setFont(new Font("宋体", Font.PLAIN, 18));
        Register.setBounds(171, 335, 399, 47);
        contentPane.add(Register);

        ID = new JTextField();
        ID.addFocusListener(new IDFocusListener());
        ID.setFont(new Font("宋体", Font.PLAIN, 16));
        ID.setBounds(171, 108, 399, 35);
        contentPane.add(ID);
        ID.setColumns(10);

        M1 = new JPasswordField();
        M1.setEchoChar('*');

        M1.setFont(new Font("宋体", Font.PLAIN, 16));
        M1.setBounds(171, 181, 399, 35);
        contentPane.add(M1);
        M1.setColumns(10);

        M2 = new JPasswordField();
        M2.setEchoChar('*');
        M2.setFont(new Font("宋体", Font.PLAIN, 16));
        M2.setBounds(171, 256, 399, 35);
        contentPane.add(M2);
        M2.setColumns(10);

        userWarning = new JLabel("  ");
        userWarning.setForeground(Color.RED);
        userWarning.setBounds(171, 148, 157, 24);
        contentPane.add(userWarning);

        switichLogin = new JLabel("已有账号？登录");
        switichLogin.addMouseListener(new SwitichLoginMouseListener());
        switichLogin.setForeground(Color.RED);
        switichLogin.setBounds(479, 417, 169, 24);
        contentPane.add(switichLogin);

        passwordWarning = new JLabel("");
        passwordWarning.setForeground(Color.RED);
        passwordWarning.setBounds(171, 301, 157, 24);
        contentPane.add(passwordWarning);


        //添加显示密码图标按钮
        viewBtn1 = new JButton("view ");
        //添加隐藏密码图标按钮
        viewHideBtn1 = new JButton("hide ");

        //添加显示密码图标按钮
        viewBtn2 = new JButton("view ");
        //添加隐藏密码图标按钮
        viewHideBtn2 = new JButton("hide ");

        M1.putClientProperty("JTextField.trailingComponent", viewBtn1);
        M2.putClientProperty("JTextField.trailingComponent", viewBtn2);

        viewBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                M1.putClientProperty("JTextField.trailingComponent", viewHideBtn1);//设置隐藏按钮显示，未使用FlatLightLaf则不需要
                M1.setEchoChar((char) 0);//设置密码显示
            }
        });

        //给隐藏密码图标绑定单击事件
        viewHideBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                M1.putClientProperty("JTextField.trailingComponent", viewBtn1);//设置显示按钮显示，未使用FlatLightLaf则不需要
                M1.setEchoChar('*');//设置密码隐藏
            }
        });
        viewBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                M2.putClientProperty("JTextField.trailingComponent", viewHideBtn2);//设置隐藏按钮显示，未使用FlatLightLaf则不需要
                M2.setEchoChar((char) 0);//设置密码显示
            }
        });

        //给隐藏密码图标绑定单击事件
        viewHideBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                M2.putClientProperty("JTextField.trailingComponent", viewBtn2);//设置显示按钮显示，未使用FlatLightLaf则不需要
                M2.setEchoChar('*');//设置密码隐藏
            }
        });

    }

    public JTextField getID() {
        return ID;
    }

    public JTextField getM1() {
        return M1;
    }

    public JTextField getM2() {
        return M2;
    }

    public JLabel getUserWarning() {
        return userWarning;
    }

    public JLabel getPasswordWarning() {
        return passwordWarning;
    }


    //用户名监听器
    private class IDFocusListener extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            //获取用户名
            String username = ID.getText();
            //判断用户名是否重复
            if (username != null && !"".equals(username.trim())) {
                PeerMG.getInstance().checkUsername(username);
            }
        }
    }

    //切换登录监界面
    private class SwitichLoginMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            PeerMG.getInstance().switchLogin(true);
        }
    }

    //注册
    private class RegisterActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //获取用户名
            String username = ID.getText();
            //获取密码
            String password = M1.getText();
            //获取确认密码
            String password2 = M2.getText();
            //判断用户名是否为空
            if (username == null || "".equals(username.trim())) {
                userWarning.setText("用户名不能为空");
                return;
            }
            //判断密码是否为空
            if (password == null || "".equals(password.trim())) {
                passwordWarning.setText("密码不能为空");
                return;
            }
            //判断确认密码是否为空
            if (password2 == null || "".equals(password2.trim())) {
                passwordWarning.setText("确认密码不能为空");
                return;
            }
            //判断两次密码是否一致
            if (PeerMG.getInstance().checkPassword(password, password2, true) && "".equals(userWarning.getText())) {
                //输入一致，提交注册
                int res = JOptionPane.showConfirmDialog(null, "是否确认提交注册", "确认框",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(res == JOptionPane.YES_OPTION){
                    //弹出提示框
                    JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    PeerMG.getInstance().register(username, password);
                }
            }
        }
    }
}
