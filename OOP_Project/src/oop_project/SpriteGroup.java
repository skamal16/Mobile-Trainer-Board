package oop_project;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static oop_project.Game.HEIGHT;
import static oop_project.Game.WIDTH;

public class SpriteGroup {
    
    private final Sprite[] sprites = new Sprite[10];
//    private final BufferedImage[] reserves = new BufferedImage[10];
//    public double zoom = 1;
//    public boolean needsResize = false;
    
    public void addSprite(Sprite sprt){
        int i = 0;
        while (i < 10){
            if(sprites[i] == null){
                sprites[i] = sprt;
//                reserves[i] = sprt.image;
                break;
            }
            i++;
        }
    }
    
    public void tick(){
//        if(needsResize){
//            int i = 0;
//            while (i < 10){
//                if (sprites[i] != null)
//                    resize(i);
//                else break;
//                i++;
//            }
//        }
        for(Sprite sprite : sprites){
            if (sprite != null)
                sprite.tick();
        }
    }
    
    public void render(Graphics g){
        for (Sprite sprite : sprites)
            if (sprite != null)
                sprite.render(g);
    }
    
//    public void resize(int i){
//        BufferedImage image = sprites[i].image;
//        int scaledWidth = (int) (image.getWidth() * zoom);
//        int scaledHeight = (int) (image.getHeight() * zoom);
//        double[] center = {WIDTH/2, HEIGHT/2};
//        double[] imageCenter = {sprites[i].x + image.getWidth()/2, sprites[i].y + image.getHeight()/2};
//        double[] foculPoint = {center[0] + (imageCenter[0] - center[0])/2, center[1] + (imageCenter[1] - center[1])/2};
//        int[] newCoords = {(int)foculPoint[0] - scaledWidth/2, (int)foculPoint[1] - scaledHeight/2};
//        
//        BufferedImage temp = new BufferedImage(scaledWidth, scaledHeight, image.getType());
//        Graphics2D g2d = temp.createGraphics();
//        g2d.drawImage(reserves[i], newCoords[0], newCoords[1], scaledWidth, scaledHeight, null);
//        g2d.dispose();
//        sprites[i].image = temp;
//        sprites[i].rect = new double[]{temp.getWidth(),temp.getHeight()};
//    }
    
}
