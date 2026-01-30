package frontend;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public MainWindow(String text) {
		setTitle("OSGI window");
		setSize(500,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		add(label);
	}

}
