package oop_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SevenSegmentDisplay extends IntegratedCircuit{
    
    private final BufferedImage[] imagesNormal = new BufferedImage[9];
    private final BufferedImage[] imagesZoomed = new BufferedImage[9];
    private BufferedImage[] imagesRender = new BufferedImage[9];

    public SevenSegmentDisplay(BufferedImage[] images) {
        super(images, 10);
        for(int i = 0; i < 18; i+=2)
            imagesNormal[i/2] = images[i];
        for(int i = 1; i < 18; i+=2)
            imagesZoomed[(i-1)/2] = images[i];
        imagesRender[0] = (TrainerBoard.zoomedIn) ? imagesZoomed[0] : imagesNormal[0];
    }
    
    @Override
    public void loadPins(Node node) throws Exception{
        NodeGroup nodeGroup = (NodeGroup)node.getParent();
        NodalGroup nodalGroup = (NodalGroup)nodeGroup.getParent();
        TrainerBoard trainerBoard = (TrainerBoard)nodalGroup.getParent();
        int nodalGroupIndex = 0, nodeGroupIndex = 0;
        
        for(int i = 0; i < trainerBoard.nodalGroups.length; i++)
            if(trainerBoard.nodalGroups[i] == nodalGroup)
                nodalGroupIndex = i;
        for(int i = 0; i < nodalGroup.nodeGroups.length; i++)
            if(nodalGroup.nodeGroups[i] == nodeGroup)
                nodeGroupIndex = i;
        
        if(node != trainerBoard.nodalGroups[nodalGroupIndex].nodeGroups[nodeGroupIndex].nodes[4])
            if(node != trainerBoard.nodalGroups[nodalGroupIndex].nodeGroups[nodeGroupIndex].nodes[3])
                if(node != trainerBoard.nodalGroups[nodalGroupIndex].nodeGroups[nodeGroupIndex].nodes[2])
                    throw new Exception("Invalid Node");
        
        int i = 0;
        while(i < 2){
            int j = 0;
            while(j < 5){
                setPin(trainerBoard.nodalGroups[nodalGroupIndex + i].nodeGroups[nodeGroupIndex + j].nodes[4], (5 * i) + j);
                j++;
            }
            i++;
        }
        
        vcc = getPin(2);
        grnd = getPin(7);
    }

    @Override
    public void evaluate() {
        
        BufferedImage[] imagesRender = new BufferedImage[9];
        
        imagesRender[0] = (TrainerBoard.zoomedIn) ? imagesZoomed[0] : imagesNormal[0];
        
        int[] values = {getPin(3).getValue(), getPin(4).getValue(), getPin(8).getValue(), getPin(6).getValue(), getPin(5).getValue(), getPin(1).getValue(), getPin(0).getValue(), getPin(9).getValue()};
        
        if (vcc != null && vcc.getValue() == 2 && grnd.getValue() == 2){
            if(TrainerBoard.zoomedIn)
                for(int i = 1; i < 9; i++)
                    imagesRender[i] = (values[i-1] == 1) ? imagesZoomed[i] : null;
            else
                for(int i = 1; i < 9; i++)
                    imagesRender[i] = (values[i-1] == 1) ? imagesNormal[i] : null;
        }
        
        this.imagesRender = imagesRender;
    }
    
    @Override
    public void tick(){
        evaluate();
    }
    
    @Override
    public void render(Graphics g){
        for(BufferedImage i: imagesRender)
            if (i != null)
                g.drawImage(i, (int)x, (int)y, null);
    }

    @Override
    public int function(Node[] input) {
        return 0;
    }
}
