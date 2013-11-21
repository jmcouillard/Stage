import stage.display.Image;
import stage.display.keystone.KeystoneStage;

Image img;
KeystoneStage stage;

void setup() {
	
  // Init PApplet (OPENGL needed)
  size(960,540, OPENGL);
  
  // Init stage
  stage = new KeystoneStage(this, width, height, 10, 5);
 
  // Init image
  img = new Image(this, "world32k.jpg");
  img.centerAnchor();
  stage.addChild(img);
  
  // How to message
  println("Press C to go into callibration mode, Then, use arrows to select, and drag to move.");
}

void draw() {
  
  // Update and draw Stage
  stage.update();
  stage.draw(this);
}

void mouseMoved() {
 img.setX(mouseX);
 img.setY(mouseY);
}
