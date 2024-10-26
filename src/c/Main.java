package c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Считываем количество дней, для которых доступна информация о мороженом
        int n = Integer.parseInt(br.readLine());

        // Используем TreeMap для хранения количества купленных порций каждого сорта мороженого.
        // TreeMap автоматически поддерживает порядок ключей в лексикографическом порядке.
        TreeMap<String, Integer> iceCreamPurchaseCount = new TreeMap<>();

        // Массив для хранения информации о сортах мороженого и их рейтингах по дням
        String[][] iceCreamData = new String[n][2];

        // Чтение данных о мороженом на каждый день
        for (int i = 0; i < n; i++) {
            // Читаем строку, убираем лишние пробелы по краям и разбиваем на две части (сорт и рейтинг)
            String[] line = br.readLine().trim().split("\\s+");
            iceCreamData[i][0] = line[0]; // Название сорта мороженого
            iceCreamData[i][1] = line[1]; // Рейтинг мороженого

        }

        // Переменная для накопления общего рейтинга съеденного мороженого, long для избежания переполнения
        long totalRating = 0;

        // Переменная для подсчёта визитов в киоск
        int visits = 0;

        // Указатель на текущий день
        int i = 0;

        // Основной цикл, который проходит по дням и планирует покупки мороженого
        while (i < n) {
            // Текущий вкус мороженого
            String currentFlavor = iceCreamData[i][0];

            // Рейтинг текущего мороженого
            int currentRating = Integer.parseInt(iceCreamData[i][1]);

            // Вспомогательный указатель j для определения дней, на которые хватит текущего мороженого
            int j = i;

            // Пока в следующие дни рейтинг не выше текущего, добавляем его к суммарному рейтингу
            while (j < n && Integer.parseInt(iceCreamData[j][1]) <= currentRating) {
                totalRating += currentRating; // Добавляем текущий рейтинг к общей сумме
                j++; // Переход на следующий день
            }

            // Количество порций мороженого, которое нужно купить для покрытия дней с текущим или более низким рейтингом
            int quantityToBuy = j - i;

            // Увеличиваем количество визитов, так как Мелентия покупает мороженое на этот период
            visits++;

            // Обновляем количество купленных порций текущего сорта мороженого в TreeMap.
            // Если сорт уже был куплен, увеличиваем его количество на quantityToBuy
            iceCreamPurchaseCount.put(currentFlavor,
                    iceCreamPurchaseCount.getOrDefault(currentFlavor, 0) + quantityToBuy);

            // Обновляем i, переходя к следующему дню с более высоким рейтингом
            i = j;
        }

        // Вывод общего суммарного рейтинга съеденного мороженого за n дней
        System.out.println(totalRating);

        // Вывод количества визитов в киоск
        System.out.println(visits);

        // Выводим в лексикографическом порядке название мороженого и количество купленных порций
        for (Map.Entry<String, Integer> entry : iceCreamPurchaseCount.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
