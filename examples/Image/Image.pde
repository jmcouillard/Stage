import stage.display.Stage;
import stage.display.Image;

Stage stage;
Image img;

void setup() {
  size(400,400, OPENGL);
  
  // Init stage
  stage = new Stage(this, width, height);
 
 // Init sprite
 img = new Image(this, "test.jpg");
 img.setX(200);
 img.setY(200);
 img.centerAnchor();
 stage.addChild(img);
}

void draw() {
  background(0);
  stage.update();
  stage.draw(this);
}