package n;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Читаем первую строку и разбиваем на целые числа
        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]); // количество растений
        int k = Integer.parseInt(line[1]); // количество снимков, которые Харитон просматривает
        int m = Integer.parseInt(line[2]); // количество раз, чтобы запомнить растение

        // Читаем названия растений из второй строки
        String[] plants = br.readLine().split(" ");
        // Множество для хранения уникальных растений, которые увидел Харитон
        Set<String> uniquePlants = new HashSet<>();
        // Словарь для подсчета количества встреч каждого растения
        Map<String, Integer> plantCount = new HashMap<>();
        // Очередь для хранения последних k увиденных растений
        Queue<String> recentPlants = new LinkedList<>();
        // Создадим отдельный хешсет, для того, чтобы он работал в паре с очередью
        // Требуется для эффективного использования метода contains
        Set<String> recentSet = new HashSet<>();

        int nikonorQueries = 0;  // Счётчик обращений к Никанору
        int rememberedPlants = 0;  // Счётчик запомненных растений

        for (String plant : plants) {
            // Проверяем, помнит ли уже Харитон это растение
            if (plantCount.getOrDefault(plant, 0) >= m) {
                // Если Харитон уже запомнил растение, он не фотографирует его и не спрашивает Никанора
                continue;
            }

            // Проверяем, есть ли растение среди последних k снимков
            if (recentSet.contains(plant)) {
                // Если растение есть в последних k снимках, увеличиваем счётчик для него
                plantCount.put(plant, plantCount.getOrDefault(plant, 0) + 1);
                // Если Харитон теперь запомнил растение
                if (plantCount.get(plant) == m) {
                    rememberedPlants++;
                }
            } else {
                // Харитону нужно спросить Никанора, увеличиваем счётчик вопросов
                nikonorQueries++;
                // Добавляем растение в множество уникальных растений, которые увидел Харитон
                uniquePlants.add(plant);
                // Обновляем счётчик для текущего растения
                plantCount.put(plant, plantCount.getOrDefault(plant, 0) + 1);
                // Если после обновления Харитон запомнил растение
                if (plantCount.get(plant) == m) {
                    rememberedPlants++;
                }

                // Добавляем растение в очередь последних увиденных растений
                recentPlants.add(plant);
                recentSet.add(plant);
                // Если размер очереди превышает k, удаляем самое старое растение из очереди и сета
                if (recentPlants.size() > k) {
                    String oldestPlant = recentPlants.poll();
                    recentSet.remove(oldestPlant);

                }
            }
        }

        System.out.println(uniquePlants.size());
        System.out.println(nikonorQueries);
        System.out.println(rememberedPlants);
    }
}