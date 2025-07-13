package Model;

import javax.swing.JPanel;

import Other.Utils;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class TeacherListModel extends JPanel {
	public JPanel panel;
	public JPanel panel_1;
	public JLabel image;
	public JPanel box1;
	public JLabel pencil;
	public JPanel box2;

	public TeacherListModel(Object[] o) {
		setLayout(new GridLayout(2, 0, 0, 0));
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel);
		
		image = new JLabel("");
		image.setSize(100, 100);
		image.setIcon(Utils.imageSize(new ImageIcon("datafiles/teacher/" + o[0] + ".jpg"), image));
		image.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(image);
		
		box1 = new JPanel();
		panel.add(box1);
		box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));
		
		panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		add(panel_1);
		
		pencil = new JLabel("");
		pencil.setSize(50, 50);
		pencil.setIcon(Utils.imageSize(new ImageIcon("datafiles/icon/pencil.png"), pencil));
		panel_1.add(pencil);
		
		box2 = new JPanel();
		panel_1.add(box2);
		box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));
		
		var l1 = new JLabel("이름: " + o[1]);
		var l2 = new JLabel("학력: " + o[2]);
		var l3 = new JLabel("총 문제풀이한 개수: " + o[3] + "개");
		var l4 = new JLabel("답변률: " + o[4] + "%");
		l4.setForeground(Color.red);
		
		box1.add(l1);
		box1.add(l2);
		box2.add(l3);
		box2.add(l4);
	}
	
	public JLabel getImage() {
		return image;
	}

}
