package oop_project;

import java.awt.image.BufferedImage;

public class NOT extends IntegratedCircuit{
    
    public final int IC = 7404;

    public NOT(BufferedImage[] images) {
        super(images, 14);
    }

    @Override
    public void evaluate() {
        Node[][] gates = new Node[6][2];
        
        for(int i = 0; i < 6; i++){
            gates[i][0] = getPin(2*i + 2);
            gates[i][1] = getPin(2*i + 1);
        }
        
        for(int i = 0; i < 6; i++){
            Node[] temp = {gates[i][1]};
            gates[i][0].setValue(function(temp));
        }
        
    }

    @Override
    public int function(Node[] input) {
        boolean input1, output;
        if(input[0].getValue() == 0)
            return 0;
        else{
            input1 = input[0].getValue() == 2;
        }
        output = !input1;
        
        if(output)
            return 2;
        else
            return 1;
        
    }
    
}