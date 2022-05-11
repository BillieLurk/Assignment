package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveHandler {

    private OutputArea outputArea;

    private String imageLine;
    private HashMap<String, Place> places = new HashMap<>();
    private ArrayList<Path> paths = new ArrayList<>();

    public SaveHandler(OutputArea outputArea) {
        this.outputArea = outputArea;
    }

    private void loadFile(String filePath) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));

            imageLine = reader.readLine();

            stringToPlaces(reader.readLine());

            ArrayList<String> pathLines = new ArrayList<>();
            String line = reader.readLine();
            pathLines.add(line);

            while (line != null) {

                // read next line
                line = reader.readLine();
                if (line != null)
                    pathLines.add(line);
            }
            reader.close();
            System.out.println(pathLines.toString());
            stringsToPaths(pathLines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stringToPlaces(String line) {
        places = new HashMap<>();

        String[] segments = line.split(";");

        for (int i = 0; i < segments.length; i += 3) {
            places.put(segments[i],
                    new Place(segments[i], Double.valueOf(segments[i + 1]), Double.valueOf(segments[i + 2])));
        }
    }

    private void stringsToPaths(ArrayList<String> arr) {
        paths = new ArrayList<>();
        for (String line : arr) {

            String[] segments = line.split(";");

            for (int i = 0; i < segments.length; i += 4) {
                paths.add(new Path(places.get(segments[i]), places.get(segments[i + 1]), segments[i + 2],
                        Integer.parseInt(segments[i + 3])));
            }
        }
    }

    private String getImageFilePath() {
        return imageLine.substring(imageLine.lastIndexOf(":") + 1);
    }

    public void loadGraph(String fromFile) {
        loadFile(fromFile);
        // TODO FIX PATH B4 HANDIN
        outputArea.setImage(getImageFilePath());
        outputArea.loadPlaces(new ArrayList<>(places.values()));
        outputArea.loadPaths(paths);
    }


    public void saveGraph(String toFile) {
        try {
            File file = new File(toFile);
            file.createNewFile();

            FileWriter writer = new FileWriter(toFile);
            writer.flush();
            writer.append(outputArea.getSave());
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
