package inter;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.util.ArrayList;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.JPanel;
import com.sun.j3d.utils.universe.SimpleUniverse;

import algebra.Grid;
import algebra.Matrix;
import algebra.Triangle;
import algebra.XYZ;
import graphic.MarchingCubes;

public class Panel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6882989332130475918L;
	
	private String path;
	
	private int width;
	private int height;
	
	private static double isolevel;
	private static String accuracy;
	
	//The list containing all the images matrix
	private ArrayList<Matrix> matrixList;
	//The list containing all the triangles
	private ArrayList<Triangle> triList;
	
	int s = 0, count = 0;
	
	// The constructor used for the test
	public Panel(int width, int height) {
		this.width = width;
		this.height = height;
		
		setLayout(new BorderLayout());
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(gc);//See the added gc? this is a preferred config
        add(BorderLayout.CENTER, canvas3D);

        BranchGroup scene = createSceneGraph();
        scene.compile();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        // This moves the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
	}
	
	// the constructor used for the folder load
	public Panel(int width, int height, ArrayList<Matrix> matrixList, double isolevel, String acc) {
		this.width = width;
		this.height = height;
		this.matrixList = matrixList;
		Panel.isolevel = isolevel;
		Panel.accuracy = acc;
		
		setLayout(new BorderLayout());
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(gc);//See the added gc? this is a preferred config
        add(BorderLayout.CENTER, canvas3D);

        BranchGroup scene = createSceneGraph();
        scene.compile();
        
        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        // This moves the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
	}
	
	// The constructor used for the file load
	public Panel (String path) {
		this.width = 800;
		this.height = 800;
		
		this.path = path;
		
	}
	
	// The constructor used for the model load
	public Panel (ArrayList<Triangle> triList) {	
		this.triList = triList;
		
		setLayout(new BorderLayout());
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(gc);//See the added gc? this is a preferred config
        add(BorderLayout.CENTER, canvas3D);
		
		BranchGroup scene = new BranchGroup();
		Triangle.drawTriangles(scene, triList);
		
		scene.compile();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        // This moves the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
	}
	
	/**
	 * The graphic for the 3d reconstruction
	 * @return
	 */
	public BranchGroup createSceneGraph() {
		
        BranchGroup lineGroup = new BranchGroup();
        
        // Select the size of the cube according to the accuracy
        int size;
        if (accuracy.equals("Low")) size = 4;
        else if (accuracy.equals("Medium")) size = 2;
        else if (accuracy.equals("High")) size = 1;
        else size = 4;
        
		MarchingCubes mc = new MarchingCubes(lineGroup);
		
		int x = width/2;
		int y = height/2;
		int z = matrixList.size()/2;
		
		for (int i = -x; i+size<x; i+=size) {
			for (int j = -y; j+size<y; j+=size) {
				for (int k = -z; k+size<z; k+=size) {
			
					Grid grid = new Grid();
					
					grid.setP(new XYZ(i,j+size,2*k), 0);
					grid.setP(new XYZ(i+size,j+size,2*k), 1);
					grid.setP(new XYZ(i+size,j,2*k), 2);
					grid.setP(new XYZ(i,j,2*k), 3);
					grid.setP(new XYZ(i,j+size,2*(k+size)), 4);
					grid.setP(new XYZ(i+size,j+size,2*(k+size)), 5);
					grid.setP(new XYZ(i+size,j,2*(k+size)), 6);
					grid.setP(new XYZ(i,j,2*(k+size)), 7);
					
					grid.setVal(matrixList.get(k+z).get(i+x, j+y+size), 0);
					grid.setVal(matrixList.get(k+z).get(i+x+size, j+y+size), 1);
					grid.setVal(matrixList.get(k+z).get(i+x+size,j+y), 2);
					grid.setVal(matrixList.get(k+z).get(i+x, j+y), 3);
					grid.setVal(matrixList.get(k+z+size).get(i+x, j+y+size), 4);
					grid.setVal(matrixList.get(k+z+size).get(i+x+size, j+y+size), 5);
					grid.setVal(matrixList.get(k+z+size).get(i+x+size, j+y), 6);
					grid.setVal(matrixList.get(k+z+size).get(i+x, j+y), 7);
					
					mc.drawMarchingcubes(grid, isolevel);

				}
				
			}
				
		}
		
		triList = mc.getTriList();
		Triangle.drawTriangles(lineGroup, triList);
		
        return lineGroup;
    }
	
	/**
	 * The graphic for the 2D view of the DICOM files
	 */
	public void paintComponent(Graphics g) {
		
		//Check if the path is null or not
		if (path != null) {
			
			// Create the DICOM reader
			ij.plugin.DICOM dcm = new ij.plugin.DICOM();
			
			// Open the file using the DICOM reader
			dcm.open(this.path);
	
			// Load the file into an image
			Image img = dcm.getImage();
	
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			//dcm.show();
			
		}
		
	}
	
	public ArrayList<Matrix> getMatrixList() {
		return matrixList;
	}

	public void setMatrixList(ArrayList<Matrix> matrixList) {
		this.matrixList = matrixList;
	}

	public ArrayList<Triangle> getTriList() {
		return triList;
	}

	public void setTriList(ArrayList<Triangle> triList) {
		this.triList = triList;
	}
	
	public double getIsolevel() {
		return isolevel;
	}

	public void setIsolevel(double isolevel) {
		Panel.isolevel = isolevel;
	}

}
