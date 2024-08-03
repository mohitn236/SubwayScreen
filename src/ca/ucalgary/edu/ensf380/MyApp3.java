// package ca.ucalgary.edu.ensf380;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.WindowAdapter;
// import java.awt.event.WindowEvent;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import com.opencsv.CSVReader;
// import com.opencsv.exceptions.CsvValidationException;

// public class MyApp3 extends JFrame implements ActionListener {
//     private static final long serialVersionUID = 1L;
//     private JTextArea outputArea;
//     private JButton startButton;
//     private JButton stopButton;
//     private Process process;
//     private ExecutorService executor;
//     private SVGPanel svgPanel;
//     private List<Station> stations;
//     private Connection connection;

//     public MyApp3() {
//         setTitle("Subway Screen 3");
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
//         addWindowListener(new WindowAdapter() {
//             public void windowClosing(WindowEvent e) {
//                 stopProcess();
//                 closeDatabaseConnection(); // Close database connection on exit
//                 dispose();
//             }
//         });

//         outputArea = new JTextArea();
//         outputArea.setEditable(false);
//         JScrollPane scrollPane = new JScrollPane(outputArea);
//         scrollPane.setPreferredSize(new Dimension(400, 300));
//         add(scrollPane, BorderLayout.CENTER);

//         JPanel buttonPanel = new JPanel();
//         startButton = new JButton("Start");
//         startButton.addActionListener(this);
//         startButton.setPreferredSize(new Dimension(100, 38));
//         buttonPanel.add(startButton);

//         stopButton = new JButton("Stop");
//         stopButton.addActionListener(this);
//         stopButton.setEnabled(false);
//         stopButton.setPreferredSize(new Dimension(100, 38));
//         buttonPanel.add(stopButton);

//         add(buttonPanel, BorderLayout.SOUTH);

//         // Adding SVGPanel
//         svgPanel = new SVGPanel("data/Trains.svg");
//         add(svgPanel, BorderLayout.CENTER);

//         // Adding WeatherPanel
//         WeatherPanel weatherPanel = new WeatherPanel();
//         String weatherReport;
//         try {
//             weatherReport = weatherPanel.fetchWeatherReport("regina");
//         } catch (Exception e) {
//             weatherReport = "Failed to load weather report";
//             e.printStackTrace();
//         }
//         JTextArea weatherArea = new JTextArea(weatherReport);
//         weatherArea.setEditable(false);
//         JScrollPane weatherScrollPane = new JScrollPane(weatherArea);
//         weatherScrollPane.setPreferredSize(new Dimension(400, 300));
//         add(weatherScrollPane, BorderLayout.EAST);

//         // Adding NewsTickerPanel
//         NewsTickerPanel newsTickerPanel = new NewsTickerPanel("Calgary");
//         add(newsTickerPanel, BorderLayout.NORTH);

//         pack();
//         setLocationRelativeTo(null);
//         setVisible(true);

//         executor = Executors.newFixedThreadPool(2);
//         initializeStationsFromCSV("./data/subway.csv");
//         initializeDatabaseConnection(); // Initialize database connection
//     }

//     private void initializeStationsFromCSV(String filePath) {
//         stations = new ArrayList<>();
//         try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
//             String[] line;
//             reader.readNext(); // Skip the header row
//             while ((line = reader.readNext()) != null) {
//                 String lineCode = line[1];
//                 int stationNumber = Integer.parseInt(line[2]);
//                 String stationCode = line[3];
//                 String stationName = line[4];
//                 double x = Double.parseDouble(line[5]);
//                 double y = Double.parseDouble(line[6]);
//                 String commonStations = line.length > 7 ? line[7] : "";

//                 stations.add(new Station(lineCode, stationNumber, stationCode, stationName, x, y, commonStations));
//             }
//         } catch (IOException | CsvValidationException e) {
//             e.printStackTrace();
//         }

//         // Pass the stations list to your svgPanel or other components
//         svgPanel.setStations(stations);
//     }

