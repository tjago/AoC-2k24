import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {
    public static void main(String[] args) throws IOException {

        Stream<String> inputStream = Files.lines(Path.of("res/Day3.txt")).collect(Collectors.joining()).lines();

        System.out.println("Multiplied muls: " + new Day3().solution(inputStream));
        System.out.println("conditional and multiplied muls: " + new Day3().solutionPart2());
    }

    private static long solution(Stream<String> inputStream) throws IOException {
        return Files
                .lines(Path.of("res/Day3.txt"))
                .map(string -> Pattern.compile("mul\\(\\d+,\\d+\\)")
                            .matcher(string)
                            .results().map(MatchResult::group)
                            .toList()
                )
                .flatMap(Collection::stream)
                .mapToLong(textOperation ->
                    Arrays.stream(textOperation.substring(4, textOperation.length() -1).split(","))
                            .mapToLong(Long::parseLong)
                            .reduce((left, right) -> left * right)
                            .getAsLong()
                )
                .sum();
    }

    /**
     * Solution for Part 2 is a copy with extra lines: 45 - 51 for regex to exclude portion of disabled muls Operations
     * @return
     * @throws IOException
     */
    private long solutionPart2() throws IOException {
        return Files
                .lines(Path.of("res/Day3.txt"))
                .collect(Collectors.joining())//join several String lines into one
                .lines()
                .map(input -> Arrays.stream(input.split("don't\\(\\).*?do\\(\\)")).toList())
                .flatMap(Collection::stream)
                .map(input -> Arrays.stream(input.split("don't\\(\\).*")).toList())//lets cut out final block not closed with do
                .flatMap(Collection::stream)
                .map(string -> Pattern.compile("mul\\(\\d+,\\d+\\)")
                        .matcher(string)
                        .results().map(MatchResult::group)
                        .toList()
                )
                .flatMap(Collection::stream)
                .mapToLong(textOperation ->
                        Arrays.stream(textOperation.substring(4, textOperation.length() -1).split(","))
                                .mapToLong(Long::parseLong)
                                .reduce((left, right) -> left * right)
                                .getAsLong()
                )
                .sum();
    }
}
