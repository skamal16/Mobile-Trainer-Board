package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;
import static oop_project.Game.HEIGHT;
import static oop_project.Game.WIDTH;

public class TrainerBoard extends Sprite {
    
    public static double dx = 0;
    public static double dy = 0;
    public static boolean state = false;
    public static boolean zoomedIn = false;
    public static double limit[] = {0, 0, WIDTH/2, HEIGHT/4 - 300};
    
    public final NodalGroup[] nodalGroups;
    public final LED[] LEDs;
    public final Toggle[] toggles;
    public final VCC vcc1;
    public final VCC vcc2;
    private final Ground grnd1;
    private final Ground grnd2;
    public static Data selected = null;
    private final LogicProbe logicProbe;

    public TrainerBoard(BufferedImage[] images) {
        super(310, 0, Arrays.copyOfRange(images, 0, 2));
        nodalGroups = new NodalGroup[24];
        BufferedImage[] newImages = Arrays.copyOfRange(images, 2, 6);
        for(int j = 0; j < 24; j+= 12)
            for(int i = 0; i < 12; i += 4){
                double transform[] = {x + 305 + (j/12)*365, y + 199 + 60*i};
                nodalGroups[i + j] = new NodalGroup(this, transform[0], transform[1], false, newImages);
                nodalGroups[i + j + 1] = new NodalGroup(this, transform[0] - 2, transform[1] + 47, true, newImages);
                nodalGroups[i + j + 2] = new NodalGroup(this, transform[0] - 2, transform[1] + 118, true, newImages);
                nodalGroups[i + j + 3] = new NodalGroup(this, transform[0] + 0.5, transform[1] + 193, false, newImages);

            }
        BufferedImage[] ledImages = Arrays.copyOfRange(images, 6, images.length);
        BufferedImage[] toggleImages = concatenate(Arrays.copyOfRange(images, 22, 26), Arrays.copyOfRange(images, 12, 18));
        LEDs = new LED[8];
        for (int i = 0; i < 8; i++){
            double x = (i < 4) ? 1370 : 1400;
            double y = (i < 4) ? 20*i + 150 : 20*(i - 4) + 150;
            LEDs[i] = new LED(27*i + 1350, 70, x, y, ledImages);
        }
        toggles = new Toggle[8];
        for (int i = 0; i < 8; i++){
            double x = i*90 + 627;
            double y = 960;
            toggles[i] = new Toggle(x, y, x + 7, y - 20, toggleImages);
        }
        double x1 = 540;
        double y1 = 200;
        
        double x2 = 1360;
        double y2 = 830;
        
        vcc1 = new VCC(x1, y1, Arrays.copyOfRange(ledImages, 6, images.length));
        vcc2 = new VCC(x2, y2, Arrays.copyOfRange(ledImages, 6, images.length));
        grnd1 = new Ground(x1, y1+20, Arrays.copyOfRange(ledImages, 8, images.length));
        grnd2 = new Ground(x2, y2+20, Arrays.copyOfRange(ledImages, 8, images.length));
        
        logicProbe = new LogicProbe(Arrays.copyOfRange(images, 18, 22));
        
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(getImage(), (int)x, (int)y, null);
        for (NodalGroup i : nodalGroups)
            i.render(g);
        for (LED i : LEDs)
            i.render(g);
        for (Toggle i : toggles)
            i.render(g);
        vcc1.render(g);
        grnd1.render(g);
        vcc2.render(g);
        grnd2.render(g);
        logicProbe.render(g);
    }
    
    public static <T> T[] concatenate(T[] a, T[] b) {
    int aLen = a.length;
    int bLen = b.length;

    @SuppressWarnings("unchecked")
    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
    System.arraycopy(a, 0, c, 0, aLen);
    System.arraycopy(b, 0, c, aLen, bLen);

    return c;
    }

    @Override
    public void tick() {
        move();
        stateChange();
        for(NodalGroup i: nodalGroups)
            i.tick();
        for(LED i: LEDs)
            i.tick();
        for(Toggle i : toggles)
            i.tick();
        vcc1.tick();
        grnd1.tick();
        vcc2.tick();
        grnd2.tick();
        findSelected();
        logicProbe.tick();
    }
    
