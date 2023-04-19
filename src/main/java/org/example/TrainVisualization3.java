package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrainVisualization3 {

    public static void main(String[] args) {
        String fileName = "train_data.json";
        String path = TrainVisualization.class.getClassLoader().getResource(fileName).getPath();
        String json = readJsonFile(path);

        JSONObject trainData = new JSONObject(json);
        JSONArray trainDataArray = trainData.getJSONArray("trainData");

        JFrame frame = new JFrame("Train Visualization");
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                int lastX = -1;
                int lastY = -1;
                for (int i = 0; i < trainDataArray.length(); i++) {
                    JSONObject dataPoint = trainDataArray.getJSONObject(i);
                    int currentPosition = dataPoint.getInt("currentPosition");
                    String timestamp = String.valueOf(dataPoint.getInt("timestamp"));
                    int x = currentPosition * 10;
                    int y = i * 20;
                    if (lastX != -1 && lastY != -1) {
                        g.drawLine(lastX, lastY, x, y);
                    }
                    lastX = x;
                    lastY = y;
                    g.fillOval(x - 5, y - 5, 10, 10);
                    g.drawString(timestamp.toString(), x + 10, y + 5);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        panel.setPreferredSize(new Dimension(1000, trainDataArray.length() * 20));
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static String readJsonFile(String path) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
