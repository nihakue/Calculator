package west.gabriel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="Exit"){
			System.exit(0);
		}
		if (e.getActionCommand() == "Show History");{
			System.out.println("show history");
			Calculator.historyFrame.setVisible(true);
			Calculator.historyFrame.setFocusableWindowState(false);
			
		}

	}

}
