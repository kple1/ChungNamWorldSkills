package Form;

import java.util.List;

import javax.swing.JFrame;

import Other.DB;
import Other.Utils;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Model.QuestionListModel;

import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;

public class QuestionList extends JFrame {
	
	public JPanel panel;
	public JLabel label;
	public Component horizontalStrut;
	public Component horizontalStrut_1;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label_1;
	public JComboBox c1;
	public JLabel label_2;
	public JComboBox c2;
	public JScrollPane scroll;
	public JPanel view;
	public QuestionList() {
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		label = new JLabel("\uC9C8\uBB38 \uBAA9\uB85D");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label, BorderLayout.CENTER);
		panel.add(Utils.line(0, 1), BorderLayout.SOUTH);
		
		horizontalStrut = Box.createHorizontalStrut(42);
		getContentPane().add(horizontalStrut, BorderLayout.WEST);
		
		horizontalStrut_1 = Box.createHorizontalStrut(41);
		getContentPane().add(horizontalStrut_1, BorderLayout.EAST);
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		label_1 = new JLabel("\uC815\uB82C: ");
		panel_2.add(label_1);
		
		c1 = new JComboBox();
		panel_2.add(c1);
		
		label_2 = new JLabel("\uD559\uB144: ");
		panel_2.add(label_2);
		
		c2 = new JComboBox();
		panel_2.add(c2);
		
		c1.setPreferredSize(new Dimension(100, 25));
		c2.setPreferredSize(new Dimension(100, 25));
		
		c1.addItem("전체");
		c1.addItem("최근 등록순");
		c1.addItem("문제번호순");
		c1.addItem("회원번호순");
		
		c2.addItem("전체");
		
		scroll = new JScrollPane();
		panel_1.add(scroll, BorderLayout.CENTER);
		
		view = new JPanel();
		scroll.setViewportView(view);
		scroll.setPreferredSize(new Dimension(500, 400));
		view.setLayout(new GridLayout(0, 2, 5, 5));
		var grades = DB.array("select distinct grade from catalog c\r\n"
				+ "join user u on u.uno = c.uno\r\n"
				+ "where tno = ?\r\n"
				+ "order by grade;", Login.no).stream().map(x -> x[0]).toArray();
		for (int i = 0; i < grades.length; i++) {
			c2.addItem(grades[i].toString());
		}
		
		c1.addActionListener(e -> {
			if (c1.getSelectedIndex() == 0) {
				reload("");
			} else if (c1.getSelectedIndex() == 1) {
				reload("order by date desc");
			} else if (c1.getSelectedIndex() == 2) {
				reload("order by type");
			} else {
				reload("order by u.uno");
			}
		});
		
		c2.addActionListener(e -> {
			reload("");
		});
		
		reload("");
		
		Utils.setFrame(this, "질문 목록");
	}

	void reload(String option) {
		view.removeAll();
		List<Object[]> list = DB.array("select name, grade, title, type, isNull(explan) from catalog c\r\n"
				+ "join user u on u.uno = c.uno\r\n"
				+ "where tno = ? " + option, Login.no);
		for (int i = 0; i < list.size(); i++) {
			var m = new QuestionListModel(list.get(i), this);
			if (c2.getSelectedItem().toString().equals("전체") || list.get(i)[1].toString().equals(c2.getSelectedItem().toString())) {
				view.add(m);
			}
		}
		view.revalidate();
		view.repaint();
	}
	
	public static void main(String[] args) {
		new QuestionList().setVisible(true);
	}
	
	

}
