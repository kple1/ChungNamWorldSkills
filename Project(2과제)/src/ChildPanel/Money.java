package ChildPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Other.Utils;

public class Money extends JPanel {

	/**
	 * Create the panel.
	 */
	public Controller controller;
	public Money(String path) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		var image = new JLabel();
		image.setSize(150, 150);
		image.setIcon(Utils.imageSize(new ImageIcon(path), image));
		add(image, BorderLayout.CENTER);
		
		controller = new Controller();
		add(controller, BorderLayout.SOUTH);
	}

}
