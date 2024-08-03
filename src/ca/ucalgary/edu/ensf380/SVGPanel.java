package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SVGPanel extends JPanel {
    private BufferedImage svgImage;
    private List<Station> stations;

    public SVGPanel(String svgFilePath) {
        try {
            svgImage = ImageIO.read(new File(svgFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stations = new ArrayList<>();
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (svgImage != null) {
            g.drawImage(svgImage, 0, 0, getWidth(), getHeight(), this);
        }
        drawStations(g);
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
        repaint();
    }

    private void drawStations(Graphics g) {
        g.setColor(Color.RED);
        for (Station station : stations) {
            int x = (int) station.getX();
            int y = (int) station.getY();
            g.fillOval(x, y, 10, 10);
            g.drawString(station.getStationCode(), x + 15, y + 15);
        }
    }
}
