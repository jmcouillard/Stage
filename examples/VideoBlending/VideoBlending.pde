import stage.display.Stage;
import stage.display.Sprite;
import stage.display.VideoPlayer;
import processing.video.Movie;

Stage stage;
VideoPlayer video1;
VideoPlayer video2;

void setup() {

  // Init PApplet (OPENGL needed)
  size(400, 400, OPENGL);

  // Init stage
  stage = new Stage(this, width, height);

  video1 = new VideoPlayer(this, "video1.mov");
  video1.loop();
  stage.addChild(video1);
  
  video2 = new VideoPlayer(this, "video2.mov");
  video2.setAnchor(160,120);
  video2.setBlendMode(ADD);
  video2.loop();
  stage.addChild(video2);
}

void draw() {

  // Clear background
  background(0);

  // Update and draw Stage
  stage.update();
  stage.draw(this);
}

void mouseMoved() {
  video2.setX(mouseX);
  video2.setY(mouseY);
}

void keyPressed() {  
  if(key=='1') video2.setBlendMode(ADD);
  if(key=='2') video2.setBlendMode(MULTIPLY);
  if(key=='3') video2.setBlendMode(SCREEN);
  if(key=='4') video2.setBlendMode(BLEND);
}

