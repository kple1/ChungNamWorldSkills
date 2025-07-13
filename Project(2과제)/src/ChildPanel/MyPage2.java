package ChildPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Other.CellRender;
import Other.DB;

public class MyPage2 extends JPanel {
	public JPanel panel;
	public JScrollPane scrollPane;
	public JTable table;
	public JButton b1;
	public JComboBox c2;
	public JLabel lblNewLabel;
	public JComboBox c1;
	public JLabel lblNewLabel_1;
	public JTextField t1;
	public JLabel lblNewLabel_2;
	public DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"번호", "이미지", "상품명", "구매 사이즈", "구매 날짜", "가격"});
	
	JFrame f;
	public MyPage2(JFrame f) {
		this.f = f;
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(panel, BorderLayout.NORTH);
		
		lblNewLabel_2 = new JLabel("\uC0C1\uD488\uBA85");
		panel.add(lblNewLabel_2);
		
		t1 = new JTextField();
		panel.add(t1);
		t1.setColumns(15);
		
		lblNewLabel_1 = new JLabel("\uC6D4");
		panel.add(lblNewLabel_1);
		
		c1 = new JComboBox();
		for (int i = 1; i <= 12; i++) {
			c1.addItem(i);
		}
		c1.setSelectedIndex(0);
		c1.setPreferredSize(new Dimension(100, 20));
		panel.add(c1);
		
		lblNewLabel = new JLabel("~\uC6D4");
		panel.add(lblNewLabel);
		
		c2 = new JComboBox();
		for (int i = 1; i <= 12; i++) {
			c2.addItem(i);
		}
		c2.setSelectedIndex(11);
		c2.setPreferredSize(new Dimension(100, 20));
		panel.add(c2);
		
		b1 = new JButton("검색");
		b1.addActionListener(e -> {
			reload();
		});
		panel.add(b1);
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					var p = new JPopupMenu();
					var a = new JMenuItem("리뷰하러 가기");
					a.addActionListener(e1 -> {
						f.dispose();
						new RegistReview(table.getValueAt(table.getSelectedRow(), 2).toString()).setVisible(true);
					});
					p.add(a);
					p.show(table, e.getX(), e.getY());
				}
			}
		});
		table.setModel(model);
		table.setRowHeight(100);
		table.getColumnModel().getColumn(1).setCellRenderer(new CellRender());
		scrollPane.setViewportView(table);
		
		reload();
	}
	
	void reload() {
		model.setRowCount(0);
		var list = DB.getPurchaseList();
		var now1 = LocalDate.parse(String.format("2024-%02d-%02d", c1.getSelectedIndex() + 1, 01));
		var get = LocalDate.parse(String.format("2024-%02d-%02d", c2.getSelectedIndex() + 1, 01));
		var now2 = LocalDate.parse(String.format("2024-%02d-%02d", c2.getSelectedIndex() + 1, get.lengthOfMonth()));
		for (int i = 0; i < list.size(); i++) {
			var convert = LocalDate.parse(list.get(i)[4].toString());
			if (list.get(i)[2].toString().contains(t1.getText()) || t1.getText().isEmpty()) {
				if (!convert.isBefore(now1) && !convert.isAfter(now2)) {
					model.addRow(new Object[] {list.get(i)[0], new ImageIcon((byte[])list.get(i)[1]), list.get(i)[2], list.get(i)[3], list.get(i)[4], list.get(i)[5]});
				}
			}
		}
	}

}
