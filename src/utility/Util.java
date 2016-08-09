package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import algebra.Triangle;

public class Util {

	/**
	 * Check if a string is double or not
	 * @param str
	 * @return
	 */
	public static boolean isValidIso(String str) {
		
		try {
			
			Double.parseDouble(str);
			
		} catch (NumberFormatException nfe) {
			
			return false;
			
		}
		
		double value = Double.parseDouble(str);
		
		if (value > 0 && value <= 1) 
			return true;	
		else 
			return false;
		
	}
	
	/**
	 * Save the 3D Reconstruction into a file 
	 * @param fileName
	 * @param toSave
	 * @throws IOException
	 */
	public static void saveModel(String fileName, ArrayList<Triangle> toSave) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
		oos.writeInt(toSave.size());
		for (Triangle e : toSave)
			oos.writeObject(e);
		oos.close();
	}
	
	/**
	 * Load the 3D Reconstruction
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<Triangle> loadModel(String fileName) throws IOException, ClassNotFoundException
	{
		ArrayList<Triangle> list = new ArrayList<Triangle>();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
		int n=ois.readInt();
		for (int i=0; i<n; i++)
			list.add((Triangle) ois.readObject());
		ois.close();
		
		return list;
	}
	
}
