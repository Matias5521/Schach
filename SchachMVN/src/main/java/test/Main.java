package test;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Main {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> new uiHelloWorld());
	}
}

class uiHelloWorld extends JFrame {
	
	public uiHelloWorld() {
		
		JLabel label = new JLabel("Hello World!", JLabel.CENTER);
		add(label);

		setSize(300, 150);
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("UI");
		setVisible(true);
		
	}
}