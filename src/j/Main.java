package j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем BufferedReader для чтения ввода с консоли
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Читаем количество строк в рейтинге йогуртов
        int n = Integer.parseInt(br.readLine());

        // Создаем Map для хранения производителей и их последней позиции в рейтинге
        Map<String, Integer> yogurtRating = new HashMap<>();

        String[] line;
        // Проходим по каждой строке с йогуртом
        for (int i = 0; i < n; i++) {
            // Разделяем строку на производителя и вкус
            line = br.readLine().split(" ");

            // Обновляем позицию производителя на текущую (последнюю для этого производителя)
            yogurtRating.put(line[0], i);
        }

        int min = Integer.MAX_VALUE;

        // Переменная для хранения производителя с минимальной позицией последнего появления
        String resultManufacturer = "";

        // Проходим по всем производителям в карте
        for (String manufacturer : yogurtRating.keySet()) {
            int rating = yogurtRating.get(manufacturer);

            // Проверяем, является ли текущая позиция меньшей, чем найденные ранее
            if (rating < min) {
                resultManufacturer = manufacturer;
                min = rating; // Обновляем минимальную позицию
            }
        }
        System.out.println(resultManufacturer);
    }
}
