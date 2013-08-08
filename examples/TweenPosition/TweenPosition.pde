import de.looksgood.ani.*;
import de.looksgood.ani.easing.*;
import stage.display.Stage;
import stage.display.Image;

Stage stage;
Image image;

void setup() {

  // Init PApplet (OPENGL needed)
  size(400, 400, OPENGL);

  // Init stage
  stage = new Stage(this, width, height);
  
  // Init tweening library
  Ani.init(this);

  // Init Sprite
  image = new Image(this, "star.png");
  image.centerAnchor();
  image.setX(200);
  image.setY(200);
  stage.addChild(image);
}

void draw() {

  // Clear background
  background(0);

  // Update and draw Stage
  stage.update();
  stage.draw(this);
}

void mousePressed() {
  image.to(1, "x:"+mouseX+",y:"+mouseY);
}
