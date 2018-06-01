package oop_project;

import java.awt.image.BufferedImage;

public class NOR extends IntegratedCircuit{
    
    public final int IC = 7402;

    public NOR(BufferedImage[] images) {
        super(images, 14);
    }

    @Override
    public void evaluate() {
        Node[][] gates = new Node[4][3];
        
        for(int i = 0; i < 4; i++){
            gates[i][0] = getPin(3*i + 1);
            gates[i][1] = getPin(3*i + 2);
            gates[i][2] = getPin(3*i + 3);
        }
        
        for(int i = 0; i < 4; i++){
            Node[] temp = {gates[i][1], gates[i][2]};
            gates[i][0].setValue(function(temp));
        }
        
    }

    @Override
    public int function(Node[] input) {
        boolean input1, input2, output;
        if(input[0].getValue() == 0 || input[1].getValue() == 0)
            return 0;
        else{
            input1 = input[0].getValue() == 2;
            input2 = input[1].getValue() == 2;
        }
        output = !(input1 || input2);
        
        if(output)
            return 2;
        else
            return 1;
        
    }
    
}