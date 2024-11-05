package l;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // Чтение количества записей (n) из входных данных
        int n = Integer.parseInt(bufferedReader.readLine());

        // Создание карты, где ключ наименование товара, значение множество производителей для данного товара
        Map<String, Set<String>> productCounterMap = new HashMap<>();

        // Заполнение карты данными
        for (int i = 0; i < n; i++) {
            // Чтение и разбиение строки на наименование товара и производителя
            String[] line = bufferedReader.readLine().split(" ");
            String productName = line[0];
            String manufacturer = line[1];

            // Если товар еще не существует в карте, добавляем его с пустым множеством производителей
            productCounterMap.putIfAbsent(productName, new HashSet<>());
            // Добавляем производителя в соответствующее множество
            productCounterMap.get(productName).add(manufacturer);
        }

        // Вывод количества уникальных наименований товаров
        System.out.println(productCounterMap.size());

        // Для каждого товара выводим его наименование и количество производителей
        for (Map.Entry<String, Set<String>> entry : productCounterMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().size());
        }
    }
}
