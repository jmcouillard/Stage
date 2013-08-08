import stage.display.Image;
import stage.display.keystone.KeystoneStage;

Image img;
KeystoneStage stage;

void setup() {
	
  // Init PApplet (OPENGL needed)
  size(400,400, OPENGL);
  
  // Init stage
  stage = new KeystoneStage(this, width, height, 4, 10);
 
  // Init image
  img = new Image(this, "test.jpg");
  img.setX(200);
  img.setY(200);
  img.centerAnchor();
  stage.addChild(img);
}

void draw() {
	
  // Clear background
  background(0);
  
  // Update and draw Stage
  stage.update();
  stage.draw(this);
}
