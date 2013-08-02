import stage.display.Stage;
import stage.display.Image;
import stage.display.Sprite;

Stage stage;
Sprite layer;
Image img;
PShader shader1;

void setup() {

  // Init PApplet (OPENGL needed)
  size(400, 400, OPENGL);

  // Init stage
  stage = new Stage(this, width, height);

  shader1 = this.loadShader("ripple2D.glsl");
  shader1.set("rippleHeight", 0.05f);
  shader1.set("rippleQuantity", 100f);
  shader1.set("rippleMaxDistance", 0.5f);
  shader1.set("rippleOrigin", 0.5f, 0.5f);

  // Init Sprite
  layer = new Sprite(this, 400, 400);
  layer.setX(200);
  layer.setY(200);
  layer.centerAnchor();
  layer.addPass(shader1, this);
  stage.addChild(layer);

  // Init image
  img = new Image(this, "test.jpg");
  img.setX(200);
  img.setY(200);
  img.centerAnchor();
  layer.addChild(img);
}

void draw() {

  // Clear background
  background(0);

  // Update and draw Stage
  stage.update();
  stage.draw(this);
  
  // Update shader time
  shader1.set("time", (millis()/100f));
}

void mouseMoved() {  
	
  // Set ripple center to mouse position
  shader1.set("rippleOrigin", (float)mouseX/width, (float)mouseY/height);
}
