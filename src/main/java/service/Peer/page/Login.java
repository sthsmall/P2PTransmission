package service.Peer.page;


import com.formdev.flatlaf.FlatLightLaf;
import utils.PeerMG;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

	static Clip clip;

	public static void playMusic() {
		try {
			//这里面放 绝对路径，音频必须是wav格式，用音频转换软件 把mp3 转成wav格式
			File musicPath = new File("C:\\Users\\时泽中\\Desktop\\1.wav");

			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-20.0f);//设置音量，范围为 -60.0f 到 6.0f
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {

			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

//
//	public void paint(Graphics g) {// g=画笔
//        try {
//           // 加载图片
//            BufferedImage bg = ImageIO.read(this.getClass().getResourceAsStream("1.jpg"));
//          // 绘制图片
//            g.drawImage(bg, 0, 0, null);
//           } catch (IOException e) {
//              System.out.println("加载图片失败");
//              }
//     }


	private JPanel contentPane;

	private JPasswordField Pw;
	private JTextField ID2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel register;
	private JLabel warning1;
	private JLabel warning2;

	public JTextField getPw() {
		return Pw;
	}

	public JTextField getID2() {
		return ID2;
	}

	public JLabel getWarning1() {
		return warning1;
	}

	public JLabel getWarning2() {
		return warning2;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			try {
				Login login = PeerMG.getInstance().getLogin();
				login.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		//设置界面
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		FlatLightLaf.setup();
		setLocationRelativeTo(null);
		//设置窗口大小不可变
		setResizable(false);
		setTitle("登录界面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 716, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//窗口居中显示
		setLocationRelativeTo(contentPane);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(10, 10, 669, 374);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton Log = new JButton("\u767B\u5F55");
		Log.setFont(new Font("宋体", Font.PLAIN, 16));
		Log.setBounds(155, 257, 424, 50);
		panel.add(Log);
		Log.addActionListener(new LoginActionListener());

		lblNewLabel_2 = new JLabel("P2P文件传输系统登录界面");
		lblNewLabel_2.setBounds(190, 10, 373, 58);
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 30));
		panel.add(lblNewLabel_2);

		lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setBounds(81, 67, 64, 62);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));

		ID2 = new JTextField();
		ID2.setToolTipText("");
		ID2.setFont(new Font("宋体", Font.PLAIN, 16));
		ID2.setBounds(155, 78, 424, 42);
		panel.add(ID2);
		ID2.setColumns(10);

		lblNewLabel_1 = new JLabel("密码：");
		lblNewLabel_1.setBounds(81, 158, 64, 58);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 16));

		Pw = new JPasswordField();
		Pw.setFont(new Font("宋体", Font.PLAIN, 16));
		Pw.setBounds(155, 167, 424, 42);
		panel.add(Pw);
		Pw.setColumns(10);
		Pw.setEchoChar('*');

		//添加显示密码图标按钮
		JButton viewBtn = new JButton("view ");
		//添加隐藏密码图标按钮
		JButton viewHideBtn = new JButton("hide ");

		Pw.putClientProperty("JTextField.trailingComponent", viewBtn);

		viewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pw.putClientProperty("JTextField.trailingComponent", viewHideBtn);//设置隐藏按钮显示，未使用FlatLightLaf则不需要
				Pw.setEchoChar((char) 0);//设置密码显示
			}
		});

		//给隐藏密码图标绑定单击事件
		viewHideBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pw.putClientProperty("JTextField.trailingComponent", viewBtn);//设置显示按钮显示，未使用FlatLightLaf则不需要
				Pw.setEchoChar('*');//设置密码隐藏
			}
		});

		register = new JLabel("没有账号？请注册");
		register.addMouseListener(new RegisterMouseListener());
		register.setForeground(Color.RED);
		register.setFont(new Font("SimSun", Font.PLAIN, 12));
		register.setBounds(557, 334, 102, 30);
		panel.add(register);

		warning1 = new JLabel("");
		warning1.setForeground(Color.RED);
		warning1.setBounds(155, 130, 278, 30);
		panel.add(warning1);

		warning2 = new JLabel("");
		warning2.setForeground(Color.RED);
		warning2.setBounds(155, 217, 278, 30);
		panel.add(warning2);
	}

	//登录按钮
	private class LoginActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = false;//用来判断输入是否为空
			String username = ID2.getText();
			String password = Pw.getText();
			//username和password不能为空
			if (username == null || "".equals(username.trim())) {
				warning1.setText("用户名不能为空");
				flag = true;
			}
			if (password == null || "".equals(password.trim())) {
				warning2.setText("密码不能为空");
				flag = true;
			}
			//若输入为空，直接返回，不再执行下面的代码
			if (flag) {
				return;
			}
			//输入不为空，登录
			PeerMG.getInstance().ToHome(username, password);
		}
	}
	//注册按钮
	private class RegisterMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			//跳转至注册界面
			PeerMG.getInstance().switchRegister();
		}
	}



}
