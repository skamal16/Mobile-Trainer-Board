package oop_project;

import java.awt.image.BufferedImage;
import static oop_project.Game.mouseLoc;

public class VCC extends Sprite implements Data{

    private final int value = 2;
    private final double tx;
    private final double ty;
    public boolean selected = false;

    public VCC(double x, double y, BufferedImage[] images) {
        super(x, y, images);
        tx = x;
        ty = y;
    }

    @Override
    public void tick() {
        selection();
    }
    
    public void selection(){
        double dist = Math.pow(Math.pow(mouseLoc[0] - 6 - x, 2) + Math.pow(mouseLoc[1] - 30 - y, 2), 0.5);
        BufferedImage redselection = getImage(4);
        BufferedImage redselection_zoom = getImage(5);
        
        if (dist < 6 && getImage() == getImage(0)){
            setImage(redselection);
            x -= 1;
            y -= 1;
            selected = true;
        }
        else if(dist < 12 && getImage() == getImage(1)){
            setImage(redselection_zoom);
            x -= 2;
            y -= 2;
            selected = true;
        }
        else if(dist > 10 && getImage() == redselection){
            setImage(getImage(0));
            x += 1;
            y += 1;
            selected = false;
        }
        else if(dist > 20 && getImage() == redselection_zoom){
            setImage(getImage(1));
            x += 2;
            y += 2;
            selected = false;
        }
    }

    @Override
    public int getValue() {
        return value;
    }
    
    @Override
    public void setValue(int value) {
        
    }

    public void stateChange() {
        if (getImage() == getImage(0)) {
            setImage(getImage(1));
        } else {
            setImage(getImage(0));
            x = tx;
            y = ty;
        }
    }

    public void movex() {
        x += TrainerBoard.dx;
    }

    public void movey() {
        y += TrainerBoard.dy;
    }
    
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

}
