package oop_project;

import java.awt.image.BufferedImage;

public abstract class IntegratedCircuit extends Sprite{
    
    private final Node[] pins;
    public Node vcc;
    public Node grnd;

    public IntegratedCircuit(BufferedImage[] images, int pins) {
        super(images);
        this.pins = new Node[pins];
        if(TrainerBoard.zoomedIn)
            setImage(getImage(1));
    }
    
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
            throw new Exception("Invalid Node");
        
        int i = 0;
        while(i < 2){
            int j = 0;
            while(j < pins.length/2){
                this.pins[(pins.length/2 * i) + j] = trainerBoard.nodalGroups[nodalGroupIndex + i].nodeGroups[nodeGroupIndex + j].nodes[4];
                j++;
            }
            i++;
        }
        
        vcc = pins[0];
        grnd = pins[pins.length - 1];
    }
    
    @Override
    public void tick(){
        if (vcc != null && vcc.getValue() == 2 && grnd.getValue() == 1)
            evaluate();
    }
    
    public Node getPin(int i){
        return pins[i];
    }
    
    public void setPin(Node n, int i){
        pins[i] = n;
    }
    
    public void stateChange(){
        BufferedImage image = getImage();
        BufferedImage normal = getImage(0);
        BufferedImage zoom = getImage(1);
        
        if(image == normal)
            setImage(zoom);
        else if(image == zoom){
            setImage(normal);
            x = pins[0].tx;
            y = pins[0].ty;
        }
    }
    
    public void movex(){
        x += TrainerBoard.dx;
    }
    
    public void movey(){
        y += TrainerBoard.dy;
    }
    
    public abstract void evaluate();
    
    public abstract int function(Node[] input);
    
}
