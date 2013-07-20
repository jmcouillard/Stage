import stage.display.Stage;
import stage.display.Image;

Stage stage;
Image img;

void setup() {
  size(400,400);
  smooth();
  
  // Init stage
  stage = new Stage(this);
 
 // Init sprite
 img = new Image(this, "test.jpg");
 stage.addChild(img);
}

void draw() {
  background(0);
  stage.draw(this);
}