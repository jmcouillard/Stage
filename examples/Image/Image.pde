import stage.display.Stage;
import stage.display.Image;

Stage stage;
Image img;

void setup() {
	
  // Init PApplet (OPENGL needed)
  size(400,400, OPENGL);
  
  // Init stage
  stage = new Stage(this, width, height);
 
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