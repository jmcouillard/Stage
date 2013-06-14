/**
 * Copyright (C) 2009 David Bouchard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package stage.display.keystone;

/**
 * Represents a single point in the mesh, along with its precomputed (u,v) 
 * textur coordinates. 
 */
public class MeshPoint {
	
	public float x;
	public float y;
	public float u;
	public float v;

	public float uPixel;
	public float vPixel;
	
	MeshPoint(float x, float y, float u, float v) {
		this.x = x;
		this.y = y;
		this.u = u;
		this.v = v;
	}
	
	void interpolateBetween(MeshPoint start, MeshPoint end, float f) {
		this.x = start.x + (end.x - start.x) * f;
		this.y = start.y + (end.y - start.y) * f;
	}	
}
