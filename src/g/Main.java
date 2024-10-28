package g;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Чтение количества задач
        int n = Integer.parseInt(reader.readLine());

        // Чтение времени, необходимого для каждой задачи, и сохранение их в список
        String[] taskTimeStrings = reader.readLine().split("\\s+");
        List<Integer> taskTimes = new ArrayList<>();
        for (String timeStr : taskTimeStrings) {
            taskTimes.add(Integer.parseInt(timeStr));
        }

        // Чтение количества интервалов времени, в которые задачи можно выполнять
        int m = Integer.parseInt(reader.readLine());

        // Чтение и сохранение интервалов. Каждый интервал представляет собой массив из двух значений:
        // si (начало интервала) и fi (конец интервала)
        List<int[]> intervals = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String[] intervalData = reader.readLine().split("\\s+");
            int si = Integer.parseInt(intervalData[0]); // Начало интервала
            int fi = Integer.parseInt(intervalData[1]); // Конец интервала
            intervals.add(new int[]{si, fi});
        }

        // Сортировка задач по убыванию времени, необходимого для их выполнения.
        // Это делается для того, чтобы сначала попытаться распределить самые сложные задачи.
        taskTimes.sort(Collections.reverseOrder());

        // Переменная, которая будет отслеживать время, когда последняя задача будет завершена.
        int currentTime = -1;

        // Массив, который отслеживает, были ли уже использованы интервалы. Изначально все значения false.
        boolean[] intervalUsed = new boolean[m];

        // Цикл по каждой задаче
        for (int taskTime : taskTimes) {
            boolean assigned = false; // Флаг, который указывает, удалось ли назначить задачу

            // Поиск подходящего интервала для текущей задачи
            for (int i = 0; i < m; i++) {
                // Проверка, был ли использован данный интервал
                if (!intervalUsed[i]) {
                    int[] interval = intervals.get(i);
                    int intervalStart = interval[0];
                    int intervalEnd = interval[1];
                    int intervalLength = intervalEnd - intervalStart;

                    // Проверка, достаточно ли длинен интервал для выполнения задачи
                    if (intervalLength >= taskTime) {
                        intervalUsed[i] = true; // Отмечаем интервал как использованный

                        // Обновляем текущее время до максимума между текущим временем и временем окончания задачи в данном интервале
                        currentTime = Math.max(currentTime, intervalStart + taskTime);

                        assigned = true; // Задача успешно назначена на интервал
                        break; // Переход к следующей задаче
                    }
                }
            }

            // Если задачу не удалось назначить ни на один интервал, выводим -1 и завершаем программу
            if (!assigned) {
                System.out.println(-1);
                return;
            }
        }

        // Выводим общее время, когда завершится выполнение всех задач
        System.out.println(currentTime);
    }
}
