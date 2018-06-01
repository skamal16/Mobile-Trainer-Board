package oop_project;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import static oop_project.Game.mouseLoc;
import static oop_project.TrainerBoard.limit;
import static oop_project.Game.trainerBoard;
import static oop_project.TrainerBoard.state;
import static oop_project.TrainerBoard.zoomedIn;

public class ICMenu extends JMenu{
    
    private final IntegratedCircuit[] ICs = new IntegratedCircuit[50];
    private IntegratedCircuit IC = null;
    
    private BufferedImage and, or, andZoom, orZoom, not, notZoom, nand, nandZoom, nor, norZoom, xor, xorZoom, sevenSegmentDecoder, sevenSegmentDecoderZoom;
    private BufferedImage[] sevenSegmentDisplay = new BufferedImage[18];
    
    public ICMenu(String name){
        super(name);
        
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            and = loader.loadImage("/AND.png");
            andZoom = loader.loadImage("/ANDzoom.png");
            or = loader.loadImage("/OR.png");
            orZoom = loader.loadImage("/ORzoom.png");
            not = loader.loadImage("/NOT.png");
            notZoom = loader.loadImage("/NOTzoom.png");
            nand = loader.loadImage("/NAND.png");
            nandZoom = loader.loadImage("/NANDzoom.png");
            nor = loader.loadImage("/NOR.png");
            norZoom = loader.loadImage("/NORzoom.png");
            xor = loader.loadImage("/XOR.png");
            xorZoom = loader.loadImage("/XORzoom.png");
            sevenSegmentDisplay[0] = loader.loadImage("/sevenSegmentDisplay_base.png");
            sevenSegmentDisplay[1] = loader.loadImage("/sevenSegmentDisplay_base_zoom.png");
            sevenSegmentDisplay[2] = loader.loadImage("/sevenSegmentDisplay_a.png");
            sevenSegmentDisplay[3] = loader.loadImage("/sevenSegmentDisplay_a_zoom.png");
            sevenSegmentDisplay[4] = loader.loadImage("/sevenSegmentDisplay_b.png");
            sevenSegmentDisplay[5] = loader.loadImage("/sevenSegmentDisplay_b_zoom.png");
            sevenSegmentDisplay[6] = loader.loadImage("/sevenSegmentDisplay_c.png");
            sevenSegmentDisplay[7] = loader.loadImage("/sevenSegmentDisplay_c_zoom.png");
            sevenSegmentDisplay[8] = loader.loadImage("/sevenSegmentDisplay_d.png");
            sevenSegmentDisplay[9] = loader.loadImage("/sevenSegmentDisplay_d_zoom.png");
            sevenSegmentDisplay[10] = loader.loadImage("/sevenSegmentDisplay_e.png");
            sevenSegmentDisplay[11] = loader.loadImage("/sevenSegmentDisplay_e_zoom.png");
            sevenSegmentDisplay[12] = loader.loadImage("/sevenSegmentDisplay_f.png");
            sevenSegmentDisplay[13] = loader.loadImage("/sevenSegmentDisplay_f_zoom.png");
            sevenSegmentDisplay[14] = loader.loadImage("/sevenSegmentDisplay_g.png");
            sevenSegmentDisplay[15] = loader.loadImage("/sevenSegmentDisplay_g_zoom.png");
            sevenSegmentDisplay[16] = loader.loadImage("/sevenSegmentDisplay_dot.png");
            sevenSegmentDisplay[17] = loader.loadImage("/sevenSegmentDisplay_dot_zoom.png");
            sevenSegmentDecoder = loader.loadImage("/sevenSegmentDecoder.png");
            sevenSegmentDecoderZoom = loader.loadImage("/sevenSegmentDecoder_zoom.png");
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        JMenuItem AndItem, OrItem, NotItem, NandItem, NorItem, XorItem, SevenSegmentDisplayItem, SevenSegmentDecoderItem;

        AndItem = new JMenuItem(new AbstractAction("AND") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = {and, andZoom};
                IC = new AND(images);
            }
        });
        OrItem = new JMenuItem(new AbstractAction("OR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = {or, orZoom};
                IC = new OR(images);
            }
        });
        NotItem = new JMenuItem(new AbstractAction("NOT") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = {not, notZoom};
                IC = new NOT(images);
            }
        });
        NandItem = new JMenuItem(new AbstractAction("NAND") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = {nand, nandZoom};
                IC = new NAND(images);
            }
        });
        NorItem = new JMenuItem(new AbstractAction("NOR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = {nor, norZoom};
                IC = new NOR(images);
            }
        });
        XorItem = new JMenuItem(new AbstractAction("XOR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = {xor, xorZoom};
                IC = new XOR(images);
            }
        });
        SevenSegmentDisplayItem = new JMenuItem(new AbstractAction("Seven-Segment Display") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = sevenSegmentDisplay;
                IC = new SevenSegmentDisplay(images);
            }
        });
        SevenSegmentDecoderItem = new JMenuItem(new AbstractAction("Seven-Segment Decoder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage[] images = {sevenSegmentDecoder, sevenSegmentDecoderZoom};
                IC = new SevenSegmentDecoder(images);
            }
        });

        add(AndItem);
        add(OrItem);
        add(NotItem);
        add(NandItem);
        add(NorItem);
        add(XorItem);
        add(SevenSegmentDisplayItem);
        add(SevenSegmentDecoderItem);
    }
    
    private void addIC(IntegratedCircuit ic){
        int i = 0;
        while (i < 50){
            if(ICs[i] == null){
                ICs[i] = ic;
                break;
            }
            i++;
        }
    }
    
    public void placeIC(Data data){
        
        try{
            Node node = (Node)data;
            IC.loadPins(node);
            IC.x = node.x;
            IC.y = node.y;
            addIC(IC);
            IC = null;
        }catch(Exception e){
            cancelPlacement();
        }
    }
    
    private void placement(){
        if(IC != null){
            IC.x = mouseLoc[0];
            IC.y = mouseLoc[1] - 25;
        }
    }
    
    public void deleteIC(){
        for(int i = 0; i < 50; i ++)
            if(ICs[i] != null){
                double dist1 = Math.pow(Math.pow(mouseLoc[0] - 6 - ICs[i].x, 2) + Math.pow(mouseLoc[1] - 30 - ICs[i].y, 2), 0.5);

                if(dist1 < 5)
                    ICs[i] = null;
            }
    }
    
    public void cancelPlacement(){
        IC = null;
    }
    
    public void tick(){
        move();
        stateChange();
        for(IntegratedCircuit i : ICs)
            if (i != null){
                if (i instanceof SevenSegmentDisplay){
                    SevenSegmentDisplay j = (SevenSegmentDisplay)i;
                    j.tick();
                }else
                    i.tick();
            }
        placement();
    }
    
    private void move(){
        if(TrainerBoard.zoomedIn)
        {
            if((TrainerBoard.dx > 0 && trainerBoard.x <= limit[0]) || (TrainerBoard.dx < 0 && trainerBoard.x + trainerBoard.rect[0] >= limit[2])){
                for(IntegratedCircuit i : ICs)
                    if(i != null)
                        i.movex();
            }
            if((TrainerBoard.dy > 0 && trainerBoard.y <= limit[1]) || (TrainerBoard.dy < 0 && trainerBoard.y + trainerBoard.rect[1] >= limit[3])){

                for(IntegratedCircuit i : ICs)
                    if(i != null)
                        i.movey();
            }
        }
    }
    
    private void stateChange(){
        if(state){
            if(zoomedIn)
                for(IntegratedCircuit i : ICs){
                    if(i != null){
                        i.x*=2;
                        i.x-=601.5;
                        i.y*=2;
                    }
                }
            for(IntegratedCircuit i : ICs){
                if(i != null)
                    i.stateChange();
            }
        }
    }
    
    public void render(Graphics g){
        for(IntegratedCircuit i : ICs)
            if (i != null){
                if (i instanceof SevenSegmentDisplay){
                    SevenSegmentDisplay j = (SevenSegmentDisplay)i;
                    j.render(g);
                }else
                    i.render(g);
            }
        if(IC != null){
            if (IC instanceof SevenSegmentDisplay){
                SevenSegmentDisplay j = (SevenSegmentDisplay)IC;
                j.render(g);
            }else
                IC.render(g);
        }
    }
}
