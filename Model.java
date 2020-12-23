import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// test


// Model
abstract class Thing {
    protected double x, y, width, height;
    public Thing(double x, double y, double w, double h){
        this.x = x; this.y = y;
        width = w; height = h;
    }
    abstract public void draw(Graphics g);
    // get系
    double getX()     { return x; }
    double getY()     { return y; }
    double getWidth() { return width;  }
    double getHeight(){ return height; }
    // set系
    void setX(double x)     { this.x = x; }
    void setY(double y)     { this.y = y; }
    void setWidth(double w) { width  = w; }
    void setHeight(double h){ height = h; }
}
class Bird extends Thing {
    public final static double V0 = 49; // 鳥の上向きの初速
    public final static double GRAVITY = 23; // 鳥に働く重力
    public final static int BIRD_WIDTH = 30, BIRD_HEIGHT = 30; // 鳥の大きさ
    private double y0; // クリックした鳥の高さ
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
    public final static int DOKAN_BUF = 3; // 土管が同時に表示される数
    public final static int FPS = 100;
    public final static int SPEED = 2;
    private final static int HABA = 130;
    public Bird bird;
    public ArrayList<Dokan> upperDokan;
    public ArrayList<Dokan> lowerDokan;
    private javax.swing.Timer timer;
    private double t;
    private boolean startFlag; // falseのゲームが間は動かないようにする
    private boolean gameOverFlag; // ゲームオーバになったらtrueになる
    private boolean scoreFlag; 
    private int score;
    private java.util.Random rand;
    public ModelObservable(){
        timer = new javax.swing.Timer(1000/FPS, this);
        timer.start();
        init();
    }
    public void init(){
        bird = new Bird(SCREEN_WIDTH/2 - Bird.BIRD_WIDTH/2, SCREEN_HEIGHT/2 - Bird.BIRD_HEIGHT/2);
        upperDokan = new ArrayList<Dokan>();
        lowerDokan = new ArrayList<Dokan>();
        rand = new java.util.Random();
        for(int i=0; i < DOKAN_BUF; i++){
            int rand_height = rand.nextInt(SCREEN_HEIGHT/2);
            upperDokan.add(new Dokan(360 + i*400, 0                                   , 40, SCREEN_HEIGHT/4 + rand_height));
            lowerDokan.add(new Dokan(360 + i*400, SCREEN_HEIGHT/4 + rand_height + HABA, 40, SCREEN_HEIGHT                ));
        }
        t = 0;
        startFlag = false;
        gameOverFlag = false;
        score = 0;
        scoreFlag = false;
    }
    private boolean isIn(){ // 近くにある土管のインデックス
        Dokan udokan = upperDokan.get(0);
        Dokan ldokan = lowerDokan.get(0);
        double dokanX = udokan.getX();
        double dokanW = udokan.getWidth();
        double birdX = bird.getX();
        double birdW = bird.getWidth();

        if(birdX+birdW > dokanX && birdX < dokanX+dokanW){
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
        return isIn();
    }
    private void calcBirdPos(){
        bird.setY(bird.getY0() - bird.V0*t + bird.GRAVITY*t*t/2);
    }
    private void updateDokan(){
        // 土管の左にずらす
        for(int i = 0; i < DOKAN_BUF; i++){
            upperDokan.get(i).moveDokan(SPEED);
            lowerDokan.get(i).moveDokan(SPEED);
        }
        // 土管がカメラの左側に流れたら土管を消して右側に追加
        if(upperDokan.get(0).getX() + upperDokan.get(0).getWidth() < 0){
            upperDokan.remove(0);
            lowerDokan.remove(0);
            int rand_height = rand.nextInt(SCREEN_HEIGHT/2);
            upperDokan.add(new Dokan(360 + 2*400, 0                                   , 40, SCREEN_HEIGHT/4 + rand_height));
            lowerDokan.add(new Dokan(360 + 2*400, SCREEN_HEIGHT/4 + rand_height + HABA, 40, SCREEN_HEIGHT                ));
            // スコアフラグを戻す
            scoreFlag = false;
        }
    }
    private void calcScore(){
        Dokan dokan = upperDokan.get(0);
        if(!scoreFlag && dokan.getX() + dokan.getWidth() < bird.getX()){
            scoreFlag = true;
            score += 100;
            System.out.println("current score: " + String.valueOf(score));
        }
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
            updateDokan();
            calcScore();
            if(isGameOver()){
                gameOverFlag = true;
                startFlag = false;
            }
        }
        setChanged();
        notifyObservers();
    }
}

