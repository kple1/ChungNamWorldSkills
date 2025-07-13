package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Other.Utils;

public class QuestionListModel extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JPanel panel;
	public JPanel panel_1;
	public JButton b1;
	public JLabel image;

	/**
	 * Create the panel.
	 */
	public QuestionListModel(Object[] o, JFrame f) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		label = new JLabel(String.format("%s (%s)", o[0], o[1]));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		
		label_1 = new JLabel(o[2].toString());
		label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label_1);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 150));
		panel.setMaximumSize(new Dimension(200, 150));
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		b1 = new JButton("\uBB38\uC81C \uD480\uC5B4\uC8FC\uAE30");
		b1.addActionListener(e -> {
			if (o[4].toString().equals("0")) {
				Utils.fail("이미 풀어준 문제입니다.");
			} else {
				f.dispose();
			}
		});
		panel_1.add(b1, BorderLayout.EAST);
		
		image = new JLabel("");
		image.setBorder(BorderFactory.createLineBorder(Color.black));
		image.setSize(200, 130);
		image.setIcon(Utils.imageSize(new ImageIcon("datafiles/question/" + o[3] + ".jpg"), image));
		panel.add(image, BorderLayout.CENTER);
		
		Utils.setColor(b1);
	}

}
