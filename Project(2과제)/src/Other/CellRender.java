package Other;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRender extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = new JLabel((ImageIcon)value);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}
