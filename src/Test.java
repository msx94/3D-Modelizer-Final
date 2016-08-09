import javax.swing.JFrame;
import javax.swing.JScrollPane;

import inter.Panel;


public class Test {

	public static void main(String[] args) {
		
		Panel drawComp = new Panel(800,800);
		
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(drawComp));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		//frame.setContentPane(drawComp);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);				

	}
	
}
