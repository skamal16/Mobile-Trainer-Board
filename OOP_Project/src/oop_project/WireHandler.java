package oop_project;

import java.awt.Graphics;
import static oop_project.Game.mouseLoc;

public class WireHandler{
    
    private final Wire[] wires = new Wire[50];
    private Wire wire = null;
    
    public void startWire(Data d){
        deleteWire();
        wire = new Wire();
        wire.setA(d);
    }
    
    public void endWire(Data d){
        deleteWire();
        if(wire != null){
            wire.setB(d);
            addWire(wire);
        }
        wire = null;
    }
    
    public void cancelWiring(){
        wire = null;
    }
    
    public void deleteWire(){
        for(int i = 0; i < 50; i ++)
            if(wires[i] != null){
                double dist1 = Math.pow(Math.pow(mouseLoc[0] - 6 - wires[i].getA().getX(), 2) + Math.pow(mouseLoc[1] - 30 - wires[i].getA().getY(), 2), 0.5);
                double dist2 = Math.pow(Math.pow(mouseLoc[0] - 6 - wires[i].getB().getX(), 2) + Math.pow(mouseLoc[1] - 30 - wires[i].getB().getY(), 2), 0.5);

                if(dist1 < 5 || dist2 < 5){
                    wires[i] = null;
                }
            }
    }
    
    private void addWire(Wire w){
        int i = 0;
        while (i < 50){
            if(wires[i] == null){
                wires[i] = w;
                break;
            }
            i++;
        }
    }
    
    public void tick(){
        for(Wire w : wires)
            if (w != null)
                w.tick();
    }
    
    public void render(Graphics g){
        for(Wire w : wires)
            if (w != null)
                w.render(g);
        if(wire != null)
            wire.drawLine(g, (int)wire.getA().getX(), (int)wire.getA().getY(), (int)mouseLoc[0], (int)mouseLoc[1], 5);
    }
}