//     private void initializeDatabaseConnection() {
//         try {
//             String url = "jdbc:mysql://localhost:3306/your_database"; // Update URL as needed
//             String user = "your_username";
//             String password = "your_password";
//             connection = DriverManager.getConnection(url, user, password);
//             System.out.println("Database connected!");
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     private void closeDatabaseConnection() {
//         if (connection != null) {
//             try {
//                 connection.close();
//                 System.out.println("Database connection closed!");
//             } catch (SQLException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(MyApp3::new);
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         if (e.getSource() == startButton) {
//             startProcess();
//         } else if (e.getSource() == stopButton) {
//             stopProcess();
//         }
//     }

//     private void startProcess() {
//         try {
//             process = new ProcessBuilder("ping", "localhost").start();
//             BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//             executor.execute(() -> readProcessOutput(reader));
//             startButton.setEnabled(false);
//             stopButton.setEnabled(true);
//         } catch (IOException ex) {
//             ex.printStackTrace();
//         }
//     }

//     private void readProcessOutput(BufferedReader reader) {
//         try {
//             String line;
//             while ((line = reader.readLine()) != null) {
//                 final String outputLine = line; // Create a final copy of the line variable
//                 SwingUtilities.invokeLater(() -> outputArea.append(outputLine + "\n"));
//             }
//         } catch (IOException ex) {
//             ex.printStackTrace();
//         }
//     }

//     private void stopProcess() {
//         if (process != null) {
//             process.destroy();
//             process = null;
//         }
//         startButton.setEnabled(true);
//         stopButton.setEnabled(false);
//     }
// }
package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class MyApp3 extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextArea outputArea;
    private JButton startButton;
    private JButton stopButton;
    private Process process;
    private ExecutorService executor;
    private SVGPanel svgPanel;
    private List<Station> stations;
    private Connection connection;
    private TrainInfoPanel trainInfoPanel; // Add this

    public MyApp3() {
        setTitle("Subway Screen 3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stopProcess();
                closeDatabaseConnection(); // Close database connection on exit
                dispose();
            }
        });

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setPreferredSize(new Dimension(100, 38));
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        stopButton.setPreferredSize(new Dimension(100, 38));
        buttonPanel.add(stopButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Adding SVGPanel
        svgPanel = new SVGPanel("data/Trains.svg");
        add(svgPanel, BorderLayout.CENTER);

        // Adding WeatherPanel
        WeatherPanel weatherPanel = new WeatherPanel();
        String weatherReport;
        try {
            weatherReport = weatherPanel.fetchWeatherReport("regina");
        } catch (Exception e) {
            weatherReport = "Failed to load weather report";
            e.printStackTrace();
        }
        JTextArea weatherArea = new JTextArea(weatherReport);
        weatherArea.setEditable(false);
        JScrollPane weatherScrollPane = new JScrollPane(weatherArea);
        weatherScrollPane.setPreferredSize(new Dimension(400, 300));
        add(weatherScrollPane, BorderLayout.EAST);

        // Adding NewsTickerPanel
        NewsTickerPanel newsTickerPanel = new NewsTickerPanel("Calgary");
        add(newsTickerPanel, BorderLayout.NORTH);

        // Adding TrainInfoPanel at the bottom
        trainInfoPanel = new TrainInfoPanel();
        add(trainInfoPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        executor = Executors.newFixedThreadPool(2);
        initializeStationsFromCSV("./data/subway.csv");
        initializeDatabaseConnection(); // Initialize database connection
    }

    private void initializeStationsFromCSV(String filePath) {
        stations = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // Skip the header row
            while ((line = reader.readNext()) != null) {
                String row = line[0];
                String lineCode = line[1];
                int stationNumber = Integer.parseInt(line[2]);
                String stationCode = line[3];
                String stationName = line[4];
                double x = Double.parseDouble(line[5]);
                double y = Double.parseDouble(line[6]);
                String commonStations = line.length > 7 ? line[7] : "";
    
                stations.add(new Station(lineCode, stationNumber, stationCode, stationName, x, y, commonStations));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    
        // Pass the stations list to your svgPanel or other components
        svgPanel.setStations(stations);
    }
    

    private void initializeDatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/your_database"; // Update URL as needed
            String user = "your_username";
            String password = "your_password";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeDatabaseConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyApp3::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startProcess();
        } else if (e.getSource() == stopButton) {
            stopProcess();
        }
    }

    private void startProcess() {
        try {
            process = new ProcessBuilder("ping", "localhost").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            executor.execute(() -> readProcessOutput(reader));
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readProcessOutput(BufferedReader reader) {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                final String outputLine = line; // Create a final copy of the line variable
                SwingUtilities.invokeLater(() -> outputArea.append(outputLine + "\n"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void stopProcess() {
        if (process != null) {
            process.destroy();
            process = null;
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
    
    // Method to update train information, call this method with the updated train list
    public void updateTrainInfo(List<Train> trainList) {
        trainInfoPanel.updateTrainInfo(trainList);
    }
}

