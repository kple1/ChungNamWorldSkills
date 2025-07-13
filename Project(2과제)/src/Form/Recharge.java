package Form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ChildPanel.Money;
import Other.Utils;

public class Recharge extends JFrame {
	public JPanel panel;
	public JPanel panel_1;
	public JButton b2;
	public JButton b1;

	public static void main(String[] args) {
		new Recharge().setVisible(true);
	}

	Money[] m = new Money[5];
	int[] prices = new int[] {500, 1000, 5000, 10000, 50000};
	int[] amount = new int[] {0, 0, 0, 0, 0};
	public Recharge() {
		addWindowListener(new WindowAdapter () {
			
		});
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		b1 = new JButton("\uCDA9\uC804");
		b1.addActionListener(e -> {
			var r = JOptionPane.showOptionDialog(null, "충전하시겠습니까?", "질문", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {"OK"}, "OK");
			if (r == 0) {
				Utils.ok("충전이 완료되었습니다.");
				
			}
		});
		panel.add(b1);
		
		b2 = new JButton("\uCDE8\uC18C");
		b2.addActionListener(e -> {
			dispose();
			new Main().setVisible(true);
		});
		panel.add(b2);
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 5, 10, 0));
		
		for (int i = 0; i < 5; i++) {
			int capture = i;
			m[i] = new Money("datafiles/충전이미지/" + (i + 1) + ".jpg");
			m[i].controller.setEnabled(true);
			m[i].controller.left.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (m[capture].controller.amount == 0) return; 
					amount[capture] -= 1;
				}
			});
			m[i].controller.right.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					amount[capture] += 1;
				}
			});
			panel_1.add(m[i]);
		}
		
		Utils.setFrame(this, "충전");
	}
}
