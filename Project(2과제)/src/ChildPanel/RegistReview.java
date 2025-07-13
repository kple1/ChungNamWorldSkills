package ChildPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Form.Main;
import Other.DB;
import Other.Utils;

public class RegistReview extends JFrame {
	public JLabel image;
	public JPanel panel;
	public JLabel title;
	public JPanel p1;
	public JPanel p2;
	public JPanel p3;
	public JPanel p4;
	public JPanel p5;
	public JLabel lblNewLabel;
	public JComboBox c1;
	public JLabel lblNewLabel_1;
	public JComboBox c2;
	public JLabel lblNewLabel_2;
	public JTextArea textArea;
	public JButton b1;

	public static void main(String[] args) {
		new RegistReview("SHOWER - 808").setVisible(true);
	}

	int stack = 0;
	int starCount = 0;
	int count = 0;

	public RegistReview(String name) {

		image = new JLabel("");
		image.setSize(300, 400);
		image.setIcon(Utils
				.imageSize(new ImageIcon(DB.getBytes("select p_img from productlist where p_name = ?", name)), image));
		getContentPane().add(image, BorderLayout.WEST);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 400));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		title = new JLabel(name);
		title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		panel.add(title);

		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel[] label = new JLabel[5];

		for (int i = 0; i < 5; i++) {
			int capture = i;
			label[i] = new JLabel();
			label[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (count == 14)
						return;
					if (stack == 0) {
						label[starCount]
								.setIcon(Utils.imageSize(new ImageIcon("datafiles/별점이미지/3.JPG"), label[capture]));
						stack = 1;
					} else if (stack == 1) {
						label[starCount]
								.setIcon(Utils.imageSize(new ImageIcon("datafiles/별점이미지/1.JPG"), label[capture]));
						stack = 2;
					} else {
						stack = 0;
						++starCount;
						p1.add(label[starCount]);
					}
					++count;
					revalidate();
				}
			});
			label[i].setSize(30, 30);
			label[i].setIcon(Utils.imageSize(new ImageIcon("datafiles/별점이미지/2.JPG"), label[i]));
		}
		p1.setAlignmentX(Component.LEFT_ALIGNMENT);
		p1.add(label[0]);
		panel.add(p1);

		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(p2);

		p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(p3);

		p4 = new JPanel();
		p4.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
		p4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
		p4.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(p4);

		lblNewLabel_2 = new JLabel("\uB9AC\uBDF0 \uC4F0\uAE30");
		p4.add(lblNewLabel_2);

		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		p4.add(textArea);

		p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p5.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(p5);

		Utils.setSize(Integer.MAX_VALUE, 40, p1, p2, p3, p5);

		b1 = new JButton("저장");
		b1.addActionListener(e -> {
			if (textArea.getText().isEmpty()) {
				Utils.fail("빈칸이 있습니다.");
			} else {
				Utils.ok("리뷰가 저장되었습니다.");
				dispose();
				new Main().setVisible(true);
			}
		});
		p5.add(b1);

		lblNewLabel_1 = new JLabel("\uD558\uC704 \uCE74\uD14C\uACE0\uB9AC");
		p3.add(lblNewLabel_1);

		c2 = new JComboBox();
		c2.addItem(DB.getString("select sb_name from subcategory where sb_no = ?",
				DB.getInt("select sb_no from productlist where p_name = ?", name)));
		c2.setPreferredSize(new Dimension(100, 20));
		p3.add(c2);

		lblNewLabel = new JLabel("\uC0C1\uC704 \uCE74\uD14C\uACE0\uB9AC");
		p2.add(lblNewLabel);

		c1 = new JComboBox();
		c1.addItem(DB.getString("select c_name from category where c_no = ?",
				DB.getString("select c_no from subcategory where sb_no = ?",
						DB.getInt("select sb_no from productlist where p_name = ?", name))));
		c1.setPreferredSize(new Dimension(100, 20));
		p2.add(c1);

		Utils.setFrame(this, "리뷰 등록");
	}
}
