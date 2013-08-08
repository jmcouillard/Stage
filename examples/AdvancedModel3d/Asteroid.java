
import stage.display.Bounds;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;

public class Asteroid {

  PApplet p;

  boolean visible = true;

  float r;
  float rVel;
  PVector rDir;

  float s;
  PVector pos;
  PVector vel;
  PShape model;

  public Asteroid(PApplet p, PShape model) {
    this.p = p;

    this.model = model;		
    this.pos = new PVector((float)(Math.random()*p.width), (float)(Math.random()*p.height), (float)(Math.random()*200f-100f));
    this.vel = new PVector((float)(Math.random()*0.4f-0.2f), (float)(Math.random()*0.4f-0.2f));

    s = (float)(Math.random()*8f+2f);
    rVel = (float)(Math.random()*0.06f-0.03f);
    rDir = new PVector((float)(Math.random()*1f), (float)(Math.random()*1f), (float)(Math.random()*1f));
  }

  public PShape getShape() {
    return model;
  }

  public void update() {
    if (!visible) return;
    pos.add(vel);
    
    // Repulse from borders
    vel.x += -Math.min(0, pos.x)*0.005f;
    vel.y += -Math.min(0, pos.y)*0.005f;
    vel.x += (Math.min(0, p.width-pos.x)*Math.random())/10f;
    vel.y += (Math.min(0, p.height-pos.y)*Math.random())/10f;

    if (vel.mag() > 0.5f) vel.mult(0.95f);
    r += rVel;
  }

  public void draw(PGraphics graphic) {
    if (!visible) return;
    graphic.pushMatrix();
    graphic.translate(pos.x, pos.y, pos.z);
    graphic.rotate(r, rDir.x, rDir.y, rDir.z);
    graphic.scale(s);
    graphic.shape(model);
    graphic.popMatrix();
  }
}

