import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;

public class Day4 {
    public static void main(String[] args) throws IOException {
        System.out.println("XMAS words in Sample ( should be 18): " + new Day4().solution("res/Day4-sample.txt"));
        System.out.println("XMAS words in puzzle input: " + new Day4().solution("res/Day4.txt")); //2455 too low
    }

    private long solution(String filename) throws IOException {
        final int windowSize = 4;

        long horizontalNumOfWords = Files.lines(Path.of(filename))
                .mapToLong(line -> countOccurrences(line, "XMAS")
                        + countOccurrences(line, "SAMX"))
                .sum();

        long verticalAndDigonalNumOfWords = Files.lines(Path.of(filename))
                .gather(Gatherers.windowSliding(windowSize))
                .mapToLong(Day4::parseWindowOfFourLines)
                .sum();

        return horizontalNumOfWords + verticalAndDigonalNumOfWords;
    }

    static long parseWindowOfFourLines(List<String> line) {

        //check XMAS numbers vertically
        long verticalCount = IntStream
                .range(0, line.get(0).length())
                .mapToObj(index -> "" + line.get(0).charAt(index)
                        + line.get(1).charAt(index)
                        + line.get(2).charAt(index)
                        + line.get(3).charAt(index))
                .mapToLong(text -> (text.contains("XMAS") | text.contains("SAMX") ) ? 1 : 0)
                .sum();

        //check XMAS numbers diagonally, from left to right
        long diagonalRightCount = IntStream
                .range(0, line.get(0).length() -3)
                .mapToObj(index -> "" + line.get(0).charAt(index)
                        + line.get(1).charAt(index +1)
                        + line.get(2).charAt(index +2)
                        + line.get(3).charAt(index +3))
                .mapToLong(text -> (text.contains("XMAS") | text.contains("SAMX") ) ? 1 : 0)
                .sum();

        //check XMAS numbers diagonally, from right to left
        long diagonalLeftCount = IntStream
                .range(3, line.get(3).length())
                .mapToObj(index -> "" + line.get(0).charAt(index)
                        + line.get(1).charAt(index -1)
                        + line.get(2).charAt(index -2)
                        + line.get(3).charAt(index -3))
                .mapToLong(text -> (text.contains("XMAS") | text.contains("SAMX") ) ? 1 : 0)
                .sum();

        return verticalCount + diagonalRightCount + diagonalLeftCount;
    }

    public static long countOccurrences(String source, String find) {
        return Pattern.compile(find) // Pattern
                .matcher(source) // Mather
                .results()       // Stream<MatchResults>
                .count();
    }
}
