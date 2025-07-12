package ChildPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Controller extends JPanel {
	public int amount = 0;
	public JButton bt;
	public JLabel left, right, num;
	public Controller() {
		setLayout(new BorderLayout());
		
		num = new JLabel("0");
		num.setHorizontalAlignment(SwingConstants.CENTER);
		
		left = new JLabel("-");
		left.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!state) return;
				if (amount != 0) {
					--amount;
					num.setText(amount + "");
				}
			}
		});
		left.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 16));
		
		right = new JLabel("+");
		right.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!state) return;
				++amount;
				num.setText(amount + "");
			}
		});
		right.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 16));
		
		add(left, BorderLayout.WEST);
		add(num, BorderLayout.CENTER);
		add(right, BorderLayout.EAST);
	}

	boolean state;
	public void setEnabled(boolean state) {
		this.state = state;
		
		num.setEnabled(state);
		left.setEnabled(state);
		right.setEnabled(state);
	}
}
