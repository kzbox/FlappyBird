import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
// view
class StartPanel extends JPanel {
    ModelObservable model;
    StartController cont;
    JButton b;
    JLabel c;
    JLabel score;
    Image img;
    String high_score;

    public void Score(){
        try{
            File file = new File("../log/high_score.txt");
            if(file.exists()){
                FileReader filereader = new FileReader(file);
                BufferedReader br = new BufferedReader(filereader);
        
                high_score = br.readLine();
        
                filereader.close();
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public StartPanel(ModelObservable mo, StartController co){
        model = mo;
        cont = co;
        this.setLayout(null);
        b = new JButton("Start!!!!");
        //b.setPreferredSize(new Dimension(200, 100));
        b.setFont(new Font("MS ゴシック", Font.BOLD, 24));
        b.setBounds(ModelObservable.SCREEN_WIDTH/4, ModelObservable.SCREEN_HEIGHT/2, 
                    ModelObservable.SCREEN_WIDTH/2, ModelObservable.SCREEN_HEIGHT/7);
        b.setForeground(Color.YELLOW);
        b.setBackground(Color.BLUE);
        b.addActionListener(cont);
        b.setActionCommand("start game");
        c = new JLabel("FlappyBird");
        c.setBounds(130, 150, 200, 50);
        c.setForeground(Color.RED);
        c.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));

        img = Toolkit.getDefaultToolkit().getImage("../images/startview.jpg");

        score = new JLabel("HIGH SCORE:" + high_score);
        score.setBounds(130, 600, 200, 50);
        score.setForeground(Color.RED);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
        this.add(c);
        this.add(b);
        this.add(score);
    }

}

// controller
class StartController implements ActionListener {
    ModelObservable model;
    StartPanel panel;
    JPanel cardPanel;
    CardLayout layout;
    public StartController(ModelObservable model, JPanel cardPanel, CardLayout layout){
        this.model = model;
        this.cardPanel = cardPanel;
        this.layout = layout;
    }
    public void setPanel(StartPanel pa){
        panel = pa;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "start game"){
            layout.show(cardPanel, "game");
        }
    }
}