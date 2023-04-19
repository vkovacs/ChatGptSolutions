package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class TrainVisualization2 {
    private static final int TRACK_LENGTH = 100;

    public static void main(String[] args) {
        try {
            // Load the train data from a JSON file
            // Read train data from JSON file
            String trainDataString = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader("src/main/resources/train_data.json"));
                String line;
                while ((line = br.readLine()) != null) {
                    trainDataString += line;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject trainDataJson = new JSONObject(trainDataString);
            JSONArray trainDataArray = trainDataJson.getJSONArray("trainData");

            // Initialize an array to represent the track
            int[] track = new int[TRACK_LENGTH];

            // Loop through the train data and update the track array
            for (int i = 0; i < trainDataArray.length(); i++) {
                JSONObject trainData = trainDataArray.getJSONObject(i);
                int currentPosition = trainData.getInt("currentPosition");
                int currentUsage = trainData.getInt("currentUsage");

                // Update the track array based on the current position and usage
                for (int j = 0; j < TRACK_LENGTH; j++) {
                    if (j == currentPosition) {
                        track[j] += currentUsage;
                    } else if (track[j] > 0) {
                        track[j]--;
                    }
                }

                // Print the current state of the track
                for (int j = 0; j < TRACK_LENGTH; j++) {
                    if (track[j] > 0) {
                        System.out.print("X");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();

                // Wait for 1 second before updating the track again
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

