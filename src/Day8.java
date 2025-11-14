import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day8 {

    record AntennaPair(Integer antA, Integer antB) {};

    public static void main(String[] args) throws IOException {
        System.out.println("Sum of unique antinodes: "
                + new Day8().solution("res/Day8-sample.txt") + " should be 14");


        System.out.println("Sum of unique antinodes: "
                + new Day8().solution("res/Day8.txt"));
        //451 too high, no distinct
        //277 too low
        //420 too high

    }
    /** pseudocode

     1. put antenna's on the grid - parse input file
        grid would be represented by a long list
     2. pairs would be lists of parsed grid, each list of diff antennas
        in a stream the list would be mapped to permutation of pairs.
     3. pairs by pairs would calculate distance between antennas, let say 50 and 61
     4. then take origin position of antenna, ex. 50 and antinode 39,
        since we know that 50 mod 10 is < than 61 mod 10, we expect opposite
        50 mod 10 > 39 mod 10 but it's not true so it means it is out of grid
     5. new list of unique positions will be a result.


     */
    private Integer solution(String filename) throws IOException {

        final var lines = Files.lines(Path.of(filename)).collect(Collectors.joining());
//        lines.stream().peek(System.out::println).toList()
        final var charsInLine = Files.lines(Path.of(filename)).findFirst().get().length();
        System.out.println("chars in line: " +charsInLine);
        Map<Character, Set<Integer>> antennas = getCharacterSetMap(lines);
        System.out.println("antennas: \n" + antennas);

        List<AntennaPair> antennasPairs = convertAntennasToPairs(antennas);

        var antinodes = antennasPairs
                .stream()
                .peek(antennaPair -> System.out.println(antennaPair.antA + " " + antennaPair.antB + " "))
                .flatMap(pair -> {
                    int distance = Math.abs(pair.antA - pair.antB);
                    //antA will always be before antB, because before we sorted positions when creating pairs
                    int antinode1 = pair.antA - distance;
                    int antinode2 = pair.antB + distance;
                    int ant1ModLine = antinode1 % charsInLine;
                    var val1 =
                            //check antinode withing grid size
                            (antinode1 > 0  && antinode1 < lines.length() -1
                                    //check antinode modulo is not outside left or right side
                                    && (pair.antA % charsInLine  > pair.antB % charsInLine
                                        && (antinode1 % charsInLine > pair.antA % charsInLine))

                                    || (pair.antA % charsInLine < pair.antB % charsInLine
                                    && (antinode1 % charsInLine < pair.antA % charsInLine))

                            )
                            ? Optional.of(antinode1) : Optional.empty();
                    var val2 =
                            //check antinode withing grid size
                            (antinode2 > 0  && antinode2 < lines.length() -1
                                    //check antinode modulo is not outside left or right side
                                    && (pair.antA % charsInLine > pair.antB % charsInLine
                                    && (antinode2 % charsInLine < pair.antB % charsInLine))

                                    || (pair.antA % charsInLine < pair.antB % charsInLine
                                    && (antinode2 % charsInLine > pair.antB % charsInLine))

                            )
                            ? Optional.of(antinode2) : Optional.empty();
                    return Stream.of(val1, val2);
                })
                .filter(Optional::isPresent)
                .peek(entry -> System.out.println(" = " + entry.get()))
                .toList();

        System.out.println("resulted antinodes:");
        System.out.println(antinodes);

        return antinodes.stream().distinct().toList().size();
    }

    private static List<AntennaPair> convertAntennasToPairs(Map<Character, Set<Integer>> antennas) {
        List<AntennaPair> antennasPairs = new ArrayList<>();

        antennas.forEach((symbol, positions) -> {
            AtomicInteger skip = new AtomicInteger(1);

            positions.stream().sorted().forEach((antA ->
                    positions
                    .stream().sorted()
                    .skip(skip.getAndIncrement())
                    .peek(antB -> System.out.println("Symbol: " + symbol + " Calculating antinodes for pair: " + antA + " " + antB))
                    .forEach(antB -> antennasPairs.add(new AntennaPair(antA, antB))) //not elegant adding mutable var inside, but oh well..
            ));
        });
        return antennasPairs;
    }

    private static Map<Character, Set<Integer>> getCharacterSetMap(String lines) {
        Map<Character, Set<Integer>> antennas = new HashMap<>();
        IntStream
                .rangeClosed(0, lines.length() -1)
                .forEach(
                        index -> {
                            var symbol = lines.charAt(index);
                            if (symbol == '.') return; //skip non-antennas
                            if (antennas.get(symbol) == null) {
                                //we need to add 1 so we dont start from 0
                                antennas.put(symbol, new HashSet<>(index));
                            }
                            antennas.get(symbol).add(index);
                        }
                );
        return antennas;
    }
}
