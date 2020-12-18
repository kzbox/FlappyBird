import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


// Model
abstract class Thing {
    protected double x, y, width, height;
    public Thing(double x, double y, double w, double h){
        this.x = x; this.y = y;
        width = w; height = h;
    }
    abstract public void draw(Graphics g);
    // get系メソッド
    double getX()     { return x; }
    double getY()     { return y; }
    double getWidth() { return width;  }
    double getHeight(){ return height; }
    // set系メソッド
    void setX(double x)     { this.x = x; }
    void setY(double y)     { this.y = y; }
    void setWidth(double w) { width  = w; }
    void setHeight(double h){ height = h; }
    // add系メソッド
    void addX(double x){ this.x += x; }
    void addY(double y){ this.y += y; }
}
class Bird extends Thing {
    public final static double V0 = 49;
    public final static double GRAVITY = 23;
    public final static int BIRD_WIDTH = 30, BIRD_HEIGHT = 30;
    private double y0;
    public Bird(double x, double y){
        super(x, y, BIRD_WIDTH, BIRD_HEIGHT);
        y0 = y;
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
    public void setY0asY(){
        y0 = y;
    }
    public double getY0(){
        return y0;
    }
}
class Dokan extends Thing {
    public Dokan(){
        super(0,0,0,0);
    }
    public Dokan(double x, double y, double w, double h){
        super(x, y, w, h);
    }
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
    public void moveDokan(int speed){
        x -= speed;
    }
}

class ModelObservable extends Observable implements ActionListener{
    public final static int SCREEN_WIDTH = 400, SCREEN_HEIGHT = 800;
    public final static int MAX_DOKAN_NUM = 15;
    public final static int FPS = 100;
    public final static int SPEED = 2;
    private final static int HABA = 130;
    public Bird bird;
    public ArrayList<Dokan> upperDokan;
    public ArrayList<Dokan> lowerDokan;
    private javax.swing.Timer timer;
    private double t;
    private boolean startFlag;
    private boolean gameOverFlag;
    private int focusDokanIndex;
    private java.util.Random rand;
    public ModelObservable(){
        timer = new javax.swing.Timer(1000/FPS, this);
        timer.start();
        bird = new Bird(SCREEN_WIDTH/2 - Bird.BIRD_WIDTH/2, SCREEN_HEIGHT/2 - Bird.BIRD_HEIGHT/2);
        upperDokan = new ArrayList<Dokan>();
        lowerDokan = new ArrayList<Dokan>();
        rand = new java.util.Random();
        for(int i=0; i < MAX_DOKAN_NUM; i++){
            int rand_height = rand.nextInt(SCREEN_HEIGHT/2);
            upperDokan.add(new Dokan(360 + i*400, 0                                   , 40, SCREEN_HEIGHT/4 + rand_height));
            lowerDokan.add(new Dokan(360 + i*400, SCREEN_HEIGHT/4 + rand_height + HABA, 40, SCREEN_HEIGHT                ));
        }
        t = 0;
        startFlag = false;
        gameOverFlag = false;
        focusDokanIndex = 0;
    }
    private boolean isIn(int index){ // 接近している土管のindexが引数
        Dokan udokan = upperDokan.get(index);
        Dokan ldokan = lowerDokan.get(index);
        double dokanX = udokan.getX();
        double dokanW = udokan.getWidth();
        double birdX = bird.getX();
        double birdW = bird.getWidth();

        if(birdX+birdW > dokanX && birdX < dokanX+dokanW){
            // return true;
            double udokanH = udokan.getHeight();
            double ldokanH = ldokan.getY();
            double birdY = bird.getY();
            double birdH = bird.getHeight();
            if(birdY < udokanH || birdY+birdH > ldokanH){
                return true;
            }
        }
        return false;
    }
    private boolean isGameOver(){
        if(bird.getY() > SCREEN_HEIGHT){
            return true;
        }
        Dokan focusDokan = upperDokan.get(focusDokanIndex);
        if(focusDokan.getX() + focusDokan.getWidth() < bird.getX()){
            focusDokanIndex++;
        }
        return isIn(focusDokanIndex);
    }
    private void calcBirdPos(){
        bird.setY(bird.getY0() - bird.V0*t + bird.GRAVITY*t*t/2);
    }
    public void setT(double time){
        t = time;
    }
    public void setStartFlag(){
        startFlag = true;
    }
    public boolean getGameOverFlag(){
        return gameOverFlag;
    }
    public void actionPerformed(ActionEvent e){
        if(startFlag){
            t += (double)10/FPS;
            calcBirdPos();
            for(int i = 0; i < MAX_DOKAN_NUM; i++){
                upperDokan.get(i).moveDokan(SPEED);
                lowerDokan.get(i).moveDokan(SPEED);
            }
            if(isGameOver()){
                gameOverFlag = true;
            }
        }
        setChanged();
        notifyObservers();
    }
}

// View
class ViewPanel extends JPanel implements Observer {
    private ModelObservable model;
    private Controller cont;
    public ViewPanel(ModelObservable mo, Controller co){
        this.setBackground(Color.WHITE);
        model = mo;
        cont = co;
        model.addObserver(this);
        this.addMouseListener(cont);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(model.getGameOverFlag()){
            g.fillRect(100, 100, 200, 200);
        }
        model.bird.draw(g);
        for(int i = 0; i < model.MAX_DOKAN_NUM; i++){
            model.upperDokan.get(i).draw(g);
            model.lowerDokan.get(i).draw(g);
        }
    }
    public void update(Observable o, Object obj){
        repaint();
    }
}

// Controller
class Controller implements MouseListener {
    private ModelObservable model;
    private ViewPanel view;
    public Controller(ModelObservable mo){
        model = mo;
    }
    public void mouseClicked(MouseEvent e){ }
    public void mousePressed(MouseEvent e){
        model.setStartFlag();
        model.setT(0);
        model.bird.setY0asY();
    }
    public void mouseReleased(MouseEvent e){ }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }
}

// Main
class FlappyBird extends JFrame {
    private ModelObservable model;
    private ViewPanel view;
    private Controller cont;
    public FlappyBird(){
        model = new ModelObservable();
        cont = new Controller(model);
        view = new ViewPanel(model, cont);
        this.setBackground(Color.RED);
        this.setSize(model.SCREEN_WIDTH, model.SCREEN_HEIGHT);
        this.add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String argv[]){
        new FlappyBird();
    }
}