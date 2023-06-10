package service.Peer.page;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class J4
{
	private Frame f;
	private MenuBar bar;
	private Menu fileMenu,subMenu;
	private MenuItem closeItem,subItem1,subItem2;
	private MenuItem openItem,saveItem;

	//定义打开和保存对话框
	private FileDialog openDia,saveDia;

	//设置文本区域来保存打开的数据
	private TextArea ta;

	private File file;

	J4()
	{
		init();
	}

	public void init()
	{
		f = new Frame("my window");
		f.setBounds(300,100,500,600);
		//f.setLayout(new FlowLayout());

		bar = new MenuBar();

		ta = new TextArea();

		fileMenu = new Menu("文件");
		subMenu = new Menu("子菜单");

		openItem = new MenuItem("打开");
		saveItem = new MenuItem("保存");
		subItem1 = new MenuItem("子条目1");
		subItem2 = new MenuItem("子条目2");
		closeItem = new MenuItem("退出");

		subMenu.add(subItem1);
		subMenu.add(subItem2);


		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(subMenu);
		fileMenu.add(closeItem);
		bar.add(fileMenu);

		f.setMenuBar(bar);

		//默认模式为 FileDialog.LOAD
		openDia = new FileDialog(f,"我的打开",FileDialog.LOAD);
		saveDia = new FileDialog(f,"我的保存",FileDialog.SAVE);

		f.add(ta);

		myEvent();

		f.setVisible(true);


	}

	private void myEvent()
	{
		saveItem.addActionListener(new ActionListener()
		{
			//设置保存文件的功能
			public void actionPerformed(ActionEvent e)
			{
				if(file == null)//文件不存在情况下 创建文件
				{
					saveDia.setVisible(true);
					String dirPath = saveDia.getDirectory();
					String fileName = saveDia.getFile();

					if(dirPath == null || fileName == null)
						return ;
					file = new File(dirPath,fileName);
				}

				try
				{
					BufferedWriter bufw = new BufferedWriter(new FileWriter(file));

					String text = ta.getText();

					bufw.write(text);

					bufw.close();
				}
				catch (IOException ex)
				{
					throw new RuntimeException("文件保存失败！");
				}
			}
		});

		openItem.addActionListener(new ActionListener()
		{
			//设置打开文件功能
			public void actionPerformed(ActionEvent e)
			{
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();//获取文件路径
				String fileName = openDia.getFile();//获取文件名称
				//System.out.println(dirPath +"++"+ fileName);

				//如果打开路径 或 目录为空 则返回空
				if(dirPath == null || fileName == null)
					return ;

				ta.setText("");//清空文本

				file = new File(dirPath,fileName);

				try
				{
					BufferedReader bufr = new BufferedReader(new FileReader(file));

					String line = null;

					while( (line = bufr.readLine())!= null)
					{
						ta.append(line +"\r\n");
					}
					bufr.close();
				}
				catch (IOException ex)
				{
					throw new RuntimeException("文件读取失败！");
				}



			}
		});

		closeItem.addActionListener(new ActionListener()
		{
			//设置退出功能
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}

	public static void main(String []args)
	{
		new J4();
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}


}