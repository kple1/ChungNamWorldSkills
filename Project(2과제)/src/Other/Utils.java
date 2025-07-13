package Other;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Utils {
	public static void ok(String msg) {
		JOptionPane.showMessageDialog(null, msg, "정보", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void fail(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void setSize(int w, int h, Component... c) {
		for (int i = 0; i < c.length; i++) {
			c[i].setPreferredSize(new Dimension(w, h));
			c[i].setMaximumSize(new Dimension(w, h));
		}
	}
	
	public static void setColor(Component... c) {
		for (int i = 0; i < c.length; i++) {
			c[i].setForeground(Color.white);
			c[i].setBackground(new Color(0, 0, 128));
		}
	}
	
	public static ImageIcon imageSize(ImageIcon icon, JLabel label) {
		Image img = icon.getImage();
		Image re = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT);
		return new ImageIcon(re);
	}
	
	public static int findSale(int pno) {
		int getPrice = DB.getInt("select p_price from productlist where p_no = ?", pno);
		var list = DB.getSaleList();
		for (int i = 0; i < list.size(); i++) {
			var getpno = Integer.parseInt(list.get(i)[1].toString());
			if (pno == getpno) {
				getPrice = (int)(getPrice - getPrice * (double)(Double.parseDouble(list.get(i)[0].toString()) * 0.01));
				break;
			}
		}
		return getPrice;
	}
	
	public static int findSale(int pno, int getPrice) {
		int price = 0;
		var list = DB.getSaleList();
		for (int i = 0; i < list.size(); i++) {
			var getpno = Integer.parseInt(list.get(i)[1].toString());
			if (pno == getpno) {
				getPrice = (int)(getPrice * (double)(Double.parseDouble(list.get(i)[0].toString()) * 0.01));
				price = getPrice;
				break;
			}
		}
		return price;
	}
	
	public static void setFrame(JFrame frame, String title) {
		frame.setTitle(title);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
}