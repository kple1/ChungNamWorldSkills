package Form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Other.AlignTop;
import Other.CellRender;
import Other.DB;
import Other.Utils;

public class MyQuestion extends JFrame {
	public JPanel panel;
	public JLabel label;
	public Component horizontalStrut;
	public Component verticalStrut;
	public Component horizontalStrut_1;
	public JPanel panel_1;
	public JPanel top;
	public JComboBox c1;
	public JScrollPane scroll;
	public static JTable table;
	public static int row = 0;
	public static String qx = "";
	DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"번호", "선생님", "타이틀", "질문", "답변", "문제이미지"});
	public MyQuestion() {
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		label = new JLabel("\uB0B4 \uC9C8\uBB38");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label, BorderLayout.CENTER);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		getContentPane().add(horizontalStrut, BorderLayout.WEST);
		
		verticalStrut = Box.createVerticalStrut(20);
		getContentPane().add(verticalStrut, BorderLayout.SOUTH);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		getContentPane().add(horizontalStrut_1, BorderLayout.EAST);
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		top = new JPanel();
		FlowLayout flowLayout = (FlowLayout) top.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_1.add(top, BorderLayout.NORTH);
		
		c1 = new JComboBox();
		c1.setPreferredSize(new Dimension(100, 25));
		top.add(c1);
		
		scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(800, 400));
		panel_1.add(scroll, BorderLayout.CENTER);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					var p = new JPopupMenu();
					var a = new JMenuItem("변경");
					a.addActionListener(e1 -> {
						row = table.getSelectedRow();
						qx = table.getValueAt(row, 3).toString().replace("<html>", "").replace("</html>", "");
						if (!table.getValueAt(row, 4).toString().equals("<html></html>")) {
							Utils.fail("답변이 있는 경우는 선생님을 변경할 수 없습니다.");
						} else {
							dispose();
							new TeacherList("내질문").setVisible(true);
						}
					});
					p.add(a);
					p.show(table, e.getX(), e.getY());
				}
			}
		});
		table.setModel(model);
		table.setRowHeight(150);
		table.getColumnModel().getColumn(2).setCellRenderer(new AlignTop());
		table.getColumnModel().getColumn(3).setCellRenderer(new AlignTop());
		table.getColumnModel().getColumn(4).setCellRenderer(new AlignTop());
		table.getColumnModel().getColumn(5).setCellRenderer(new CellRender());
		table.getColumnModel().getColumn(5).setPreferredWidth(200);
		scroll.setViewportView(table);
		
		c1.addItem("전체");
		c1.addItem("답변완료");
		c1.addItem("답변 미완료");
		
		c1.addActionListener(e -> {
			reload();
		});
		reload();
		Utils.setFrame(this, "내 질문");
	}
	
	void reload() {
		model.setRowCount(0);
		var list = DB.array("SELECT t.name, title, questionexplan, explan, type FROM catalog c\r\n"
				+ "join teacher t on t.tno = c.tno\r\n"
				+ "where uno = ?", Login.no);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)[3] == null) list.get(i)[3] = "";
			
			if (c1.getSelectedIndex() == 0) {
				add(list, i);
			}
			
			if (c1.getSelectedIndex() == 1 && !list.get(i)[3].toString().isEmpty()) {
				add(list, i);
			}
			
			if (c1.getSelectedIndex() == 2 && list.get(i)[3].toString().isEmpty()) {
				add(list, i);
			}
		}
	}
	
	void add(List<Object[]> list, int i) {
		model.addRow(new Object[] {
				i + 1,
				list.get(i)[0],
				"<html>" + list.get(i)[1] + "</html>",
				"<html>" + list.get(i)[2] + "</html>",
				"<html>" + list.get(i)[3] + "</html>",
				new ImageIcon("datafiles/question/" + list.get(i)[4]+ ".jpg")
		});
	}

	public static void main(String[] args) {
		new MyQuestion().setVisible(true);
	}

}
