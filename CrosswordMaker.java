import java.util.*;

public class CrosswordMaker {

    private final int size;
    private final char[][] grid;
    private final List<String> placedWords = new ArrayList<>();

    public CrosswordMaker(int size) {
        this.size = size;
        this.grid = new char[size][size];
        for (char[] row : grid) {
            Arrays.fill(row, '.');
        }
    }

    public boolean placeWord(String word) {
        // First word: place it in the middle horizontally
        if (placedWords.isEmpty()) {
            int row = size / 2;
            int col = (size - word.length()) / 2;
            if (canPlaceHorizontally(word, row, col)) {
                for (int i = 0; i < word.length(); i++) {
                    grid[row][col + i] = word.charAt(i);
                }
                placedWords.add(word + " at (" + row + ", " + col + ") H");
                return true;
            }
            return false;
        }

        // Try to cross with already placed letters
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                char c = grid[row][col];
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == c) {
                        
                        // Try horizontal placement
                        int startCol = col - i;
                        if (startCol >= 0 && startCol + word.length() <= size) {
                            if (canPlaceHorizontally(word, row, startCol)) {
                                for (int j = 0; j < word.length(); j++) {
                                    grid[row][startCol + j] = word.charAt(j);
                                }
                                placedWords.add(word + " at (" + row + ", " + startCol + ") H");
                                return true;
                            }
                        }

                        // Try vertical placement
                        int startRow = row - i;
                        if (startRow >= 0 && startRow + word.length() <= size) {
                            if (canPlaceVertically(word, startRow, col)) {
                                for (int j = 0; j < word.length(); j++) {
                                    grid[startRow + j][col] = word.charAt(j);
                                }
                                placedWords.add(word + " at (" + startRow + ", " + col + ") V");
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean canPlaceHorizontally(String word, int row, int col) {
        // Check left buffer
        if (col > 0 && grid[row][col - 1] != '.') return false;

        // Check right buffer
        if (col + word.length() < size && grid[row][col + word.length()] != '.') return false;

        for (int i = 0; i < word.length(); i++) {
            char current = grid[row][col + i];

            // Conflict with a different letter
            if (current != '.' && current != word.charAt(i)) return false;

            // Avoid adjacent words unless it's a crossing letter
            if (current == '.') {
                if (row > 0 && grid[row - 1][col + i] != '.') return false;
                if (row < size - 1 && grid[row + 1][col + i] != '.') return false;
            }
        }

        return true;
    }

    private boolean canPlaceVertically(String word, int row, int col) {
        // Check top buffer
        if (row > 0 && grid[row - 1][col] != '.') return false;

        // Check bottom buffer
        if (row + word.length() < size && grid[row + word.length()][col] != '.') return false;

        for (int i = 0; i < word.length(); i++) {
            char current = grid[row + i][col];

            // Conflict with a different letter
            if (current != '.' && current != word.charAt(i)) return false;

            // Avoid adjacent words unless it's a crossing letter
            if (current == '.') {
                if (col > 0 && grid[row + i][col - 1] != '.') return false;
                if (col < size - 1 && grid[row + i][col + 1] != '.') return false;
            }
        }

        return true;
    }

    public void printGrid() {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public void printPlacedWords() {
        for (String word : placedWords) {
            System.out.println(word);
        }
    }

    // Sample driver
    public static void main(String[] args) {
        CrosswordMaker maker = new CrosswordMaker(30);

        List<String> words = Arrays.asList("apartment", "breeze", "cascade", "dawn", "emerald", "flicker", "glimpse", "harvest", "illusion", "jewel", "kindle", "luminous", "marvel", "nectar", "omega", "puzzle", "quartz", "ripple", "symphony", "twilight", "umbrella", "vortex", "whisper", "xenon", "yonder", "zenith", "orbit", "fusion", "cascade");

        for (String word : words) {
            if (!maker.placeWord(word)) {
                System.out.println("Could not place: " + word);
            }
        }

        System.out.println("\nCrossword Grid:");
        maker.printGrid();

        System.out.println("\nPlaced Words:");
        maker.printPlacedWords();
    }
}

