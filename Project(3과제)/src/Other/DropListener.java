package Other;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DropListener extends DropTargetAdapter {

	JLabel label;
	public static int sel = 0;
	
	public DropListener(JLabel label) {
		this.label = label;
	}


	@Override
	public void drop(DropTargetDropEvent event) {
		event.acceptDrop(DnDConstants.ACTION_COPY);
		try {
			var data = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
			var files = (List<?>)data;
			for (var file: files) {
				label.setIcon(Utils.imageSize(new ImageIcon(file.toString()), label));
			}
			sel = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
