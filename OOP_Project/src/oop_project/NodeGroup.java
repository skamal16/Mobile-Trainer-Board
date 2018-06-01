package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class NodeGroup extends Sprite{
    
    public Node[] nodes;
    private int value;
    private Node source;
    
    public NodeGroup(NodalGroup parent, double x, double y, BufferedImage[] images){
        super(parent, x, y);
        value = 0;
        boolean state = NodalGroup.state;
        if (!state){
            nodes = new Node[25];
            for(int j = 0; j < 25; j+=5)
                for(int i = 0; i < 5; i++)
                    nodes[i + j] = new Node(this, x + i*11 + (j/5)*66.7, y, images);
        }else{
            nodes = new Node[5];
            for(int i = 0; i < 5; i++)
                nodes[i] = new Node(this, x, y + i*10, images);
        }
        source = nodes[0];
    }

    @Override
    public void tick() {
        for(Node i: nodes)
            i.tick();
        checkValues();
    }
    
    @Override
    public void render(Graphics g){
        for(Node i : nodes)
            i.render(g);
    }
    
    public void checkValues(){
        
        for (Node i: nodes){
            if(i.getValue() > source.getValue()){
                source = i;
                break;
            }
            i.setValue(source.getValue());
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    
    
}
