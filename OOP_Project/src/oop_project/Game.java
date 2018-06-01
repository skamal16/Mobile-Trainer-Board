package oop_project;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class Game extends Canvas implements Runnable
{
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 1240;
    public static final int HEIGHT = 1080;
    public static final int SCALE = 2;
    public final String TITLE = "MOBILE LOGIC DESIGN";
    public static final ICMenu ICMenu = new ICMenu("ICs");
    
    private boolean running = false;
    private Thread thread;
    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public static TrainerBoard trainerBoard;
    private final SpriteGroup primaryGroup = new SpriteGroup();
    private Point pointer;
    private WireHandler wireHandler;
    public static double[] mouseLoc = new double[2];
    
    //temp
    //private BufferedImage player;
    
    public void init(){
        this.requestFocus();
        addKeyListener(new KeyInput(this));
        addMouseListener(new MouseInput(this));
        TrainerBoardLoader loader = new TrainerBoardLoader();
        wireHandler = new WireHandler();
        trainerBoard = loader.load();
        primaryGroup.addSprite(trainerBoard);
        
        //SpriteSheet ss = new SpriteSheet(spriteSheet);
        //player = ss.grabImage(1, 1, 32, 32);
    }
    
    private synchronized void start()
    {
        if(running)
            return;
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    private synchronized void stop()
    {
        if(!running)
            return;
        
        running = false;
        try
        {
            thread.join();
        } 
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        System.exit(1);
        
    }
    
    @Override
    public void run()
    {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            
            render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick(){
        
        pointer = MouseInfo.getPointerInfo().getLocation();
        mouseLoc[0] = pointer.getX();
        mouseLoc[1] = pointer.getY() - 20;
        
        ICMenu.tick();
        primaryGroup.tick();
        wireHandler.tick();
        
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        //////////////////////////
        
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        primaryGroup.render(g);
        wireHandler.render(g);
        ICMenu.render(g);
        
        //////////////////////////
        g.dispose();
        bs.show();
        
    }
    
    public void mousePressed(MouseEvent me) {
        int button = me.getButton();
        
        switch (button) {
            case MouseEvent.BUTTON1:
                if(trainerBoard.selected != null)
                    wireHandler.startWire(TrainerBoard.selected);
                Toggle.activate();
                trainerBoard.reset();
                break;
            case MouseEvent.BUTTON2:
                break;
            case MouseEvent.BUTTON3:
                if(trainerBoard.selected != null){
                    wireHandler.endWire(TrainerBoard.selected);
                    ICMenu.placeIC(TrainerBoard.selected);
                    trainerBoard.reset();
                }
                else
                    wireHandler.cancelWiring();
                break;
            default:
                break;
        }
    }

    public void mouseReleased(MouseEvent me) {
        int button = me.getButton();
        
        switch (button) {
            case MouseEvent.BUTTON1:
                break;
            case MouseEvent.BUTTON2:
                break;
            case MouseEvent.BUTTON3:
                break;
            default:
                break;
        }
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_RIGHT:
                TrainerBoard.dx = -10;
                break;
            case KeyEvent.VK_D:
                TrainerBoard.dx = -10;
                break;
            case KeyEvent.VK_LEFT:
                TrainerBoard.dx = 10;
                break;
            case KeyEvent.VK_A:
                TrainerBoard.dx = 10;
                break;
            case KeyEvent.VK_DOWN:
                TrainerBoard.dy = -10;
                break;
            case KeyEvent.VK_S:
                TrainerBoard.dy = -10;
                break;
            case KeyEvent.VK_UP:
                TrainerBoard.dy = 10;
                break;
            case KeyEvent.VK_W:
                TrainerBoard.dy = 10;
                break;
            case KeyEvent.VK_ALT:
                TrainerBoard.state = true;
                TrainerBoard.zoomedIn = ! TrainerBoard.zoomedIn;
                break;
            case KeyEvent.VK_X:
                wireHandler.deleteWire();
                ICMenu.deleteIC();
                trainerBoard.reset();
                break;
            case KeyEvent.VK_Q:
                LogicProbe.on = !LogicProbe.on;
                break;
            default:
                break;
        }
        
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_RIGHT:
                trainerBoard.dx = 0;
                break;
            case KeyEvent.VK_D:
                trainerBoard.dx = 0;
                break;
            case KeyEvent.VK_LEFT:
                trainerBoard.dx = 0;
                break;
            case KeyEvent.VK_A:
                trainerBoard.dx = 0;
                break;
            case KeyEvent.VK_DOWN:
                trainerBoard.dy = 0;
                break;
            case KeyEvent.VK_S:
                trainerBoard.dy = 0;
                break;
            case KeyEvent.VK_UP:
                trainerBoard.dy = 0;
                break;
            case KeyEvent.VK_W:
                trainerBoard.dy = 0;
                break;
//            case KeyEvent.VK_PAGE_UP:
//                primaryGroup.needsResize = false;
//                break;
//            case KeyEvent.VK_PAGE_DOWN:
//                primaryGroup.needsResize = false;
//                break;
            default:
                break;
        } 
    }
    
    public static void main(String args[])
    {
        Game game = new Game();
        
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
        JFrame frame = new JFrame(game.TITLE);
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(ICMenu);

        frame.setJMenuBar(menuBar);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        game.start();
    }
}