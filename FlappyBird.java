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
        this.setSize(model.SCREEN_WIDTH, model.SCREEN_HEIGHT);
        this.add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String argv[]){
        new FlappyBird();
    }
}