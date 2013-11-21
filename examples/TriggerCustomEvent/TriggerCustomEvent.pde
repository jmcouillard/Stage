import stage.display.Stage;
import stage.display.Sprite;
import stage.events.MouseEvent;

Stage stage;
Button button1;
Button button2;

void setup() {

  // Init PApplet (OPENGL needed)
  size(400, 400, OPENGL);

  // Init stage
  stage = new Stage(this, width, height);

  // Init a first button instance
  button1 = new Button(this);
  button1.setX(200);
  button1.setY(100f);
  stage.addChild(button1);
  
  // Init a second button instance
  button2 = new Button(this);
  button2.setX(200f);
  button2.setY(300f);
  stage.addChild(button2);

  // Add a global event listener for "randomBgColor" event
  stage.addCustomEventListener("randomBgColor", this, "randomize");
}

void draw() {

  // Update and draw Stage
  stage.update();
  stage.draw(this);
}

void randomize(CustomEvent e) {
  println("randomize!");
  
  // Set a new color to Stage
  int newColor = color(255, random(255), random(255), random(255));
  stage.setBgColor(newColor);
  
}

