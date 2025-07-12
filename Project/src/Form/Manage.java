package Form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Other.Utils;

public class Manage extends JFrame {
	public Component horizontalStrut;
	public Component horizontalStrut_1;
	public Component verticalStrut;
	public Component verticalStrut_1;
	public JPanel panel;
	public JButton b1;
	public JButton b2;
	public JButton b3;
	public JButton b4;
	public JButton b5;
	public static void main(String[] args) {
		new Manage().setVisible(true);
	}

	public Manage() {
		
		horizontalStrut = Box.createHorizontalStrut(20);
		getContentPane().add(horizontalStrut, BorderLayout.WEST);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		getContentPane().add(horizontalStrut_1, BorderLayout.EAST);
		
		verticalStrut = Box.createVerticalStrut(20);
		getContentPane().add(verticalStrut, BorderLayout.SOUTH);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		getContentPane().add(verticalStrut_1, BorderLayout.NORTH);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 300));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(5, 1, 0, 5));
		
		b1 = new JButton("\uB85C\uADF8\uC544\uC6C3");
		b1.addActionListener(e -> {
			dispose();
			Utils.ok("로그아웃 되었습니다.");
			Login.no = 0;
			new Main().setVisible(true);
		});
		panel.add(b1);
		
		b2 = new JButton("\uC815\uBCF4\uC218\uC815");
		b2.addActionListener(e -> {
			dispose();
			new MyPage(1).setVisible(true);
		});
		panel.add(b2);
		
		b3 = new JButton("\uAD6C\uB9E4\uBAA9\uB85D");
		b3.addActionListener(e -> {
			dispose();
			new MyPage(2).setVisible(true);
		});
		panel.add(b3);
		
		b4 = new JButton("\uC7A5\uBC14\uAD6C\uB2C8");
		b4.addActionListener(e -> {
			dispose();
			new MyPage(3).setVisible(true);
		});
		panel.add(b4);
		
		b5 = new JButton("\uC0C1\uD488\uB4F1\uB85D");
		panel.add(b5);

		Utils.setColor(b1, b2, b3, b4, b5);
		Utils.setFrame(this, "관리");
	}
}
