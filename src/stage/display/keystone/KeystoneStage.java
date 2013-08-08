package stage.display.keystone;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.PrintWriter;

import stage.display.Stage;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.XML;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.media.jai.WarpGrid;
import javax.media.jai.WarpPerspective;

public class KeystoneStage extends Stage {

	private MeshPoint[] mesh;
	private int res;

	WarpPerspective warpPerspective = null;
	boolean calibration = false;
	int calibrationPointIndex = 0;
	int calibrationPoint = 0;
	Point dragHandle = new Point();

	int[] calibrationPoints;
	int div;
	float gridAlpha = 0.25f;
	float divRes;

	boolean groupTransfom = false;
	boolean shift = false;

	PApplet parent;

	public KeystoneStage(PApplet p, int w, int h, int resolution, int subdivisions) {

		super(p, w, h);

		this.parent = p;
		
		// Make sure that the subdivision number is compatible
		if(resolution%subdivisions != 0){
			subdivisions -= resolution%subdivisions;
			System.err.println("KeystoneStage : Sudivision must be a fraction of resolution. Setting to "+ subdivisions  +" to avoit errors.");
		}

		// Define values
		this.div = Math.min(resolution / 2, subdivisions);
		this.res = resolution + (1);
		this.divRes = (float) resolution / div;

		// Initialize the point array
		mesh = new MeshPoint[res * res];
		for (int i = 0; i < mesh.length; i++) {
			float x = (i % res) / (float) (res - 1);
			float y = (i / res) / (float) (res - 1);
			mesh[i] = new MeshPoint(x * w, y * h, x * w, y * h);
		}
		calculatePixelUVs();

		calibrationPoints = new int[(div * div) + (div * 2) + 1];

		for (int j = 0; j <= div; j++) {
			for (int i = 0; i <= div; i++) {
				calibrationPoints[i + j * (div + 1)] = PApplet.ceil(i * divRes + (res * divRes * j));
			}
		}
	}

	public void draw(PApplet p) {

		// Begin draw
		p.beginShape(PApplet.QUADS);

		// Draw grid if we are in calibration mode
		if (calibration) {
			if (groupTransfom) p.stroke(0xFFFF11FF);
			else p.stroke(0xFF00FF00);
		} else {
			p.noStroke();
		}

		p.texture(graphic);
		for (int x = 0; x < res - 1; x++) {
			for (int y = 0; y < res - 1; y++) {
				
				p.vertex(mesh[(x) + (y) * res].x, mesh[(x) + (y) * res].y, mesh[(x) + (y) * res].uPixel, mesh[(x) + (y) * res].vPixel);
				p.vertex(mesh[(x + 1) + (y) * res].x, mesh[(x + 1) + (y) * res].y, mesh[(x + 1) + (y) * res].uPixel, mesh[(x + 1) + (y) * res].vPixel);
				p.vertex(mesh[(x + 1) + (y + 1) * res].x, mesh[(x + 1) + (y + 1) * res].y, mesh[(x + 1) + (y + 1) * res].uPixel, mesh[(x + 1) + (y + 1) * res].vPixel);
				p.vertex(mesh[(x) + (y + 1) * res].x, mesh[(x) + (y + 1) * res].y, mesh[(x) + (y + 1) * res].uPixel, mesh[(x) + (y + 1) * res].vPixel);

				// MeshPoint mp;
				//
				// mp = mesh[(x) + (y) * res];
				// p.vertex(mp.x, mp.y, mp.uPixel, mp.vPixel);
				//
				// mp = mesh[(x + 1) + (y) * res];
				// p.vertex(mp.x, mp.y, mp.uPixel, mp.vPixel);
				//
				// mp = mesh[(x + 1) + (y + 1) * res];
				// p.vertex(mp.x, mp.y, mp.uPixel, mp.vPixel);
				//
				// mp = mesh[(x) + (y + 1) * res];
				// p.vertex(mp.x, mp.y, mp.uPixel, mp.vPixel);
			}
		}

		// End draw
		p.endShape(PApplet.CLOSE);

		// Draw edited corner
		if (calibration) {

			// Draw grid
			int rangeX = PApplet.ceil(divRes);
			int rangeY = PApplet.ceil(divRes * res);
			int odd = 0;
			for (int j = 0; j < div; j++) {
				for (int i = 0; i < div; i++) {

					int index = PApplet.ceil((j * divRes * res) + (i * divRes));

					p.fill(255f * ((odd - j) % 2), gridAlpha * 255f);

					p.beginShape(PGraphics.QUADS);
					p.vertex(mesh[index].x, mesh[index].y, 0);
					p.vertex(mesh[index + rangeX].x, mesh[index + rangeX].y);
					p.vertex(mesh[(int) (index + rangeX + rangeY)].x, mesh[(int) (index + rangeX + rangeY)].y);
					p.vertex(mesh[(int) (index + rangeY)].x, mesh[(int) (index + rangeY)].y);

					p.endShape();

					odd++;
				}
			}

			// Draw edited corner
			if (!groupTransfom) {
				MeshPoint mp = mesh[calibrationPoint];
				p.fill(0x3300FF00);
				// p.stroke(0xFF00FF00);
				p.ellipseMode(PApplet.CENTER);
				p.ellipse(mp.x, mp.y, 100f, 100f);
			}

			// Draw points
			p.noStroke();
			p.fill(0xFFFF0000);
			for (int i : calibrationPoints) {
				p.ellipse(mesh[i].x, mesh[i].y, 8f, 8f);
			}
		}
	}

