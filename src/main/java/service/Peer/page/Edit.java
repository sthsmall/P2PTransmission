package service.Peer.page;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import utils.PeerMG;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;

public class Edit extends JFrame {

	private final JPanel contentPane;
	private final JLabel lblNewLabel;
	private final JTextField newUsernameField;
	private final JLabel lblNewLabel_1;
	private final JPasswordField newPwField;
	private final JLabel lblNewLabel_2;
	private final JPasswordField newPeAckField;
	private final JLabel usernameWarning;
	private final JLabel newPwWaring1;
	private final JLabel newPwWaring2;
	private final JButton updateButton;

	public JLabel getUsernameWarning() {
		return usernameWarning;
	}

	public JLabel getNewPwWaring1() {
		return newPwWaring1;
	}

	public JLabel getNewPwWaring2() {
		return newPwWaring2;
	}

	public JTextField getNewUsernameField() {
		return newUsernameField;
	}

	public JPasswordField getNewPwField() {
		return newPwField;
	}

	public JPasswordField getNewPeAckField() {
		return newPeAckField;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit frame = PeerMG.getInstance().getEdit();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Edit() {
		//设置界面
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLocationRelativeTo(null);
		FlatLightLaf.setup();
		//设置窗口大小不可变
		setResizable(false);
		setTitle("更改界面");
		//设置关闭方式，防止关闭后整个程序退出
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 483, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//窗口居中显示
		setLocationRelativeTo(contentPane);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setBounds(19, 26, 76, 32);
		contentPane.add(lblNewLabel);

		newUsernameField = new JTextField();
		newUsernameField.setEditable(false);
		newUsernameField.setBounds(118, 27, 293, 32);
		contentPane.add(newUsernameField);
		newUsernameField.setColumns(10);

		lblNewLabel_1 = new JLabel("新密码：");
		lblNewLabel_1.setBounds(19, 92, 88, 32);
		contentPane.add(lblNewLabel_1);

		newPwField = new JPasswordField();
		newPwField.setEchoChar('*');
		newPwField.setColumns(10);
		newPwField.setBounds(118, 93, 293, 32);
		contentPane.add(newPwField);

		lblNewLabel_2 = new JLabel("确认密码：");
		lblNewLabel_2.setBounds(19, 155, 100, 32);
		contentPane.add(lblNewLabel_2);

		newPeAckField = new JPasswordField();
		newPeAckField.setEchoChar('*');
		newPeAckField.setColumns(10);
		newPeAckField.setBounds(118, 156, 293, 32);
		contentPane.add(newPeAckField);


		usernameWarning = new JLabel("");
		usernameWarning.setFont(new Font("宋体", Font.PLAIN, 10));
		usernameWarning.setForeground(Color.RED);
		usernameWarning.setBounds(118, 68, 88, 15);
		contentPane.add(usernameWarning);

		newPwWaring1 = new JLabel("");
		newPwWaring1.setFont(new Font("宋体", Font.PLAIN, 11));
		newPwWaring1.setForeground(Color.RED);
		newPwWaring1.setBounds(118, 135, 193, 15);
		contentPane.add(newPwWaring1);

		newPwWaring2 = new JLabel("");
		newPwWaring2.setFont(new Font("宋体", Font.PLAIN, 11));
		newPwWaring2.setForeground(Color.RED);
		newPwWaring2.setBounds(118, 198, 130, 15);
		contentPane.add(newPwWaring2);

		updateButton = new JButton("更改");
		updateButton.addActionListener(new UpdateButtonActionListener());
		updateButton.setBounds(118, 223, 293, 32);
		contentPane.add(updateButton);

		//添加显示密码图标按钮
		JButton viewBtn1 = new JButton("view ");
		//添加隐藏密码图标按钮
		JButton viewHideBtn1 = new JButton("hide ");

		//添加显示密码图标按钮
		JButton viewBtn2 = new JButton("view ");
		//添加隐藏密码图标按钮
		JButton viewHideBtn2 = new JButton("hide ");

		newPwField.putClientProperty("JTextField.trailingComponent", viewBtn1);
		newPeAckField.putClientProperty("JTextField.trailingComponent", viewBtn2);

		viewBtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newPwField.putClientProperty("JTextField.trailingComponent", viewHideBtn1);//设置隐藏按钮显示，未使用FlatLightLaf则不需要
				newPwField.setEchoChar((char) 0);//设置密码显示
			}
		});

		//给隐藏密码图标绑定单击事件
		viewHideBtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newPwField.putClientProperty("JTextField.trailingComponent", viewBtn1);//设置显示按钮显示，未使用FlatLightLaf则不需要
				newPwField.setEchoChar('*');//设置密码隐藏
			}
		});
		viewBtn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newPeAckField.putClientProperty("JTextField.trailingComponent", viewHideBtn2);//设置隐藏按钮显示，未使用FlatLightLaf则不需要
				newPeAckField.setEchoChar((char) 0);//设置密码显示
			}
		});

		//给隐藏密码图标绑定单击事件
		viewHideBtn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newPeAckField.putClientProperty("JTextField.trailingComponent", viewBtn2);//设置显示按钮显示，未使用FlatLightLaf则不需要
				newPeAckField.setEchoChar('*');//设置密码隐藏
			}
		});

	}
	//更改提交按钮
	private class UpdateButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			{
				//获取用户名
				String username = newUsernameField.getText();
				//获取密码
				String password = newPwField.getText();
				//获取确认密码
				String password2 = newPeAckField.getText();
				//判断用户名是否为空
				if (username == null || "".equals(username.trim())) {
					usernameWarning.setText("用户名不能为空");
					return;
				}
				//判断密码是否为空
				if (password == null || "".equals(password.trim())) {
					newPwWaring1.setText("密码不能为空");
					return;
				}
				//判断确认密码是否为空
				if (password2 == null || "".equals(password2.trim())) {
					newPwWaring2.setText("确认密码不能为空");
					return;
				}
				//判断两次密码是否一致
				if (PeerMG.getInstance().checkPassword(password, password2, false)) {
					int res = JOptionPane.showConfirmDialog(null, "是否确认提交更改", "确认框",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (res == JOptionPane.YES_OPTION){
						//弹出提示框
						JOptionPane.showMessageDialog(null, "更改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
						//输入一致，提交更改
						PeerMG.getInstance().update(username, password);
					}
				}
			}
		}
	}
}
