import stage.display.Image;
import stage.display.Sprite;
import stage.display.Sprite;
import stage.events.CustomEvent;

public class Button extends Sprite {

  public Button (PApplet p) {
    super(p, 100, 30);    
    
    this.centerAnchor();
    this.graphic.beginDraw();
    this.graphic.noStroke();
    this.graphic.fill(0xFFFF0000);
    this.graphic.rect(0,0,width,height);
    this.graphic.endDraw();
    
    this.addEventListener(MouseEvent.MOUSE_CLICK, this, "onClick");
  }
  
  void onClick(MouseEvent e) {
  
    if(this.getBounds().isOver(e.screenX, e.screenY)) {
      println("Click!");
      stage.dispatchEvent(new CustomEvent("randomBgColor"));
    }
    
  }
  
}


