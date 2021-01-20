import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
import java.net.MalformedURLException;

// Model
abstract class Thing {
    // フィールド
    protected double x, y, width, height;
    // コンストラクタ
    public Thing(double x, double y, double w, double h){
        this.x = x; this.y = y;
        width = w; height = h;
    }
    // メソッド
    abstract public void draw(Graphics g);
    // getter
    double getX()     { return x; }
    double getY()     { return y; }
    double getWidth() { return width;  }
    double getHeight(){ return height; }
    public double getRightX(){ return x+width; }
    public double getLowY(){ return y+height; }
    // setter
    void setX(double x)     { this.x = x; }
    void setY(double y)     { this.y = y; }
    void setWidth(double w) { width  = w; }
    void setHeight(double h){ height = h; }
}
class Bird extends Thing {
    // 定数
    public final static double V0 = 49; // 鳥の上向きの初速
    public final static double GRAVITY = 23; // 鳥に働く重力
    public final static int BIRD_WIDTH = 60, BIRD_HEIGHT = 60; // 鳥の大きさ
    // フィールド
    private double y0; // クリックした鳥の高さ
    // コンストラクタ
    public Bird(double x, double y){
        super(x, y, BIRD_WIDTH, BIRD_HEIGHT);
        y0 = y;
    }
    // publicメソッド
    public void setY0asY(){
        y0 = y;
    }
    public double getY0(){
        return y0;
    }
    @Override public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
}
class Dokan extends Thing {
    // コンストラクタ
    public Dokan(double x, double y, double w, double h){
        super(x, y, w, h);
    }
    // メソッド
    public void moveDokan(int speed){
        x -= speed;
    }
    @Override public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
}

