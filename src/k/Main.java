package k;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");

        // Считываем количество орхидей (n) и порог уникальности (k)
        int n = Integer.parseInt(line[0]);
        int k = Integer.parseInt(line[1]);

        Map<Integer, Integer> typeCounterList = new HashMap<>(); // для подсчета количества орхидей каждого типа
        Map<Integer, Integer> colorCounterList = new HashMap<>(); // для подсчета количества орхидей каждого цвета

        // Создаем список для хранения исходных данных каждой орхидеи(тип и цвет)
        List<int[]> inputData = new ArrayList<>();
        // Считываем строку с типами орхидей и сохраняем ее в массив строк
        String[] typeLine = br.readLine().split(" ");
        // Считываем строку с цветами орхидей и сохраняем ее в массив строк
        String[] colorLine = br.readLine().split(" ");


        for (int i = 0; i < n; i++) {
            // Преобразуем строковые значения в целые числа: тип и цвет орхидеи
            int type = Integer.parseInt(typeLine[i]);
            int color = Integer.parseInt(colorLine[i]);

            // Подсчитываем количество орхидей каждого типа
            // Если типа еще нет, добавляем его со значением 0
            typeCounterList.putIfAbsent(type, 0);
            // Увеличиваем счетчик для текущего типа орхидеи на 1
            typeCounterList.put(type, typeCounterList.get(type) + 1);

            // Подсчитываем количество орхидей каждого цвета
            // Если цвета еще нет, добавляем его со значением 0
            colorCounterList.putIfAbsent(color, 0);
            // Увеличиваем счетчик для текущего цвета орхидеи на 1
            colorCounterList.put(color, colorCounterList.get(color) + 1);

            // Добавляем в список inputData массив с двумя значениями: тип и цвет орхидеи
            inputData.add(new int[]{type, color});
        }

        // Создаем список для хранения порядковых номеров орхидей, которые соответствуют критерию Харитона
        List<Integer> resultList = new ArrayList<>();

        // Цикл для вычисления уникальности каждого экземпляра орхидеи
        for (int i = 0; i < n; i++) {
            // Получаем тип и цвет текущей орхидеи из inputData
            int type = inputData.get(i)[0];
            int color = inputData.get(i)[1];

            // уникальностью экземпляра
            int uniqueness = typeCounterList.get(type) + colorCounterList.get(color);

            // Если уникальность экземпляра не превышает порог k, добавляем его в список результатов
            if (uniqueness <= k) {
                resultList.add(i + 1); // Сохраняем порядковый номер орхидеи
            }
        }


        Collections.sort(resultList);

        System.out.println(resultList.size());

        for (Integer result : resultList) {
            System.out.print(result + " ");
        }
    }
}
