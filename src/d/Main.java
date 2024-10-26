package d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // количество тем, которые предстоит изучить Харитону.
        int n = Integer.parseInt(br.readLine());

        // Map для хранения информации о темах
        Map<Integer, int[]> topics = new HashMap<>();

        // Считывание данных и заполнение Map
        for (int i = 1; i <= n; i++) {
            String[] line = br.readLine().trim().split("\\s+");
            int pages = Integer.parseInt(line[0]); // количество страниц
            int link = Integer.parseInt(line[1]);  // ссылка на другую тему
            topics.put(i, new int[]{pages, link}); // сохраняем как (номер темы -> {pages, link})
        }

        // Список для хранения порядка изучения тем
        List<Integer> studyOrder = new ArrayList<>();

        // Множество для отслеживания уже изученных тем
        Set<Integer> visited = new HashSet<>();

        //System.out.println(topics.keySet());
        // Создаем список topicIndices, содержащий номера всех тем, чтобы затем отсортировать их по количеству страниц
        List<Integer> topicIndices = new ArrayList<>(topics.keySet());
        // Сортируем темы по количеству страниц.
        Collections.sort(topicIndices, Comparator.comparingInt((Integer i) -> topics.get(i)[0]).thenComparingInt(i -> i));
        //System.out.println(topicIndices);


        // Обрабатываем каждую тему, следуя отсортированному порядку
        for (int topic : topicIndices) {
            // Проверяем, изучена ли уже тема
            if (!visited.contains(topic)) {
                // Временный стек для отслеживания пути зависимостей тем
                Stack<Integer> stack = new Stack<>();
                while (topic != 0 && !visited.contains(topic)) {
                    stack.push(topic); // Добавляем текущую тему в стек
                    visited.add(topic); // Отмечаем тему как изученную
                    topic = topics.get(stack.peek())[1]; // Переход к ссылочной теме
                }
                // Извлекаем темы из стека и добавляем их в studyOrder в нужном порядке
                while (!stack.isEmpty()) {
                    //метод pop возвращает самое последнее значение и также его убирает
                    studyOrder.add(stack.pop());
                }
            }
        }
        for (Integer integer : studyOrder) {
            System.out.print(integer + " ");
        }


    }
}
