import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// test

// test2

// Model
abstract class Thing {
    protected double x, y, width, height;
    public Thing(double x, double y, double w, double h){
        this.x = x; this.y = y;
        width = w; height = h;
    }
    abstract public void draw(Graphics g);
    // getter
    double getX()     { return x; }
    double getY()     { return y; }
    double getWidth() { return width;  }
    double getHeight(){ return height; }
    // setter
    void setX(double x)     { this.x = x; }
    void setY(double y)     { this.y = y; }
    void setWidth(double w) { width  = w; }
    void setHeight(double h){ height = h; }
}
class Bird extends Thing {
    // public定数
    public final static double V0 = 49; // 鳥の上向きの初速
    public final static double GRAVITY = 23; // 鳥に働く重力
    public final static int BIRD_WIDTH = 30, BIRD_HEIGHT = 30; // 鳥の大きさ
    // フィールド
    private ModelObservable model;
    private double y0; // クリックした鳥の高さ
    // コンストラクタ
    public Bird(ModelObservable mo, double x, double y){
        super(x-BIRD_WIDTH/2, y-BIRD_HEIGHT/2, BIRD_WIDTH, BIRD_HEIGHT);
        y0 = y;
        model = mo;
    }
    // publicメソッド
    public void calcBirdPos(){
        y = y0 - V0*model.getT() + GRAVITY*model.getT()*model.getT()/2;
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
    public void setY0asY(){
        y0 = y;
    }

}
class Dokan extends Thing {
    // フィールド
    private boolean passed;
    private int difficulty;
    private int award;
    // コンストラクタ
    public Dokan(int x, int y, int w, int h){
        super(x, y, w, h);
        difficulty = 0;
        passed = false;
    }
    // publicフィールド
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
    public void moveDokan(int speed){
        x -= speed;
    }
}
class PairDokan {
    // private定数
    private final static int HABA = 130;
    // フィールド
    private ModelObservable model;
    private Dokan upperDokan, lowerDokan;
    // コンストラクタ
    public PairDokan(ModelObservable mo, int index){
        model = mo;
        java.util.Random rand = new java.util.Random();
        int rand_height = rand.nextInt(model.SCREEN_HEIGHT/2);
        upperDokan = new Dokan(360 + index*400, -2000, 40, model.SCREEN_HEIGHT/4 + rand_height - HABA/2);
        lowerDokan = new Dokan(360 + index*400, model.SCREEN_HEIGHT/4 + rand_height + HABA/2, 40, 2000);
    }
    //メソッド
    public void draw(Graphics g){
        upperDokan.draw(g);
        lowerDokan.draw(g);
    }
    public void moveDokan(int speed){
        upperDokan.moveDokan(speed);
        lowerDokan.moveDokan(speed);
    }
    //getter
    public Dokan getUpperDokan(){
        return upperDokan;
    }
    public Dokan getLowerDokan(){
        return lowerDokan;
    }
}

class ModelObservable extends Observable implements ActionListener{
    // private定数
    private final static int FPS = 100;
    // public定数
    public final static int SCREEN_WIDTH = 400, SCREEN_HEIGHT = 800;
    public final static int DOKAN_BUF = 3; // 土管が同時に表示される数
    public final static int SPEED = 2;
    // privateフィールド
    private Bird bird;
    // private ArrayList<Dokan> upperDokan;
    // private ArrayList<Dokan> lowerDokan;
    private ArrayList<PairDokan> dokan;
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
        bird = new Bird(this, SCREEN_WIDTH/2 - Bird.BIRD_WIDTH/2, SCREEN_HEIGHT/2 - Bird.BIRD_HEIGHT/2);
        // upperDokan = new ArrayList<Dokan>();
        // lowerDokan = new ArrayList<Dokan>();
        dokan = new ArrayList<PairDokan>();
        rand = new java.util.Random();
        for(int i=0; i < DOKAN_BUF; i++){
            // int rand_height = rand.nextInt(SCREEN_HEIGHT/2);
            // upperDokan.add(new Dokan(360 + i*400, 0                                     , 40, SCREEN_HEIGHT/4 + rand_height - HABA/2));
            // lowerDokan.add(new Dokan(360 + i*400, SCREEN_HEIGHT/4 + rand_height + HABA/2, 40, SCREEN_HEIGHT                ));
            dokan.add(new PairDokan(this, i));
        }
        t = 0;
        startFlag = false;
        gameOverFlag = false;
        score = 0;
        scoreFlag = false;
    }
    private boolean isIn(){
        Dokan udokan = dokan.get(0).getUpperDokan();
        Dokan ldokan = dokan.get(0).getLowerDokan();
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
    private void updateDokan(){
        // 土管の左にずらす
        for(int i = 0; i < DOKAN_BUF; i++){
            // upperDokan.get(i).moveDokan(SPEED);
            // lowerDokan.get(i).moveDokan(SPEED);
            dokan.get(i).moveDokan(SPEED);
        }
        // 土管がカメラの左側に流れたら土管を消して右側に追加
        if(dokan.get(0).getUpperDokan().getX() + dokan.get(0).getUpperDokan().getWidth() < 0){
            // upperDokan.remove(0);
            // lowerDokan.remove(0);
            dokan.remove(0);
            // int rand_height = rand.nextInt(SCREEN_HEIGHT/2);
            // upperDokan.add(new Dokan(360 + 2*400, 0                                   , 40, SCREEN_HEIGHT/4 + rand_height));
            // lowerDokan.add(new Dokan(360 + 2*400, SCREEN_HEIGHT/4 + rand_height + HABA, 40, SCREEN_HEIGHT                ));
            dokan.add(new PairDokan(this, 2));
            // スコアフラグを戻す
            scoreFlag = false;
        }
    }
    private void calcScore(){
        Dokan dokan = this.dokan.get(0).getUpperDokan();
        if(!scoreFlag && dokan.getX() + dokan.getWidth() < bird.getX()){
            scoreFlag = true;
            score += 100;
            System.out.println("current score: " + String.valueOf(score));
        }
    }
    // getter
    public double getT(){
        return t;
    }
    public Bird getBird(){
        return bird;
    }
    // public ArrayList<Dokan> getUpperDokan(){
    //     return upperDokan;
    // }
    // public ArrayList<Dokan> getLowerDokan(){
    //     return lowerDokan;
    // }
    public ArrayList<PairDokan> getDokan(){
        return dokan;
    }
    public boolean getGameOverFlag(){
        return gameOverFlag;
    }
    // setter
    public void setT(double time){
        t = time;
    }
    public void setStartFlag(boolean flag){
        startFlag = flag;
    }

    public void actionPerformed(ActionEvent e){
        if(startFlag){
            t += (double)10/FPS;
            bird.calcBirdPos();
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

