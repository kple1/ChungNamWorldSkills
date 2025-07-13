package Form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import Other.DB;
import Other.Utils;

public class WrongNote extends JFrame implements ActionListener {
	public JPanel panel;
	public JLabel title;
	public JPanel view;
	public JPanel c;
	public JPanel d;
	public JPanel b;
	public JPanel a;
	public JLabel comment;
	public JLabel pro;
	public JPanel panel_3;
	public JLabel label_1;
	public JTextArea t1;
	public JComboBox c1;
	public JButton b1;
	public JButton b3;
	public JButton b2;
	public WrongNote() {
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		title = new JLabel("\uC624\uB2F5\uB178\uD2B8");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		panel.add(title, BorderLayout.CENTER);
		panel.add(Utils.line(0, 1), BorderLayout.SOUTH);
		
		view = new JPanel();
		getContentPane().add(view, BorderLayout.CENTER);
		
		a = new JPanel(new BorderLayout());
		view.add(a);
		
		b = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		view.add(b);
		
		c = new JPanel();
		c.setBorder(BorderFactory.createLineBorder(Color.black));
		view.add(c);
		
		d = new JPanel(new BorderLayout(100, 0));
		view.add(d);
		view.setPreferredSize(new Dimension(640, 350));
		
		a.setPreferredSize(new Dimension(600, 100));
		
		comment = new JLabel("<html>풀어야 할 문제가 <font color = 'red'>" + list.size() + "개</font> 남았습니다.</html>");
		a.add(comment, BorderLayout.NORTH);
		
		pro = new JLabel();
		pro.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		pro.setHorizontalAlignment(0);
		pro.setBorder(BorderFactory.createLineBorder(Color.black));
		a.add(pro, BorderLayout.WEST);
		
		panel_3 = new JPanel();
		panel_3.setBorder(BorderFactory.createLineBorder(Color.black));
		a.add(panel_3, BorderLayout.EAST);
		b.setPreferredSize(new Dimension(600, 35));
		
		c1 = new JComboBox();
		c1.setPreferredSize(new Dimension(130, 25));
		c1.setRenderer(new ListCellRenderer<JLabel>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index,
					boolean isSelected, boolean cellHasFocus) {
				if (isSelected) {
					value.setOpaque(true);
				} else {
					value.setOpaque(false);
				}
				return value;
			}
		});
		
		var l1 = new JLabel("그리기");
		l1.setSize(15, 15);
		l1.setIcon(Utils.imageSize(new ImageIcon("datafiles/icon/pencil.png"), l1));
		
		var l2 = new JLabel("지우기");
		l2.setSize(15, 15);
		l2.setIcon(Utils.imageSize(new ImageIcon("datafiles/icon/eraser.png"), l2));
		
		c1.addItem(l1);
		c1.addItem(l2);
		
		c1.setSelectedIndex(1);
		
		c1.addActionListener(e -> {
			if (c1.getSelectedIndex() == 0) {
				Utils.ok("그리기를 실행하겠습니다.");
			} else {
				c.repaint();
			}
		});
		
		b.add(c1);
		c.setPreferredSize(new Dimension(600, 150));
		d.setPreferredSize(new Dimension(600, 30));
		
		b1 = new JButton("<");
		d.add(b1, BorderLayout.WEST);
		
		b3 = new JButton(">");
		d.add(b3, BorderLayout.EAST);
		
		b2 = new JButton("\uD655\uC778\uD558\uAE30");
		d.add(b2, BorderLayout.CENTER);
		
		pro.setPreferredSize(new Dimension(290, 0));
		panel_3.setPreferredSize(new Dimension(290, 0));
		panel_3.setLayout(new BorderLayout(0, 0));
		
		label_1 = new JLabel("\uC815\uB2F5:");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		panel_3.add(label_1, BorderLayout.WEST);
		
		t1 = new JTextArea();
		t1.setBackground(new Color(240, 240, 240));
		panel_3.add(t1, BorderLayout.CENTER);
		
		b1.addActionListener(this);
		b3.addActionListener(this);
		b2.addActionListener(this);
		
		Utils.setColor(b1,b3,b2);
		Utils.setFrame(this, "오답노트");
		
		init();
		b1.setEnabled(false);
	}

	public static void main(String[] args) {
		new WrongNote().setVisible(true);
	}

	void reset() {
		t1.setText("");
		c.repaint();
		c1.setSelectedIndex(1);
	}
	
	void init() {
		if (list.size() == 0) {
			view.removeAll();
			view.setLayout(new BorderLayout());
			var label = new JLabel("오답이 없습니다.");
			label.setForeground(Color.red);
			label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			label.setHorizontalAlignment(0);
			view.add(label);
			view.revalidate();
			view.repaint();
		} else {
			pro.setText(list.get(page)[2].toString());
		}
	}
	
	int page = 0;
	List<Object[]> list = DB.getWrong();
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(b1)) {
			if (page != 0) {
				b3.setEnabled(true);
				--page;
				pro.setText(list.get(page)[2].toString());
			} else {
				b1.setEnabled(false);
			}
		} else if (e.getSource().equals(b2)) {
			if (t1.getText().isEmpty()) {
				Utils.fail("답을 입력해주세요.");
			} else if (!list.get(page)[3].toString().equals(t1.getText() + ".0")) {
				Utils.fail("틀린 답입니다.");
				t1.setText("");
				c.repaint();
			} else {
				Utils.ok("정답입니다.");
				
				DB.update("delete from wrong where wno = ?", list.get(page)[0]);
				
				reset();
				list = DB.getWrong();
				comment.setText("<html>풀어야 할 문제가 <font color = 'red'>" + list.size() + "개</font> 남았습니다.</html>");	
				try {
					pro.setText(list.get(page)[2].toString());
				} catch (Exception e2) {

				}
				
				if (list.size() == 0) {
					Utils.ok("모든 문제를 풀었습니다.");
					dispose();
					new StudentMain().setVisible(true);
				}
			}
		} else if (e.getSource().equals(b3)) {
			if (page != list.size() - 1) {
				b1.setEnabled(true);
				++page;
				pro.setText(list.get(page)[2].toString());
			} else {
				b3.setEnabled(false);
			}
		}
	}

}
