import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day6 {
    public static void main(String[] args) throws IOException {
        System.out.println("The guard has visited "
                + new Day6().solution("res/Day6-sample.txt") + " distinct positions (should be 41)");
        System.out.println("The guard has visited "
                + new Day6().solution("res/Day6.txt") + " distinct positions");
    }

    private long solution(String filename) throws IOException {
        Files.lines(Path.of(filename))
                .forEach(System.out::println);
        return 0;
    }
}
