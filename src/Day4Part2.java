import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class Day4Part2 {
    public static void main(String[] args) throws IOException {
        System.out.println("XMAS words in Sample ( should be 9): " + new Day4Part2().solution("res/Day4-sample.txt"));
        System.out.println("XMAS words in puzzle input: " + new Day4Part2().solution("res/Day4.txt")); //2455 too low
    }

    private long solution(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .gather(Gatherers.windowSliding(3))
                .mapToLong(Day4Part2::parseWindowOfThreeLines)
                .sum();
    }

    static long parseWindowOfThreeLines(List<String> line) {

        long count = IntStream
                .range(0, line.get(0).length() -2)
                .mapToObj(index -> ""
                        //diagonal word to the right
                        + line.get(0).charAt(index)
                        + line.get(1).charAt(index +1)
                        + line.get(2).charAt(index +2)

                        //diagonal word to the left
                        + line.get(0).charAt(index + 2)
                        + line.get(1).charAt(index + 1)
                        + line.get(2).charAt(index)
                )
                .mapToLong(text -> (text.contains("MASMAS")
                        | text.contains("SAMSAM")
                        | text.contains("SAMMAS")
                        | text.contains("MASSAM") ) ? 1 : 0)
                .sum();

        return count;
    }
}
