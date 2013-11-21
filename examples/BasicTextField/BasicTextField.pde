import stage.display.Stage;
import stage.display.TextField;
import stage.display.FontsManager;

Stage stage;
TextField tf;

void setup() {
	
  // Init PApplet (OPENGL needed)
  size(400,400, OPENGL);
  
  // Init stage
  stage = new Stage(this, width, height);
  
  // Init fonts manager
  FontsManager.init(this);
  
  // Create texfield
  tf = new TextField(this, 200, 200);
  tf.setFont("Lato-Bol.ttf", 25);
  tf.setColor(0xFFFFFFFF);
  tf.setAlign(CENTER, CENTER);
  tf.setText("Hello world!");
  tf.centerAnchor();
  tf.setPos(200,200);
  stage.addChild(tf);
}

void draw() {
	
  // Clear background
  background(0);
  
  // Update and draw Stage
  stage.update();
  stage.draw(this);
}

void mousePressed() {
  tf.showBounds(true);
}
void mouseReleased() {
  tf.showBounds(false);
}
