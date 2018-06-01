package oop_project;

import java.awt.image.BufferedImage;
import static oop_project.Game.mouseLoc;

public class Node extends Sprite implements Data{

    public final double tx;
    public final double ty;
    private int value = 0;
    public boolean selected = false;
    
    public Node(NodeGroup parent, double x, double y, BufferedImage[] images) {
        super(parent, x, y, images);
        tx = x;
        ty = y;
    }

    @Override   
    public void tick() {
        selection();
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
    
    public void selection(){
        double dist = Math.pow(Math.pow(mouseLoc[0] - 6 - x, 2) + Math.pow(mouseLoc[1] - 30 - y, 2), 0.5);
        
        
        if (dist < 3 && getImage() == getImage(0)){
            setImage(getImage(2));
            x -= 1;
            y -= 1;
            selected = true;
        }
        else if(dist < 6 && getImage() == getImage(1)){
            setImage(getImage(3));
            x -= 2;
            y -= 2;
            selected = true;
        }
        else if(dist > 5 && getImage() == getImage(2)){
            setImage(getImage(0));
            x += 1;
            y += 1;
            selected = false;
        }
        else if(dist > 10 && getImage() == getImage(3)){
            setImage(getImage(1));
            x += 2;
            y += 2;
            selected = false;
        }
    }
    
    public void stateChange(){
        if(getImage() == getImage(0))
            setImage(getImage(1));
        else if(getImage() == getImage(2))
            setImage(getImage(3));
        else if(getImage() == getImage(3)){
            setImage(getImage(2));
            x = tx;
            y = ty;
        }
        else{
            setImage(getImage(0));
            x = tx;
            y = ty;
        }
    }
    
    public void movex(){
        x += TrainerBoard.dx;
    }
    
    public void movey(){
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
