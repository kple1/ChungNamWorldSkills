package Form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Other.DB;
import Other.Utils;

public class Statistics extends JFrame {
	public JPanel a;
	public JPanel b;
	public JPanel c;
	public JPanel panel;
	public Component horizontalStrut;
	public Component horizontalStrut_1;
	public JPanel percent;
	public JPanel bottomGrid;
	public JPanel backView;
	public Component horizontalStrut_2;
	public Component horizontalStrut_3;
	public JPanel view;
	public JPanel top;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JLabel title;
	public JComboBox c1;
	public Statistics() {
		
		a = new JPanel();
		getContentPane().add(a, BorderLayout.WEST);
		
		b = new JPanel();
		getContentPane().add(b, BorderLayout.SOUTH);
		
		a.setPreferredSize(new Dimension(50, 0));
		a.setLayout(new BorderLayout(0, 0));
		b.setPreferredSize(new Dimension(0, 50));
		b.setLayout(new BorderLayout(0, 0));
		
		c = new JPanel();
		b.add(c, BorderLayout.WEST);
		c.setPreferredSize(new Dimension(50, 50));
		
		panel = new JPanel();
		b.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, BorderLayout.WEST);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1, BorderLayout.EAST);
		
		a.add(Utils.line(1, 0), BorderLayout.EAST);
		
		percent = new JPanel(new GridLayout(5, 0));
		for (int i = 100; i > 0; i -= 20) {
			var label = new JLabel(i + "%");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setVerticalAlignment(SwingConstants.TOP);
			percent.add(label);
		}
		a.add(percent, BorderLayout.CENTER);
		panel.add(Utils.line(0, 1), BorderLayout.NORTH);
		
		bottomGrid = new JPanel();
		panel.add(bottomGrid, BorderLayout.CENTER);
		bottomGrid.setLayout(new GridLayout(0, 5, 20, 0));
		
		backView = new JPanel();
		getContentPane().add(backView, BorderLayout.CENTER);
		backView.setLayout(new BorderLayout(0, 0));
		
		horizontalStrut_2 = Box.createHorizontalStrut(20);
		backView.add(horizontalStrut_2, BorderLayout.WEST);
		
		horizontalStrut_3 = Box.createHorizontalStrut(20);
		backView.add(horizontalStrut_3, BorderLayout.EAST);
		
		view = new JPanel();
		backView.add(view, BorderLayout.CENTER);
		view.setLayout(new GridLayout(0, 5, 20, 0));
		view.setPreferredSize(new Dimension(400, 400));
		
		top = new JPanel();
		getContentPane().add(top, BorderLayout.NORTH);
		top.setLayout(new GridLayout(1, 0, 0, 0));
		
		panel_1 = new JPanel();
		top.add(panel_1);
		
		panel_2 = new JPanel();
		top.add(panel_2);
		
		title = new JLabel("\uD1B5\uACC4");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		panel_2.add(title);
		
		panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		top.add(panel_3);
		
		c1 = new JComboBox();
		c1.setPreferredSize(new Dimension(130, 25));
		panel_3.add(c1);
		
		c1.addItem("인기많은 선생님");
		c1.addItem("어려운 문제");
		
		c1.addActionListener(e -> {
			if (c1.getSelectedIndex() == 0) {
				list = DB.option1();
				option = 1;
			} else {
				list = DB.option2();
				option = 2;
			}
			reload();
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				reload();	
			}
		});
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				reload();
			}
		});
		
		Utils.setFrame(this, "통계");
	}
	
	List<Object[]> list = DB.option1();
	int option = 1;
	void reload() {
		view.removeAll();
		bottomGrid.removeAll();
		
		int max = 0;
		for (int i = 0; i < 5; i++) {
			var c = Integer.parseInt(list.get(i)[1].toString());
			if (max < c) {
				max = c;
			}
		}
		
		Color[] colors = new Color[] {Color.red, Color.yellow, Color.green, Color.blue, Color.pink};
		for (int i = 0; i < 5; i++) {
			var box = new JPanel();
			box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
			
			var c = Integer.parseInt(list.get(i)[1].toString());
			var colorSize = (int)(view.getHeight() * ((double)c / max));
			int emptySize = view.getHeight() - colorSize;
			
			var emptyPanel = new JPanel();
			var colorPanel = new JPanel();
			if (option == 1) {
				colorPanel.setToolTipText(list.get(i)[1] + "/" + list.get(i)[2] + "/" + list.get(i)[3]);
			} else {
				colorPanel.setToolTipText("<html><img src = 'file:datafiles/question/" + list.get(i)[0] + ".jpg' width = '300' height = '200'>");
			}
			colorPanel.setBackground(colors[i]);
			
			Utils.setSize(Integer.MAX_VALUE, emptySize, emptyPanel);
			Utils.setSize(Integer.MAX_VALUE, colorSize, colorPanel);
			
			box.add(emptyPanel);
			box.add(colorPanel);
			
			view.add(box);
		}
		
		for (int i = 0; i < 5; i++) {
			var label = new JLabel(list.get(i)[0].toString());
			label.setHorizontalAlignment(0);
			label.setVerticalAlignment(SwingConstants.TOP);
			bottomGrid.add(label);
		}
		
		view.revalidate();
		view.repaint();
		bottomGrid.revalidate();
		bottomGrid.repaint();
	}

	public static void main(String[] args) {
		new Statistics().setVisible(true);
	}
	
}
