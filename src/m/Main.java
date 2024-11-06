package m;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Быстрый ввод-вывод
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Чтение количества товаров и их наименований
        int n = Integer.parseInt(br.readLine());
        String[] items = br.readLine().split(" ");
        Map<String, Integer> itemIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            itemIndex.put(items[i], i + 1); // Запоминаем индекс каждого товара
        }

        // Чтение информации о магазинах и определение ближайшего магазина для каждого товара
        int m = Integer.parseInt(br.readLine());
        //массив листов, где каждый лист будет содержать список продуктов, которые есть в магазине
        List<String>[] shops = new List[m];
        // для каждого продукта будем записывать ближайший магазин
        Map<String, Integer> closestShop = new HashMap<>();
        for (int i = 0; i < m; i++) {
            String[] shopInput = br.readLine().split(" ");
            int k = Integer.parseInt(shopInput[0]);
            shops[i] = new ArrayList<>();
            for (int j = 1; j <= k; j++) {
                String item = shopInput[j];
                shops[i].add(item);
                closestShop.put(item, i); // Запоминаем ближайший магазин для каждого товара
            }
        }

        // Список для хранения всех пакетов
        List<List<String>> bags = new ArrayList<>();
        // Последний использованный пакет
        List<String> lastBag = null;
        // Индекс верхнего товара в последнем пакете
        int topItemIndex = -1;

        // Проходим по магазинам с самого дальнего к ближайшему
        for (int i = 0; i < m; i++) {
            List<String> shopItems = shops[i];
            // Определяем товары, которые нужно купить в этом магазине
            List<String> itemsToBuy = new ArrayList<>();
            for (String item : shopItems) {
                if (closestShop.get(item) == i) {
                    itemsToBuy.add(item);
                }
            }

            // Сортируем товары по возрастанию номеров (позициям в списке Фалалея)
            itemsToBuy.sort(Comparator.comparingInt(itemIndex::get));

            // Временный пакет для текущего магазина
            List<String> currentBag = null;
            int currentTopItemIndex = -1;

            // Обрабатываем товары в магазине
            for (String item : itemsToBuy) {
                int currentItemIndex = itemIndex.get(item);

                if (lastBag != null && currentItemIndex > topItemIndex) {
                    // Можно положить в последний пакет
                    lastBag.add(item);
                    topItemIndex = currentItemIndex;
                    currentBag = lastBag;
                    currentTopItemIndex = topItemIndex;
                } else if (currentBag != null && currentItemIndex > currentTopItemIndex) {
                    // Можно положить в текущий пакет
                    currentBag.add(item);
                    currentTopItemIndex = currentItemIndex;
                } else {
                    // Создаём новый пакет
                    List<String> newBag = new ArrayList<>();
                    newBag.add(item);
                    bags.add(newBag);
                    currentBag = newBag;
                    currentTopItemIndex = currentItemIndex;
                }
            }

            // После обработки магазина обновляем lastBag и topItemIndex
            if (currentBag != null) {
                lastBag = currentBag;
                topItemIndex = currentTopItemIndex;
            }
        }

        // Выводим результат с помощью System.out.println
        System.out.println(bags.size());
        for (List<String> bag : bags) {
            System.out.print(bag.size());
            for (String item : bag) {
                System.out.print(" " + item);
            }
            System.out.println();
        }
    }
}
