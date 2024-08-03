// package ca.ucalgary.edu.ensf380;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.image.BufferedImage;

// public class ImagePanel extends JPanel {
//     private BufferedImage image;

//     public ImagePanel() {
//         // Set preferred size or layout as needed
//         setPreferredSize(new Dimension(800, 600));
//     }

//     public void setImage(BufferedImage image) {
//         this.image = image;
//         repaint(); // Request to repaint the panel with the new image
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         if (image != null) {
//             g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
//         }
//     }
// }
