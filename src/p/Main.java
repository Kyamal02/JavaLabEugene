package p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        // массив со всеми котами
        String[] colors = new String[n];
        for (int i = 0; i < n; i++) {
            colors[i] = br.readLine();
        }

        // Карта для хранения очереди доступных котиков для каждой масти
        Map<String, Queue<Integer>> availableCats = new HashMap<>();
        // Карта для хранения текущего количества котиков каждой масти
        Map<String, Integer> currentCatsPerColor = new HashMap<>();

        int totalCats = 0;
        int maxCatsSameColor = 0;

        for (int i = 0; i < n; i++) {
            String color = colors[i];

            // Инициализация очереди и счётчиков для новой масти
            availableCats.putIfAbsent(color, new LinkedList<>());
            currentCatsPerColor.putIfAbsent(color, 0);

            Queue<Integer> queue = availableCats.get(color);

            // Удаляем из очереди котиков, которые уже не могут быть использованы
            while (!queue.isEmpty() && i - queue.peek() - 1 > m) {
                queue.poll();
            }

            boolean reused = false;

            // Проверяем, можно ли переиспользовать котика
            if (!queue.isEmpty()) {
                int lastPettingTime = queue.peek();
                if (lastPettingTime < i - 1) {
                    // Переиспользуем котика
                    queue.poll();
                    queue.offer(i);
                    reused = true;
                }
            }

            if (!reused) {
                // Создаём нового котика
                totalCats++;
                currentCatsPerColor.put(color, currentCatsPerColor.get(color) + 1);
                maxCatsSameColor = Math.max(maxCatsSameColor, currentCatsPerColor.get(color));
                queue.offer(i);
            }
        }

        System.out.println(totalCats);
        System.out.println(maxCatsSameColor);
    }
}
