package Form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Other.DB;
import Other.Utils;

import javax.swing.JTextField;

public class Login extends JFrame {
	public JLabel title;
	public JPanel panel;
	public JPanel view;
	public JPanel a;
	public JButton b1;
	public JPanel b;
	public JLabel comment;
	public static int no = 1;
	public static int responseRate = 36;
	public static String name = "박지우";
	public JLabel label;
	public JLabel label_1;
	public JTextField t1;
	public JTextField t2;
	public Login() {
		
		title = new JLabel("Question");
		title.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(title, BorderLayout.NORTH);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(420, 150));
		getContentPane().add(panel, BorderLayout.CENTER);
		
		view = new JPanel();
		view.setPreferredSize(new Dimension(400, 100));
		panel.add(view);
		view.setLayout(new BorderLayout(5, 0));
		
		a = new JPanel(new GridLayout(2, 0, 0, 20));
		view.add(a, BorderLayout.WEST);
		
		label = new JLabel("\uC544\uC774\uB514");
		a.add(label);
		
		label_1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		a.add(label_1);
		
		b1 = new JButton("\uB85C\uADF8\uC778");
		b1.addActionListener(e -> {
			if (t1.getText().isEmpty() || t2.getText().isEmpty()) {
				comment.setText("<html><u>빈칸이 있습니다.</u></html>");
				return;
			}
			
			if (t1.getText().equals("admin") && t2.getText().equals("1234")) {
				Utils.ok("관리자님 환영합니다.");
				dispose();
				return;
			}
			
			var getId = DB.requestUserInfo("id", t1.getText());
			var getPw = DB.requestUserInfo("pw", t2.getText());
			
			if ((int)getId[0] == 0) {
				comment.setText("<html><u>존재하지 않는 회원입니다.</u></html>");
				return;
			}
			
			if ((int)getPw[0] == 0) {
				comment.setText("<html><u>비밀번호가 일치하지 않았습니다.</u></html>");
				return;
			}
			
			comment.setText("");
			name = getId[1].toString();
			no = Integer.parseInt(getId[0].toString());
			dispose();
			
			if (getId[4].toString().equals("user")) {
				Utils.ok(String.format("%s 학생님 환영합니다.", name));
			} else {
				responseRate = DB.getInt("select floor(sum(explan is not null) / count(*) * 100) from catalog where tno = ?", no);
				Utils.ok(String.format("%s 선생님 환영합니다.", name));
			}
		});
		view.add(b1, BorderLayout.EAST);
		
		b = new JPanel(new GridLayout(2, 0, 0, 20));
		view.add(b, BorderLayout.CENTER);
		
		t1 = new JTextField();
		b.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		b.add(t2);
		t2.setColumns(10);
		
		comment = new JLabel("");
		comment.setPreferredSize(new Dimension(350, 25));
		comment.setForeground(new Color(255, 0, 0));
		panel.add(comment);
		
		Utils.setColor(b1);
		Utils.setFrame(this, "로그인");
	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	}

}
