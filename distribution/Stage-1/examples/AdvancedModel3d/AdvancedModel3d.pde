
import stage.display.Stage;
import stage.display.Sprite;

Stage stage;

PShader shader1;
AsteroidsSystem asteroids;

void setup() {

  // Init PApplet (OPENGL needed)
  size(960, 540, OPENGL);

  // Init stage
  stage = new Stage(this, width, height);

  // Load glow shader 
  shader1 = loadShader("innerglow.glsl");
  shader1.set("BlendMode", 0);
  shader1.set("TexelSize", 0.025f, 0.025f);
  shader1.set("BlurAmount", 10);
  shader1.set("BlurScale", 0.05f);
  shader1.set("Multiplicator", 2.5f);
  shader1.set("ColorOffset", 0.2f, 0.0f, 0.0f, 0f);

  // Asteroid sprite
  asteroids = new AsteroidsSystem(this, 200);
  asteroids.centerAnchor();
  asteroids.setX(width/2);
  asteroids.setY(height/2);
  asteroids.setZSortOffset(10);
  asteroids.addPass(shader1, this);
  stage.addChild(asteroids);
}

void draw() {

  // Update and draw Stage
  stage.update();
  stage.draw(this);
}

void mouseMoved() {  
  asteroids.setRotationY((mouseX-width/2)/200.0f);
  shader1.set("TexelSize", mouseY/(height*20f), 0.05f);
}

void mouseDragged(){
  asteroids.translateX += (mouseX - pmouseX)*1.0f;
  asteroids.translateZ += (mouseY - pmouseY)*10.0f;
}

void keyPressed() {  
  if(key=='1') shader1.set("ColorOffset", 0.2f, 0.1f, 0.1f, 0f);
  if(key=='2') shader1.set("ColorOffset", 0.2f, 0.0f, 0.2f, 0f);
  if(key=='3') shader1.set("ColorOffset", 0.0f, 0.0f, 0.2f, 0f);
  if(key=='4') shader1.set("ColorOffset", 0.0f, 0.4f, 0.0f, 0f);
}

