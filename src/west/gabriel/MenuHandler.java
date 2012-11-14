package west.gabriel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="Exit"){
			System.exit(0);
		}

	}

}
