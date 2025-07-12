package ChildPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import Other.Utils;

public class ProductController extends JPanel {

	public Controller controller;
	public JButton bt;
	public ProductController(String size) {
		setLayout(new BorderLayout());
		
		bt = new JButton(size);
		bt.setBackground(Color.white);
		bt.setForeground(Color.black);
		bt.addActionListener(e -> {
			if (bt.getBackground() == Color.white) {
				Utils.setColor(bt);
				controller.setEnabled(true);
			} else {
				bt.setBackground(Color.white);
				bt.setForeground(Color.black);
				controller.setEnabled(false);
			}
		});
		
		controller = new Controller();
		
		add(bt, BorderLayout.CENTER);
		add(controller, BorderLayout.SOUTH);
	}

}
