package Form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Other.DB;
import Other.Utils;

public class StudentMain extends JFrame implements MouseListener {
	public JPanel panel;
	public JLabel label;
	public JPanel tabbed;
	public JLayeredPane layer;
	public JLabel left;
	public JLabel right;
	public JPanel image;
	public StudentMain() {
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 100));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		label = new JLabel("<html>대기중인 질문: <font color = 'red'>" + DB.getInt("select sum(explan is null) from catalog where uno = ?", Login.no) + "</font>개</html>");
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new MyQuestion().setVisible(true);
			}
		});
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		
		tabbed = new JPanel();
		FlowLayout flowLayout = (FlowLayout) tabbed.getLayout();
		flowLayout.setHgap(20);
		panel.add(tabbed);
		
		String[] ln = new String[] {"질문 등록", "내 질문", "문제퀴즈", "오답노트"};
		for (int i = 0; i < 4; i++) {
			int capture = i;
			var border = new JPanel(new BorderLayout());
			
			var label = new JLabel(ln[i]);
			label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					dispose();
					if (capture == 0) {
						new RegistQuestion().setVisible(true);
					} else if (capture == 1) {
						new MyQuestion().setVisible(true);
					} else if (capture == 2) {
						
					} else {
						new WrongNote().setVisible(true);
					}
				}
			});
			label.setHorizontalAlignment(0);
			
			border.add(label);
			border.add(Utils.line(100, 1), BorderLayout.SOUTH);
			
			tabbed.add(border);
		}
		
		layer = new JLayeredPane();
		layer.setPreferredSize(new Dimension(700, 350));
		getContentPane().add(layer, BorderLayout.CENTER);
		
		left = new JLabel("<");
		left.setForeground(new Color(255, 255, 255));
		left.setBackground(new Color(255, 255, 255));
		left.setFont(new Font("굴림", Font.BOLD, 30));
		left.setBounds(12, 107, 50, 50);
		layer.add(left, 0);
		
		right = new JLabel(">");
		right.setForeground(new Color(255, 255, 255));
		right.setFont(new Font("굴림", Font.BOLD, 30));
		right.setBackground(Color.WHITE);
		right.setBounds(650, 107, 30, 50);
		layer.add(right, 0);
		
		image = new JPanel();
		image.setBounds(0, 0, 700 * 3, 350);
		layer.add(image, 100);
		image.setLayout(new GridLayout(0, 3, 0, 0));
		
		for (int i = 0; i < 3; i++) {
			var imager = new JLabel();
			imager.setSize(700, 350);
			imager.setIcon(Utils.imageSize(new ImageIcon("datafiles/main/" + (i + 1) + ".png"), imager));
			image.add(imager);
		}
		
		left.addMouseListener(this);
		right.addMouseListener(this);
		
		Utils.setFrame(this, "학생 메인");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Utils.ok("로그아웃 되었습니다.");
				new Login().setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		new StudentMain().setVisible(true);
	}

	int page = 1;
	Timer timer;
	int x = 0;
	boolean state = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(left)) {
			if (page == 0 || state) {
				return;
			} else {
				timer = new Timer(10, e1 -> {
					if (x == -700 * (page - 1)) {
						timer.stop();
						state = false;
						--page;
					} else {
						state = true;
						x += 10;
						image.setLocation(x, 0);
					}
				});
				timer.start();
			}
		} else if (e.getSource().equals(right)) {
			if (page == 3 || state) {
				return;
			} else {
				timer = new Timer(10, e1 -> {
					if (x == -700 * page) {
						timer.stop();
						state = false;
						++page;
					} else {
						state = true;
						x-= 10;
						image.setLocation(x, 0);
					}
				});
				timer.start();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
