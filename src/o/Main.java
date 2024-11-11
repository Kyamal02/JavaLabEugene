package o;

import java.util.*;
import java.io.*;

public class Main {

    static class Article {
        int pj; // время публикации
        int rj; // рейтинг
        int tj; // время чтения
        int sj; // новая величина навыка

        public Article(int pj, int rj, int tj, int sj) {
            this.pj = pj;
            this.rj = rj;
            this.tj = tj;
            this.sj = sj;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Чтение количества статей
        int n = Integer.parseInt(br.readLine());
        Article[] articles = new Article[n];
        for (int i = 0; i < n; i++) {
            String[] parts = br.readLine().split(" ");
            int pj = Integer.parseInt(parts[0]);
            int rj = Integer.parseInt(parts[1]);
            int tj = Integer.parseInt(parts[2]);
            int sj = Integer.parseInt(parts[3]);
            articles[i] = new Article(pj, rj, tj, sj);
        }
        // Сортировка статей по времени публикации
        Arrays.sort(articles, Comparator.comparingInt(a -> a.pj));

        // Предварительный расчет максимальных sj от i до конца
        int[] maxSjFrom = new int[n + 1];
        maxSjFrom[n] = Integer.MIN_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            maxSjFrom[i] = Math.max(articles[i].sj, maxSjFrom[i + 1]);
        }

        // Приоритетная очередь с компаратором
        PriorityQueue<Article> availableArticles = new PriorityQueue<>(new Comparator<Article>() {
            public int compare(Article a1, Article a2) {
                if (a1.rj != a2.rj) {
                    return Integer.compare(a2.rj, a1.rj); // Более высокий рейтинг вперед
                }
                if (a1.sj != a2.sj) {
                    return Integer.compare(a2.sj, a1.sj); // Больший sj вперед
                }
                return Integer.compare(a1.tj, a2.tj); // Меньшее время чтения вперед
            }
        });

        long currentTime = 0;
        int currentSkill = 0;
        int idx = 0;
        int articlesRead = 0;
        List<Integer> skillsGained = new ArrayList<>();

        while (true) {
            // Добавляем все статьи, опубликованные до текущего времени
            while (idx < n && articles[idx].pj <= currentTime) {
                availableArticles.add(articles[idx]);
                idx++;
            }

            boolean foundArticle = false;

            while (!availableArticles.isEmpty()) {
                Article topArticle = availableArticles.peek(); // Получаем статью с наивысшим приоритетом
                if (topArticle.sj > currentSkill) {
                    availableArticles.poll(); // Удаляем статью из очереди
                    currentTime += topArticle.tj; // Обновляем текущее время после чтения статьи
                    currentSkill = topArticle.sj; // Обновляем текущий навык Евлампия
                    articlesRead++; // Увеличиваем счетчик прочитанных статей
                    skillsGained.add(currentSkill); // Записываем новый навык в список навыков
                    foundArticle = true;

                    // Обновляем очередь, добавляя статьи, которые стали доступны во время чтения
                    while (idx < n && articles[idx].pj <= currentTime) {
                        availableArticles.add(articles[idx]);
                        idx++;
                    }
                    break; // Выходим из вложенного цикла, чтобы начать с нового `currentTime`
                } else {
                    // Если статья не увеличивает навык, удаляем её из очереди
                    availableArticles.poll();
                }
            }

            if (foundArticle) {
                continue; // Продолжаем цикл, если статья была найдена и прочитана
            }

            // Проверяем, есть ли оставшиеся статьи, способные увеличить навык
            if (idx < n && maxSjFrom[idx] > currentSkill) {
                // Ждем до времени публикации следующей статьи
                currentTime = Math.max(currentTime, articles[idx].pj);
            } else {
                // Если нет статей, способных увеличить навык, завершаем выполнение
                break;
            }
        }

        // Вывод результатов
        System.out.println(articlesRead + " " + currentTime);
        for (int i = 0; i < skillsGained.size(); i++) {
            System.out.print(skillsGained.get(i));
            if (i < skillsGained.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
