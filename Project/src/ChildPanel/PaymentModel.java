package ChildPanel;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Other.Utils;

public class PaymentModel extends JPanel {
	public JCheckBox c1;
	public JLabel image;
	public JLabel t1;

	/**
	 * Create the panel.
	 */
	public int price;
	public int pno;
	public int sno;
	public PaymentModel(byte[] img, String name, int price, int pno, int sno) {
		this.price = price;
		this.pno = pno;
		this.sno = sno;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		c1 = new JCheckBox("");
		add(c1);
		
		image = new JLabel();
		image.setSize(100, 100);
		image.setIcon(Utils.imageSize(new ImageIcon(img), image));
		add(image);
		
		t1 = new JLabel(String.format("<html>%s<br>%,d</html>", name, price));
		add(t1);
		
	}

}
