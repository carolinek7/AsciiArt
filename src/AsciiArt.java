public class AsciiArt {
    char[][] img;
    int height;
    int width;

    // constructor
    public AsciiArt(char[][] img) {
        this.img = img;
        this.height = img.length;
        this.width = img[0].length;
    }

    public AsciiArt(String art) {
        this.processArt(art);
    }

    // generate new copy from original already processed
    public AsciiArt(AsciiArt original) {
        this.img = original.img;
        this.height = original.height;
        this.width = original.width;
    }

    private void processArt(String art) {
        String[] lines = art.split("\n");
        this.height = lines.length;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > this.width) {
                this.width = lines[i].length(); // max width!
            }
        }
        this.img = new char[height][width];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                img[i][j] = lines[i].charAt(j);
            }
        }
        // fill all zeroes with empty space
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (img[i][j] == 0) {
                    img[i][j] = ' ';
                } // will not allow overlap.
            }
        }
    }

    public void resize(int width2) {
        // only resizing upwards for now
        // add some columns, only worth repeating the pattern n times if possible.
        if (width2 / width > 1) {
            char[][] newImg = new char[height][width2];
            for (int i = 0; i < height; i++) {
                // copy existing pattern only if it fits
                for (int j = 0; j < width2 && j / width < (width2 / width); j++) {
                    newImg[i][j] = img[i][j % width]; // next to itself
                }
                // #3. fill the rest with empty space if the image is narrower than IMAGE_WIDTH
                for (int j = (width2 / width) * width; j < width2; j++) {
                    newImg[i][j] = ' ';
                }
            }
            this.img = newImg;
            this.width = width2;
        }

    }

    char[][] getImg() {
        return this.img;
    }

    AsciiArt switchRowsAndCols() {
        char[][] newImg = new char[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newImg[j][i] = img[i][j];
            }
        }
        this.img = newImg;
        int temp = this.width;
        this.width = this.height;
        this.height = temp;
        return this;
    }

    static AsciiArt getWidestArt(AsciiArt[] asciis) {
        if (asciis.length == 0) {
            return null;
        }
        AsciiArt widest = asciis[0];
        for (AsciiArt art : asciis) {
            if (art.width > widest.width) {
                widest = art;
            }
        }
        return widest;
    }

    // really only for debugging
    private void printArt() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.img[i][j]);
            }
            System.out.println();
        }
    }
}