    public void reset(){
        for(NodalGroup i : nodalGroups)
            for(NodeGroup j : i.nodeGroups)
                for(Node k : j.nodes)
                    k.setValue(0);
        for(LED i : LEDs)
            i.terminal.setValue(0);
    }
    
    private void findSelected(){
        Data selected = null;
        
        for(NodalGroup i : nodalGroups)
            for(NodeGroup j: i.nodeGroups)
                for(Node k: j.nodes)
                    if(k.selected){
                        selected = k;
                        break;
                    }
        for(LED i : LEDs)
            if(i.terminal.selected){
                selected = i.terminal;
                break;
            }
        for(Toggle i : toggles)
            if(i.terminal.selected){
                selected = i.terminal;
                break;
            }
        if(vcc1.selected)
            selected = vcc1;
        if(grnd1.selected)
            selected = grnd1;
        if(vcc2.selected)
            selected = vcc2;
        if(grnd2.selected)
            selected = grnd2;
        
        if(TrainerBoard.selected != selected)
            TrainerBoard.selected = selected;
    }
    
    private void stateChange(){
        if(state){
            if (zoomedIn){
                setImage(getImage(1));
                x = 20;
                for(NodalGroup i : nodalGroups)
                    for(NodeGroup j: i.nodeGroups)
                        for(Node k: j.nodes){
                            k.x*=2;
                            k.x-=601.5;
                            k.y*=2;
                        }
                for(LED i : LEDs){
                    i.x*=2;
                    i.x-=601.5;
                    i.y*=2;
                    i.terminal.x*=2;
                    i.terminal.x-=601.5;
                    i.terminal.y*=2;
                }
                for(Toggle i : toggles){
                    i.x*=2;
                    i.x-=601.5;
                    i.y*=2;
                    i.terminal.x*=2;
                    i.terminal.x-=601.5;
                    i.terminal.y*=2;
                }
                vcc1.x*=2;
                vcc1.x-=600;
                vcc1.y*=2;
                grnd1.x*=2;
                grnd1.x-=600;
                grnd1.y*=2;
                vcc2.x*=2;
                vcc2.x-=600;
                vcc2.y*=2;
                grnd2.x*=2;
                grnd2.x-=600;
                grnd2.y*=2;
                state = false;
            }else{
            setImage(getImage(0));
            x = 310;
            y = 0;
            state = false;
            }
            
            for(NodalGroup i : nodalGroups)
                for(NodeGroup j: i.nodeGroups)
                    for(Node k: j.nodes)
                        k.stateChange();
            for(LED i : LEDs)
                i.stateChange();
            for(Toggle i : toggles)
                i.stateChange();
            vcc1.stateChange();
            grnd1.stateChange();
            vcc2.stateChange();
            grnd2.stateChange();
        }
    }
    
    private void move(){
        if(zoomedIn)
        {
            if((dx > 0 && x <= limit[0]) || (dx < 0 && x + this.rect[0] >= limit[2])){
                x += dx;
                for(NodalGroup i : nodalGroups)
                    for(NodeGroup j: i.nodeGroups)
                        for(Node k: j.nodes)
                            k.movex();
                for(LED i : LEDs)
                    i.movex();
                for(Toggle i : toggles)
                    i.movex();
                vcc1.movex();
                grnd1.movex();
                vcc2.movex();
                grnd2.movex();
                
            }
            if((dy > 0 && y <= limit[1]) || (dy < 0 && y + this.rect[1] >= limit[3])){
                y += dy;
                for(NodalGroup i : nodalGroups)
                    for(NodeGroup j: i.nodeGroups)
                        for(Node k: j.nodes)
                            k.movey();
                for(LED i : LEDs)
                    i.movey();
                for(Toggle i : toggles)
                    i.movey();
                vcc1.movey();
                grnd1.movey();
                vcc2.movey();
                grnd2.movey();
            }
        }
    }
}
