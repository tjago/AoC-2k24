import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Day2 {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello Advent of Code Day 2");
        new Day2().solution("res/Day2.txt");
    }

    private void solution(String filename) throws IOException {

        Stream<String> inputs = Files.lines(Path.of(filename));

        var sumOfGoodReports = inputs.mapToInt(
                s -> {
                    var report = Arrays
                            .stream(s.split(" "))
                            .mapToInt(Integer::parseInt)
                            .boxed()
                            .toList();
                    System.out.println("report: " + report);

                    //When validations are passing we return 1, when they dont pass we return 0
                    if ( report.size() != report.stream().distinct().count() ) {
                        System.out.println("report with duplicates");
                        return 0;
                    } else if (report
                            .stream()
                            .gather(Gatherers.windowSliding(2))
                            .mapToInt(arr -> abs(arr.getFirst() - arr.getLast()))
                            .max().getAsInt() > 3) {
                        System.out.println("Level differences are higher than 3");
                        return 0;
                    } else if (report.stream().sorted().toList().equals(report)) {
                        System.out.println("we have sorted list with rising order");
                        return 1;
                    } else if ( report.stream().sorted(Collections.reverseOrder()).toList().equals(report)) {
                        System.out.println("we have sorted list with descending order");
                        return 1;
                    } else {
                        System.out.println("report mismatched");
                        return 0;
                    }

                }
        ).sum();

        System.out.println("number of correct reports: " + sumOfGoodReports);
    }
}
