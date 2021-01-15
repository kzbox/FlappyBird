import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Main
class FlappyBird extends JFrame {
    // Model
    private ModelObservable model;
    // CardLayout親
    private JPanel cardPanel;
    private CardLayout layout;
    // CardLayout子
    private GamePanel gamePanel;
    private GameController gameCont;
    private StartPanel startPanel;
    private StartController startCont;

    public FlappyBird(){
        model = new ModelObservable();
        // CardLayoutの設定
        layout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(layout);
        // カードの設定(StartPanel)
        startCont = new StartController(model, cardPanel, layout);
        startPanel = new StartPanel(model, startCont);
        startCont.setPanel(startPanel);
        
        // カードの設定(GamePanel)
        gameCont = new GameController(model, cardPanel, layout);
        gamePanel = new GamePanel(model, gameCont);
        gameCont.setPanel(gamePanel);
        // カードの追加
        cardPanel.add(startPanel, "start");
        cardPanel.add(gamePanel, "game");
        this.add(cardPanel);
    }
    public static void main(String argv[]){
        FlappyBird frame = new FlappyBird();
        frame.setSize(ModelObservable.SCREEN_WIDTH, ModelObservable.SCREEN_HEIGHT);
        frame.setTitle("Play FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setFocusable(true); // キーが効かなくなったから追加したが効果はなかった。
        frame.setVisible(true);
    }
}