package a;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/a/test1")));
        int n = Integer.parseInt(br.readLine().trim());
        Map<String, Integer> ratings = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String[] tokens = br.readLine().trim().split("\\s+");
            String better = tokens[0];
            String worse = tokens[1];

            int betterRating = ratings.getOrDefault(better, 0);
            int worseRating = ratings.getOrDefault(worse, 0);

            if (betterRating <= worseRating) {
                ratings.put(better, worseRating + 1);
            }
        }

        // Нахождение максимального рейтинга и сортов с этим рейтингом
        int maxRating = 0;
        for (int rating : ratings.values()) {
            if (rating > maxRating) {
                maxRating = rating;
            }
        }

        List<String> maxRatedFlavors = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ratings.entrySet()) {
            if (entry.getValue() == maxRating) {
                maxRatedFlavors.add(entry.getKey());
            }
        }

        // Вывод результата
        System.out.println(maxRating + " " + maxRatedFlavors.size());
        for (String flavor : maxRatedFlavors) {
            System.out.println(flavor);
        }
        long endTime = System.nanoTime();
        long durationInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Время выполнения: " + durationInMillis + " мс");
    }
}

