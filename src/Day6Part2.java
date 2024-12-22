import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Day6Part2 {

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

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Step step = (Step) o;
            return pos == step.pos && dir == step.dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, dir);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("The guard can fall into "
                + new Day6Part2().solution("res/Day6-sample.txt") + " loops (should be 6)");
        System.out.println("The guard can fall into "
                + new Day6Part2().solution("res/Day6.txt") + " loops");
    }

    private long solution(String filename) throws IOException {

        int gridWidth = Files.lines(Path.of(filename)).findFirst().get().length();

        List<Field> labirynth = Files
                .lines(Path.of(filename))
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
                .toList();

        return IntStream
                .range(0, labirynth.size())
                .mapToLong(labirynthCellIndex -> walkLabyrinth(labirynth, gridWidth, labirynthCellIndex))
                .sum();
        //1852 too high
    }

    //return 0 if path cannot be looped
    private int walkLabyrinth(List<Field> labirynth, int labyrinthWidth, int extraObstructionAtPosition) {
        int startPosition = labirynth.indexOf(Field.START);
        Direction startDirection = Direction.UP;

        Step step = new Step(startPosition, startDirection);

        List<Field> moddedLabirynth = new ArrayList<>(labirynth);

        if (labirynth.get(extraObstructionAtPosition).equals(Field.EMPTY)) {
            moddedLabirynth.set(extraObstructionAtPosition, Field.OBSTRUCTION);
        } else {
            return 0;
        }

        //Before we start walk we need to add mechanism to detect looping
        //here I add set of steps, if step is already in the set, then we are in the loop
        Set<Step> uniqueSteps = new HashSet<>();
        int duplicatedStepsCount = 1000; //assume 1000 repeated steps means we are in the loop
        do {
            if (uniqueSteps.add(step) != true) { //for this to work I needed equals and hashset methods for inner class
//                System.out.println("starting to repeat steps, exiting..");
                if (duplicatedStepsCount-- < 0) {
//                    System.out.println("1k repeated steps" + duplicatedStepsCount);
                    return 1; //step repeated 1000 times, must be loop..
                }
            }
//            System.out.println("Walking: " +step.pos + " dir: " +step.dir);
            step = walkIt(moddedLabirynth, labyrinthWidth, step);

        } while ( step != null);

        return 0;//no loop detected
    }

    /*
        Initially it was recursive function until I got stack overflow exception
     */
    private Step walkIt(List<Field> labirynth, int labyrinthWidth, Step step) {
//        System.out.println("Walking position: " + step.pos);
        if(step.dir == Direction.LEFT && step.pos % labyrinthWidth == labyrinthWidth - 1) return null; //left map from left
        if(step.dir == Direction.RIGHT && step.pos % labyrinthWidth == 0) return null; //left map from right

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
//            System.out.println("Out of map");
            return null;
        }
    }

    // Part 2 - quickest to implement brute force, add obstruction at position, and add steps to set,
    // once duplicated step is found it must be a loop

}
