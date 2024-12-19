import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day6 {

    private enum Field {
        EMPTY(46), VISITED(1), OBSTRUCTION(35), START(94);

        final long charNumber;

        Field(long charNum) {
            this.charNumber = charNum;
        }
    }

    private enum Direction {LEFT, UP, RIGHT, DOWN}

    private class Step {
        int pos;
        Direction dir;

        Step(int position, Direction direction) {
            this.pos = position;
            this.dir = direction;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("The guard has visited "
                + new Day6().solution("res/Day6-sample.txt") + " distinct positions (should be 41)");
        System.out.println("The guard has visited "
                + new Day6().solution("res/Day6.txt") + " distinct positions");
    }

    private long solution(String filename) throws IOException {

        int gridWidth = Files.lines(Path.of(filename)).findFirst().get().length();

        List<Field> labirynth = Files
                .lines(Path.of(filename))
//                .peek(System.out::println)
                .flatMapToInt(line -> line.chars())
                .mapToObj(charNumber -> {
                    switch (charNumber) {
                        case 35:
                            return Field.OBSTRUCTION;
                        case 94:
                            return Field.START;
                        default:
                            return Field.EMPTY;
                    }
                })
//                .peek(System.out::println)
                .toList();

        Set<Integer> positionsVisited = new HashSet<>();
        walkLabyrinth(labirynth, gridWidth, positionsVisited);
        return positionsVisited.size();
    }
    private void walkLabyrinth(List<Field> labirynth, int labyrinthWidth, Set<Integer> positionsVisited) {
        int startPosition = labirynth.indexOf(Field.START);
        Direction startDirection = Direction.UP;

        Step step = new Step(startPosition, startDirection);
        do {
            step = walkIt(labirynth, labyrinthWidth, positionsVisited, step);
        } while ( step != null);
    }

    /*
        Initially it was recursive function until I got stack overflow exception
     */
    private Step walkIt(List<Field> labirynth, int labyrinthWidth, Set<Integer> posVisited, Step step) {
//        System.out.println("Walking position: " + step.pos);
        if(step.dir == Direction.LEFT && step.pos % labyrinthWidth == labyrinthWidth - 1) return null; //left map from left
        if(step.dir == Direction.RIGHT && step.pos % labyrinthWidth == 0) return null; //left map from right

        posVisited.add(step.pos);
        //if obstructed change direction
        try {
            switch (step.dir) {
                case UP -> {
                    if (!labirynth.get(step.pos - labyrinthWidth).equals(Field.OBSTRUCTION)) {
                        return new Step(step.pos - labyrinthWidth, Direction.UP);
                    } else return new Step(step.pos + 1, Direction.RIGHT);
                }
                case RIGHT -> {
                    if (!labirynth.get(step.pos + 1).equals(Field.OBSTRUCTION)) {
                        return new Step(step.pos +1, Direction.RIGHT);
                    } else
                        return new Step(step.pos + labyrinthWidth, Direction.DOWN);
                }
                case DOWN -> {
                    if (!labirynth.get(step.pos + labyrinthWidth).equals(Field.OBSTRUCTION)) {
                        return new Step(step.pos + labyrinthWidth, Direction.DOWN);
                    } else return new Step(step.pos - 1, Direction.LEFT);
                }
                case LEFT -> {
                    if (!labirynth.get(step.pos - 1).equals(Field.OBSTRUCTION)) {
                        return new Step(step.pos - 1, Direction.LEFT);
                    } else
                        return new Step(step.pos - labyrinthWidth, Direction.UP);
                }
                default -> {
                    throw new RuntimeException("Not recognised Direction");
                }
            }
        } catch(IndexOutOfBoundsException ex) {
            System.out.println("Out of map");
            return null;
        }
    }

}
