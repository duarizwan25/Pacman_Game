
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class myFrame extends myClass implements ActionListener {
    JButton button;
    JPanel panel1,panel2;
    JFrame fr;

    myFrame(){
        button = new JButton("Start");
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBackground(new Color(140,140,140));
        button.setPreferredSize(new Dimension(100, 50));

        panel1 = createPanel1();
        fr = new JFrame();
        fr.setTitle("My first frame"); // to set the title
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(672,480);
        fr.add(panel1);

        ImageIcon img = new ImageIcon("logo.jpg");
        fr.setIconImage(img.getImage());
        fr.getContentPane().setBackground(new Color(0x123456));
        fr.setVisible(true);
    }
    private JPanel createPanel1(){
        ImageIcon image = new ImageIcon("img1.png");
        JLabel label = new JLabel("PackMan Game");
        label.setForeground(new Color(193,216,230));
        label.setFont(new Font("Times New Roman",Font.BOLD,50));
        label.setIcon(image);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setHorizontalTextPosition(JLabel.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x123456));
        panel.add(label);
        panel.add(button);
        return panel;
    }

    private void createPanel2() {

        panel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        panel2.setBackground(new Color(0x123456));

        fr.getContentPane().removeAll();
        ClassicPacManGame gamePanel = new ClassicPacManGame();
        fr.add(gamePanel);
        fr.revalidate();
        fr.repaint();
        gamePanel.requestFocusInWindow();
        fr.setVisible(true);
        fr.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            fr.getContentPane().removeAll();
            createPanel2();
            fr.revalidate();
            fr.repaint();
        }
    }
}
