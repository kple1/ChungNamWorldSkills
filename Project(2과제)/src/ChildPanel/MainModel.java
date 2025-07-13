package ChildPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Other.Utils;

public class MainModel extends JPanel {
	public JPanel panel;
	public JLabel l1;
	public JLabel l2;
	public JLabel image;

	public MainModel(Object[] o) {
		setLayout(new BorderLayout(0, 0));
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel = new JPanel(new GridLayout(2, 0, 0, 0));
		panel.setPreferredSize(new Dimension(0, 40));
		add(panel, BorderLayout.SOUTH);
		
		l1 = new JLabel("New label");
		if (o[1].toString().length() >= 15) {
			l1.setText(o[1].toString().substring(0, 15) + "...");
		} else {
			l1.setText(o[1].toString());
		}
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(l1);
		
		l2 = new JLabel("New label");
		var price = Integer.parseInt(o[2].toString());
		var salePrice = Utils.findSale(Integer.parseInt(o[3].toString()));
		if (price != salePrice) {
			l2.setText(String.format("<html><strike>%d¿ø</strike> <font color = 'red'>%d¿ø</font></html>", price, salePrice));
		} else {
			l2.setText(String.format("%d¿ø", price));
		}
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(l2);
		
		image = new JLabel("");
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setSize(150, 150);
		image.setIcon(Utils.imageSize(new ImageIcon((byte[])o[0]), image));
		add(image, BorderLayout.CENTER);
	}

	public JLabel getImage() {
		return image;
	}
}
