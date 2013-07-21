import stage.display.Stage;
import stage.display.Image;
import stage.display.Sprite;

Stage stage;
Sprite layer;
PShader shader1;

void setup() {
	
  // Init PApplet (OPENGL needed)
  size(400,400, OPENGL);
  
  // Init stage
  stage = new Stage(this, width, height);
  
  PShader shader1 = this.loadShader("ripple2D.glsl");
  
 
  // Init Sprite
  layer = new Sprite(this, 400, 400);
  layer.setX(200);
  layer.setY(200);
  layer.centerAnchor();
  layer.addPass(shader1, this);
  stage.addChild(layer);
  
}

void draw() {
	
  // Clear background
  background(0);
  
  // Update and draw Stage
  stage.update();
  stage.draw(this);
}