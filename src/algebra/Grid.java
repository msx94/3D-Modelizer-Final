package algebra;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

/**
 * Defines a cube.
 * @author mquentin
 */

public class Grid {

	private XYZ p[];
	private double val[];

	/**
	 * Creat a cube with coordinates and values for each corner
	 */
	public Grid() {
		this.p = new XYZ[8];
		this.val = new double[8];
	}
	
	/**
	 * Returns the i point of the cube
	 * @param i
	 * @return
	 */
	public XYZ getP(int i) {
		return p[i];
	}
	
	/**
	 * Returns the value of the i cube's corner
	 * @param i
	 * @return
	 */
	public double getVal(int i) {
		return val[i];
	}
	
	/**
	 * Sets the @i-th corner of the cube to the given point @xyz
	 * @param xyz
	 * @param i
	 */
	public void setP(XYZ xyz, int i) {
		this.p[i] = xyz;
	}
	
	/**
	 * Sets the @i-th corner of the cube to given value @val
	 * @param val
	 * @param i
	 */
	public void setVal(double val, int i) {
		this.val[i] = val;
	}
	
	/**
	 * Draw the cube into a graphic using its coordinates
	 * @param g
	 * @param width
	 * @param height
	 */
	public void drawCube(BranchGroup lineGroup) {
		
		Appearance app = new Appearance();
		
		QuadArray lsa = new QuadArray(48,QuadArray.COORDINATES|QuadArray.NORMALS);
		
        Vector3f [] normals = new Vector3f[24]; 
        for(int i=0;i<24;i++) {
        	normals[i] = new Vector3f();
        }
        
        Point3f [] pts = new Point3f[24]; 
        for(int i=0;i<24;i++) {
        	pts[i]=new Point3f();
        }
        
        Color3f [] clrs = new Color3f[24];      
        for(int i=0;i<24;i++){
        	clrs[i] = new Color3f(0.5f,0.5f,0.5f);
        }
        
        //cube = 6 quads 
        //left quad 7-4-0-3
        pts[0 ].x = p[7].getX()/1000.0f; pts[0 ].y = p[7].getY()/1000.0f; pts[0 ].z = p[7].getZ()/1000.0f;
        pts[1 ].x = p[4].getX()/1000.0f; pts[1 ].y = p[4].getY()/1000.0f; pts[1 ].z = p[4].getZ()/1000.0f;
        pts[2 ].x = p[0].getX()/1000.0f; pts[2 ].y = p[0].getY()/1000.0f; pts[2 ].z = p[0].getZ()/1000.0f;
        pts[3 ].x = p[3].getX()/1000.0f; pts[3 ].y = p[3].getY()/1000.0f; pts[3 ].z = p[3].getZ()/1000.0f;
        normals[0].x=-1;normals[1].x=-1;normals[2].x=-1;normals[3].x=-1;
        
        //right quad 6-5-1-2
        pts[4 ].x = p[6].getX()/1000.0f; pts[4 ].y = p[6].getY()/1000.0f; pts[4 ].z = p[6].getZ()/1000.0f;
        pts[5 ].x = p[5].getX()/1000.0f; pts[5 ].y = p[5].getY()/1000.0f; pts[5 ].z = p[5].getZ()/1000.0f;
        pts[6 ].x = p[1].getX()/1000.0f; pts[6 ].y = p[1].getY()/1000.0f; pts[6 ].z = p[1].getZ()/1000.0f;
        pts[7 ].x = p[2].getX()/1000.0f; pts[7 ].y = p[2].getY()/1000.0f; pts[7 ].z = p[2].getZ()/1000.0f;
        normals[4].x=1;normals[5].x=1;normals[6].x=1;normals[7].x=1;

        //back quad 4-5-1-0
        pts[8 ].x = p[4].getX()/1000.0f; pts[8 ].y = p[4].getY()/1000.0f; pts[8 ].z = p[4].getZ()/1000.0f;
        pts[9 ].x = p[5].getX()/1000.0f; pts[9 ].y = p[5].getY()/1000.0f; pts[9 ].z = p[5].getZ()/1000.0f;
        pts[10].x = p[1].getX()/1000.0f; pts[10].y = p[1].getY()/1000.0f; pts[10].z = p[1].getZ()/1000.0f;
        pts[11].x = p[0].getX()/1000.0f; pts[11].y = p[0].getY()/1000.0f; pts[11].z = p[0].getZ()/1000.0f;
        normals[8].z=-1;normals[9].z=-1;normals[10].z=-1;normals[11].z=-1;
        
        //front quad 7-6-2-3
        pts[12].x = p[7].getX()/1000.0f; pts[12].y = p[7].getY()/1000.0f; pts[12].z = p[7].getZ()/1000.0f;
        pts[13].x = p[6].getX()/1000.0f; pts[13].y = p[6].getY()/1000.0f; pts[13].z = p[6].getZ()/1000.0f;
        pts[14].x = p[2].getX()/1000.0f; pts[14].y = p[2].getY()/1000.0f; pts[14].z = p[2].getZ()/1000.0f;
        pts[15].x = p[3].getX()/1000.0f; pts[15].y = p[3].getY()/1000.0f; pts[15].z = p[3].getZ()/1000.0f;
        normals[12].z=1;normals[13].z=1;normals[14].z=1;normals[15].z=1;
        
        //under quad 0-1-2-3
        pts[16].x = p[0].getX()/1000.0f; pts[16].y = p[0].getY()/1000.0f; pts[16].z = p[0].getZ()/1000.0f;
        pts[17].x = p[1].getX()/1000.0f; pts[17].y = p[1].getY()/1000.0f; pts[17].z = p[1].getZ()/1000.0f;
        pts[18].x = p[2].getX()/1000.0f; pts[18].y = p[2].getY()/1000.0f; pts[18].z = p[2].getZ()/1000.0f;
        pts[19].x = p[3].getX()/1000.0f; pts[19].y = p[3].getY()/1000.0f; pts[19].z = p[3].getZ()/1000.0f;
        normals[16].y=-1;normals[17].y=-1;normals[18].y=-1;normals[19].y=-1;
        
        //top quad 4-5-6-7
        pts[20].x = p[4].getX()/1000.0f; pts[20].y = p[4].getY()/1000.0f; pts[20].z = p[4].getZ()/1000.0f;
        pts[21].x = p[5].getX()/1000.0f; pts[21].y = p[5].getY()/1000.0f; pts[21].z = p[5].getZ()/1000.0f;
        pts[22].x = p[6].getX()/1000.0f; pts[22].y = p[6].getY()/1000.0f; pts[22].z = p[6].getZ()/1000.0f;
        pts[23].x = p[7].getX()/1000.0f; pts[23].y = p[7].getY()/1000.0f; pts[23].z = p[7].getZ()/1000.0f;
        normals[20].y=1;normals[21].y=1;normals[22].y=1;normals[23].y=1;
        
        lsa.setNormals(0, normals);
        lsa.setCoordinates(0, pts);
        
        Shape3D sh = new Shape3D();
        
        PolygonAttributes pa = new PolygonAttributes();
        
        pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        
        Material mat = new Material();
        
        mat.setEmissiveColor(new Color3f(0.5f,0.5f,0.5f));
        mat.setAmbientColor(new Color3f(0.1f,0.1f,0.1f));
        mat.setDiffuseColor(new Color3f(0.2f,0.3f,0.4f));
        mat.setSpecularColor(new Color3f(0.6f,0.3f,0.2f));
        mat.setLightingEnable(true);
        
        RenderingAttributes ra = new RenderingAttributes();
        
        ra.setIgnoreVertexColors(true);
        
        ColoringAttributes ca = new ColoringAttributes();
        
        ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        ca.setColor(new Color3f(0.2f,0.5f,0.9f));
        
        app.setColoringAttributes(ca);
        app.setRenderingAttributes(ra);   
        app.setMaterial(mat);
        app.setPolygonAttributes(pa);
        
        sh.setGeometry(lsa);
        sh.setAppearance(app);
        sh.setPickable(true);
        
        TransformGroup objRotate = new TransformGroup();
        
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.addChild(sh);

        // Add a rotate object to rotate the cube
        MouseRotate f1 = new MouseRotate();
        f1.setSchedulingBounds(new BoundingSphere());
        f1.setTransformGroup(objRotate);
        lineGroup.addChild(f1);
        
        // Add a zoom object to zoom on the cube
        MouseWheelZoom f2 = new MouseWheelZoom();
        f2.setSchedulingBounds(new BoundingSphere());
        f2.setFactor(0.05);
        f2.setTransformGroup(objRotate);
        lineGroup.addChild(f2);
        
        // Add the objRotate to the lineGroup  
        lineGroup.addChild(objRotate);
        
	}
	
}
