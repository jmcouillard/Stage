import stage.display.Stage;
import stage.display.Image;
import stage.display.Sprite;

Stage stage;
Sprite layer;

Image life;
Image death;

PShader shader1;
PGraphics mask;
PImage brush;

void setup() {

  // Init PApplet (OPENGL needed)
  size(400, 800, OPENGL);

  // Init stage
  stage = new Stage(this, width, height);

  // Init death image
  death = new Image(this, "death.jpg");
  stage.addChild(death);

  // Init sprite and shader
  shader1 = this.loadShader("mask.glsl");
  layer = new Sprite(this, 400, 400);
  layer.setX(200);
  layer.setY(200);
  layer.centerAnchor();
  layer.addPass(shader1, this);
  layer.setBgColor(0x00777777);
  layer.setBlendMode(BLEND);
  stage.addChild(layer);
  
  // Init life image
  life = new Image(this, "life.jpg");
  layer.addChild(life);
    
  // Mask
  mask = createGraphics(400,400);
  mask.beginDraw();
  mask.background(0,0,0,255);
  mask.endDraw();
  
  // Brush
  brush = loadImage("brush.png");
}

void draw() {

  // Update and draw Stage
  stage.update();
  stage.draw(this);
  
  image(mask,0,400);
  
  // Update shader time;
  shader1.set("mask", mask);
}


void mouseDragged() {  
  mask.beginDraw();
  mask.noStroke();
  //mask.ellipse(mouseX, mouseY%400, 20,20);
  mask.imageMode(CENTER);
  mask.image(brush, mouseX, mouseY%400);
  mask.endDraw();
}
