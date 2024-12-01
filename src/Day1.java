import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day1 {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello Day 1");
        new Day1().solution("res/Day1.txt");
    }

    private void solution(String filename) throws IOException {

        Stream<String> inputs = Files.lines(Path.of(filename));

        //Build 2 lists with numbers
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        inputs.forEach(
                s -> {
                    String[] splittedText = s.split("   ");
                    list1.add(Integer.parseInt(splittedText[0]));
                    list2.add(Integer.parseInt(splittedText[1]));
                }
        );

        //debugging
        System.out.println("List 1");
        list1.forEach(integer -> System.out.printf("%d ", integer));
        System.out.println("\nList 2");
        list2.forEach(integer -> System.out.printf("%d ", integer));

        //Sort Lists
        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);

        //debugging
        System.out.println("\nList 1 sorted");
        list1.forEach(integer -> System.out.printf("%d ", integer));
        System.out.println("\nList 2 sorted ");
        list2.forEach(integer -> System.out.printf("%d ", integer));

        long sum = IntStream
                .range(0, list1.size())
                .mapToLong(index -> Math.abs(list1.get(index) - list2.get(index)))
                .sum();

        System.out.println("Total sum of all numbers distances equals: ");
        System.out.println(sum);

        var similarityScore = list1
                .stream()
                .mapToLong(integer -> integer *
                        list2
                        .stream()
                        .filter(integer1 -> integer1.equals(integer))
                        .count())
                .sum();

        System.out.println("\nSimilarity score: ");
        System.out.println(similarityScore);
    }
}
