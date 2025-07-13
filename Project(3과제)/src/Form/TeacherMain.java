package Form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Model.MainModel;
import Other.DB;
import Other.Utils;

public class TeacherMain extends JFrame {
	public JPanel panel;
	public JPanel top;
	public JLabel label;
	public JPanel panel_1;
	public JLabel logo;
	public JLabel name;
	public JButton b1;
	public JButton b2;
	public JPanel panel_2;
	public JLabel title;
	public Component verticalStrut;
	public Component horizontalStrut;
	public JScrollPane scroll;
	public JPanel view;
	public TeacherMain() {
		
		panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(150, 0));
		getContentPane().add(panel, BorderLayout.WEST);
		
		top = new JPanel();
		FlowLayout fl_top = (FlowLayout) top.getLayout();
		fl_top.setAlignment(FlowLayout.LEFT);
		panel.add(top, BorderLayout.NORTH);
		
		logo = new JLabel("");
		logo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Utils.ok("·Î±×¾Æ¿ô µÇ¾ú½À´Ï´Ù.");
				dispose();
				new Login().setVisible(true);
			}
		});
		logo.setSize(40, 40);
		logo.setIcon(Utils.imageSize(new ImageIcon("datafiles/icon/logo.png"), logo));
		top.add(logo);
		
		name = new JLabel("<html><font size = '5'>" + Login.name + "</font> ¼±»ý´Ô, <br>È¯¿µÇÕ´Ï´Ù.</html>");
		name.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		top.add(name);
		
		label = new JLabel("³» ´äº¯·ü : " + Login.responseRate + "%");
		label.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label, BorderLayout.SOUTH);
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		b1 = new JButton("\uC9C8\uBB38 \uBAA9\uB85D");
		b1.setBounds(25, 65, 97, 23);
		panel_1.add(b1);
		
		b2 = new JButton("\uD1B5\uACC4");
		b2.setBounds(25, 177, 97, 23);
		panel_1.add(b2);
		
		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		title = new JLabel("\uB2F5\uBCC0\uD558\uC9C0 \uC54A\uC740 \uC9C8\uBB38");
		title.setPreferredSize(new Dimension(0, 80));
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		panel_2.add(title, BorderLayout.NORTH);
		
		verticalStrut = Box.createVerticalStrut(40);
		panel_2.add(verticalStrut, BorderLayout.SOUTH);
		
		horizontalStrut = Box.createHorizontalStrut(42);
		panel_2.add(horizontalStrut, BorderLayout.EAST);
		
		scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(400, 300));
		panel_2.add(scroll, BorderLayout.CENTER);
		
		view = new JPanel();
		scroll.setViewportView(view);
		view.setLayout(new GridLayout(0, 1, 0, 5));
		
		var list = DB.array("select c.uno, title, name, grade from catalog c\r\n"
				+ "join user u on u.uno = c.uno\r\n"
				+ "where tno = ? and isNull(explan);", Login.no);
		for (int i = 0; i < list.size(); i++) {
			var m = new MainModel(list.get(i));
			m.getImage().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					dispose();
				}
			});
			view.add(m);
		}
		
		Utils.setColor(b1, b2);
		Utils.setFrame(this, "¼±»ý´Ô ¸ÞÀÎ");
	}

	public static void main(String[] args) {
		new TeacherMain().setVisible(true);
	}
}
