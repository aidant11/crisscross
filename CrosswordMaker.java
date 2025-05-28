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
        // First word: place in the center horizontally
        if (placedWords.isEmpty()) {
            //int row = size / 2;
            //int col = (size - word.length()) / 2;
            int row = 1;
            int col = 1;
            if (canPlaceHorizontally(word, row, col)) {
                for (int i = 0; i < word.length(); i++) {
                    grid[row][col + i] = word.charAt(i);
                }
                placedWords.add(word + " at (" + row + ", " + col + ") H");
                return true;
            }
            return false;
        }

        // Try to find a place where it can cross with an existing word
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                char c = grid[row][col];
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == c) {
                        // Try placing horizontally
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

                        // Try placing vertically
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
        for (int i = 0; i < word.length(); i++) {
            char current = grid[row][col + i];
            if (current != '.' && current != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean canPlaceVertically(String word, int row, int col) {
        for (int i = 0; i < word.length(); i++) {
            char current = grid[row + i][col];
            if (current != '.' && current != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public void printGrid() {
        for (char[] row : grid) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    public void printPlacedWords() {
        for (String word : placedWords) {
            System.out.println(word);
        }
    }

    public static void main(String[] args) {
        CrosswordMaker crossword = new CrosswordMaker(25);
        String[] words = {"Apple", "Apricot", "Avocado", "Banana", "Blueberry", "Cherry", "Coconut", "Cranberry", "Date", "Fig", "Grape", "Grapefruit", "Guava", "Kiwi", "Lemon", "Lime", "Mango", "Orange", "Papaya", "Peach", "Pear", "Pineapple", "Plum", "Pomegranate", "Raspberry", "Strawberry", "Watermelon"};

        for (String word : words) {
            if (!crossword.placeWord(word)) {
                System.out.println("Failed to place: " + word);
            }
        }

        System.out.println("\nCrossword Grid:");
        crossword.printGrid();
        System.out.println("\nPlaced Words:");
        crossword.printPlacedWords();
    }
}

