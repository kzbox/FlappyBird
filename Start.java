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
    JLabel d;
    JLabel score;
    Image img;
    Image img2;
    String high_score;

    public void score(){
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
        b = new JButton("プレイする");
        b.setBackground(new Color(0x00D9FF));
        b.setFont(new Font("MS ゴシック", Font.BOLD, ModelObservable.SCREEN_WIDTH/10));
        b.setBounds(ModelObservable.SCREEN_WIDTH/7, ModelObservable.SCREEN_HEIGHT/2, 
                    ModelObservable.SCREEN_WIDTH*5/7, ModelObservable.SCREEN_HEIGHT/5);
        b.setForeground(Color.BLACK);
        // b.setBackground(Color.WHITE);
        b.addActionListener(cont);
        b.setActionCommand("start game");
        c = new JLabel("FlappyBird");
        c.setBounds(ModelObservable.SCREEN_WIDTH/8, ModelObservable.SCREEN_HEIGHT/6, 
                    ModelObservable.SCREEN_WIDTH*5/7, ModelObservable.SCREEN_HEIGHT/4);
        c.setForeground(Color.BLUE);
        c.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 50));

        d = new JLabel("～幸せを運ぶ青い鳥～");
        d.setBounds(ModelObservable.SCREEN_WIDTH/6, ModelObservable.SCREEN_HEIGHT/4, 
                    ModelObservable.SCREEN_WIDTH*4/5, ModelObservable.SCREEN_HEIGHT/4);
        d.setForeground(Color.BLUE);
        d.setFont(new Font("SERIF", Font.BOLD | Font.ITALIC, 32));


        img = Toolkit.getDefaultToolkit().getImage("../images/sunset.jpg");

        img2 = Toolkit.getDefaultToolkit().getImage("../images/bird1.3.png");

        score();
        score = new JLabel("HIGH SCORE:" + high_score);
        score.setFont(new Font("Arial", Font.BOLD, 24));
        score.setBounds(ModelObservable.SCREEN_WIDTH/5, ModelObservable.SCREEN_HEIGHT*3/4, 
                        ModelObservable.SCREEN_WIDTH*4/5, ModelObservable.SCREEN_HEIGHT/5);
        score.setForeground(Color.DARK_GRAY);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
        g.drawImage(img2, 50, 50, 100, 100, this);
        this.add(c);
        this.add(b);
        this.add(d);
        this.add(score);
        score();
        score.setText("HIGH SCORE: " + high_score);
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