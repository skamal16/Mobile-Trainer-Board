package oop_project;

import java.awt.Color;
import java.awt.Graphics;

public class Wire{
    
    private Data a;
    public NodeGroup aGroup;
    private Data b;
    public NodeGroup bGroup;
    public Data input;
    public Data output;

    public Data getA() {
        return a;
    }

    public Data getB() {
        return b;
    }
    
    public void setA(Data a) {
        this.a = a;
    }

    public void setB(Data b) {
        this.b = b;
    }
    
    public void tick(){
        if(a instanceof Ground){
            input = a;
            output = b;
        }
        else if(b instanceof Ground){
            input = b;
            output = a;
        }
        else if (a.getValue() > b.getValue()){
            input = a;
            output = b;
        }else{
            input = b;
            output = a;
        }
        output.setValue(input.getValue());
    }
    
    public void render(Graphics g){
        
        if (a != null && b != null)
            drawLine(g, (int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY(), 5);
    }
    
    public void drawLine(Graphics g, int x1, int y1, int x2, int y2, int width){
        
        g.setColor(Color.BLUE);
        
        int dX = x2 - x1;
        int dY = y2 - y1;
        double lineLength = Math.sqrt(dX * dX + dY * dY);
        
        double scale = (double)(width) / (2 * lineLength);

        // The x,y increments from an endpoint needed to create a rectangle...
        double ddx = -scale * (double)dY;
        double ddy = scale * (double)dX;
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        int dx = (int)ddx;
        int dy = (int)ddy;

        // Now we can compute the corner points...
        int xPoints[] = new int[4];
        int yPoints[] = new int[4];

        xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
        xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;

        g.fillPolygon(xPoints, yPoints, 4);
    }

}
