package inter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import algebra.Matrix;
import algebra.Triangle;
import utility.Load;
import utility.Util;

public class WindowsListener implements ActionListener {

	// The attributes
	private static JFrame window ;
	private static Panel panel;
	private static String path;
	private static boolean isoInput;
	private static String acc;
	public static double isosurface;
	private String actionType;
	
	// Used to select the files
	private JFileChooser chooser = new JFileChooser();
	
	/**
	 * Constructor
	 * @param window
	 */
	public WindowsListener(JFrame window) {
		WindowsListener.setWindow(window);
	}
	
	/**
	 * Constructor
	 * @param window
	 * @param actionType
	 */
	public WindowsListener(JFrame window, String actionType) {
		WindowsListener.setWindow(window);
		this.setActionType(actionType);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String path;
		String[] accTable = {"Low","Medium","High"};
		
		switch (actionType) {
			case "loadFile" :
				// Change the access to the features
				setIsoInput(false);
				
				// Use the JFile Chooser
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select your file");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		         
				// Show all the files of the folder
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
		             
					// The path of the file
					path = chooser.getSelectedFile().getPath();
	               
					// Check if the path is correct or not
					File myFile = new File(path);
					if (!myFile.exists()) {	
						// The entered path is not correct
						JOptionPane.showMessageDialog(null, "The entered file is unfindable","Error",JOptionPane.ERROR_MESSAGE);			
					} else {
						// The entered path is correct
						this.setPath(path);
						
						// Instantiation of a Panel object
						panel = new Panel(path);
						panel.setBackground(Color.BLACK);
						window.setContentPane(panel);
						
						// Set the window visible
						window.setVisible(true);
		                
					}
						
		         } else {
		        	 
		        	 JOptionPane.showMessageDialog(null, "The entered file is unfindable","Error",JOptionPane.ERROR_MESSAGE);
		        	 
	             }
				
				break;
				
			case "loadFolder" : {
				// Change the access to the features
				setIsoInput(true);
				
				// Use the JFile Chooser
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select your folder");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		         
				// Show all the files of the folder
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {

					// The path of the folder
					path = chooser.getSelectedFile().getPath();
					
					File myFolder = new File(path);
					if (!myFolder.exists()) {
						// The entered path is not correct
						JOptionPane.showMessageDialog(null, "The entered folder is unfindable","Error",JOptionPane.ERROR_MESSAGE);	
					} else {
						//The entered path is correct
						this.setPath(path);
						
						// Ask for the isosurface value
						String value = JOptionPane.showInputDialog(null, "Choose your Isosurface");
						
						// Check if the entered value is correct
						if (Util.isValidIso(value)) {
							
							// Ask the accuracy
							acc = (String)JOptionPane.showInputDialog(null, 
								      "Please choose your accuracy",
								      "Accuracy",
								      JOptionPane.QUESTION_MESSAGE,
								      null,
								      accTable,
								      accTable[2]);
							
							isosurface = Double.parseDouble(value) ;
							
							// Load all the files of the folder
							Load.listFilesForFolder(new File(path));			
							
							// Retrieve of the list containing all the 2D slices
							ArrayList<Matrix> matrixList = Load.getMatrixList();
							
							// Compute of the minimal size
							int minRow = 1000;
							int minCol = 1000;
							for (Matrix m : matrixList) {
								if (m.nRows() < minRow) minRow = m.nRows();
								if (m.nCols() < minCol) minCol = m.nCols();
							} 
							
							// Instantiation of a Panel Object
							panel = new Panel(minRow, minCol, matrixList, isosurface, acc);
							panel.setBackground(Color.BLACK);
							
							// Add the Characteristic of the 3D Reconstruction
							JLabel label = new JLabel("                                 Quality : " + acc + "            IsoSurface selected : " + isosurface + "            Number of Triangles drawn by the algorithm : " + panel.getTriList().size());	
							label.setForeground(Color.WHITE);
							label.setBackground(Color.BLACK);
							label.setOpaque(true);							
							panel.add(label,BorderLayout.SOUTH);
							
							// Add of the content
							window.setContentPane(panel);
						    
							// Set the window visible
							window.setVisible(true);
							
						} else {
							// The entered double is not correct
							JOptionPane.showMessageDialog(null, "This is not a correct value. Please, enter a number between 0 and 1","Error",JOptionPane.ERROR_MESSAGE);
						}
					
					}
					
				} else {
		        	 
		        	 JOptionPane.showMessageDialog(null, "The entered file is unfindable","Error",JOptionPane.ERROR_MESSAGE);
		        	 
				}			
				
				break;
				
			}
			
			case "chooseIso" : {
				// Check if the user can use this feature
				if (isoInput) {
				
					// Change the access to the features
					setIsoInput(true);
					
					// Ask for the isosurface value
					String value = JOptionPane.showInputDialog(null, "Choose your Isosurface");
					// Check if the entered value is correct
					if (Util.isValidIso(value)) {
					
						// The new iso value
						isosurface = Double.parseDouble(value) ;
						
						// Check if the current path is null or not
						if (WindowsListener.path != null) {
							
							Load.listFilesForFolder(new File(WindowsListener.path));			
							
							// Retrieve of the list containing all the 2D slices
							ArrayList<Matrix> matrixList = Load.getMatrixList();
							
							// Compute of the minimal size
							int minRow = 1000;
							int minCol = 1000;
							for (Matrix m : matrixList) {
								if (m.nRows() < minRow) minRow = m.nRows();
								if (m.nCols() < minCol) minCol = m.nCols();
							} 
							
							// Instantiation of a Panel Object
							panel = new Panel(minRow, minCol, matrixList, isosurface, acc);
							panel.setBackground(Color.BLACK);
						    
							// Add the Characteristic of the 3D Reconstruction
							JLabel label = new JLabel("                                 Quality : " + acc + "            IsoSurface selected : " + isosurface + "            Number of Triangles drawn by the algorithm : " + panel.getTriList().size());	
							label.setForeground(Color.WHITE);
							label.setBackground(Color.BLACK);
							label.setOpaque(true);							
							panel.add(label,BorderLayout.SOUTH);
							
							// Add of the content
							window.setContentPane(panel);
						    
							// Set the window visible
							window.setVisible(true);
							
						}
					
					} else {
						// The entered double is not correct
						JOptionPane.showMessageDialog(null, "This is not a correct value. Please, enter a number between 0 and 1","Error",JOptionPane.ERROR_MESSAGE);				
						
					}
						
				}
				
				break;
				
			}
			
			case "chooseAcc" : {
				
				// Change the access to the features
				setIsoInput(true);
					
				// Check if the current path is null or not
				if (WindowsListener.path != null) {
					
					// Ask the accuracy
					acc = (String)JOptionPane.showInputDialog(null, 
						      "Please choose your accuracy",
						      "Accuracy",
						      JOptionPane.QUESTION_MESSAGE,
						      null,
						      accTable,
						      accTable[2]);
					
					Load.listFilesForFolder(new File(WindowsListener.path));			
					
					// Retrieve of the list containing all the 2D slices
					ArrayList<Matrix> matrixList = Load.getMatrixList();
					
					// Compute of the minimal size
					int minRow = 1000;
					int minCol = 1000;
					for (Matrix m : matrixList) {
						if (m.nRows() < minRow) minRow = m.nRows();
						if (m.nCols() < minCol) minCol = m.nCols();
					} 
					
					// Instantiation of a Panel Object
					panel = new Panel(minRow, minCol, matrixList, isosurface, acc);
					panel.setBackground(Color.BLACK);
				    
					// Add the Characteristic of the 3D Reconstruction
					JLabel label = new JLabel("                                 Quality : " + acc + "            IsoSurface selected : " + isosurface + "            Number of Triangles drawn by the algorithm : " + panel.getTriList().size());	
					label.setForeground(Color.WHITE);
					label.setBackground(Color.BLACK);
					label.setOpaque(true);							
					panel.add(label,BorderLayout.SOUTH);
					
					// Add of the content
					window.setContentPane(panel);
				    
					// Set the window visible
					window.setVisible(true);
					
				}
					
			}
			
			break;

			case "saveModel" : {
				
				if (isoInput) {
				
					// Use the JFile Chooser
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Save your Model");
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			         
					// Show all the files of the folder
					chooser.setAcceptAllFileFilterUsed(false);
					if (chooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
						// The path of the folder
						path = chooser.getSelectedFile().getPath();
						
						try {
							
							Util.saveModel(path, panel.getTriList());
							
						} catch (IOException e1) {
							
							e1.printStackTrace();
							
						}
						
					}
					
				}
				break;
			}
			
			case "loadModel" : {		
				// Change the access to the features
				setIsoInput(true);
				
				// Use the JFile Chooser
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select your folder");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		         
				// Show all the files of the folder
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {

					// The path of the file
					path = chooser.getSelectedFile().getPath();
					
					File myFile = new File(path);
					if (!myFile.exists()) {
						// The entered path is not correct
						JOptionPane.showMessageDialog(null, "The entered file is unfindable","Error",JOptionPane.ERROR_MESSAGE);	
					} else {
						//The entered path is correct
						this.setPath(path);
										
						// Load the model
						try {
							
							ArrayList<Triangle> triList = (ArrayList<Triangle>) Util.loadModel(path);
							// Instantiation of a Panel Object
							panel = new Panel(triList);
							panel.setBackground(Color.BLACK);
						    
							// Add the Characteristic of the 3D Reconstruction
							JLabel label = new JLabel("  Number of Triangles drawn by the algorithm : " + triList.size());	
							label.setForeground(Color.WHITE);
							label.setBackground(Color.BLACK);
							label.setOpaque(true);							
							panel.add(label,BorderLayout.SOUTH);
							
							// Add of the content
							window.setContentPane(panel);
						    
							// Set the window visible
							window.setVisible(true);
							
						} catch (ClassNotFoundException | IOException e1) {

							e1.printStackTrace();
							
						}			
					
					}
					
				} else {
		        	 
		        	 JOptionPane.showMessageDialog(null, "The entered file is unfindable","Error",JOptionPane.ERROR_MESSAGE);
		        	 
				}			
				
				break;
			}
			
		}
						
	}
	
	/**
	 * Return the current window
	 * @return
	 */
	public static JFrame getWindow() {
		return window;
	}

	/**
	 * Set the current window
	 * @param window
	 */
	public static void setWindow(JFrame window) {
		WindowsListener.window = window;
	}
	
	/**
	 * Return the current path stored 
	 * @return
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Set the path of the file/folder entered by the user
	 * @param path
	 */
	public void setPath(String path) {
		WindowsListener.path = path;
	}

	/**
	 * Return the type of the action, load file or load folder
	 * @return
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * Set the type of the action, load file or load folder
	 * @param actionType
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * Check if the user can use the feature to change the iso
	 * @return
	 */
	public boolean isIsoInput() {
		return isoInput;
	}

	/**
	 * Change the current authorization of the user concerning the changing of the iso
	 * @param isoInput
	 */
	public void setIsoInput(boolean isoInput) {
		WindowsListener.isoInput = isoInput;
	}

}
