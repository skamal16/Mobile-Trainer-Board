package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Sprite {
    
    private BufferedImage[] images;
    private BufferedImage image;
    public double[] rect;
    public double x;
    public double y;
    private Object parent;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    public BufferedImage getImage(int i){
        return images[i];
    }
    
    public void setImage(BufferedImage image, int i){
        images[i] = image;
    }
    
    public Sprite(Object parent, double a, double b, BufferedImage[] images){
        this.parent = parent;
        this.images = images;
        if(images != null)
            image = images[0];
        this.x = a;
        this.y = b;
        
        if(image != null)
            rect = new double[]{image.getWidth(),image.getHeight()};
    }

    public Sprite(double a, double b, BufferedImage[] images) {
        this(null, a, b, images);
    }
    
    public Sprite(BufferedImage image){
        this.images = new BufferedImage[1];
        this.images[0] = image;
        this.image = image;
        this.x = 0;
        this.y = 0;
    }
    
    public Sprite(double x, double y){
        this(null, x, y, null);
    }
    
    public Sprite(BufferedImage[] images){
        this(null, 0, 0, images);
    }

    public Sprite(Object parent, double x, double y) {
        this(parent, x, y, null);
    }
    
    
    
    public abstract void tick();
    
    public void render(Graphics g){
        
        if (image != null)
            g.drawImage(image, (int)x, (int)y, null);
        
    }
    
    public Object getParent(){
        return parent;
    }
    
}
