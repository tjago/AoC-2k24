public class Day8 {
    public static void main(String[] args) {
        System.out.println("Sum of unique antinodes: "
                + new Day8().solution("res/Day8-sample.txt") + " should be 14");

    }
    /** pseudocode

     1. put antenna's on the grid - parse input file
        grid would be represented by a long list
     2. pairs would be lists of parsed grid, each list of diff antennas
        in a stream the list would be mapped to permuation of pairs.
     3. pairs by pairs would calcualate distance between antenas, let say 50 and 61
     4. then take origin position of antenna, ex. 50 and antinode 39,
        since we know that 50 mod 10 is < than 61 mod 10, we expect opposite
        50 mod 10 > 39 mod 10 but it's not true so it means it is out of grid
     5. new list of unique posiions will be a result.


     */
    private String solution(String s) {
        return null; //TODO implement me, I'm too lazy
    }
}
