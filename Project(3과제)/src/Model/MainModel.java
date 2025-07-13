package Model;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Other.Utils;

public class MainModel extends JPanel {
	public JLabel image;
	public JPanel panel;

	/**
	 * Create the panel.
	 */
	public MainModel(Object[] o) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createLineBorder(Color.gray));
		
		image = new JLabel("");
		image.setBorder(BorderFactory.createLineBorder(Color.gray));
		image.setSize(100, 100);
		image.setIcon(Utils.imageSize(new ImageIcon("datafiles/user/" + o[0] + ".jpg"), image));
		add(image);
		
		panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		var l1 = new JLabel(o[1].toString());
		var l2 = new JLabel(o[2] + " ÇÐ»ý");
		var l3 = new JLabel("ÇÐ³â: " + o[3]);
		
		l1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		l2.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		l3.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		
		panel.add(l1);
		panel.add(l2);
		panel.add(l3);
	}

	public JLabel getImage() {
		return image;
	}
}
