package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Считывание и обработка входных данных: считываем количество сортов, время, за которое каждый из них съедается, и массивы с номерами сортов и отзывами Харитона.
        int n = Integer.parseInt(br.readLine());

        // Считываем массив hj — время, за которое Харитон съест каждый сорт мороженого
        String[] hStr = br.readLine().split(" ");
        int[] hj = new int[n + 1]; // Массив с 1-based индексированием для удобства
        for (int i = 1; i <= n; i++) {
            hj[i] = Integer.parseInt(hStr[i - 1]);
        }

        // Считываем массив tj — время, за которое Мелентий съест каждый сорт мороженого
        String[] tStr = br.readLine().split(" ");
        int[] tj = new int[n + 1]; // Массив с 1-based индексированием для удобства
        for (int i = 1; i <= n; i++) {
            tj[i] = Integer.parseInt(tStr[i - 1]);
        }

        // Считываем количество порций m, которые попробует Харитон
        int m = Integer.parseInt(br.readLine());

        // Считываем массив p — номера сортов мороженого, которые попробует Харитон
        String[] pStr = br.readLine().split(" ");
        int[] p = new int[m];
        for (int i = 0; i < m; i++) {
            p[i] = Integer.parseInt(pStr[i]);
        }

        // Считываем строку likesStr — указывает, понравился ли сорт мороженого Харитону ('O' или 'X')
        String likesStr = br.readLine();
        boolean[] liked = new boolean[m]; // Булев массив для хранения информации о понравившихся сортах
        for (int i = 0; i < m; i++) {
            liked[i] = likesStr.charAt(i) == 'O'; // true, если понравилось ('O'), иначе false
        }

        // Вычисление времени окончания порций Харитона: используем массив для отслеживания времени, когда Харитон заканчивает есть каждую порцию, чтобы точно знать, когда происходит событие.
        long[] H = new long[m]; // Массив для хранения времени окончания поедания каждой порции Харитоном
        long cumulativeTime = 0;
        for (int i = 0; i < m; i++) {
            cumulativeTime += hj[p[i]];
            H[i] = cumulativeTime; // Записываем момент окончания поедания порции
        }

        // Инициализация переменных
        long currentTime = 0; // Текущее время в симуляции
        int haritonIndex = 0; // Индекс текущей порции Харитона
        long melentyBusyUntil = 0; // Время, до которого Мелентий занят поеданием мороженого
        Integer melentyRememberedVariety = null; // Запоминаем сорт мороженого, который понравился, пока Мелентий занят
        ArrayList<Integer> melentyPortions = new ArrayList<>(); // Список сортов, которые съест Мелентий

        // Перебор событий: определяем, что произойдет раньше — Харитон заканчивает порцию или Мелентий заканчивает есть.
        // Если Харитону понравился сорт и Мелентий свободен, он сразу ест этот сорт.
        // Если Мелентий занят, он запоминает сорт и ест его позже.
        while (haritonIndex < m || currentTime < melentyBusyUntil || melentyRememberedVariety != null) {
                // Определяем время следующего события для Харитона
                long nextHaritonTime;
                if (haritonIndex < m) {
                    nextHaritonTime = H[haritonIndex]; // Время окончания текущей порции Харитона
                } else {
                    nextHaritonTime = Long.MAX_VALUE; // Если Харитон закончил, то бесконечное время
                }

                // Определяем время следующего события для Мелентия
                long nextMelentyTime;
                if (melentyBusyUntil > currentTime) {
                    nextMelentyTime = melentyBusyUntil; // Если Мелентий занят, то время, когда он освободится
                } else {
                    if (melentyRememberedVariety != null) {
                        nextMelentyTime = currentTime; // Если Мелентий свободен и есть запомненный сорт, он сразу начинает есть
                    } else {
                        nextMelentyTime = Long.MAX_VALUE; // Если нечего есть, бесконечное время
                    }
                }

                // Определяем ближайшее событие между Харитоном и Мелентием
                long nextEventTime;
                if (nextHaritonTime < nextMelentyTime) {
                    nextEventTime = nextHaritonTime;
                } else {
                    nextEventTime = nextMelentyTime;
                }

                // Обновляем текущее время до времени следующего события
                currentTime = nextEventTime;

                // Обработка события Харитона
                if (haritonIndex < m && H[haritonIndex] == currentTime) {
                    if (liked[haritonIndex]) { // Если Харитону понравился сорт
                        int variety = p[haritonIndex];
                        if (currentTime >= melentyBusyUntil) {
                            // Мелентий свободен, он сразу начинает есть этот сорт
                            melentyBusyUntil = currentTime + tj[variety];
                            melentyPortions.add(variety); // Добавляем сорт в список Мелентия
                            melentyRememberedVariety = null; // Очищаем запомненный сорт
                            // Важно: Это позволяет Мелентию сразу реагировать на понравившийся сорт, если он свободен,
                            // что ускоряет процесс и уменьшает время ожидания.
                        } else {
                            // Мелентий занят, он запоминает этот сорт для будущего
                            melentyRememberedVariety = variety;
                            // Важно: Если Мелентий занят, сорт запоминается, чтобы он мог его съесть позже,
                            // когда освободится. Это позволяет Мелентию не терять понравившиеся Харитону порции.
                        }
                    }
                    haritonIndex++; // Переходим к следующей порции Харитона
                }

                // Обработка события Мелентия
                if (currentTime >= melentyBusyUntil) {
                    // Если Мелентий заканчивает есть или свободен
                    while (melentyRememberedVariety != null) {
                        int variety = melentyRememberedVariety; // Достаем запомненный сорт
                        melentyBusyUntil = currentTime + tj[variety]; // Начинаем его есть
                        melentyPortions.add(variety); // Добавляем сорт в список Мелентия
                        melentyRememberedVariety = null; // Очищаем запомненный сорт
                        // Важно: Если есть запомненный сорт, Мелентий сразу начинает его есть, как только освобождается.
                        // Это гарантирует, что он ест все понравившиеся Харитону порции в порядке их поступления и минимизирует время простоя.
                        break;
                    }
                }
        }

        // Вывод результатов: в конце мы выводим количество порций, которые съел Мелентий, время, когда он закончит, и номера сортов в порядке их поедания.
        System.out.println(melentyPortions.size() + " " + melentyBusyUntil); // Количество порций и время окончания
        for (int i = 0; i < melentyPortions.size(); i++) {
            System.out.print(melentyPortions.get(i)); // Выводим номера сортов
            if (i < melentyPortions.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println(); // Завершаем вывод новой строкой
    }
}
