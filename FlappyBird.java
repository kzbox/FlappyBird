import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Main
class FlappyBird extends JFrame {
    private ModelObservable model;
    private GamePanel gamePanel;
    private GameController gameCont;
    
    private JPanel switchPanel;
    private CardLayout layout;

    public FlappyBird(){
        model = new ModelObservable();
        gameCont = new GameController(model);
        gamePanel = new GamePanel(model, gameCont);

        switchPanel = new JPanel();
        layout = new CardLayout();
        switchPanel.setLayout(layout);

        switchPanel.add(gamePanel);
        this.add(switchPanel);
    }
    public static void main(String argv[]){
        FlappyBird frame = new FlappyBird();
        frame.setSize(ModelObservable.SCREEN_WIDTH, ModelObservable.SCREEN_HEIGHT);
        frame.setTitle("Play FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}