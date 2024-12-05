import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day5 {
    public static void main(String[] args) throws IOException {
        System.out.println("The sum of correct odrers middle pages is (sample: 143): "
                +  new Day5().solution("res/Day5-sample.txt"));
    }

    private long solution(String filename) throws IOException {
        Files.lines(Path.of(filename))
                .forEach(System.out::println);
        return 0;
    }
}
