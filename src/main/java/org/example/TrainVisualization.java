package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrainVisualization {

    public static void main(String[] args) {

        // Read train data from JSON file
        String jsonString = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/train_data.json"));
            String line;
            while ((line = br.readLine()) != null) {
                jsonString += line;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse JSON string to create JSONArray of train data
        JSONArray trainDataArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            trainDataArray = jsonObject.getJSONArray("trainData");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Print train data to console
        System.out.println("Train Data:");
        for (int i = 0; i < trainDataArray.length(); i++) {
            JSONObject trainData = trainDataArray.getJSONObject(i);
            System.out.println("Timestamp: " + trainData.getInt("timestamp") + ", Current Position: "
                    + trainData.getInt("currentPosition") + ", Current Usage: " + trainData.getInt("currentUsage"));
        }

        // Visualize train traffic on track
        System.out.println("\nTrain Traffic Visualization:");
        int trackLength = 100; // Length of track in pixels
        for (int i = 0; i < trainDataArray.length(); i++) {
            JSONObject trainData = trainDataArray.getJSONObject(i);
            int currentPosition = trainData.getInt("currentPosition");
            int currentUsage = trainData.getInt("currentUsage");
            int trainLength = 10; // Length of train in pixels
            int trainStart = currentPosition - (trainLength / 2);
            int trainEnd = currentPosition + (trainLength / 2);
            int usageHeight = (int) ((double) currentUsage / 10.0 * trackLength); // Scale usage to track height
            StringBuilder trackLine = new StringBuilder();
            for (int j = 0; j < trackLength; j++) {
                if (j == trainStart) {
                    trackLine.append("|"); // Start of train
                } else if (j > trainStart && j < trainEnd) {
                    trackLine.append("="); // Middle of train
                } else {
                    trackLine.append("-"); // Track
                }
                if (j == trackLength - 1) {
                    trackLine.append("|"); // End of track
                }
            }
            for (int j = 0; j < usageHeight; j++) {
                System.out.println(trackLine.toString()); // Print usage on top of track
            }
        }
    }
}
