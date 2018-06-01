package oop_project;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter    {

    Game game;
    
    public MouseInput(Game game){
        this.game = game;
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        game.mousePressed(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        game.mouseReleased(me);
    }
    
}
