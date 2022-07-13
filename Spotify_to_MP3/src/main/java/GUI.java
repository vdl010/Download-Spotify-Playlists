import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class GUI {
    private JPanel panel1;
    private JButton downloadButton;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel imageLogo;
    MAIN main = new MAIN();

    public GUI() {
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    main.downloadPlaylist(textField1.getText(),textField2.getText());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Converter");
        GUI gui = new GUI();

        ImageIcon imageIcon = new ImageIcon("/Users/janvanderlinde/IdeaProjects/gad22e10-ge47bak/Spotify_to_MP3/src/Ressources/Spotify_Logo_RGB_White.png");

        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(250, 80,  Image.SCALE_AREA_AVERAGING); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        gui.imageLogo.setIcon(imageIcon);

        frame.setContentPane(gui.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        java.net.URL url = ClassLoader.getSystemResource("/Users/janvanderlinde/IdeaProjects/gad22e10-ge47bak/Spotify_to_MP3/src/Ressources/Spotify_logo_without_text.png");
        frame.setVisible(true);
        frame.setSize(400,300);
    }

}
