package f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем BufferedReader для чтения ввода
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Считываем количество списков
        int n = Integer.parseInt(br.readLine().trim());

        // Список для хранения всех наборов слов из ввода
        ArrayList<Set<String>> essentialWordsList = new ArrayList<>();

        // Чтение и хранение каждого списка в виде множества
        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().trim().split("\\s+"); // Читаем строку и разделяем на слова
            int numberOfWords = Integer.parseInt(line[0]); // Первое значение - количество слов
            Set<String> essentialWords = new HashSet<>(); // Создаем множество для уникальных слов
            for (int j = 1; j <= numberOfWords; j++) { // Добавляем каждое слово в множество
                essentialWords.add(line[j]);
            }
            essentialWordsList.add(essentialWords); // Добавляем множество в список всех списков
        }
        System.out.println(essentialWordsList);

        // Список для хранения номеров информативных списков
        ArrayList<Integer> informativeLines = new ArrayList<>();

        // Проверка информативности каждого списка
        for (int i = 0; i < n; i++) {
            Set<String> currentList = essentialWordsList.get(i); // Получаем текущий список
            boolean isInformative = true; // Предполагаем, что список информативен
            // Проверяем текущий список с каждым другим
            for (int j = 0; j < n; j++) {
                if (i != j) { // Пропускаем сравнение списка с самим собой
                    Set<String> compareList = essentialWordsList.get(j); // Список для сравнения

                    // Считаем количество общих слов между текущим и сравниваемым списком
                    int commonWordsCount = 0;
                    for (String word : currentList) {
                        if (compareList.contains(word)) { // Если слово есть в обоих списках
                            commonWordsCount++;
                        }
                    }
                    // Проверяем, является ли другой список "половинным перекрытием"
                    if (commonWordsCount * 2 >= currentList.size()) {
                        // Если другой список покрывает не менее половины слов текущего списка
                        isInformative = false; // Текущий список не информативен
                        break; // Прерываем цикл, так как нашли подходящий список
                    }
                }
            }
            // Если не найден ни один другой список, покрывающий хотя бы половину слов, добавляем список в результат
            if (isInformative) {
                informativeLines.add(i + 1); // Номера списков начинаются с 1, поэтому добавляем 1
            }
        }

        // Вывод результатов
        System.out.println(informativeLines.size()); // Выводим количество информативных списков
        if (!informativeLines.isEmpty()) {
            for (int line : informativeLines) {
                System.out.print(line + " "); // Выводим номера информативных списков через пробел
            }
            System.out.println(); // Переход на новую строку после вывода номеров
        }
    }
}
