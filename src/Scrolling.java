import java.util.Random;

public class Scrolling {
    static final int WIDTH = getTerminalWidth() - 1;
    static final int CAT_HEIGHT = 26;
    static final int CAT_WIDTH = 58;
    static final Random rand = new Random();

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int iterations = 0;
        char[][] nextRows = new char[CAT_HEIGHT][WIDTH]; // store upcoming rows
        for (int i = 0; i < nextRows.length; i++) {
            nextRows[i] = emptyRow();
        }

        while (true) {
            // At each column on the top row, 1% chance to add a new image
            for (int x = 0; x < WIDTH - CAT_WIDTH; x++) {
                if (rand.nextDouble() < 0.01 && isAreaEmpty(nextRows, x)) {
                    loadNextRowsWithImage(nextRows, x);
                }
            }
            // Print and remove the top row
            System.out.println(new String(nextRows[0]));
            // Shift all rows up
            shiftRowsUp(nextRows);
            Thread.sleep(40); // Delay in ms
            long time = System.currentTimeMillis() - startTime;
            iterations++;
            //System.err.println("average time per frame: " + (time / iterations) + " ms");
        }
    }

    private static void loadNextRowsWithImage(char[][] nextRows, int x) {
        char[][] img;
        if (rand.nextBoolean()) {
            img = ScrollCat.getCat();
        } else {
            img = ScrollTotoro.getTotoro();
        }
        for (int iy = 0; iy < img.length; iy++) {
            for (int ix = 0; ix < img[iy].length; ix++) {
                nextRows[iy][x + ix] = img[iy][ix];
            }
        }
    }

    static void shiftRowsUp(char[][] nextRows) {
        for (int i = 1; i < nextRows.length; i++) {
            nextRows[i - 1] = nextRows[i];
        }
        nextRows[nextRows.length - 1] = emptyRow();
    }

    static char[] emptyRow() {
        char[] row = new char[WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            row[i] = ' ';
        }
        return row;
    }

    // Returns true if there's nothing in the space where a new picture would go
    static boolean isAreaEmpty(char[][] nextRows, int x) {
        for (int y = 0; y < CAT_HEIGHT; y++) {
            for (int i = x; i < x + CAT_WIDTH; i++) {
                if (nextRows[y][i] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }


    public static int getTerminalWidth() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return getUnixTerminalWidth();
        } else {
            return 80; // fallback for unknown OS
        }
    }

    private static int getUnixTerminalWidth() {
        try {
            // Try to get terminal size from environment variables first
            String columns = System.getenv("COLUMNS");
            if (columns != null && !columns.isEmpty()) {
                return Integer.parseInt(columns);
            }

            // Fallback to stty command
            ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty size </dev/tty");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
            String output = reader.readLine();
            if (output != null && !output.isEmpty()) {
                String[] parts = output.trim().split(" ");
                return Integer.parseInt(parts[1]); // columns
            }
        } catch (Exception ignored) {
            // Silently ignore errors and fall back to default
        }
        return 80; // fallback
    }
}