	private void calculateMesh() {

		// Define warp points
		float[] warpPositions = new float[(div + 1) * (div + 1) * 2];
		for (int j = 0; j < div + 1; j++) {
			for (int i = 0; i < div + 1; i++) {

				// Define which calibration should be used with this warp point
				int calibrationPointIndex = j * (div + 1) + i;

				// Define x
				warpPositions[(i + j * (div + 1)) * 2] = mesh[calibrationPoints[calibrationPointIndex]].x;

				// Define y
				warpPositions[(i + j * (div + 1)) * 2 + 1] = mesh[calibrationPoints[calibrationPointIndex]].y;
			}
		}

		// Define transformation object
		WarpGrid warp = new WarpGrid(0, getWidth() / div, div, 0, getHeight() / div, div, warpPositions);

		// Move all mesh points
		for (int j = 0; j < (div) * divRes + 1; j++) {
			for (int i = 0; i < (div) * divRes + 1; i++) {

				// Pour chaque mesh point
				int index = j * (int) res + i;

				// Skip calibration
				if (isCalibrationPoint(index)) {
					continue;
				}

				// Define source position
				float px = (float) Math.min(i * getWidth() / (div * divRes), getWidth() - 1);
				float py = (float) Math.min(j * getHeight() / (div * divRes), getHeight() - 1);
				Point2D p = new Point2D.Float(px, py);

				// Calculate destination position
				Point2D wp = warp.mapDestPoint(p);
				mesh[index].x = (float) wp.getX();
				mesh[index].y = (float) wp.getY();
			}
		}
	}

	// Recompute pixel position
	private void calculatePixelUVs() {
		int tX = 0;
		int tY = 0;
		int tW = getWidth();
		int tH = getHeight();

		for (int x = 0; x < res; x++) {
			for (int y = 0; y < res; y++) {
				MeshPoint mp;
				mp = mesh[(x) + (y) * res];
				mp.uPixel = PApplet.map(mp.u, 0, tW, tX, tX + tW);
				mp.vPixel = PApplet.map(mp.v, 0, tH, tY, tY + tH);
			}
		}
	}

	// Apply a transfomation to whole grid (every single mesh point)
	private void groupTransfom(float translateX, float translateY, float scale) {
		for (int x = 0; x < res; x++) {
			float scaleX = (x - res / 2f) / (res / 2f);
			for (int y = 0; y < res; y++) {
				float scaleY = (y - res / 2f) / (res / 2f);
				MeshPoint mp;
				mp = mesh[(x) + (y) * res];
				mp.x += translateX + (scaleX * scale);
				mp.y += translateY + (scaleY * scale);
			}
		}
	}

	private void resetGrid() {
		for (int x = 0; x < res; x++) {
			for (int y = 0; y < res; y++) {
				MeshPoint mp;
				mp = mesh[(x) + (y) * res];
				mp.x = x * (getWidth() / (res - 1));
				mp.y = y * (getHeight() / (res - 1));
			}
		}
		calculatePixelUVs();
	}

	public void mouseEvent(MouseEvent e) {

		super.mouseEvent(e);

		if (calibration && calibrationPoint != -1 && e.getAction() == MouseEvent.PRESS) {
			dragHandle.x = e.getX() - (int) mesh[calibrationPoint].x;
			dragHandle.y = e.getY() - (int) mesh[calibrationPoint].y;
		}

		if (calibration && calibrationPoint != -1 && e.getAction() == MouseEvent.DRAG) {
			mesh[calibrationPoint].x = e.getX() - dragHandle.x;
			mesh[calibrationPoint].y = e.getY() - dragHandle.y;
			calculateMesh();
			calculatePixelUVs();
		}
	}

