import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {
    public static void main(String[] args) throws IOException {
        System.out.println("sum of the sample equations is: "
                + new Day7().solution("res/Day7-sample.txt") + " Does it match 3749 ?");

        System.out.println("sum of the sample equations is: "
                + new Day7().solution("res/Day7.txt"));
    }

    record Equation (long result, List<Integer> values)
    {
        static Equation buildEquation(String line) {
            return new Equation(
                    Long.parseLong(line.split(": ")[0]),
                    Arrays.stream(line.split(": ")[1].split(" "))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()
                            )
            );
        }

        static long isCorrect(Equation eq) {
            if (calculatePart2(eq, 0) == true) {
                return eq.result;
            }
            return 0;
        }
        // we will use recursion to compute equation and get values
        static private boolean calculate(Equation equationValues, long subResult) {

            if (equationValues.values.size() == 0) {
                return equationValues.result == subResult;
            }
            Equation remainingEquation = new Equation(equationValues.result, equationValues.values.subList(1, equationValues.values.size()));
            long nextVal = equationValues.values().get(0);

            return calculate(remainingEquation, subResult + nextVal)
                    || calculate(remainingEquation, subResult * nextVal);
        }

        //part 2, just slightly different
        static private boolean calculatePart2(Equation equationValues, long subResult) {

            if (equationValues.values.isEmpty()) {
                return equationValues.result == subResult;
            }
            Equation remainingEquation = new Equation(equationValues.result, equationValues.values.subList(1, equationValues.values.size()));
            long nextVal = equationValues.values().getFirst();

            return calculatePart2(remainingEquation, subResult + nextVal)
                    || calculatePart2(remainingEquation, subResult * nextVal)
                    || calculatePart2(remainingEquation, mergeNumbers(subResult, nextVal));
        }

        static private long mergeNumbers( long a, long b) {
            return Long.parseLong(String.valueOf(a) + String.valueOf(b));
        }

        @Override
        public String toString() {
            return "Equation{" +
                    "result=" + result +
                    ", values=" + values +
                    '}';
        }
    }

    private long solution(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .map(Equation::buildEquation)
                .mapToLong(Equation::isCorrect)
                .sum();

    }
}
