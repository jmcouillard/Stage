import stage.display.Stage;
import stage.display.Sprite;

Stage stage;
Sprite layer;

void setup() {

  // Init PApplet (OPENGL needed)
  size(400, 400, OPENGL);

  // Init stage
  stage = new Stage(this, width, height);

  // Init Sprite
  layer = new Sprite(this, 200, 200);
  layer.setX(200);
  layer.setY(200);
  layer.centerAnchor();
  layer.graphic.beginDraw();
  layer.graphic.fill(0xFFFF0000);
  layer.graphic.rect(0,0,200,200);
  layer.graphic.endDraw();
  stage.addChild(layer);
}

void draw() {

  // Clear background
  background(0);

  // Update and draw Stage
  stage.update();
  stage.draw(this);
}

void mouseMoved() {
  layer.setX(mouseX);
  layer.setY(mouseY);
}
