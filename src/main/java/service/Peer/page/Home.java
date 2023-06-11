package service.Peer.page;

import utils.PeerMG;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.border.TitledBorder;

//首页面

public class Home extends JFrame {


    private JPanel contentPane;
    private JTextField txtPp;
    private JLabel Score;
    private JLabel ID;
    private JLabel image;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 958, 687);
        //设置窗口大小不可变
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.text);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //窗口居中显示
        setLocationRelativeTo(contentPane);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtPp = new JTextField();
        txtPp.setEditable(false);
        txtPp.setBackground(SystemColor.textHighlight);
        txtPp.setText("P2P文件传输系统");
        txtPp.setBounds(10, 9, 907, 35);
        contentPane.add(txtPp);
        txtPp.setColumns(10);

        JLabel lblNewLabel = new JLabel("用户名：");
        lblNewLabel.setBounds(24, 408, 78, 34);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("积分：");
        lblNewLabel_1.setBounds(34, 452, 68, 34);
        contentPane.add(lblNewLabel_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(277, 234, 640, 372);
        contentPane.add(scrollPane);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u83DC\u5355", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(10, 54, 907, 77);
        contentPane.add(panel);
        panel.setLayout(null);

        JButton Help = new JButton("\u5E2E\u52A9");
        Help.setBounds(117, 19, 127, 48);
        panel.add(Help);

        JButton Relation = new JButton("\u76F8\u5173");
        Relation.setBounds(239, 19, 127, 48);
        panel.add(Relation);

        JButton File = new JButton("文件");
        File.setBounds(10, 19, 109, 48);
        panel.add(File);
        Relation.addActionListener(new ActionListener() {
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
        });
        Help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
                frame.setSize(500, 500);
                JTextArea jt = new JTextArea();// 注意类名别写错了。
                jt.setBounds(500, 500, 500, 500);

                jt.setLineWrap(true);        //激活自动换行功能
                jt.setWrapStyleWord(true);            // 激活断行不断字功能
                frame.getContentPane().add(jt);
                jt.setText("项目简介：\r\n"
                        + "P2P，即点对点网络（ Peer - to - Peer Network )，是指一种基于去中心化的计算机网络模型，它不依赖于传统的服务器或中心节点，而是将节点之间的计算能力、存储空间、带宽等资源进行共享和协作，实现分布式计算、数据传输和共享等功能。\r\n"
                        + "众所周知，传统的 C / S 模式的文件传输中，任意一个客户端想要请求一个文件都需要向服务端发起请求然后由服务器返回文件资源。服务器频繁大量地向大量客户端接收和发送数据时，服务器负载就会提高，而文件传输又恰好是一个需要大量带宽资源的服务，这就使服务器的上载带宽成为整个系统的主要瓶颈，想要解决这个瓶颈就需要增加网络设备的性能。这必将对服务提供者带来相应的成本提高，类似的场景有许多像 CDN 和一些大型的视频网站也都使用了p2p的技术进行加速。\r\n"
                        + "本系统利用了P2P技术，将传统的客户端分布式的文件资源能够实现相互传输、相互补全，相应的流量也转移了用户与用户之间。从而解决了传统的 C / S 模式中服务端带宽受限的瓶颈。\r\n"
                        + "考虑到系统网络的节点个数局限性，本系统采用了中心化的P2P类型，即由一台或多台服务器节点设备（我们称之为 tracker ）对P2P网络文件资源进行调配，虽然采用了服务端，但是由于服务器只接受简单且数据量少的文件请求信息，大流量的文件传输仍由各个P2P节点之间进行从而减少服务器负担。\r\n"
                        + "");


                // 参数 APPLICATION_MODAL：阻塞同一 Java 应用程序中的所有顶层窗口（它自己的子层次
                frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
                frame.setVisible(true);

            }
        });

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "\u5DE5\u5177", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(10, 138, 907, 86);
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

        JButton Copy = new JButton("\u6587\u4EF6\u62F7\u8D1D");
        Copy.setFont(new Font("宋体", Font.PLAIN, 12));
        Copy.setBounds(788, 26, 109, 50);
        panel_1.add(Copy);

        JButton Stop = new JButton("\u505C\u6B62\u4E0B\u8F7D");
        Stop.setFont(new Font("宋体", Font.PLAIN, 12));
        Stop.setBounds(468, 26, 109, 50);
        panel_1.add(Stop);
        Stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        Copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                CopyFile frame = new CopyFile();
                frame.setVisible(true);
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
        Exit.setBounds(24, 565, 197, 43);
        contentPane.add(Exit);

        ID = new JLabel("");
        ID.setBounds(102, 408, 165, 35);
        contentPane.add(ID);

        Score = new JLabel("");
        Score.setBounds(94, 451, 101, 35);
        contentPane.add(Score);

        JButton Edit = new JButton("修改信息");
        Edit.addActionListener(new EditActionListener());
        Edit.setBounds(24, 511, 197, 43);
        contentPane.add(Edit);

        // 加载图片
        ClassLoader classLoader = Home.class.getClassLoader();
        URL imageURl = classLoader.getResource("img.png");
        assert imageURl != null;
        ImageIcon imageIcon = new ImageIcon(imageURl);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        image = new JLabel(imageIcon);
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setBounds(18, 248, 249, 150);
        contentPane.add(image);
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
}
