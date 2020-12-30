import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class StartPanel extends JPanel implements Observer{
    ModelObservable model;
    StartController cont;
    public StartPanel(ModelObservable mo, StartController co){
        model = mo;
        cont = co;
    }
    public void update(Observable o, Object obj){ }
}

class StartController implements ActionListener {
    public StartController(ModelObservable mo){

    }
    public void actionPerformed(ActionEvent e){

    }
}