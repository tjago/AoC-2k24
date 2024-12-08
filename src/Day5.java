import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) throws IOException {
        System.out.println("The sum of correct orders middle pages is (sample: 143): "
                +  new Day5().solution("res/Day5-sample.txt"));
        System.out.println("The sum of correct orders middle pages is : "
                +  new Day5().solution("res/Day5.txt"));
    }

    private long solution(String filename) throws IOException {

        var rules = new HashMap<Integer, Set<Integer>>();
        Files.lines(Path.of(filename))
                .filter(s -> s.contains("|"))
                .forEach(textRule -> {
                    String splittedRules[] = textRule.split("\\|");
                    int updatePage = Integer.parseInt(splittedRules[0]);
                    int afterPage = Integer.parseInt(splittedRules[1]);

                    if (!rules.containsKey(updatePage)) {
                        rules.put(updatePage, new HashSet<>(afterPage));
                    }
                    rules.get(updatePage).add(afterPage);
                });

        //Debug list
//        rules.forEach((integer, integers) -> System.out.println(integer + "->" + integers));

        return Files.lines(Path.of(filename))
                .filter(s -> !s.contains("|"))
                .filter(s -> s.length() > 1)
                .map(textOrders -> textOrders.split(","))
                .map(stringArray ->
                    Arrays.stream(stringArray)
                            .mapToInt(Integer::parseInt)
                            .boxed()
                            .collect(Collectors.toList())
                )
                .peek(integers -> System.out.println("LIST: " + integers))
                .mapToLong(numberList -> isInOrder(numberList, rules))
                .peek(System.out::println)
                .sum();
    }

    long isInOrder(List<Integer> updatePages, HashMap<Integer, Set<Integer>> rules) {
        List<Integer> pagesCopy = new ArrayList<>(updatePages);
        List<Integer> priorPages = new ArrayList<>();
        long count =
                updatePages
                .stream()
                .filter(currPage -> {
                    if (priorPages.isEmpty() || rules.get(currPage) == null) {
                        priorPages.add(currPage);
                        return true;
                    }
                    long countValidPages = priorPages
                            .stream()
                            .filter(lookingAtPage -> !Objects.equals(lookingAtPage, currPage))
                            .peek(value -> System.out.println(
                                    "checking " + currPage + " is blocked by " + value +
                                    " comparing with rules: "        + rules.get(currPage).stream().toList()))
                            .filter(page -> !rules.get(currPage).contains(page))
                            .peek(page -> System.out.println("Page: " + currPage + " not blocked by " + page + " rules:"
                            +  rules.get(currPage).stream().toList()))
                            .count();

                    pagesCopy.remove(currPage);
                    priorPages.add(currPage);
                    System.out.println("countValidPages: " + countValidPages + " priorPages size: " + priorPages.size());
                    return countValidPages == priorPages.size() -1;
                })
                .count();
        //if number of valid pages match orders size then return value of the element in the middle, else zero
        return count == updatePages.size() ? updatePages.get(updatePages.size() / 2 ) : 0;
    }
}
