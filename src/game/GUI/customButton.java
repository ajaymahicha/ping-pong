package game.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Praveen on 30-Apr-16.
 */
public class customButton extends JButton {

    String path="";

    public customButton(String p)
    {
        path=p;

        setPreferredSize(new Dimension(280,90));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    public void paint( Graphics g ) {
        super.paint( g );
        ImageIcon multi = null;
        try {
            multi = new ImageIcon(ImageIO.read(getClass().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(multi.getImage(),  0 , 0 , getWidth() , getHeight() , null);
    }
}
