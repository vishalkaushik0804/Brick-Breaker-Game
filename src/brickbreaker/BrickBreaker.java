package brickbreaker;

import javax.swing.JFrame;

public class BrickBreaker {

    public static void main(String[] args) {
        JFrame jfrm = new JFrame();
        jfrm.setBounds(10, 10, 700, 600);
        jfrm.setTitle("Break Breaker");
        jfrm.setResizable(false);
        jfrm.setVisible(true);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GamePlay gamePlay = new GamePlay();
        jfrm.add(gamePlay);
    }
    
}
