package Other;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRender extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
		var label = new JLabel();
		label.setSize(200, 150);
		label.setHorizontalAlignment(0);
		label.setIcon(Utils.imageSize(new ImageIcon(value.toString()), label));
		return label;
	}
}
