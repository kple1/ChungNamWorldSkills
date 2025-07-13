package Form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import ChildPanel.MainModel;
import Other.DB;
import Other.Utils;

public class Main extends JFrame implements ActionListener {
	public JPanel panel;
	public JPanel panel_1;
	public JLabel title;
	public JTextField t1;
	public JButton b1;
	public JButton b2;
	public JPanel panel_2;
	public JPanel panel_3;
	public JLabel image;
	public JPanel panel_4;
	public JLabel l1;
	public JLabel l2;
	public JScrollPane scrollPane;
	public JTree tree;
	public JPanel panel_5;
	public JPanel panel_6;
	public JButton c1;
	public JButton c2;
	public JButton c3;
	public JScrollPane scrollPane_1;
	public JPanel view;

	public static void main(String[] args) {
		new Main().setVisible(true);
	}
	
	public Main() {
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setPreferredSize(new Dimension(800, 40));
		
		panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_1.setPreferredSize(new Dimension(500, 40));
		panel.add(panel_1, BorderLayout.WEST);
		
		title = new JLabel("ClothingStore");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		panel_1.add(title);
		
		t1 = new JTextField();
		panel_1.add(t1);
		t1.setColumns(15);
		
		b1 = new JButton("\uAC80\uC0C9");
		panel_1.add(b1);
		
		b2 = new JButton("\uB85C\uADF8\uC778");
		panel_1.add(b2);
		
		panel_2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel_2.setPreferredSize(new Dimension(300, 40));
		panel.add(panel_2, BorderLayout.EAST);
		
		panel_3 = new JPanel();
		panel_3.setVisible(false);
		panel_3.setPreferredSize(new Dimension(120, 30));
		panel_2.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		image = new JLabel("");
		image.addMouseListener(new MouseAdapter () {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new Manage().setVisible(true);
			}
		});
		panel_3.add(image, BorderLayout.WEST);
		
		panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		l1 = new JLabel("New label");
		panel_4.add(l1);
		
		l2 = new JLabel("New label");
		panel_4.add(l2);
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 400));
		getContentPane().add(scrollPane, BorderLayout.WEST);
		
		tree = new JTree();
		scrollPane.setViewportView(tree);
		
		panel_5 = new JPanel();
		getContentPane().add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		panel_6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel_5.add(panel_6, BorderLayout.NORTH);
		
		c1 = new JButton("\uAC00\uACA9\uC21C(\u2193)");
		panel_6.add(c1);
		
		c2 = new JButton("\uAC00\uACA9\uC21C(\u2191)");
		panel_6.add(c2);
		
		c3 = new JButton("\uBCC4\uC810\uC21C(\u2193)");
		panel_6.add(c3);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(700, 0));
		panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
		view = new JPanel();
		scrollPane_1.setViewportView(view);
		view.setLayout(new GridLayout(0, 4, 5, 5));
		
		reload();
		treeManage();
		
		Utils.setFrame(this, "메인");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		c1.addActionListener(this);
		c2.addActionListener(this);
		c3.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				try {
					image.setSize(40, 40);
					image.setIcon(Utils.imageSize(new ImageIcon(Login.img), image));
					
					l1.setText(Login.name);
					l2.setText(String.format("%,d원", Login.price));
					if (Login.no == 0) {
						panel_3.setVisible(false);
						b2.setVisible(true);
					} else {
						panel_3.setVisible(true);
						b2.setVisible(false);
					}	
				} catch (Exception e2) {
					
				}
			}
		});
	}
	
	List<Integer> cf = new ArrayList<>();
	List<Integer> sf = new ArrayList<>();
	void treeManage() {
		var c = DB.getCategory();
		var s = DB.getSubCategory();
		
		var model1 = new DefaultMutableTreeNode("전체");
		for (int i = 0; i < c.length; i++) {
			var node = new DefaultMutableTreeNode(c[i].toString());
			var multiple = i * 5;
			for (int j = multiple; j < multiple + 5; j++) {
				node.add(new DefaultMutableTreeNode(s[j].toString()));
			}
			model1.add(node);
		}
		var model2 = new DefaultTreeModel(model1);
		tree.setModel(model2);
		
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				var lp = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (lp.isLeaf()) {
					list = DB.getProductList("where s.sb_no = " + DB.getInt("select sb_no from subcategory where sb_name = ?", lp.getUserObject()));
					reload();
				}
			}
		});
		
		nv();
	}
	
	List<Object[]> list = DB.getProductList("");
	int rowCount = 0;
	void reload() {
		view.removeAll();
		rowCount = 0;
		cf.clear();
		sf.clear();
		
		for (int i = 0; i < list.size(); i++) {
			int capture = i;
			var p = new MainModel(list.get(i));
			p.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (Login.no == 0) {
						Utils.fail("로그인을 해주세요.");
						dispose();
						new Login().setVisible(true);
					} else {
						dispose();
						new Product(list.get(capture)[3]).setVisible(true);
					}
				}
			});
			if (list.get(i)[1].toString().contains(t1.getText()) || t1.getText().isEmpty()) {
				view.add(p);
				++rowCount;
				
				cf.add(Integer.parseInt(list.get(i)[5].toString()));
				sf.add(Integer.parseInt(list.get(i)[6].toString()));
			}
		}
		
		if (cf.stream().distinct().count() == 1 && sf.stream().distinct().count() == 1) {
			selectedNode(DB.getString("select sb_name from subcategory where sb_no = ?", sf.get(0)));
		} else if (cf.stream().distinct().count() == 1) {
			nv();
			tree.expandRow(cf.get(0));
		}
		if (rowCount == 0) {
			Utils.fail("검색 결과가 없습니다.");
		}
		view.revalidate();
		view.repaint();
	}
	
	void selectedNode(String name) {
		var root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		var foundNode = Collections.list(root.breadthFirstEnumeration())
				.stream()
				.map(x -> (DefaultMutableTreeNode)x)
				.filter(x -> x.getUserObject().toString().equals(name))
				.findFirst();
		foundNode.ifPresent(x -> tree.setSelectionPath(new TreePath(x.getPath())));
	}
	
	void nv() {
		for (int i = tree.getRowCount() - 1; i >= 0; i--) {
			tree.collapseRow(i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(b1)) {
			nv();
			list = DB.getProductList("");
			reload();
		} else if (e.getSource().equals(b2)) {
			dispose();
			new Login().setVisible(true);
		} else if (e.getSource().equals(c1)) {
			list = DB.getProductList("order by cast(pro.p_price as signed) desc");
			reload();
		} else if (e.getSource().equals(c2)) {
			list = DB.getProductList("order by cast(pro.p_price as signed) asc");
			reload();
		} else if (e.getSource().equals(c3)) {
			list = DB.getProductList("order by star desc");
			reload();
		}
	}
}