	public void setMeshPoint(int index, float x, float y) {
		mesh[calibrationPoints[index]].x = x;
		mesh[calibrationPoints[index]].y = y;
		calculateMesh();
		calculatePixelUVs();
	}

	public void setGridAlpha(float val) {
		gridAlpha = val;
	}

	public void keyEvent(KeyEvent e) {

		super.keyEvent(e);

		//println(e.getModifiers());
		
		// Test group transform
		if (e.getModifiers() == 8 || e.getModifiers() == 9) groupTransfom = true;
		else groupTransfom = false;

		// Test shift
		if (e.getModifiers() == 1 || e.getModifiers() == 9) shift = true;
		else shift = false;

		if (e.getKey() == 'c' && e.getAction() == KeyEvent.PRESS) {

			calibration = !calibration;
			dragHandle.x = 0;
			dragHandle.y = 0;

		} else if (calibration) {

			if (e.getKeyCode() == 38 && e.getAction() == KeyEvent.PRESS) {
				// Up
				if (groupTransfom) {
					groupTransfom(0,  shift ? -10f : -1f, 0);
				} else {
					calibrationPointIndex -= (div + 1);
					if (calibrationPointIndex < 0) calibrationPointIndex = calibrationPoints.length + calibrationPointIndex;
				}
			} else if (e.getKeyCode() == 39 && e.getAction() == KeyEvent.PRESS) {
				// Right
				if (groupTransfom) {
					groupTransfom( shift ? 10f : 1f, 0, 0);
				} else {
					calibrationPointIndex += 1;
					calibrationPointIndex = calibrationPointIndex % calibrationPoints.length;
				}
			} else if (e.getKeyCode() == 40 && e.getAction() == KeyEvent.PRESS) {
				// Down
				if (groupTransfom) {
					groupTransfom(0,  shift ? 10f : 1f, 0);
				} else {
					calibrationPointIndex += (div + 1);
					if (calibrationPointIndex < 0) calibrationPointIndex = calibrationPoints.length - 1;
					calibrationPointIndex = calibrationPointIndex % calibrationPoints.length;
				}
			} else if (e.getKeyCode() == 37 && e.getAction() == KeyEvent.PRESS) {
				// Left
				if (groupTransfom) {
					groupTransfom( shift ? -10f : -1f, 0, 0);
				} else {
					calibrationPointIndex -= 1;
					if (calibrationPointIndex < 0) calibrationPointIndex = calibrationPoints.length - 1;
				}
			} else if (e.getKeyCode() == 65 && e.getAction() == KeyEvent.PRESS) {
				// A
				if (groupTransfom) {
					groupTransfom(0, 0, shift ? 10f : 1f);
				}
			} else if (e.getKeyCode() == 90 && e.getAction() == KeyEvent.PRESS) {
				// Z
				if (groupTransfom) {
					groupTransfom(0, 0, shift ? -10f : -1f);
				}
			} else if (e.getKeyCode() == 82 && e.getAction() == KeyEvent.PRESS) {
				// R
				resetGrid();
			}

			calibrationPoint = calibrationPoints[calibrationPointIndex];
		}
	}

	public int getSubdivsions() {
		return div;
	}

	public int getCalibrationPointsCount() {
		return calibrationPoints.length;
	}

	public boolean isCalibrationPoint(int index) {
		for (int i : calibrationPoints)
			if (i == index) return true;
		return false;
	}

	public void printData() {
		for (int i = 0; i < calibrationPoints.length; i++) {
			PApplet.println("<keystone" + i + " x=\"" + mesh[calibrationPoints[i]].x + "\"  y=\"" + mesh[calibrationPoints[i]].y + "\" />");
		}
	}

	public void saveData(String filename) {

		File file = parent.dataFile(filename);

		try {

			XML xml = new XML(file);

			for (int i = 0; i < calibrationPoints.length; i++) {

				boolean done = false;
				for (XML item : xml.getChildren()) {
					if (item.getName().compareTo("keystone" + i) == 0) {
						item.setFloat("x", mesh[calibrationPoints[i]].x);
						item.setFloat("y", mesh[calibrationPoints[i]].y);
						done = true;
					}
				}
				if (!done) {
					XML el = xml.addChild("keystone" + i);
					el.setFloat("x", mesh[calibrationPoints[i]].x);
					el.setFloat("y", mesh[calibrationPoints[i]].y);
				}
			}

			PrintWriter output = PApplet.createWriter(file);
			output.print(xml.format(0));
			output.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
