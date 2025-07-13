package Form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Other.DB;
import Other.Utils;

public class Login extends JFrame {
	public JLabel title;
	public JPanel panel;
	public JLabel l1;
	public JLabel l2;
	public JButton b1;
	public JPanel panel_1;
	public JTextField t1;
	public JTextField t2;
	public static int no = 0;
	public static String name = "";
	public static byte[] img = null;
	public static int price;
	public static void main(String[] args) {
		new Login().setVisible(true);
	}
	
	public Login() {
		
		title = new JLabel("\uB85C\uADF8\uC778");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(400, 50));
		title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		getContentPane().add(title, BorderLayout.NORTH);
		
		panel = new JPanel(new GridLayout(2, 0, 0, 30));
		panel.setPreferredSize(new Dimension(100, 100));
		getContentPane().add(panel, BorderLayout.WEST);
		
		l1 = new JLabel("\uC544\uC774\uB514");
		panel.add(l1);
		
		l2 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		panel.add(l2);
		
		b1 = new JButton("로그인");
		b1.addActionListener(e -> {
			if (t1.getText().isEmpty() || t2.getText().isEmpty()) {
				Utils.fail("빈칸이 있습니다.");
			} else if (DB.getInt("select count(*) from user where u_id = ? and u_pw = ?", t1.getText(), t2.getText()) == 0) {
				Utils.fail("아이디 또는 비밀번호를 확인해주세요.");
			} else {
				no = DB.getInt("select u_no from user where u_id = ?", t1.getText());
				name = DB.getString("select u_name from user where u_id	= ?", t1.getText());
				img = DB.getBytes("select u_img from user where u_id = ?", t1.getText());
				price = DB.getInt("select u_price from user where u_id = ?", t1.getText());
				Utils.ok(String.format("%s님 환영합니다.", name));
				dispose();
				new Main().setVisible(true);
			}
		});
		getContentPane().add(b1, BorderLayout.EAST);
		
		panel_1 = new JPanel(new GridLayout(2, 1, 0, 30));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		t1 = new JTextField();
		panel_1.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		panel_1.add(t2);
		t2.setColumns(10);
	
		Utils.setColor(b1);
		Utils.setFrame(this, "로그인");
	}
	
}
