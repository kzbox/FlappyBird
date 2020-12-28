import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

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
        this.add(view);
        // this.setSize(ModelObservable.SCREEN_WIDTH, ModelObservable.SCREEN_HEIGHT);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setVisible(true);
    }
    public static void main(String argv[]){
        FlappyBird frame = new FlappyBird();
        frame.setSize(ModelObservable.SCREEN_WIDTH, ModelObservable.SCREEN_HEIGHT);
        frame.setTitle("Play FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}