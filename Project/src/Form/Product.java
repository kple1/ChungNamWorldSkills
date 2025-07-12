package Form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ChildPanel.ProductController;
import ChildPanel.ProductModel;
import Other.DB;
import Other.Utils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Product extends JFrame {
	public JLabel image;
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel l1;
	public JPanel p1;
	public JLabel l2;
	public JLabel l3;
	public JLabel l4;
	public JPanel p2;
	public JLabel l5;
	public JPanel p3;

	public static void main(String[] args) {
		new Product(1).setVisible(true);
	}
	
	Object[] list;
	public JButton b1;
	public JButton b2;
	public JPanel view;
	public int sbno;
	public Object pno;
	public Product(Object pno) {
		this.pno = pno;
		sbno = DB.getInt("select sb_no from productlist where p_no = ?", pno);
		list = DB.sendProductData(sbno, pno);
		
		image = new JLabel("");
		image.setBorder(BorderFactory.createLineBorder(Color.black));
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setPreferredSize(new Dimension(250, 400));
		image.setSize(250, 400);
		getContentPane().add(image, BorderLayout.WEST);
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 400));
		getContentPane().add(scrollPane, BorderLayout.EAST);
		
		view = new JPanel(new GridLayout(0, 1, 0, 0));
		scrollPane.setViewportView(view);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 400));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		l1 = new JLabel("New label");
		panel.add(l1);
		
		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(p1);
		
		l2 = new JLabel("New label");
		panel.add(l2);
		
		l3 = new JLabel("New label");
		panel.add(l3);
		
		l4 = new JLabel("New label");
		panel.add(l4);
		
		p2 = new JPanel(new GridLayout(0, 4, 10, 0));
		p2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(p2);
		
		l5 = new JLabel("New label");
		panel.add(l5);
		
		p3 = new JPanel();
		p3.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(p3);
		
		Utils.setSize(Integer.MAX_VALUE, 30, l1, l2, l3, l4, l5);
		Utils.setSize(Integer.MAX_VALUE, 40, p1, p3);
		
		b1 = new JButton("\uAD6C\uB9E4");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean state = false;
				for (int i = 0; i < 4; i++) {
					if (pc[i].controller.bt.getBackground() != Color.white) {
						state = true;
						break;
					}
				}
				if (state) {
					Utils.fail("사이즈를 선택해 주세요.");
				} else {
					dispose();
					new Payment().setVisible(true);
				}
			}
		});
		p3.add(b1);
		
		b2 = new JButton("\uC7A5\uBC14\uAD6C\uB2C8");
		b2.addActionListener(e -> {
			if (Login.no == 0) {
				Utils.fail("로그인을 해주세요.");
			} else {
				dispose();
				new MyPage(3).setVisible(true);
			}
		});
		p3.add(b2);
		Utils.setSize(Integer.MAX_VALUE, 80, p2);
		Utils.setFrame(this, "상품");
		
		set();
	}
	
	int totalPrice = 0;
	int price;
	ProductController[] pc = new ProductController[4];
	void set() {
		p1.removeAll();
		p2.removeAll();
		view.removeAll();
		totalPrice = 0;
		
		var a = DB.getProductInfo(sbno, pno);
		image.setSize(250, 400);
		image.setIcon(Utils.imageSize(new ImageIcon((byte[])list[1]), image));
		
		l1.setText("제품명 : " + list[2].toString());
		try {
			var starCount = Integer.parseInt(list[4].toString());
			repeat(5 - starCount, 1);
			repeat(5 - (5 - starCount), 2);
		} catch (Exception e) {
			repeat(5, 2);
		}
		l2.setText(list[3].toString());
		l3.setText(list[5].toString());
		
		price = Integer.parseInt(list[6].toString());
		if (Utils.findSale(Integer.parseInt(pno.toString())) != price) {
			price = Utils.findSale(Integer.parseInt(pno.toString()));
		}
		totalPrice += price;
		
		l4.setText(String.format("상품 금액 : %,d", price));
		l5.setText(String.format("총 금액 : %,d", price));
		
		String[] size = new String[] {"S", "M", "L", "XL"};
		for (int i = 0; i < 4; i++) {
			int capture = i;
			pc[i] = new ProductController(size[i]);
			pc[i].controller.left.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (pc[capture].controller.amount == 0) return;
					totalPrice -= price;
					l5.setText(String.format("총 금액 : %,d", totalPrice));
				}
			});
			pc[i].controller.right.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					totalPrice += price;
					l5.setText(String.format("총 금액 : %,d", totalPrice));
				}
			});
			pc[i].controller.setEnabled(false);
			p2.add(pc[i]);
		}
		
		for (int i = 0; i < a.size(); i++) {
			int capture = i;
			var pm = new ProductModel((byte[])a.get(i)[0], a.get(i)[1].toString(), Integer.parseInt(a.get(i)[2].toString()));
			pm.getImage().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					pno = a.get(capture)[2];
					list = DB.sendProductData(sbno, pno);
					set();
				}
			});
			view.add(pm);
		}
		p1.revalidate();
		p2.revalidate();
		view.revalidate();
		p1.repaint();
		p2.repaint();
		view.repaint();
	}
	
	void repeat(int c, int type) {
		for (int i = 0; i < c; i++) {
			var image = new JLabel();
			image.setSize(30, 30);
			if (type == 1) {
				image.setIcon(Utils.imageSize(new ImageIcon("datafiles/별점이미지/1.PNG"), image));
			} else {
				image.setIcon(Utils.imageSize(new ImageIcon("datafiles/별점이미지/2.PNG"), image));
			}
			p1.add(image);
		}
	}
}