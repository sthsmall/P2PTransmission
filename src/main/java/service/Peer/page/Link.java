package service.Peer.page;

import service.Peer.FileTransmission.ASK.ASKTrackerForTorrent;
import utils.PeerMG;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Link extends JFrame {

	public JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Link frame = PeerMG.getInstance().getLink();
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
	public Link() {

		//设置界面
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置窗口大小不可变
		setResizable(false);
		//窗口居中显示
		setLocationRelativeTo(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("请输入磁链");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(34, 31, 226, 26);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(34, 67, 498, 86);
		contentPane.add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 16));
		scrollPane.setViewportView(textArea);

		JButton btnNewButton = new JButton("确认");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String link = textArea.getText();
				if(link == null || link.trim().equals("")){
					JOptionPane.showMessageDialog(null, "请输入磁链", "提示", JOptionPane.ERROR_MESSAGE);
					return;
				}
				new ASKTrackerForTorrent(textArea.getText().trim()).run();
				PeerMG.getInstance().AddDownLoad(textArea.getText().trim());
				PeerMG.getInstance().closeLink();
			}

		});
		btnNewButton.setBounds(401, 170, 139, 42);
		contentPane.add(btnNewButton);
		

	}

}