class ModelObservable extends Observable implements ActionListener{
    // private定数
    private final static int HABA = 180;
    // public定数
    public final static int SCREEN_WIDTH = 460, SCREEN_HEIGHT = 800;
    public final static int DOKAN_BUF = 5; // 土管が同時に表示される数
    public final static int FPS = 100;
    public final static int SPEED = 2;
    // フィールド
    private Bird bird;
    private ArrayList<Dokan> upperDokan, lowerDokan;
    private javax.swing.Timer timer;
    private double time;
    private boolean startFlag; // falseのゲームが間は動かないようにする
    private boolean gameOverFlag; // ゲームオーバになったらtrueになる
    private boolean scoreFlag; // スコアカウントしたらtrueにする
    private int score;
    private java.util.Random rand = new java.util.Random();
    private int dokanTail;
    private Clip click = createClip(new File("../audio/click3.wav"), (float)0.05);
    private Clip bgm = createClip(new File("../audio/bgm2.wav"), (float)0.1);
    // コンストラクタ
    public ModelObservable(){
        timer = new javax.swing.Timer(1000/FPS, this);
        timer.start();
        init();
    }
    // privateメソッド
    private boolean isIn(){ // 近くにある土管のインデックス
        Dokan udokan;
        Dokan ldokan;
        if(upperDokan.get(0).getRightX() < bird.getX()){
            udokan = upperDokan.get(1);
            ldokan = lowerDokan.get(1);
        }
        else{
            udokan = upperDokan.get(0);
            ldokan = lowerDokan.get(0);
        }
        double dokanX = udokan.getX();
        double dokanRightX = udokan.getRightX();
        double birdX = bird.getX();
        double birdRightX = bird.getRightX();
        if(birdRightX > dokanX && birdX < dokanRightX){
            double udokanH = udokan.getHeight();
            double ldokanH = ldokan.getY();
            double birdY = bird.getY();
            double birdLowY = bird.getLowY();
            if(birdY < udokanH || birdLowY > ldokanH){
                return true;
            }
        }
        return false;
    }
    private boolean isGameOver(){
        if(bird.getLowY() > SCREEN_HEIGHT-60){
            return true;
        }
        return isIn();
    }
    private void calcBirdPos(){
        bird.setY(bird.getY0() - Bird.V0*time + Bird.GRAVITY*time*time/2);
    }
    private void addDokan(){
        if(upperDokan.size() > 0){
            dokanTail = (int)upperDokan.get(upperDokan.size()-1).getX() + (int)(SCREEN_WIDTH/1.7) + rand.nextInt((int)(SCREEN_WIDTH/2.2));
        }
        int w = SCREEN_WIDTH/8;
        int rand_height = rand.nextInt(SCREEN_HEIGHT/2);
        int mid = SCREEN_HEIGHT/4 + rand_height;
        upperDokan.add(new Dokan(dokanTail, 0, w, mid - HABA/2));
        lowerDokan.add(new Dokan(dokanTail, mid + HABA/2, w, SCREEN_HEIGHT));
    }
    private void updateDokan(){
        // 土管の左にずらす
        // dokanTail -= SPEED;
        for(int i = 0; i < DOKAN_BUF; i++){
            upperDokan.get(i).moveDokan(SPEED);
            lowerDokan.get(i).moveDokan(SPEED);
        }
        // 土管がカメラの左側に流れたら土管を消して右側に追加
        Dokan firstDokan = upperDokan.get(0);
        if(firstDokan.getX() + firstDokan.getWidth() < 0){
            upperDokan.remove(0);
            lowerDokan.remove(0);
            addDokan();
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
    private void makeLogFile(File folder, File file){
        if(!folder.exists() && folder.mkdir() == true){
            System.out.print("フォルダ作成成功");
        }
        else{
            System.out.print("フォルダ作成失敗");
        }
        System.out.println(folder.getPath());
        if(file.exists()) return;
        try{
            file.createNewFile();
            System.out.print("ファイル作成成功: ");
            System.out.println(file.getPath());
            write(file, "0");
        }
        catch(IOException e){
            System.out.print("ファイル作成失敗: ");
            System.out.println(file.getPath());
        }
    }
    private boolean write(File file, String str){
        try{
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(str);
            
            filewriter.close();
        }
        catch(IOException e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    private void writeScore(){
        File file = new File("../log/high_score.txt");
        int high_score = 0;
        if(file.exists() == true && file.length() > 0){
            try{
                FileReader filereader = new FileReader(file);
                BufferedReader br = new BufferedReader(filereader);

                String str = br.readLine();
                high_score = Integer.parseInt(str);

                filereader.close();
            }
            catch(IOException e){
                System.out.println(e);
            }
        }
        else{
            System.out.println("ファイルが存在しない or ファイルが空");
        }
        if(high_score < score){
            write(file, String.valueOf(score));
        }
    }
    private void controlByLinearScalar(FloatControl vol, double linearScalar){
        vol.setValue((float)Math.log10(linearScalar) * 20);
    }
    private Clip createClip(File path, float vol){
        //指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){
			//ファイルの形式取得
			AudioFormat af = ais.getFormat();
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
            Clip c = (Clip)AudioSystem.getLine(dataLine);
			//再生準備完了
			c.open(ais);
            // 音量調整
            FloatControl volume = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
            controlByLinearScalar(volume, vol);
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
    // publicメソッド
    public void init(){
        bird = new Bird(SCREEN_WIDTH/2 - Bird.BIRD_WIDTH/2, SCREEN_HEIGHT/2 - Bird.BIRD_HEIGHT/2);
        upperDokan = new ArrayList<Dokan>();
        lowerDokan = new ArrayList<Dokan>();
        dokanTail = SCREEN_WIDTH;
        for(int i=0; i < DOKAN_BUF; i++){
            addDokan();            
        }
        time = 0;
        startFlag = false;
        gameOverFlag = false;
        score = 0;
        scoreFlag = false;
        makeLogFile(new File("../log"), new File("../log/high_score.txt"));
    }
    public void flyBird(){
        if(gameOverFlag == false){
            startFlag = true;
            bird.setY0asY();
            time = 0;
            try {
                play(click);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public void play(Clip c) throws Exception{
        c.stop();
        c.flush();
        c.setFramePosition(0);
        Thread.sleep(10);
        c.start();
    }
    // getter
    public boolean getGameOverFlag(){
        return gameOverFlag;
    }
    public Bird getBird(){
        return bird;
    }
    public ArrayList<Dokan> getUpperDokan(){
        return upperDokan;
    }
    public ArrayList<Dokan> getLowerDokan(){
        return lowerDokan;
    }
    public int getScore(){
        return score;
    }
    // setter
    public void setTime(double t){
        this.time = t;
    }
    public void setStartFlag(boolean flag){
        startFlag = flag;
    }
    public void actionPerformed(ActionEvent e){
        if(startFlag){
            time += (double)10/FPS;
            calcBirdPos();
            updateDokan();
            calcScore();
            bgm.start();
            if(isGameOver()){
                gameOverFlag = true;
                startFlag = false;
                writeScore();
                bgm.stop();
                bgm.flush();
                bgm.setFramePosition(0);
            }
        }
        setChanged();
        notifyObservers();
    }
}

