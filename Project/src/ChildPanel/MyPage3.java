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

public class MyPage3 extends JPanel {
	public JPanel panel;
	public JScrollPane scrollPane;
	public JTable table;
	public JButton b1;
	public DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"번호", "이미지", "상품명", "구매 사이즈", "판매자명", "가격"});
	public MyPage3() {
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(panel, BorderLayout.NORTH);
		
		b1 = new JButton("\uAD6C\uB9E4\uD558\uAE30");
		b1.addActionListener(e -> {
			
		});
		panel.add(b1);
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					var p = new JPopupMenu();
					var a = new JMenuItem("삭제");
					a.addActionListener(e1 -> {
						model.removeRow(table.getSelectedRow());
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
		
		var list = DB.getShoppingBasket();
		for (int i = 0; i < list.size(); i++) {
			model.addRow(new Object[] {list.get(i)[0], new ImageIcon((byte[])list.get(i)[1]), list.get(i)[2], list.get(i)[3], list.get(i)[4], list.get(i)[5]});
		}
	}
}
