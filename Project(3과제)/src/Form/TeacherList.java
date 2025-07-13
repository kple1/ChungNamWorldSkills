package Form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Model.TeacherListModel;
import Other.DB;
import Other.Utils;

public class TeacherList extends JFrame {
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JLabel label;
	public JComboBox c1;
	public Component verticalStrut;
	public JScrollPane scroll;
	public JPanel view;
	public static int tno = 0;
	String fn;
	public TeacherList(String fn) {
		this.fn = fn;
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		
		label = new JLabel("\uC120\uC0DD\uB2D8\uC744 \uC120\uD0DD\uD558\uC138\uC694!");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label);
		
		panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_3);
		
		c1 = new JComboBox();
		panel_3.add(c1);
		
		verticalStrut = Box.createVerticalStrut(40);
		getContentPane().add(verticalStrut, BorderLayout.SOUTH);
		
		scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(21);
		scroll.setHorizontalScrollBarPolicy(31);
		scroll.addMouseMotionListener(new MouseMotionAdapter() {
			Point lastPos;
			public void mouseDragged(MouseEvent e) {
				var viewPort = scroll.getViewport();
				var viewPos = viewPort.getViewPosition();
				viewPos.translate(lastPos.x - e.getX(), 0);
				viewPos.x = Math.max(0, viewPos.x);
				viewPort.setViewPosition(viewPos);
				lastPos = e.getPoint();
			}
			
			public void mouseMoved(MouseEvent e) {
				lastPos = e.getPoint();
			}
		});
		getContentPane().add(scroll, BorderLayout.CENTER);
		
		c1.addItem("전체");
		c1.addItem("답변률순");
		c1.addItem("문제풀이가 많은 순");
		
		view = new JPanel();
		scroll.setViewportView(view);
		scroll.setPreferredSize(new Dimension(600, 200));
		view.setLayout(new GridLayout(1, 0, 5, 0));
		
		c1.addActionListener(e -> {
			if (c1.getSelectedIndex() == 0) {
				reload("");
			} else if (c1.getSelectedIndex() == 1) {
				reload("order by b desc");
			} else {
				reload("order by a desc");
			}
		});
		
		reload("");
		
		Utils.setFrame(this, "선생님 목록");
	}
	
	void reload(String option) {
		view.removeAll();
		
		var list = DB.getTeacherList(option);
		for (int i = 0; i < list.size(); i++) {
			int capture = i;
			var m = new TeacherListModel(list.get(i));
			m.getImage().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					dispose();
					tno = Integer.parseInt(list.get(capture)[0].toString());
					if (fn.equals("질문등록")) {
						new RegistQuestion().setVisible(true);
						RegistQuestion.b1.setText(list.get(capture)[1].toString() + " 선생님");
					} else {
						new MyQuestion().setVisible(true);
						MyQuestion.table.setValueAt(list.get(capture)[1], MyQuestion.row, 1);
						DB.update("update catalog set tno = ? where questionexplan = ?", list.get(capture)[0], MyQuestion.qx);
					}
				}
			});
			view.add(m);
		}
		view.revalidate();
		view.repaint();
	}

	public static void main(String[] args) {
		new TeacherList("질문등록").setVisible(true);
	}

}
