package inter;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9033313953418007681L;

	public Window() {
		
	}
	
	public void build() {
		// The window containing the modelization 
		JFrame window = new JFrame();
		
		//Define a title for the window
		window.setTitle("3D Modelization (beta)");
		
		//Define the size of the window
		window.setSize(800, 800);
		
		//To locate the object at the center
		window.setLocationRelativeTo(null);
		
		//End the process by using the red cross to close the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add a menuBar
		JMenuBar menuBar = new JMenuBar();
		
		////////////////////// Create the elements of the menuBars //////////////////////
		//The first menu
		JMenu menu1 = new JMenu("File");
		JMenuItem loadFile = new JMenuItem(new MultiActions(this,"load file","Load file"));
		JMenuItem loadFolder = new JMenuItem(new MultiActions(this,"load folder","Load folder"));
		JMenuItem exit = new JMenuItem(new MultiActions(this,"exit", "Exit"));
		JMenuItem saveModel = new JMenuItem(new MultiActions(this,"save model","Save model"));
		JMenuItem loadModel = new JMenuItem(new MultiActions(this,"load model","Load model"));
		
		//Add the listener
		loadFile.addActionListener(new WindowsListener(window, "loadFile"));
		loadFolder.addActionListener(new WindowsListener(window, "loadFolder"));
		saveModel.addActionListener(new WindowsListener(window,"saveModel"));
		loadModel.addActionListener(new WindowsListener(window, "loadModel"));
		
		//Add the elements to the menu
		menu1.add(loadFile);
		menu1.add(loadFolder);
		menu1.addSeparator();
		menu1.add(saveModel);
		menu1.add(loadModel);
		menu1.addSeparator();
		menu1.add(exit);
		
		//the second menu
		JMenu menu2 = new JMenu("Edit");
		JMenuItem chooseIso = new JMenuItem(new MultiActions(this, "chooseIso","Choose the iso"));
		JMenuItem chooseAccuracy = new JMenuItem(new MultiActions(this, "chooseAcc","Choose the accuracy"));
		
		//Add the listener
		chooseIso.addActionListener(new WindowsListener(window, "chooseIso"));
		chooseAccuracy.addActionListener(new WindowsListener(window,"chooseAcc"));
		
		//Add the elements to the menu
		menu2.add(chooseIso);
		menu2.add(chooseAccuracy);
		
		//The third menu
		JMenu menu3 = new JMenu("?");
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//Add the menu to the menu bars
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		
		//Add the menuBars
		window.setJMenuBar(menuBar);
		
		//Set the window visible
		window.setVisible(true);
	}
	
}
