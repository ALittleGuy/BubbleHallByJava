package com.game.view;

import java.awt.*;
import javax.swing.*;

public class PartJpanel extends JPanel {
    private Image image;

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public PartJpanel(String imageUrl, int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
        image = new ImageIcon(imageUrl).getImage();
        setPreferredSize(new Dimension(x1,y1));
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0,
                x1, y1,
                0, 0, image.getWidth(this), image.getHeight(this),
                null);
    }

}
