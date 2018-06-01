package oop_project;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class TrainerBoardLoader {
    
    private BufferedImage base;
    private BufferedImage base_zoomed;
    private BufferedImage black_indicator;
    private BufferedImage black_indicator_zoom;
    private BufferedImage blue_indicator;
    private BufferedImage blue_indicator_zoom;
    private BufferedImage black_led;
    private BufferedImage black_led_zoom;
    private BufferedImage green_led;
    private BufferedImage green_led_zoom;
    private BufferedImage red_led;
    private BufferedImage red_led_zoom;
    private BufferedImage terminal;
    private BufferedImage terminal_zoom;
    private BufferedImage ground;
    private BufferedImage ground_zoom;
    private BufferedImage red_selection;
    private BufferedImage red_selection_zoom;
    private BufferedImage logicProbe;
    private BufferedImage zero;
    private BufferedImage lo;
    private BufferedImage hi;
    private BufferedImage active, activeZoom, inactive, inactiveZoom;
    
    public TrainerBoard load(){
        
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            base_zoomed = loader.loadImage("/trainer_board2 - zoom.png");
            base = loader.loadImage("/trainer_board2.png");
            black_indicator = loader.loadImage("/particle_black.png");
            black_indicator_zoom = loader.loadImage("/particle_black_zoom.png");
            blue_indicator = loader.loadImage("/particle_blue.png");
            blue_indicator_zoom = loader.loadImage("/particle_blue_zoom.png");
            black_led = loader.loadImage("/LED_BLACK.png");
            black_led_zoom = loader.loadImage("/LED_BLACK_zoom.png");
            red_led = loader.loadImage("/LED_RED.png");
            red_led_zoom = loader.loadImage("/LED_RED_zoom.png");
            terminal = loader.loadImage("/Slot.png");
            terminal_zoom = loader.loadImage("/Slot_zoom.png");
            ground = loader.loadImage("/Slot2.png");
            ground_zoom = loader.loadImage("/Slot2_zoom.png");
            red_selection = loader.loadImage("/red_selection.png");
            red_selection_zoom = loader.loadImage("/red_selection_zoom.png");
            green_led = loader.loadImage("/LED_GREEN.png");
            green_led_zoom = loader.loadImage("/LED_GREEN_zoom.png");
            logicProbe = loader.loadImage("/logicProbe.png");
            zero = loader.loadImage("/Zero.png");
            lo = loader.loadImage("/Lo.png");
            hi = loader.loadImage("/Hi.png");
            active = loader.loadImage("/active.png");
            activeZoom = loader.loadImage("/activeZoom.png");
            inactive = loader.loadImage("/inactive.png");
            inactiveZoom = loader.loadImage("/inactiveZoom.png");
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        BufferedImage[] images = {base, base_zoomed, black_indicator, black_indicator_zoom, blue_indicator, blue_indicator_zoom, black_led, black_led_zoom, red_led, red_led_zoom, green_led, green_led_zoom, terminal, terminal_zoom, ground, ground_zoom, red_selection, red_selection_zoom, logicProbe, zero, lo, hi, active, activeZoom, inactive, inactiveZoom};
        
        TrainerBoard trainerBoard = new TrainerBoard(images);
        
        return trainerBoard;
        
    }
    
}
