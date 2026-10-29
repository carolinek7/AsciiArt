import java.util.Random;

public class Scrolling2 {
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
        String snoopy = """
                        ,_____    
                    ____/     \\   
                   /     0     )  
                 o|        |## |  
                   \\_______(## )  
                        \\__|---   
                        /  |      
                        |    \\     
                        |   |#|    
                        \\  \\ /--- 
                       _|| |-\\--)
                      /----  |     
                      \\------/     
                    JZ
                """;
        AsciiArt s = new AsciiArt (snoopy);
        AsciiArt[] images = { new AsciiArt(ScrollCat.getCat()), new AsciiArt(ScrollTotoro.getTotoro()), s, new AsciiArt (getMiffy()), new AsciiArt (getWoodstock()), new AsciiArt(peppaPrincess())};
        AsciiArt art = images[rand.nextInt(images.length)];
        AsciiArt widestArt = AsciiArt.getWidestArt(images);
        art.resize(widestArt.width);

        for (int iy = 0; iy < art.height; iy++) { // height
            for (int ix = 0; ix < art.width; ix++) { // width
                nextRows[iy][x + ix] = art.img[iy][ix];
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

    static char[][] getMiffy() {
        char[][]img=new char [22][23];
        for(int y=0; y<22; y++ ){
            for(int x=0;x<23;x++){
                img[y][x]=' ';
            }
        }

        // ears
        img[1][7] = '_';
        img[2][6] = '/';
        img[2][8] = '\\';
        img[3][5] = '(';
        img[3][9] = ')';
        img[4][5] = '|';
        img[4][9] = '|';
        img[5][5] = '|';
        img[5][9] = '|';

        img[1][16] = '_';
        img[2][15] = '/';
        img[2][17] = '\\';
        img[3][14] = '(';
        img[3][18] = ')';
        img[4][14] = '|';
        img[4][18] = '|';
        img[5][14] = '|';
        img[5][18] = '|';

        img[5][10] = '_';
        img[5][11] = '_';
        img[5][12] = '_';
        img[5][13] = '_';

        // head

        img[6][4] = '/';
        img[7][3] = '/';
        img[8][2] = '/';
        img[6][19] = '\\';
        img[7][20] = '\\';
        img[8][21] = '\\';

        img[9][2] = '|';
        img[9][21] = '|';

        img[10][3] = '\\';
        img[11][4] = '\\';
        img[10][20] = '/';
        img[11][19] = '/';

        for(int i = 5; i <= 18; i++){
            img[11][i] = '_';
        }

        // arms and body

        img[12][5] = '/';
        img[13][4] = '/';
        img[14][3] = '/';
        img[15][2] = '/';
        img[16][2] = '\\';
        img[17][3] = '\\';

        img[12][18] = '\\';
        img[13][19] = '\\';
        img[14][20] = '\\';
        img[15][21] = '\\';
        img[16][21] = '/';
        img[17][20] = '/';

        img[16][4] = '|';
        img[17][4] = '|';
        img[18][4] = '|';
        img[16][19] = '|';
        img[17][19] = '|';
        img[18][19] = '|';

        for(int i = 5; i <= 18; i++){
            img[18][i] = '_';
        }

        // feet

        img[19][8] = '/';
        img[19][12] = '|';
        img[19][16] = '\\';

        img[19][9] = '_';
        img[19][10] = '_';
        img[19][11] = '_';
        img[19][13] = '_';
        img[19][14] = '_';
        img[19][15] = '_';

        // face

        img[8][7] = '@';
        img[8][16] = '@';
        img[9][11] = '\\';
        img[10][12] = '\\';
        img[9][12] = '/';
        img[10][11] = '/';

        // signature
        img[21][4] = 'E';
        img[21][5] = 'Y';
        return img;
    }

    static char[][] getWoodstock() {
        char[][] img = new char[10][10];

        for (int y = 0; y < img[0].length; y++) {
            for (int x = 0; x < img.length; x++) {
                img[x][y] = ' ';
            }
        }

        img[2][2] = '_';
        img[2][3] = '(';
        img[2][4] = '\\';
        img[2][5] = '(';
        img[2][6] = '_';
        img[3][2] = '<';
        img[3][5] = '\'';
        img[3][6] = '_';
        img[3][7] = ')';
        img[4][3] = ')';
        img[4][5] = '(';
        img[5][2] = '<';
        img[5][3] = '/';
        img[5][6] = ')';
        img[6][4] = '√';
        img[6][5] = '√';
        img[7][7] = 'S';
        img[7][8] = 'C';


        return img;
    }

    public static char[][] peppaPrincess() {
            char[][] img = new char[25][30];
            for(int y = 0; y < 25; y++) {
                for(int x = 0; x < 30; x++) {
                    img[y][x] = ' ';
                }
            }
            img[14][0] = '_';
            img[15][1] = '/';
            img[14][2] = '/';
            img[15][2] = '\\';
            img[6][3] = '|';
            img[13][3] = '/';
            img[5][4] = '/';
            img[7][4] = '\\';
            img[12][4] = '/';
            img[13][4] = '|';
            img[14][4] = '|';
            img[15][4] = '|';
            img[16][4] = '|';
            img[17][4] = '|';
            img[5][5] = 'o';
            img[7][5] = '\\';
            img[11][5] = '/';
            img[17][5] = '_';
            img[8][6] = '|';
            img[17][6] = '_';
            img[4][7] = '-';
            img[5][7] = 'o';
            img[7][7] = '/';
            img[9][7] = '\\';
            img[10][7] = '/';
            img[17][7] = '_';
            img[20][7] = '=';
            img[0][8] = '`';
            img[1][8] = '\\';
            img[4][8] = '-';
            img[6][8] = '/';
            img[10][8] = '\\';
            img[17][8] = '_';
            img[20][8] = '=';
            img[2][9] = '\\';
            img[4][9] = '-';
            img[9][9] = '\\';
            img[17][9] = '_';
            img[18][9] = '|';
            img[19][9] = '|';
            img[20][9] = '|';
            img[1][10] = '`';
            img[2][10] = 'o';
            img[3][10] = '\\';
            img[4][10] = '-';
            img[9][10] = '_';
            img[10][10] = '_';
            img[17][10] = '_';
            img[2][11] = '`';
            img[4][11] = '-';
            img[5][11] = '0';
            img[9][11] = '_';
            img[10][11] = '_';
            img[17][11] = '_';
            img[2][12] = '/';
            img[4][12] = '-';
            img[9][12] = '_';
            img[10][12] = '_';
            img[17][12] = '_';
            img[1][13] = '/';
            img[2][13] = 'o';
            img[4][13] = '-';
            img[7][13] = '(';
            img[9][13] = '/';
            img[10][13] = '_';
            img[17][13] = '_';
            img[0][14] = '`';
            img[1][14] = '`';
            img[4][14] = '-';
            img[5][14] = '0';
            img[6][14] = '_';
            img[7][14] = '_';
            img[10][14] = '_';
            img[17][14] = '_';
            img[20][14] = '=';
            img[2][15] = '`';
            img[4][15] = '-';
            img[6][15] = '_';
            img[7][15] = '_';
            img[10][15] = '_';
            img[17][15] = '_';
            img[20][15] = '=';
            img[2][16] = 'o';
            img[3][16] = '/';
            img[4][16] = '-';
            img[7][16] = ')';
            img[17][16] = '_';
            img[18][16] = '|';
            img[19][16] = '|';
            img[20][16] = '|';
            img[1][17] = '/';
            img[2][17] = '/';
            img[5][17] = '`';
            img[10][17] = '/';
            img[17][17] = '_';
            img[0][18] = '`';
            img[1][18] = '/';
            img[6][18] = '\\';
            img[9][18] = '/';
            img[10][18] = '\\';
            img[17][18] = '_';
            img[7][19] = '\\';
            img[8][19] = '|';
            img[17][19] = '_';
            img[11][20] = '\\';
            img[17][20] = '_';
            img[12][21] = '\\';
            img[13][21] = '|';
            img[14][21] = '|';
            img[15][21] = '|';
            img[16][21] = '|';
            img[17][21] = '|';
            img[13][22] = '\\';
            img[16][22] = '%';
            img[14][23] = '\\';
            img[15][23] = '/';
            img[16][23] = '%';
            img[15][24] = '\\';
            img[14][25] = '_';
            img[23][28] = 'E';
            img[23][29] = 'W';

        return img;
    }

}
