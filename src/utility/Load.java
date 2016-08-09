package utility;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import algebra.Matrix;

public class Load {

	//The list containing all the images matrix
	private static ArrayList<Matrix> matrixList;
	
	public static ArrayList<Matrix> getMatrixList() {
		return matrixList;
	}
	
	public static void setMatrixList(ArrayList<Matrix> matrixList) {
		Load.matrixList = matrixList;
	}
	
	public static void listFilesForFolder(final File folder) {

		// Create the DICOM reader
		ij.plugin.DICOM dcm = new ij.plugin.DICOM();
		
		int nCans = folder.listFiles().length;
		matrixList = new ArrayList<Matrix>(nCans/2);//nCans/2 = 382 != 365
		
		//int nCans = folder.listFiles().length;
		//matrixList = new ArrayList<Matrix>(nCans);
		
		int it = 1;
		for (final File fileEntry : folder.listFiles()) {
			// We take only the first half of images
			if (it <= 382) {
				if (fileEntry.isDirectory()) {
					listFilesForFolder(fileEntry);
				} else {
					System.out.println(fileEntry.getName());
					
					// Open the file using the DICOM reader
					dcm.open(folder.getName() + "/" + fileEntry.getName());
					if (dcm.getWidth() == 0) {
						JOptionPane.showMessageDialog(null, "The entering file is unfindable","Error",JOptionPane.ERROR_MESSAGE);
					} else {
						// Load the file into an image
						Image img = dcm.getImage();
						// Store the image into a matrix
						Matrix pixels = ImageUtil.rgb2gray(img);
						//Store the matrix into the list
						matrixList.add(pixels);
					}
				}
				it++;
			}
		}
	}
	
}
