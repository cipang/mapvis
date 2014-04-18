package mapvis.vistools.colormap;

import javax.swing.*;
import java.awt.*;

public class ColorBarTest {

    public static void main(String[] args){
        JFrame frame = new JFrame("Navigable Image Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.getContentPane().add(new ColorBar(ColorMap.JET::getColor, ColorBar.Vertical));
        frame.getContentPane().add(new ColorBar(ColorMap.hsv::getColor, ColorBar.Vertical));
        frame.getContentPane().add(new ColorBar(ColorMap.hot::getColor, ColorBar.Vertical));
        frame.getContentPane().add(new ColorBar(ColorMap.gray::getColor, ColorBar.Vertical));

        frame.pack();
        frame.setVisible(true);
    }
}


