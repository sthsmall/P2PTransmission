package service.Peer.page;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ShowTime extends JFrame {

    private final JPanel contentPane;
    private final JPanel panel;
    private final JButton startButton;
    private final JButton stopButton;
    private final JLabel timeLabel;
    private boolean flag = true;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShowTime frame = new ShowTime();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ShowTime() {
        //设置Windows风格界面
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置关闭方式，防止关闭后整个程序退出
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 573, 258);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //窗口居中显示
        setLocationRelativeTo(contentPane);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "\u5F53\u524D\u65F6\u95F4", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(10, 10, 539, 117);
        contentPane.add(panel);
        panel.setLayout(null);

        timeLabel = new JLabel("");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("宋体", Font.BOLD, 30));
        timeLabel.setBounds(129, 22, 292, 64);
        panel.add(timeLabel);

        startButton = new JButton("开始计时");
        startButton.addActionListener(new StartButtonActionListener());
        startButton.setFont(new Font("宋体", Font.PLAIN, 14));
        startButton.setBounds(59, 150, 149, 42);
        contentPane.add(startButton);

        stopButton = new JButton("停止计时");
        stopButton.addActionListener(new StopButtonActionListener());
        stopButton.setFont(new Font("宋体", Font.PLAIN, 14));
        stopButton.setBounds(322, 150, 149, 42);
        contentPane.add(stopButton);
    }
    private class StartButtonActionListener implements ActionListener {
        Date date;
        public void actionPerformed(ActionEvent e) {
            new Thread(new Time()).start();
        }
        class Time implements Runnable{

            @Override
            public void run() {
                while (flag) {
                    date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    String time = simpleDateFormat.format(date);
                    timeLabel.setText(time);
                    //间隔1s
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    private class StopButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //将flag改为false，计时就会停止
            flag = false;
        }
    }
}