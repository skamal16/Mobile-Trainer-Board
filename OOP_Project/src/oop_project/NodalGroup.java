package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class NodalGroup extends Sprite{
    
    public NodeGroup[] nodeGroups;
    public static boolean state;

    public NodalGroup(TrainerBoard parent, double x, double y, boolean state, BufferedImage[] images) {
        super(parent, x, y);
        this.state = state;
        if(!state){
            nodeGroups = new NodeGroup[2];
            nodeGroups[0] = new NodeGroup(this, x, y, images);
            nodeGroups[1] = new NodeGroup(this, x, y + 10, images);
        }else{
            nodeGroups = new NodeGroup[30];
            for(int i = 0; i < 30; i++)
                nodeGroups[i] = new NodeGroup(this, x + i*10.85, y, images);
        }
    }

    @Override
    public void tick() {
        for(NodeGroup i : nodeGroups)
            i.tick();
    }
    
    @Override
    public void render(Graphics g){
        for(NodeGroup i : nodeGroups)
            i.render(g);
    }
    
}
