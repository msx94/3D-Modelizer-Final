package algebra;

import java.io.Serializable;
import java.util.ArrayList;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

/**
 * Class representing a triangle
 * @author maxime
 *
 */
public class Triangle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2438132270156283836L;
	private XYZ p[];
	
	/**
	 * Creates a triangle with three points
	 */
	public Triangle() {
		p = new XYZ[3];
	}
	
	/**
	 * Returns the @i-th point of the triangle
	 * @param i
	 * @return
	 */
	public XYZ getP(int i) {	
		return p[i];
	}

	/**
	 * Sets the @i-th corner of the triangle to the point @xyz
	 * @param i
	 * @param xyz
	 */
	public void setP(int i, XYZ xyz) {
		this.p[i] = xyz;
	}

	/**
	 * Compute the area of the triangle
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return
	 */
	protected double triangleArea () {
		return p[1].getX () * p[2].getY () - p[1].getY () * p[2].getX () 
				+ p[2].getX () * p[0].getY () - p[0].getX () * p[2].getY ()
				+ p[0].getX () * p[1].getY () - p[1].getX () * p[0].getY ();
	}
	
	/**
	 * Compute the Barycentric matrix of 3 pixels
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return
	 */
	public Matrix makeBarycentricCoordsMatrix () {
		Matrix C = null;
		try {
			C = new Matrix (3, 3);
		} catch (InstantiationException e) {
			/* unreached */
		}

		double area = triangleArea ();
		double x1 = p[0].getX();
		double y1 = p[0].getY();
		double x2 = p[1].getX();
		double y2 = p[1].getY();
		double x3 = p[2].getX();
		double y3 = p[2].getY();
		C.set (0, 0, (x2 * y3 - x3 * y2) / area);
		C.set (0, 1, (y2 - y3) / area);
		C.set (0, 2, (x3 - x2) / area);
		C.set (1, 0, (x3 * y1 - x1 * y3) / area);
		C.set (1, 1, (y3 - y1) / area);
		C.set (1, 2, (x1 - x3) / area);
		C.set (2, 0, (x1 * y2 - x2 * y1) / area);
		C.set (2, 1, (y1 - y2) / area);
		C.set (2, 2, (x2 - x1) / area);

		return C;
	}
	
	/**
	 * Draw the triangle on a graphic g according to the size of the panel used (width*height)
	 * @param g
	 * @param width
	 * @param height
	 */
	public void drawTriangle(BranchGroup lineGroup) {	
		
		// Appearance is used to define the properties of the point
		Appearance app = new Appearance();
		
		// Color property
        ColoringAttributes ca = new ColoringAttributes(new Color3f(204.0f, 204.0f, 204.0f), ColoringAttributes.SHADE_FLAT);
        
        // Add of the properties
        app.setColoringAttributes(ca);

        // Construction of the 3 corner of the triangle
        Point3f[] plaPts = new Point3f[3];
        plaPts[0] = new Point3f(p[0].getX()/1000.0f,p[0].getY()/1000.0f,p[0].getZ()/1000.0f);
        plaPts[1] = new Point3f(p[1].getX()/1000.0f,p[1].getY()/1000.0f,p[1].getZ()/1000.0f);
        plaPts[2] = new Point3f(p[2].getX()/1000.0f,p[2].getY()/1000.0f,p[2].getZ()/1000.0f);
           
        // Construction of the triangle
        TriangleArray pla = new TriangleArray(3, GeometryArray.COORDINATES);
        pla.setCoordinates(0, plaPts);
        
        // Fill of the triangle
        PolygonAttributes la = new PolygonAttributes();
        la.setPolygonMode(PolygonAttributes.POLYGON_FILL);
        la.setCullFace(PolygonAttributes.CULL_NONE);
        app.setPolygonAttributes(la);
        
        Shape3D plShape = new Shape3D(pla, app);
        
        TransformGroup objRotate = new TransformGroup();
        
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.addChild(plShape);
        
        // Add a rotate object to rotate the triangle
        MouseRotate f1 = new MouseRotate();
        f1.setSchedulingBounds(new BoundingSphere());
        f1.setTransformGroup(objRotate);
        lineGroup.addChild(f1);
        
        // Add a zoom object to zoom on the triangle
        MouseWheelZoom f2 = new MouseWheelZoom();
        f2.setSchedulingBounds(new BoundingSphere());
        f2.setFactor(0.05);
        f2.setTransformGroup(objRotate);
        lineGroup.addChild(f2);
        
        // Add the objRotate to the lineGroup
        lineGroup.addChild(objRotate);

	}
	
	/**
	 * Draw several triangles as one polygone
	 * @param lineGroup
	 * @param triList
	 */
	public static void drawTriangles(BranchGroup lineGroup, ArrayList<Triangle> triList) {
		
		// The number of triangles to draw
		int size = triList.size();
		
		// Appearance is used to define the properties of the point
		Appearance app = new Appearance();
		
		// Color property
        ColoringAttributes ca = new ColoringAttributes(new Color3f(204.0f, 204.0f, 204.0f), ColoringAttributes.SHADE_FLAT);
        
        /// ADD ///
        ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        ca.setColor(new Color3f(0.4f,0.4f,0.4f));
        ///////////
        
        // Add of the properties
        app.setColoringAttributes(ca);

        // Construction of the triangles
        TriangleArray pla = new TriangleArray(3*triList.size(), GeometryArray.COORDINATES);              
        
        //TriangleStripArray pla = new TriangleStripArray(3*size, GeometryArray.COORDINATES, new int[] { 3*size });
        
        Point3f[] plaPts = new Point3f[3*size];
        int it = 0;
        for (int i = 0; i<3*size; i+=3) {
        
        	// The current triangle
        	Triangle tri = triList.get(it);

	        // Construction of the 3 corner of the triangle
	        plaPts[i+0] = new Point3f(tri.p[0].getX()/1000.0f,tri.p[0].getY()/1000.0f,tri.p[0].getZ()/1000.0f);
	        plaPts[i+1] = new Point3f(tri.p[1].getX()/1000.0f,tri.p[1].getY()/1000.0f,tri.p[1].getZ()/1000.0f);
	        plaPts[i+2] = new Point3f(tri.p[2].getX()/1000.0f,tri.p[2].getY()/1000.0f,tri.p[2].getZ()/1000.0f);       
        
	        it++;
        }       
        
        // Add the triangles to the TriangleArray
        pla.setCoordinates(0, plaPts);
        
        // Fill of the triangles
        PolygonAttributes pa = new PolygonAttributes();
        
        // Default, filled triangles, better for the spine
        pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        
        // With points and without the cull, better look of the inside
        //pa.setPolygonMode(PolygonAttributes.POLYGON_POINT);
        
        // With the filled triangles and without the cull, better look of the brain shape
        //pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
        
        app.setPolygonAttributes(pa);
        
        Shape3D plShape = new Shape3D(pla, app);
        
        TransformGroup objRotate = new TransformGroup();
        
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.addChild(plShape);
        
        // Add a rotate object to rotate the triangles
        MouseRotate f1 = new MouseRotate();
        f1.setSchedulingBounds(new BoundingSphere());
        f1.setTransformGroup(objRotate);
        lineGroup.addChild(f1);
        
        // Add a zoom object to zoom on the triangles
        MouseWheelZoom f2 = new MouseWheelZoom();
        f2.setSchedulingBounds(new BoundingSphere());
        f2.setFactor(0.05);
        f2.setTransformGroup(objRotate);
        lineGroup.addChild(f2);
     
        // Add the objRotate to the lineGroup
        lineGroup.addChild(objRotate);
		        
	}
	
	/**
	 * Returns a string representation of the triangle
	 */
	public String toString() {
		String result = "";
		
		result = result.concat("p1 : \n" + p[0] + "\n\n");
		result = result.concat("p2 : \n" + p[1] + "\n\n");
		result = result.concat("p3 : \n" + p[2] + "\n\n");
		
		return result;
	}
	
}
