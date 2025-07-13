package Form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ChildPanel.PaymentModel;
import Other.DB;
import Other.Utils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Payment extends JFrame {
	public JPanel panel;
	public JCheckBox c1;
	public JPanel bottom;
	public JPanel panel_1;
	public JButton b1;
	public JButton b2;
	public JPanel panel_2;
	public JPanel panel_3;
	public JPanel panel_4;
	public JScrollPane scrollPane;
	public JPanel view;
	int totalPrice = 0;
	int salePrice = 0;
	int resultPrice = 0;
	JLabel l1, l2, l3, l4;
	public static void main(String[] args) {
		new Payment().setVisible(true);
	}

	public Payment() {
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		getContentPane().add(panel, BorderLayout.NORTH);
		
		c1 = new JCheckBox("\uC804\uCCB4\uC120\uD0DD");
		c1.addActionListener(e -> {
			for (int i = 0; i < list.size(); i++) {
				if (!pm[i].c1.isSelected()) {
					totalPrice += pm[i].price;
					pm[i].c1.setSelected(true);
					salePrice += Utils.findSale(pm[i].pno, pm[i].price);
				}
			}
			set();
		});
		panel.add(c1);
		
		bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(400, 300));
		getContentPane().add(bottom, BorderLayout.SOUTH);
		bottom.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		bottom.add(panel_1, BorderLayout.SOUTH);
		
		b1 = new JButton("\uCDE8\uC18C");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Main().setVisible(true);
			}
		});
		panel_1.add(b1);
		
		b2 = new JButton("\uACB0\uC81C\uD558\uAE30");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Login.price > resultPrice) {
					dispose();
					new Recharge().setVisible(true);
				} else {
					Utils.ok("°áÁ¦°¡ ¿Ï·áµÇ¾ú½À´Ï´Ù.");
					int update = Login.price - totalPrice;
					DB.update("update user set u_price = ? and u_no = ?", update, Login.no);
					for (int i = 0; i < list.size(); i++) {
						if (pm[i].c1.isSelected()) {
							DB.update("delete from shoppingbasket where s_no = ?", pm[i].sno);
						}
					}
					dispose();
					new Main().setVisible(true);
				}
			}
		});
		panel_1.add(b2);
		
		panel_2 = new JPanel();
		bottom.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(4, 1, 0, 0));
		
		String[] ls = new String[] {"ÃæÀü ÀÜ¾×", "ÁÖ¹® ±Ý¾×", "ÇÒÀÎ ±Ý¾×", "°áÁ¦ ±Ý¾×"};
		for (int i = 0; i < ls.length; i++) {
			var label = new JLabel(ls[i]);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel_3.add(label);
		}
		
		panel_4 = new JPanel(new GridLayout(4, 1, 0, 0));
		panel_2.add(panel_4);
		
		Utils.setColor(b1, b2);
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(400, 200));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		view = new JPanel();
		scrollPane.setViewportView(view);
		view.setLayout(new GridLayout(0, 1, 0, 10));
		
		l1 = new JLabel(String.format("\\ %,d", DB.getInt("select u_price from user where u_no = ?", Login.no)));
		l1.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		l2 = new JLabel();
		l2.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		l3 = new JLabel();
		l4 = new JLabel();
		l3.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		l4.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		
		panel_4.add(l1);
		panel_4.add(l2);
		panel_4.add(l3);
		panel_4.add(l4);
		
		set();
		
		var list = DB.getPayment();
		for (int i = 0; i < list.size(); i++) {
			int capture = i;
			pm[i] = new PaymentModel((byte[])list.get(i)[0], list.get(i)[1].toString(), Integer.parseInt(list.get(i)[2].toString()), Integer.parseInt(list.get(i)[3].toString()), Integer.parseInt(list.get(i)[4].toString()));
			pm[i].c1.addActionListener(e -> {
				if (pm[capture].c1.isSelected()) {
					totalPrice += pm[capture].price;
					salePrice += Utils.findSale(pm[capture].pno, pm[capture].price);
				} else {
					totalPrice -= pm[capture].price;
					salePrice -= Utils.findSale(pm[capture].pno, pm[capture].price);
				}
				set();
			});
			view.add(pm[i]);
		}
		
		Utils.setFrame(this, "°áÁ¦");
	}
	
	List<Object[]> list = DB.getPayment();
	PaymentModel[] pm = new PaymentModel[list.size()];
	void set() {
		l2.setText(String.format("\\ %,d", totalPrice));
		l3.setText(String.format("\\ %,d", salePrice));
		l4.setText(String.format("\\ %,d", totalPrice - salePrice));
	}
}
