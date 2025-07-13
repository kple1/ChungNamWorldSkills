package Other;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

public class Utils {
	public static void ok(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Á¤º¸", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void fail(String msg) {
		JOptionPane.showMessageDialog(null, msg, "°æ°í", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void setSize(int w, int h, Component... c) {
		for (int i = 0; i < c.length; i++) {
			c[i].setPreferredSize(new Dimension(w, h));
			c[i].setMaximumSize(new Dimension(w, h));
		}
	}
	
	public static void setColor(Component... c) {
		for (int i = 0; i < c.length; i++) {
			c[i].setBackground(new Color(0, 128, 0));
			c[i].setForeground(Color.white);
			c[i].setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		}
	}
	
	public static ImageIcon imageSize(ImageIcon ic, JLabel label) {
		var get = ic.getImage();
		var re = get.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT);
		return new ImageIcon(re);
	}
	
	public static void setFrame(JFrame f, String title) {
		f.setTitle(title);
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setIconImage(new ImageIcon("datafiles/icon/logo.png").getImage());
	}
	
	public static void placeholder(JTextComponent c, String text) {
		c.setText(text);
		c.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (c.getText().isEmpty()) {
					c.setText(text);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (c.getText().equals(text)) {
					c.setText("");
				}
			}
		});
	}
	
	public static void llm(JTextComponent c, int leng) {
		((AbstractDocument)c.getDocument()).setDocumentFilter(new DocumentFilter() {
			public void replace(FilterBypass fb, int offset, int length, String text,
					AttributeSet attrs) throws BadLocationException {
				int a = fb.getDocument().getLength();
				int b = text.length();
				int c = a - length + b;
				if (a <= leng) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		});
	}
	
	public static boolean findBadwords(String[] text, JTextComponent... c) {
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < text.length; j++) {
				if (c[i].getText().contains(text[j])) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static JPanel line(int w, int h) {
		var line = new JPanel();
		line.setBackground(Color.black);
		line.setPreferredSize(new Dimension(w, h));
		return line;
	}
}
