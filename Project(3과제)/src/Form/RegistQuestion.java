package Form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Other.DB;
import Other.DropListener;
import Other.Utils;

public class RegistQuestion extends JFrame {
	public JLabel label;
	public JPanel panel;
	public JLabel image;
	public JPanel border;
	public JTextField t1;
	public JPanel panel_1;
	public JTextArea t2;
	public static JButton b1;
	public JButton b2;
	public RegistQuestion() {
		
		label = new JLabel("\uC9C8\uBB38 \uB4F1\uB85D");
		label.setFont(new Font("���� ���", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(label, BorderLayout.NORTH);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		image = new JLabel("");
		image.setBorder(BorderFactory.createLineBorder(Color.black));
		image.setPreferredSize(new Dimension(350, 300));
		panel.add(image);
		
		border = new JPanel();
		border.setPreferredSize(new Dimension(300, 300));
		panel.add(border);
		border.setLayout(new BorderLayout(0, 5));
		
		t1 = new JTextField();
		border.add(t1, BorderLayout.NORTH);
		t1.setColumns(10);
		
		panel_1 = new JPanel();
		border.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		b1 = new JButton("\uC120\uC0DD\uB2D8\uC744 \uC120\uD0DD\uD574\uC8FC\uC138\uC694.");
		b1.addActionListener(e -> {
			dispose();
			new TeacherList("�������").setVisible(true);
		});
		b1.setBorder(BorderFactory.createLineBorder(Color.black));
		b1.setBackground(new Color(255, 255, 255));
		panel_1.add(b1, BorderLayout.WEST);
		
		b2 = new JButton("\uC9C8\uBB38 \uB4F1\uB85D");
		b2.addActionListener(e -> {
			if (TeacherList.tno == 0) {
				var r = JOptionPane.showConfirmDialog(null, "�������� �������� �ʾҽ��ϴ�. ������ ������ �̵��Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (r == 0) {
					dispose();
					new TeacherList("�������").setVisible(true);
				}
			} else if (t1.getText().isEmpty() || t2.getText().isEmpty() || t1.getText().equals("����")||t2.getText().equals("��������")) {
				Utils.fail("������ �Է����ּ���.");
			} else if (DropListener.sel == 0) {
				Utils.fail("������ �������ּ���.");
			} else if (Utils.findBadwords(new String[] {"����", "�ù�", "��", "����", "����", "�Ƕ���", "������", "����"}, t1, t2)) {
				Utils.fail("��Ӿ�� ����Ͻ� �� �����ϴ�.");
			} else {
				Utils.ok("������ ��ϵǾ����ϴ�.");
				DB.update("insert into catalog (uno, tno, type, title, date, questionexplan) values (?, ?, ?, ?, ?, ?)",
						Login.no,
						TeacherList.tno,
						1,
						t1.getText(),
						LocalDate.now(),
						t2.getText());
				
				TeacherList.tno = 0;
				DropListener.sel = 0;
				
				dispose();
				new StudentMain().setVisible(true);
			}
		});
		panel_1.add(b2, BorderLayout.EAST);
		
		t2 = new JTextArea();
		border.add(t2, BorderLayout.CENTER);
		
		t1.setBackground(new Color(240, 240, 240));
		t2.setBackground(new Color(240, 240, 240));
		t1.setBorder(BorderFactory.createLineBorder(Color.black));
		t2.setBorder(BorderFactory.createLineBorder(Color.black));
		
		image.setDropTarget(new DropTarget(image, new DropListener(image)));
		
		t1.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (t1.getText().length() == 0) {
						SwingUtilities.invokeLater(() -> t1.setText("����"));
					}
				}
			}
		});
		
		t2.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (t2.getText().length() == 0) {
						SwingUtilities.invokeLater(() -> t2.setText("��������"));
					}
				}
			}
		});
		
		SwingUtilities.invokeLater(() -> requestFocusInWindow());
		
		Utils.llm(t1, 30);
		Utils.llm(t2, 500);
		Utils.placeholder(t1, "����");
		Utils.placeholder(t2, "��������");
		Utils.setColor(b2);
		Utils.setFrame(this, "���� ���");
	}

	public static void main(String[] args) {
		new RegistQuestion().setVisible(true);
	}

}
