package algebra;

import java.io.Serializable;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

/**
 * Represents a 3D point
 * @author maxime
 *
 */
public class XYZ implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1461447026546634298L;
	private int x;
	private int y;
	private int z;
	
	/**
	 * Creates a 3D point initialized at the origin
	 */
	public XYZ() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/**
	 * Creates a 3D point which coordinates are @xyz
	 * @param xyz
	 */
	public XYZ(XYZ xyz) {
		this.x = xyz.getX();
		this.y = xyz.getY();
		this.z = xyz.getZ();
	}
	
	/**
	 * Creates a 3D point which coordinates are @x, @y, @z
	 * @param x
	 * @param y
	 * @param z
	 */
	public XYZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the x coordinate of the 3D point
	 * @return
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Returns the y coordinate of the 3D point
	 * @return
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Returns the z coordinate of the 3D point
	 * @return
	 */
	public int getZ() {
		return this.z;
	}
	
	/**
	 * Sets the x coordinate of the point to the given value @x
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Sets the y coordinate of the point to the given value @y
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Sets the z coordinate of the point to the given value @z
	 * @param z
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
	 * Sets the xyz coordinates of the point to the given values @xyz
	 * @param xyz
	 */
	public void set(XYZ xyz) {
		this.setX(xyz.getX());
		this.setY(xyz.getY());
		this.setZ(xyz.getZ());
	}
	
	/**
	 * Draw the point on a given graphic @g
	 * @param g
	 */
	public void draw(BranchGroup lineGroup) {
		
		// Appearance is used to define the properties of the point
		Appearance app = new Appearance();
		
		// Color property
        ColoringAttributes ca = new ColoringAttributes(new Color3f(204.0f, 204.0f, 204.0f), ColoringAttributes.SHADE_FLAT);
        // Size property
        PointAttributes pointAttributes = new PointAttributes();
        pointAttributes.setPointSize(3);
        // Shape property
        pointAttributes.setPointAntialiasingEnable(true);
        
        // Add of the properties
        app.setColoringAttributes(ca);
        app.setPointAttributes(pointAttributes);

        //Construction of a Point3f using the coordinates x,y,z of the 3D Point    
        Point3f p = new Point3f(x/1000.0f,y/1000.0f,z/1000.0f);

        PointArray pla = new PointArray(3, GeometryArray.COORDINATES);
        pla.setCoordinate(0, p);
        Shape3D plShape = new Shape3D(pla, app);
        
        TransformGroup objRotate = new TransformGroup();
        
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.addChild(plShape);
        
        // Add a rotate object to rotate the 3DPoint
        MouseRotate f1 = new MouseRotate();
        f1.setSchedulingBounds(new BoundingSphere());
        f1.setTransformGroup(objRotate);
        lineGroup.addChild(f1);
        
        // Add a zoom object to zoom on the 3DPoint
        MouseWheelZoom f2 = new MouseWheelZoom();
        f2.setSchedulingBounds(new BoundingSphere());
        f2.setFactor(0.05);
        f2.setTransformGroup(objRotate);
        lineGroup.addChild(f2);
        
        // Add the objRotate to the lineGroup    
        lineGroup.addChild(objRotate);
		
	}

	/**
	 * Returns a String representation of the point
	 */
	public String toString() {
		return this.x + "\t" + this.y + "\t" + this.z + "\n";
	}
	
}
