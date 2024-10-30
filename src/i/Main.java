package i;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine().trim()); // Количество посещений магазинов

        // Словарь для хранения списков посещений для каждого товара
        Map<String, List<Integer>> productVisits = new HashMap<>();

        // Чтение данных о посещениях и заполнение словаря
        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().trim().split(" "); // Разделяем строку на слова
            int pi = Integer.parseInt(line[0]); // Количество покупок в текущем посещении

            for (int j = 1; j <= pi; j++) {
                String product = line[j]; // Название товара
                productVisits.putIfAbsent(product, new ArrayList<>());
                productVisits.get(product).add(i); // Добавляем индекс посещения
            }
        }

        // Словарь для хранения минимальных промежутков между покупками для товаров, купленных более одного раза
        Map<String, Integer> productMinIntervals = new HashMap<>();

        // Вычисление минимальных интервалов между покупками для каждого товара
        for (Map.Entry<String, List<Integer>> entry : productVisits.entrySet()) {
            String product = entry.getKey(); // Название товара
            List<Integer> visits = entry.getValue(); // Список индексов посещений для товара

            // Проверяем, что товар куплен более одного раза
            if (visits.size() > 1) {
                int minInterval = 999999999; // Устанавливаем начальное большое значение для минимального интервала
                // Цикл для поиска минимального интервала между покупками
                for (int i = 1; i < visits.size(); i++) {
                    int interval = visits.get(i) - visits.get(i - 1); // Интервал между текущим и предыдущим посещением
                    if (minInterval > interval) {
                        minInterval = interval; // Обновляем минимальный интервал, если найден меньший
                    }
                }
                // Сохраняем минимальный интервал для текущего товара
                productMinIntervals.put(product, minInterval);
            }
        }

        // Сортируем товары по имени для вывода в алфавитном порядке
        List<String> sortedProducts = new ArrayList<>(productMinIntervals.keySet());
        Collections.sort(sortedProducts);

        // Вывод результатов
        System.out.println(sortedProducts.size());
        for (String product : sortedProducts) {
            System.out.println(product + " " + productMinIntervals.get(product));
        }
    }
}

