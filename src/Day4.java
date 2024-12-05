import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day4 {
    public static void main(String[] args) throws IOException {
        System.out.println("XMAS words in Sample ( should be 18): " + new Day4().solution("res/Day4-sample.txt"));
    }

    private long solution(String filename) throws IOException {

        Files.lines(Path.of(filename))
                .forEach(System.out::println);

        return 0;
    }
}
