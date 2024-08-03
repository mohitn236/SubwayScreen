package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrainInfoPanel extends JPanel {
    private JTextArea trainInfoArea;

    public TrainInfoPanel() {
        setLayout(new BorderLayout());
        trainInfoArea = new JTextArea();
        trainInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(trainInfoArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTrainInfo(List<Train> trainList) {
        StringBuilder info = new StringBuilder();
        if (trainList.size() > 0) {
            Train currentTrain = trainList.get(0);
            Train previousTrain = trainList.size() > 1 ? trainList.get(1) : null;
            List<Train> nextTrains = trainList.subList(2, Math.min(trainList.size(), 6));
            
            if (previousTrain != null) {
                info.append("Previous Train: ").append(previousTrain).append("\n");
            }
            info.append("Current Train: ").append(currentTrain).append("\n");
            info.append("Next Trains:\n");
            for (Train train : nextTrains) {
                info.append(train).append("\n");
            }
        } else {
            info.append("No train information available.");
        }
        trainInfoArea.setText(info.toString());
    }
}
