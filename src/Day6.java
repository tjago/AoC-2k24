import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class Day6 {
    public static void main(String[] args) throws IOException {
        System.out.println("The guard has visited "
                + new Day6().solution("res/Day6-sample.txt") + " distinct positions (should be 41)");
        System.out.println("The guard has visited "
                + new Day6().solution("res/Day6.txt") + " distinct positions");
    }

    private long solution(String filename) throws IOException {

        int gridWidth  = Files.lines(Path.of(filename)).findAny().get().length();
        int gridHeight = (int) Files.lines(Path.of(filename)).count();
        enum FIELD_STATUS { EMPTY, VISITED, OBSTRUCTION}
        FIELD_STATUS[][] grid = new FIELD_STATUS[gridWidth][gridHeight];

//        IntStream
//                .range(0, gridWidth)
//                .forEach(x -> IntStream
//                        .range(0, gridHeight)
//                        .forEach(y -> {
//                            Files.lines(Path.of(filename)).
//                            switch ("dupa") {
//                                case "dupa":
//
//                            }
//                            grid[x][y] = 0;
//                        }));

        Files.lines(Path.of(filename))
                .forEach(System.out::println);
        return 0;
    }
}
