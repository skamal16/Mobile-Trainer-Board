package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class LED extends Sprite{
    
    public Terminal terminal;
    private boolean on = false;
    private boolean state = false;
    private double tx;
    private double ty;

    public LED(double x, double y, double x2, double y2, BufferedImage[] images) {
        super(x, y, images);
        terminal = new Terminal(x2, y2, Arrays.copyOfRange(images, 6, images.length));
        tx = x;
        ty = y;
    }

    @Override
    public void tick() {
        
        on = terminal.getValue() == 2 || terminal.getValue() == 1;
        state = terminal.getValue() == 2;
        selection();
        terminal.tick();
    }
    
    private void selection(){
        BufferedImage redLed = getImage(2);
        BufferedImage redLedZoom = getImage(3);
        BufferedImage greenLed = getImage(4);
        BufferedImage greenLedZoom = getImage(5);
        
        if (on && getImage() == getImage(0))
            if(state)
                setImage(redLed);
            else
                setImage(greenLed);
        else if(on && getImage() == getImage(1)){
            if(state)
                setImage(redLedZoom);
            else
                setImage(greenLedZoom);
        }
        else if(!on && (getImage() == redLed || getImage() == greenLed)){
            setImage(getImage(0));
        }
        else if(!on && (getImage() == redLedZoom || getImage() == greenLedZoom)){
            setImage(getImage(1));
        }
    }
    
    public void stateChange(){
        if(getImage() == getImage(0))
            setImage(getImage(1));
        else if(getImage() == getImage(2))
            setImage(getImage(3));
        else if(getImage() == getImage(4))
            setImage(getImage(5));
        else if(getImage() == getImage(3)){
            setImage(getImage(2));
            x = tx;
            y = ty;
        }
        else if(getImage() == getImage(5)){
            setImage(getImage(4));
            x = tx;
            y = ty;
        }
        else{
            setImage(getImage(0));
            x = tx;
            y = ty;
        }
        terminal.stateChange();
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(getImage(), (int)x, (int)y, null);
        terminal.render(g);
    }
    
    public void movex(){
        x += TrainerBoard.dx;
        terminal.movex();
    }
    
    public void movey(){
        y += TrainerBoard.dy;
        terminal.movey();
    }
    
}
