package service.Peer.page;

import utils.PeerMG;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

//首页面

public class Home extends JFrame {


    private final JPanel contentPane;
    private final JLabel Score;
    private final JLabel ID;
    private final JLabel image;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu Help1;
    private JMenuItem makeTorrent;
    private JMenuItem downloadTorrent;
    private JMenuItem linkDownload;

    public JLabel getScore() {
        return Score;
    }

    public JLabel getID() {
        return ID;
    }

    public JLabel getImage() {
        return image;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Home home = PeerMG.getInstance().getHome();
                home.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Home() {
        setTitle("P2P文件传输系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 958, 587);
        //设置窗口大小不可变
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.text);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //窗口居中显示
        setLocationRelativeTo(contentPane);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("用户名：");
        lblNewLabel.setBounds(16, 326, 78, 34);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("积分：");
        lblNewLabel_1.setBounds(26, 370, 68, 34);
        contentPane.add(lblNewLabel_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(269, 152, 640, 372);
        contentPane.add(scrollPane);


        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "\u5DE5\u5177", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(10, 54, 924, 86);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JButton Delete = new JButton("\u5220\u9664");
        Delete.setFont(new Font("宋体", Font.PLAIN, 12));
        Delete.setBounds(574, 26, 109, 50);
        panel_1.add(Delete);
        Delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton Open = new JButton("\u6253\u5F00\u76EE\u5F55");
        Open.setFont(new Font("宋体", Font.PLAIN, 12));
        Open.setBounds(681, 26, 109, 50);
        panel_1.add(Open);

        JButton mTorrent = new JButton("\u5236\u4F5Ctorrent");
        mTorrent.setFont(new Font("宋体", Font.PLAIN, 12));
        mTorrent.setBounds(10, 26, 127, 50);
        panel_1.add(mTorrent);

        JButton dLfTorrent = new JButton("torrent\u4E0B\u8F7D");
        dLfTorrent.setFont(new Font("宋体", Font.PLAIN, 12));
        dLfTorrent.setBounds(129, 26, 127, 50);
        panel_1.add(dLfTorrent);

        JButton dLflink = new JButton("\u78C1\u94FE\u4E0B\u8F7D");
        dLflink.setFont(new Font("宋体", Font.PLAIN, 12));
        dLflink.setBounds(254, 26, 109, 50);
        panel_1.add(dLflink);

        JButton Dl = new JButton("\u5F00\u59CB\u4E0B\u8F7D");
        Dl.setFont(new Font("宋体", Font.PLAIN, 12));
        Dl.setBounds(361, 26, 109, 50);
        panel_1.add(Dl);

        JButton Stop = new JButton("\u505C\u6B62\u4E0B\u8F7D");
        Stop.setFont(new Font("宋体", Font.PLAIN, 12));
        Stop.setBounds(468, 26, 109, 50);
        panel_1.add(Stop);
        Stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        Dl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });
        dLfTorrent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        mTorrent.addActionListener(new MTorrentActionListener());
        Open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                OpenExplorer frame = new OpenExplorer();
                frame.setVisible(true);


            }

