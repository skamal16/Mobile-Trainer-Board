package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import static oop_project.Game.mouseLoc;

public class Toggle extends Sprite {

    public Terminal terminal;
    public static Toggle selected;
    private boolean toggle = false;
    private final double tx;
    private final double ty;

    public Toggle(double x, double y, double x2, double y2, BufferedImage[] images) {
        super(x, y, images);
        terminal = new Terminal(x2, y2, Arrays.copyOfRange(images, 4, images.length));
        tx = x;
        ty = y;
    }

    @Override
    public void tick() {
        
        double dist = Math.pow(Math.pow(mouseLoc[0] - 6 - this.x, 2) + Math.pow(mouseLoc[1] - 50 - this.y, 2), 0.5);
        if(((getImage() == getImage(0) || getImage() == getImage(2)) && dist < 20) || ((getImage() == getImage(1) || getImage() == getImage(3)) && dist < 40))
            selected = this;
        
        if(selected != null){
            dist = Math.pow(Math.pow(mouseLoc[0] - 6 - selected.x, 2) + Math.pow(mouseLoc[1] - 50 - selected.y, 2), 0.5);
            if(((selected.getImage() == selected.getImage(0) || selected.getImage() == selected.getImage(2)) && dist >= 20) || ((selected.getImage() == selected.getImage(1) || selected.getImage() == selected.getImage(3)) && dist >= 40))
                selected = null;
        }
        
        int value = (toggle) ? 2 : 1;
        terminal.setValue(value);
        selection();
        terminal.tick();
    }
    
    public static void activate(){
        if(selected != null){
            selected.toggle = !selected.toggle;
            selected = null;
        }
    }

    private void selection(){
        BufferedImage image = getImage();
        BufferedImage active = getImage(0);
        BufferedImage inactive = getImage(2);
        BufferedImage activeZoom = getImage(1);
        BufferedImage inactiveZoom = getImage(3);
        
        if(image == inactive && toggle)
            setImage(active);
        else if(image == inactiveZoom && toggle)
            setImage(activeZoom);
        else if(image == active && !toggle)
            setImage(inactive);
        else if(image == activeZoom && !toggle)
            setImage(active);
    }
    
    public void stateChange(){
        BufferedImage image = getImage();
        BufferedImage active = getImage(0);
        BufferedImage inactive = getImage(2);
        BufferedImage activeZoom = getImage(1);
        BufferedImage inactiveZoom = getImage(3);
        
        if(image == inactive)
            setImage(inactiveZoom);
        else if(image == active)
            setImage(activeZoom);
        else if(image == inactiveZoom){
            setImage(inactive);
            x = tx;
            y = ty;
        }
        else if(image == activeZoom){
            setImage(active);
            x = tx;
            y = ty;
        }
        terminal.stateChange();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), (int) x, (int) y, null);
        terminal.render(g);
    }

    public void movex() {
        x += TrainerBoard.dx;
        terminal.movex();
    }

    public void movey() {
        y += TrainerBoard.dy;
        terminal.movey();
    }

}
