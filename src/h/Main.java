package h;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Чтение количества орхидей
        int n = Integer.parseInt(br.readLine());

        // Чтение видов орхидей
        String[] typesStr = br.readLine().split(" ");
        int[] types = new int[n];
        for (int i = 0; i < n; i++) {
            types[i] = Integer.parseInt(typesStr[i]);
        }

        // Чтение цветов орхидей
        String[] colorsStr = br.readLine().split(" ");
        int[] colors = new int[n];
        for (int i = 0; i < n; i++) {
            colors[i] = Integer.parseInt(colorsStr[i]);
        }

        // Инициализация коллекций для отслеживания
        HashSet<Integer> seenColors = new HashSet<>();      // Множество всех встреченных цветов
        HashSet<Integer> readTypes = new HashSet<>();       // Множество видов, описание которых он прочитал
        HashSet<Integer> allTypes = new HashSet<>();  // Множество всех встреченных видов

        int surprised = 0;      // Подсчёт числа удивлений
        int mistaken = 0;       // Подсчёт числа ошибочных предположений

        for (int i = 0; i < n; i++) {
            int type = types[i];
            int color = colors[i];

            if (!seenColors.contains(color)) {
                // Новый цвет, Харитон читает описание вида

                if (readTypes.contains(type)) {
                    // Если описание вида уже было прочитано, Харитон удивляется
                    surprised++;
                } else {
                    // Читает описание вида впервые
                    readTypes.add(type);
                }
                // Добавляем цвет в множество встреченных цветов
                seenColors.add(color);
            } else {
                // Цвет уже встречался, Харитон предполагает новый вид и не читает описание
                if (allTypes.contains(type)) {
                    // Вид уже известен, но Харитон ошибочно предполагает, что он новый
                    mistaken++;
                }
                // Вид не добавляется в прочитанные описания, так как описание не читалось
            }
            // Добавляем вид в множество встреченных видов
            allTypes.add(type); // Сохраняем все уникальные типы
        }

        // Подсчёт непрочитанных описаний
        // Это все уникальные типы минус те, описания которых были прочитаны
        int unreadDescriptions = allTypes.size() - readTypes.size();

        // Вывод результатов
        System.out.println(surprised);
        System.out.println(mistaken);
        System.out.println(unreadDescriptions);
    }
}
