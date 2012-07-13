package com.yahoo.platform.yui.compressor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class Main extends JFrame {
	private static final Logger logger = Logger.getLogger(Main.class);
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		logger.info("Yui Compress UI started...");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setTitle("YUI Compress,by Xia,hsiayong@gmail.com");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "JS/CSS\u538B\u7F29",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("JS/CSS压缩", null, panel, null);
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][]"));

		JLabel lblNewLabel = new JLabel("源文件目录");
		panel.add(lblNewLabel, "cell 0 1,alignx trailing");

		textField = new JTextField();
		textField.setEditable(false);
		panel.add(textField, "flowx,cell 1 1,growx");
		textField.setColumns(10);

		JButton btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = getFile();
				textField.setText(path);
				textField_1.setText(path + "/../bak");
			}
		});
		panel.add(btnNewButton, "cell 1 1");

		JLabel lblNewLabel_1 = new JLabel("目标目录");
		panel.add(lblNewLabel_1, "cell 0 2,alignx trailing");

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panel.add(textField_1, "flowx,cell 1 2,growx");
		textField_1.setColumns(10);

		JButton btnNewButton_1 = new JButton("...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_1.setText(getFile());
			}
		});
		panel.add(btnNewButton_1, "cell 1 2");

		final JCheckBox chckbxNewCheckBox = new JCheckBox("创建对应的目录结构");
		chckbxNewCheckBox.setSelected(true);
		panel.add(chckbxNewCheckBox, "cell 1 3");

		JButton btnNewButton_2 = new JButton("压缩JS/CSS");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String srcDir = textField.getText();
				String destDir = textField_1.getText();
				if (srcDir == null || "".equals(srcDir) || destDir == null
						|| "".equals(destDir)) {
					JOptionPane.showMessageDialog(Main.this, "目录为必选项");
					return;
				}
				try {
					File file = new File(destDir);
					if (!file.exists())
						FileUtils.forceMkdir(file);
					YUICompressor.process(srcDir, destDir,
							chckbxNewCheckBox.isSelected());
					JOptionPane.showMessageDialog(Main.this, "生成任务执行完成!");
				} catch (Exception e1) {
					logger.error("出错了："+e1.getMessage(),e1);
					JOptionPane.showMessageDialog(Main.this,
							"出错了：" + e1.getMessage());
				}
			}
		});
		panel.add(btnNewButton_2, "cell 1 5");

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("JS/CSS合并", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[][][][][][][]", "[][][][]"));

		JLabel lblNewLabel_2 = new JLabel("建设中...");
		panel_1.add(lblNewLabel_2, "cell 6 3");

		center(this);
	}

	private String getFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText("确定");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只选择目录
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return "";
	}

	public static void center(Window window) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Dimension windowSize = window.getSize();

		if (windowSize.height > screenSize.height)

			windowSize.height = screenSize.height;

		if (windowSize.width > screenSize.width)

			windowSize.width = screenSize.width;

		window.setLocation((screenSize.width - windowSize.width) / 2,

		(screenSize.height - windowSize.height) / 2);

	}

}
