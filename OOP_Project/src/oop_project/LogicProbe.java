package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static oop_project.Game.mouseLoc;

public class LogicProbe extends Sprite{
    
    public static boolean on = false;

    public LogicProbe(BufferedImage[] images) {
        super(images);
    }

    @Override
    public void tick() {
        if(on){
            selection();
            x = mouseLoc[0] + 10;
            y = mouseLoc[1] - 10;
        }
    }
    
    private void selection(){
        BufferedImage zero = getImage(1);
        BufferedImage lo = getImage(2);
        BufferedImage hi = getImage(3);
        if(TrainerBoard.selected == null)
            setImage(getImage(0));
        else if(TrainerBoard.selected.getValue() == 0)
            setImage(zero);
        else if(TrainerBoard.selected.getValue() == 1)
            setImage(lo);
        else
            setImage(hi);
    }
    
    @Override
    public void render(Graphics g){
        if(on)
            super.render(g);
    }
    
}
