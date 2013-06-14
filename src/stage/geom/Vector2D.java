package stage.geom;

public class Vector2D {

	public float x;
	public float y;
	/*
	 * Constructor with no parameters make a 0 vector
	 */
	public Vector2D() {
		x = 0;
		y = 0;
	}
	/*
	 * Constructor with a vector as a parameter make a copy of that vector
	 */
	public Vector2D(Vector2D v) {
		x = v.x;
		y = v.y;
	}
	/*
	 * Constructor with two float
	 */
	public Vector2D(float x1, float y1) {
		x = x1;
		y = y1;
	}
	public void setTo(float x1, float y1) {
		x = x1;
		y = y1;
	}
	public void setTo(Vector2D v) {
		x = v.x;
		y = v.y;
	}
	/*
	 * add a vector to this vector
	 */
	public void add(Vector2D v) {
		x += v.x;
		y += v.y;
	}
	public Vector2D add2(Vector2D v) {
		return new Vector2D(x + v.x,y+v.y);
	}
	/*
	 * add a vector from two float to this vector
	 */
	public void add(float x1, float y1) {
		x += x1;
		y += y1;
	}
	/*
	 * static add for two vectors 
	 */
	public static Vector2D add(Vector2D v, Vector2D v2) {

		return new Vector2D(v.x + v2.x, v.y + v2.y);
	}
	/*
	 * subtract vector to this vector
	 */
	public void subtract(Vector2D v) {
		x -= v.x;
		y -= v.y;
	}
	public void subtract(float x1, float y1) {
		x -= x1;
		y -= y1;
	}
	/*
	 * static subtract two vectors
	 */
	public static Vector2D subtract(Vector2D v, Vector2D v2) {

		return new Vector2D(v.x - v2.x, v.y - v2.y);
	}
	/*
	 * divide this vector on a float
	 */
	public void divide(float d) {
		x /= d;
		y /= d;
	}
	/*
	 * return a new copy of this divided by a float
	 */
	public Vector2D divide2(float d) {
		return (new Vector2D(x / d,y /d));
		
	}
	/*
	 * multiply this vector with a float
	 */
	public void multiply(float d) {
		x *= d;
		y *= d;
	}
	public Vector2D multiply2(float d) {
		return new Vector2D( x *d,y * d);
	}

	
	/*
	 * returns the dot product of this with a vector v
	 */
	public float dotProduct(Vector2D v) {
		float cp = this.x * v.x + this.y * v.y;
		return cp;
	}
	public float dotProduct(float x1, float y1) {
		float cp = this.x * x + this.y * y;
		return cp;
	}
	/*
	 * returns the crossproduct of two vectors
	 */
	public static Vector2D crossProduct(Vector2D v1, Vector2D v2) {
		return new Vector2D((v1.y * v2.x - v1.x * v2.y),(v1.x * v2.y - v1.x * v2.y));
	}
	/*
	 * returns the length of this vector
	 */
	public float length() {
		return (float)Math.sqrt(x * x + y * y);
	}
	/*
	 * makes the length of this vector 1
	 */
	public void normalize() {
		float length = length();
		x = x / length;
		y = y / length;
	}
	public Vector2D normalize2() {
		float length = length();
		return new Vector2D( x / length,y / length);
	}
	/*
	 * makes this a 0 vector
	 */
	public void setToZero() {
		x = 0;
		y = 0;
	}
}