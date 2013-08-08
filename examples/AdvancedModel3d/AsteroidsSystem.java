import de.looksgood.ani.*;
import de.looksgood.ani.easing.*;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;
import processing.opengl.PShader;
import stage.display.Image;
import stage.display.Sprite;

public class AsteroidsSystem extends Sprite {

  PShape[] partModels = new PShape[3];
  Asteroid[] parts;

  float translateX = 0;
  float translateZ = 0;

  public AsteroidsSystem (PApplet p, int count) {
    super(p, p.width, p.height, PApplet.OPENGL);		

    partModels[0] = p.loadShape("stone1.obj");
    partModels[0].scale(2f);
    partModels[1] = p.loadShape("stone2.obj");
    partModels[1].scale(2f);
    partModels[2] = p.loadShape("stone3.obj");
    partModels[2].scale(2f);

    parts = new Asteroid[count];
    for(int i = 0; i < count; i++) {
      parts[i] = new Asteroid(p, partModels[i%3]);
    }
  }
  
  public void update() {

    graphic.beginDraw();
    //graphic.smooth(8);

    graphic.background(0, 0);    

    graphic.lights();
    graphic.directionalLight(100, 100, 100, 0.5f, 1f, 1f);
    graphic.ambientLight(255, 230, 230);

    graphic.pushMatrix();
    graphic.translate(translateX,0,translateZ);
    for (Asteroid asteroid : parts) {
      asteroid.update();
      asteroid.draw(graphic);
    }
    graphic.popMatrix();
   
    graphic.endDraw();
    
    super.update();
  }

  public void draw(PGraphics dest) {
    super.draw(dest);
  }
  
}