            private void setEnabled(boolean b) {
                // TODO Auto-generated method stub

            }
        });

        JButton Exit = new JButton("退出");
        Exit.addActionListener(new ExitActionListener());
        Exit.setBounds(16, 483, 197, 43);
        contentPane.add(Exit);

        ID = new JLabel("");
        ID.setBounds(94, 326, 165, 35);
        contentPane.add(ID);

        Score = new JLabel("");
        Score.setBounds(86, 369, 101, 35);
        contentPane.add(Score);

        JButton Edit = new JButton("修改信息");
        Edit.addActionListener(new EditActionListener());
        Edit.setBounds(16, 429, 197, 43);
        contentPane.add(Edit);

        // 加载图片
        ClassLoader classLoader = Home.class.getClassLoader();
        URL imageURl = classLoader.getResource("img.png");
        assert imageURl != null;
        ImageIcon imageIcon = new ImageIcon(imageURl);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        image = new JLabel(imageIcon);
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setBounds(10, 166, 249, 150);
        contentPane.add(image);

        menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 944, 44);
        contentPane.add(menuBar);

        file = new JMenu("文件");
        menuBar.add(file);

        makeTorrent = new JMenuItem("制作torrent");
        makeTorrent.addActionListener(new MakeTorrentActionListener());
        file.add(makeTorrent);

        downloadTorrent = new JMenuItem("torrent下载");
        file.add(downloadTorrent);

        linkDownload = new JMenuItem("磁链下载");
        file.add(linkDownload);

        Help1 = new JMenu("帮助");
        menuBar.add(Help1);

        JMenu relation = new JMenu("相关");


        menuBar.add(relation);

        JMenuItem project = new JMenuItem("项目");
        project.addActionListener(new ProjectActionListener());

        relation.add(project);

        JMenuItem team = new JMenuItem("团队");
        team.addActionListener(new TeamActionListener());
        relation.add(team);
    }

    private class MTorrentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }
    }

    //退出按钮
    private class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //跳转至登录界面
            PeerMG.getInstance().switchLogin(false);
        }
    }

    //编辑用户信息
    private class EditActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //获取用户名
            String username = ID.getText();
            //跳转至编辑界面
            PeerMG.getInstance().switchEdit(username);
        }
    }

    //制作torrent
    private class MakeTorrentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(11);
        }
    }


    //团队介绍
    private class TeamActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
            frame.setSize(500, 500);
            //窗口居中显示
            setLocationRelativeTo(contentPane);
            JTextArea ja = new JTextArea();// 注意类名别写错了。
            ja.setBounds(500, 500, 500, 500);
            ja.setLineWrap(true);        //激活自动换行功能
            ja.setWrapStyleWord(true);            // 激活断行不断字功能
            frame.getContentPane().add(ja);
            ja.setText("制作人员：\r\n"
                    + "叶兆威：\r\n"
                    + "时泽中：\r\n"
                    + "李茂源:");
            // 参数 APPLICATION_MODAL：阻塞同一 Java 应用程序中的所有顶层窗口（它自己的子层次
            frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
            frame.setVisible(true);



        }
    }

    //项目
    private class ProjectActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
            frame.setSize(500, 500);

            JTextArea ja = new JTextArea();// 注意类名别写错了。
            ja.setBounds(500, 500, 500, 500);
            ja.setLineWrap(true);        //激活自动换行功能
            ja.setWrapStyleWord(true);            // 激活断行不断字功能
            frame.getContentPane().add(ja);
            ja.setText("首先介绍一下该系统中的名词\r\n"
                    + "1.tracker：p2p网络中的服务节点，对P2P网络文件资源进行调配。\r\n"
                    + "2.torrent文件（种子文件）：种子文件中存有分享文件的元信息，包括大小，名称，和唯一的哈希值。\r\n"
                    + "3.磁力链接：包含了tracker的地址和tracker中相应的种子文件的哈希值。\r\n"
                    + "4.peer：有共同torrent文件的节点。\r\n"
                    + "\r\n"
                    + "\r\n"
                    + "//为提高性能，节点的文件将被分割成块，相应的块使用多线程进行传输，并且不同节点之间会尽量保证优先下载不同的分块以此来保证性能。//(不一定有)\r\n"
                    + "\r\n"
                    + "系统共享的原理如下\r\n"
                    + "1.先有分享者发布torrent文件到tracker服务器\r\n"
                    + "2.tracker服务器维护一个torrent文件和磁力链接的映射\r\n"
                    + "3.由下载者向tracker服务器发送磁力链接，返回peer节点信息和torrent文件\r\n"
                    + "4.对peer节点建立链接，根据torrent文件进行下载\r\n"
                    + "");


            // 参数 APPLICATION_MODAL：阻塞同一 Java 应用程序中的所有顶层窗口（它自己的子层次
            frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
            frame.setVisible(true);
        }
    }

}
