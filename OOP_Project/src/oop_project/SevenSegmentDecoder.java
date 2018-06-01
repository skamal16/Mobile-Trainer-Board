package oop_project;

import java.awt.image.BufferedImage;

public class SevenSegmentDecoder extends IntegratedCircuit{
    
    public final int IC = 7447;

    public SevenSegmentDecoder(BufferedImage[] images) {
        super(images, 16);
    }
    
    @Override
    public void tick(){
        if (vcc != null && (getPin(14).getValue() != 0) && (getPin(8).getValue() != 0) && (getPin(9).getValue() != 0) && (getPin(13).getValue() != 0) && vcc.getValue() == 2 && grnd.getValue() == 1)
            evaluate();
    }

    @Override
    public void evaluate() {
        Node[] inputs = {getPin(14), getPin(8), getPin(9), getPin(13)};
        Node[] outputs = {getPin(3), getPin(4), getPin(5), getPin(6), getPin(7), getPin(1), getPin(2)};
        
        Boolean[] input = new Boolean[4];
        Boolean[] output = new Boolean[7];
        
        for(int i = 0; i < 4; i++){
            input[i] = inputs[i].getValue() == 2;
        }
        
        boolean d = input[0];
        boolean c = input[1];
        boolean b = input[2];
        boolean a = input[3];
        
        output[0] = a || c || (b && d) || (!b && !d);
        output[1] = !b || (!c && !d) || (c && d);
        output[2] = b || !c || d;
        output[3] = (!b && !d) || (c && !d) || (b && !c && d) || (!b && c) || a;
        output[4] = (!b && !d) || (c && !d);
        output[5] = a || (!c && !d) || (b && !c) || (b && !d);
        output[6] = a || (b && !c) || (!b && c) || (c && !d);
        
        for(int i = 0; i < 7; i++)
            outputs[i].setValue((output[i]) ? 1 : 2);
    }

    @Override
    public int function(Node[] input) {
        return 0;
    }
    
}
