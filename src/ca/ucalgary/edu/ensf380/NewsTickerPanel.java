package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The NewsTickerPanel class creates a panel that fetches and displays news headlines in a scrolling ticker format.
 */
public class NewsTickerPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel newsLabel;
    private javax.swing.Timer scrollTimer;
    private List<String> newsList = new ArrayList<>();
    private int newsIndex = 0;
    private int charIndex = 0;
    private String currentNews = "";
    private String apiKey = "pub_494866bcb7914a1d2808e27732130296ce529"; // Replace with your actual API key
    private String newsApiUrl = "https://newsdata.io/api/1/news?apikey=" + apiKey;

    /**
     * Constructs a NewsTickerPanel that fetches and displays news headlines for a specified city.
     *
     * @param city the city to fetch news for
     */
    public NewsTickerPanel(String city) {
        setLayout(new BorderLayout());
        newsLabel = new JLabel("Loading news...", SwingConstants.CENTER);
        newsLabel.setForeground(Color.WHITE);
        newsLabel.setFont(new Font("Courier New", Font.PLAIN, 20)); // different font
        newsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newsLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(newsLabel, BorderLayout.CENTER);
        setBackground(Color.BLACK);

        setPreferredSize(new Dimension(800, 30)); // Smaller panel size

        if (city != null && !city.isEmpty()) {
            newsApiUrl += "&q=" + URLEncoder.encode(city, StandardCharsets.UTF_8);
        }

        new Timer(true).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> fetchNews());
            }
        }, 0, 60000); // Fetch news every 60 seconds

        scrollTimer = new javax.swing.Timer(200, e -> scrollNews());
        scrollTimer.start();
    }

    /**
     * Fetches news headlines from the API and updates the news list.
     */
    private void fetchNews() {
        try {
            URI uri = new URI(newsApiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray articles = jsonResponse.getJSONArray("results");

            newsList.clear();
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                newsList.add(article.getString("title"));
            }

            if (!newsList.isEmpty()) {
                newsIndex = 0;
                charIndex = 0;
                currentNews = newsList.get(newsIndex);
            }

        } catch (Exception e) {
            e.printStackTrace();
            newsLabel.setText("Failed to load news");
        }
    }

    /**
     * Scrolls the news headlines by displaying one character at a time.
     */
    private void scrollNews() {
        if (newsList.isEmpty()) return;

        if (charIndex < currentNews.length()) {
            newsLabel.setText(currentNews.substring(0, charIndex + 1));
            charIndex++;
        } else {
            newsIndex = (newsIndex + 1) % newsList.size();
            charIndex = 0;
            currentNews = newsList.get(newsIndex);
        }
    }
}
