package e;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int m = Integer.parseInt(br.readLine().trim());
        int n = Integer.parseInt(br.readLine().trim());
        // предпочтения покупателей
        List<int[]> preferences = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().trim().split("\\s+");
            int f1 = Integer.parseInt(line[0]);
            int f2 = Integer.parseInt(line[1]);
            preferences.add(new int[]{f1, f2});
        }

        // Все возможные варианты
        List<int[]> AllFruitPairs = new ArrayList<>();
        // заполнение List всеми возможными вариантами
        for (int i = 1; i <= m; i++) {
            for (int j = i + 1; j <= m; j++) {
                AllFruitPairs.add(new int[]{i, j});
            }
        }

        // Переменная для отслеживания максимального числа довольных покупателей
        int maxSatisfied = 0;

        //для хранения пар фруктов, которые дают максимальное количество довольных покупателей
        List<int[]> maxPairs = new ArrayList<>();

        // Проходим по всем возможным парам фруктов
        for (int[] pair : AllFruitPairs) {
            int satisfied = 0; // переменная для подсчета количества довольных покупателей для текущей пары
            for (int[] pref : preferences) {
                // Проверяем текущую пару для всех предпочтений покупателей
                if (pref[0] == pair[0] || pref[0] == pair[1] ||
                        pref[1] == pair[0] || pref[1] == pair[1]) {
                    satisfied++; // Увеличиваем счетчик довольных покупателей
                }
            }
            // Если для текущей пары число довольных покупателей больше, чем для предыдущих пар
            if (satisfied > maxSatisfied) {
                maxSatisfied = satisfied; // обновляем максимальное число довольных покупателей
                maxPairs.clear(); // очищаем список пар, так как нашли более оптимальную пару
                maxPairs.add(pair); // добавляем текущую пару как новую оптимальную
                // Если текущее число довольных покупателей равно максимальному
            } else if (satisfied == maxSatisfied) {
                // добавляем текущую пару как еще одну оптимальную
                maxPairs.add(pair);
            }
        }
        System.out.println(maxPairs.size());
        for (int[] pair : maxPairs) {
            System.out.println(pair[0] + " " + pair[1]);
        }


    }
}
