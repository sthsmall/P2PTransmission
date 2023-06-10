package service.Peer.page;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class H2 extends JFrame {

    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField sourceField;
    private JLabel lblNewLabel_1;
    private JTextField targetField;
    private JProgressBar progressBar;
    private JButton copyButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    H2 frame = new H2();
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
    public H2() {
        //设置Windows风格界面
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 789, 272);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNewLabel = new JLabel("原文件路径");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        lblNewLabel.setBounds(27, 24, 85, 26);
        contentPane.add(lblNewLabel);

        sourceField = new JTextField();
        sourceField.setFont(new Font("宋体", Font.PLAIN, 14));
        sourceField.setBounds(122, 22, 631, 32);
        contentPane.add(sourceField);
        sourceField.setColumns(10);

        lblNewLabel_1 = new JLabel("目标文件路径");
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(27, 78, 85, 26);
        contentPane.add(lblNewLabel_1);

        targetField = new JTextField();
        targetField.setFont(new Font("宋体", Font.PLAIN, 14));
        targetField.setColumns(10);
        targetField.setBounds(122, 76, 631, 32);
        contentPane.add(targetField);

        progressBar = new JProgressBar();
        progressBar.setBounds(27, 143, 726, 26);
        contentPane.add(progressBar);

        copyButton = new JButton("开始复制");
        copyButton.addActionListener(new CopyButtonActionListener());
        copyButton.setFont(new Font("宋体", Font.PLAIN, 14));
        copyButton.setBounds(27, 190, 119, 32);
        contentPane.add(copyButton);
    }

    private class CopyButtonActionListener implements ActionListener {
        //采用字节缓冲流，可以处理任何类型的文件
        BufferedInputStream bis;
        BufferedOutputStream bos;

        public void actionPerformed(ActionEvent e) {
            try {
                copy();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Thread thread = new Thread(new Progress());
            thread.start();

        }
        public void copy() throws IOException{
            //获取源文件路径和目标文件路径
            String source = sourceField.getText();
            String target = targetField.getText();
            //创建输入流和输出流
            bis = new BufferedInputStream(new FileInputStream(source));
            bos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes,0,len);
                bos.flush();
            }
            bis.close();
            bos.close();
        }

        class Progress implements Runnable{
            @Override
            public void run() {
                for(int i = 0; i <= 100; i++){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    progressBar.setValue(i);
                }
                //弹出提示框
                JOptionPane.showMessageDialog(null, "复制成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}