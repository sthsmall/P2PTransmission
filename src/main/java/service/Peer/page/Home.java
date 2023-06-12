package service.Peer.page;

import domain.Torrent;
import utils.PeerMG;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;

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
    private JScrollPane scrollPane;
    private DefaultListModel<String> defaultListModel;
    private String folderPath = "./src/Download";

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
                PeerMG.getInstance().init();
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
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(269, 152, 653, 374);
        contentPane.add(scrollPane);

        defaultListModel = new DefaultListModel<String>();
        JList list = new JList(defaultListModel);
        scrollPane.setViewportView(list);



        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "\u5DE5\u5177", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(10, 54, 924, 86);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JButton Delete = new JButton("\u5220\u9664");
        Delete.setFont(new Font("宋体", Font.PLAIN, 12));
        Delete.setBounds(223, 26, 109, 50);
        panel_1.add(Delete);
        //删除
        Delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton Open = new JButton("\u6253\u5F00\u76EE\u5F55");
        Open.setFont(new Font("宋体", Font.PLAIN, 12));
        Open.setBounds(330, 26, 109, 50);
        panel_1.add(Open);

        JButton Dl = new JButton("\u5F00\u59CB\u4E0B\u8F7D");
        Dl.setFont(new Font("宋体", Font.PLAIN, 12));
        Dl.setBounds(10, 26, 109, 50);
        panel_1.add(Dl);

        JButton Stop = new JButton("\u505C\u6B62\u4E0B\u8F7D");
        Stop.setFont(new Font("宋体", Font.PLAIN, 12));
        Stop.setBounds(117, 26, 109, 50);
        panel_1.add(Stop);
        Stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        Dl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                defaultListModel.addElement("dfsadsa");

            }
        });
        //打开目录
        Open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    File folder = new File(folderPath);

                    if (folder.exists()) {
                        try {
                            desktop.open(folder);
                        } catch ( IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        System.out.println("Folder does not exist: " + folderPath);
                    }
                } else {
                    System.out.println("Desktop is not supported.");
                }


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
        downloadTorrent.addActionListener(new DownloadTorrentActionListener());
        file.add(downloadTorrent);

        linkDownload = new JMenuItem("磁链下载");
        linkDownload.addActionListener(new LinkDownloadActionListener());
        file.add(linkDownload);

        Help1 = new JMenu("帮助");

        menuBar.add(Help1);

        JMenuItem help = new JMenuItem("帮助");
        help.addActionListener(new HelpActionListener());
        Help1.add(help);

        JMenu relation = new JMenu("相关");


        menuBar.add(relation);

        JMenuItem project = new JMenuItem("项目");
        project.addActionListener(new ProjectActionListener());

        relation.add(project);

        JMenuItem team = new JMenuItem("团队");
        team.addActionListener(new TeamActionListener());
        relation.add(team);
    }

    public void addOneDownloadTask(String name) {
        String tname = "name";
        defaultListModel.addElement(tname);
    }

    private class MTorrentActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();

            // 设置文件选择器的初始目录
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setMultiSelectionEnabled(true);
            // 显示文件选择器对话框
            int result = fileChooser.showOpenDialog(Home.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                // 用户选择了一个文件
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            }
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

    //制作
    private class MakeTorrentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();

            // 设置文件选择器的初始目录
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.setMultiSelectionEnabled(true);
            // 显示文件选择器对话框
            int result = fileChooser.showOpenDialog(Home.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                // 用户选择了一个文件
                File[] selectedFile = fileChooser.getSelectedFiles();
                ArrayList<File> files = new ArrayList<>(Arrays.asList(selectedFile));
                PeerMG.getInstance().MakeTorrentFromFile(files,"随机");
                for (File file : selectedFile) {
                    System.out.println("Selected file: " + file.getAbsolutePath());
                }
            }
        }
    }


    //团队介绍
    private class TeamActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
            frame.setSize(500, 600);
            //窗口居中显示
            frame.setLocationRelativeTo(null);
            frame.setSize(500, 500);
            JTextArea ja = new JTextArea();// 注意类名别写错了。
            ja.setEditable(false);
            ja.setBounds(500, 500, 500, 700);
            ja.setLineWrap(true);        //激活自动换行功能
            ja.setWrapStyleWord(true);            // 激活断行不断字功能
            frame.getContentPane().add(ja);
            ja.setText("制作人员：\r\n"
                    + "叶兆威\r\n"
                    + "时泽中\r\n"
                    + "李茂源");
            // 参数 APPLICATION_MODAL：阻塞同一 Java 应用程序中的所有顶层窗口（它自己的子层次
            frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
            frame.setVisible(true);
        }
    }

    //项目
    private class ProjectActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
            frame.setSize(500, 600);
            frame.setLocationRelativeTo(null);
            frame.setSize(500, 500);
            JTextArea ja = new JTextArea();// 注意类名别写错了。
            ja.setEditable(false);
            ja.setBounds(500, 500, 500, 700);
            ja.setLineWrap(true);        //激活自动换行功能
            ja.setWrapStyleWord(true);            // 激活断行不断字功能
            frame.getContentPane().add(ja);
            //
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
    //帮助按钮
    private class HelpActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {


            JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
            frame.setSize(500, 600);
            frame.setLocationRelativeTo(null);
            JTextArea ja = new JTextArea();// 注意类名别写错了。
            ja.setEditable(false);
            ja.setBounds(500, 500, 500, 700);
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
    //通过Torrent文件下载
    private class DownloadTorrentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            System.out.println("makeTorrent");
            JFileChooser fileChooser = new JFileChooser();

            // 设置文件选择器的初始目录
            fileChooser.setCurrentDirectory(new File("./src/Torrents"));

            // 显示文件选择器对话框
            int result = fileChooser.showOpenDialog(Home.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                // 用户选择了一个文件
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                PeerMG.getInstance().getHashToFile().put(selectedFile.getName(), selectedFile);
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));

                    PeerMG.getInstance().getHashToTorrent().put(selectedFile.getName(), (Torrent) ois.readObject());
                    PeerMG.getInstance().AddDownLoad(selectedFile.getName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    //磁链下载
    private class LinkDownloadActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PeerMG.getInstance().openLink();
        }
    }
}
