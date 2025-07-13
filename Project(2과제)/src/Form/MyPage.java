package Form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ChildPanel.MyPage1;
import ChildPanel.MyPage2;
import ChildPanel.MyPage3;
import Other.DB;
import Other.Utils;

public class MyPage extends JFrame implements MouseListener {
	public JPanel panel;
	public JPanel panel_1;
	public static JLabel image;
	public JPanel panel_2;
	public JLabel l1;
	public JLabel l2;
	public JPanel west;
	public JLabel f1;
	public JLabel f2;
	public JLabel f3;
	public JPanel view;
	MyPage1 m1 = new MyPage1(this);
	MyPage2 m2 = new MyPage2(this);
	MyPage3 m3 = new MyPage3();
	public static void main(String[] args) {
		new MyPage(1).setVisible(true);
	}
	
	public MyPage(int page) {
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(800, 50));
		
		panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_1.setPreferredSize(new Dimension(300, 40));
		panel.add(panel_1);
		
		image = new JLabel("");
		image.setSize(40, 40);
		image.setIcon(Utils.imageSize(new ImageIcon(DB.getBytes("select u_img from user where u_no = ?", Login.no)), image));
		panel_1.add(image);
		
		panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		l1 = new JLabel(Login.name);
		panel_2.add(l1);
		
		l2 = new JLabel(String.format("%,dø¯", Login.price));
		panel_2.add(l2);
		
		west = new JPanel();
		west.setPreferredSize(new Dimension(100, 350));
		getContentPane().add(west, BorderLayout.WEST);
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		
		f1 = new JLabel("\uC815\uBCF4\uC218\uC815");
		f1.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 20));
		west.add(f1);
		
		f2 = new JLabel("\uAD6C\uB9E4 \uBAA9\uB85D");
		f2.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 20));
		west.add(f2);
		
		f3 = new JLabel("\uC7A5\uBC14\uAD6C\uB2C8");
		f3.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.PLAIN, 20));
		west.add(f3);
		
		Utils.setSize(Integer.MAX_VALUE, 40, f1, f2, f3);
		
		view = new JPanel(new BorderLayout());
		view.setPreferredSize(new Dimension(700, 350));
		getContentPane().add(view, BorderLayout.CENTER);
		
		if (page == 1) {
			view.add(m1);	
		} else if (page == 2) {
			view.add(m2);
		} else {
			view.add(m3);
		}
		
		Utils.setFrame(this, "∏∂¿Ã∆‰¿Ã¡ˆ");
		
		f1.addMouseListener(this);
		f2.addMouseListener(this);
		f3.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		view.removeAll();
		if (e.getSource().equals(f1)) {
			view.add(m1);
		} else if (e.getSource().equals(f2)) {
			view.add(m2);
		} else if (e.getSource().equals(f3)) {
			view.add(m3);
		}
		view.revalidate();
		view.repaint();
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
