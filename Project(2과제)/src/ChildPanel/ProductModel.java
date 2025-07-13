package ChildPanel;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Other.Utils;

public class ProductModel extends JPanel {

	JLabel image;
	public ProductModel(byte[] img, String name, int pno) {
		setLayout(new BorderLayout());
		
		image = new JLabel();
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setSize(150, 150);
		image.setIcon(Utils.imageSize(new ImageIcon(img), image));
		
		var label = new JLabel(name);
		if (name.length() >= 20) {
			label.setText(name.substring(0, 20) + "...");
		}
		
		add(image, BorderLayout.CENTER);
		add(label, BorderLayout.SOUTH);
	}

	public JLabel getImage() {
		return image;
	}
}
