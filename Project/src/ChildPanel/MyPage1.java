package ChildPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Form.Login;
import Form.MyPage;
import Form.Recharge;
import Other.DB;
import Other.Utils;

public class MyPage1 extends JPanel implements ActionListener {
	public JLabel image;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JTextField t1;
	public JTextField t2;
	public JTextField t3;
	public JPanel panel_3;
	public JPanel panel_4 = new JPanel();
	public JPanel panel_5;
	public JTextField t4;
	public JButton b1;
	public JTextField c1;
	public JLabel l1;
	public JTextField c2;
	public JLabel l2;
	public JTextField c3;
	public JButton b2;
	public JButton b3;
	public Object[] uif = DB.getUserInfo();
	JFrame f;
	public MyPage1(JFrame f) {
		this.f = f;
		setLayout(new BorderLayout(0, 0));
		
		image = new JLabel("");
		image.setBorder(BorderFactory.createLineBorder(Color.black));
		image.setPreferredSize(new Dimension(300, 300));
		image.setSize(300, 300);
		image.setIcon(Utils.imageSize(new ImageIcon(DB.getBytes("select u_img from user where u_no = ?", Login.no)), image));
		add(image, BorderLayout.WEST);
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel(new GridLayout(6, 1, 0, 20));
		panel_1.setPreferredSize(new Dimension(100, 0));
		panel.add(panel_1, BorderLayout.WEST);
		
		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(6, 1, 0, 20));
		
		t1 = new JTextField(uif[0].toString());
		panel_2.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField(uif[1].toString());
		t2.setEnabled(false);
		panel_2.add(t2);
		t2.setColumns(10);
		t3 = new JTextField(uif[2].toString());
		panel_2.add(t3);
		t3.setColumns(10);
		
		panel_3 = new JPanel();
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		panel_2.add(panel_3);
		
		t4 = new JTextField(uif[3].toString());
		panel_3.add(t4);
		t4.setColumns(10);
		
		b1 = new JButton("\uCDA9\uC804\uD558\uAE30");
		panel_3.add(b1);
		panel_2.add(panel_4);
		
		panel_5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_2.add(panel_5);
		
		b2 = new JButton("\uC774\uBBF8\uC9C0 \uBC14\uAFB8\uAE30");
		panel_5.add(b2);
		
		b3 = new JButton("\uC800\uC7A5\uD558\uAE30");
		panel_5.add(b3);
		
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		var sp = uif[4].toString().split("-");
		c1 = new JTextField(sp[0]);
		c1.setColumns(10);
		panel_4.add(c1);
		
		l1 = new JLabel("-");
		panel_4.add(l1);
		
		c2 = new JTextField(sp[1]);
		panel_4.add(c2);
		c2.setColumns(10);
		
		l2 = new JLabel("-");
		panel_4.add(l2);
		
		c3 = new JTextField(sp[2]);
		panel_4.add(c3);
		c3.setColumns(10);

		String[] list = new String[] {"이름", "아이디", "비밀번호", "보유 돈", "생년월일", "이미지"};
		Utils.setColor(b1, b2, b3);
		for (int i = 0; i < list.length; i++) {
			panel_1.add(new JLabel(list[i]));
		}
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}

	boolean imageState = true;
	@Override
	public void actionPerformed(ActionEvent e) {
		imageState = true;
		if (e.getSource().equals(b1)) {
			f.dispose();
			new Recharge().setVisible(true);
		} else if (e.getSource().equals(b2)) {
			var file = new JFileChooser();
			var r = file.showOpenDialog(null);
			
			if (r == JFileChooser.APPROVE_OPTION) {
				var s = file.getSelectedFile();
				image.setSize(300, 400);
				image.setIcon(Utils.imageSize(new ImageIcon(s.toString()), image));
				
				MyPage.image.setSize(40, 40);
				MyPage.image.setIcon(Utils.imageSize(new ImageIcon(s.toString()), image));
				
				imageState = false;
			}
		} else if (e.getSource().equals(b3)) {		
			String birth = String.format("%s-%s-%s", c1.getText(), c2.getText(), c3.getText());
			String[] jt = new String[] {t1.getText(), t2.getText(), t3.getText(), t4.getText(), birth};
			
			boolean state = true;
			for (int i = 0; i < jt.length; i++) {
				if (i == 1) continue;
				if (!uif[i].toString().equals(jt[i])) {
					state = false;
				}
			}
			
			if (imageState && state) {
				Utils.fail("수정된 곳이 없습니다.");
			} else {
				try {
					var parse = LocalDate.parse(birth);
					if (parse.isAfter(LocalDate.now())) {
						Utils.fail("생년월일 형식 확인");
					} else {
						Utils.ok("정보가 수정되었습니다.");
					}
				} catch (Exception e2) {
					Utils.fail("생년월일 형식 확인");
				}
			}
		}
	}
}
