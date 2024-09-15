package org.aldanari.asciiinc;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameScreen extends TextArea {

    private final Integer xSize;
    private final Integer ySize;

    public GameScreen(int fontSize) {
        super();

        this.setEditable(false);
        this.setWrapText(false);
        this.setFont(Font.font("Monospaced", fontSize));
        this.setId("GameScreen");

        this.xSize = getCharsPerLine();
        this.ySize = getLinePerHeigth();
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    private int getCharsPerLine() {
        double width = this.getWidth();

        Text text = new Text("W");
        text.setFont(this.getFont());
        double charWidth = text.getLayoutBounds().getWidth();

        return  (int) (width / charWidth);
    }

    private int getLinePerHeigth() {
        double heigth = this.getHeight();

        Text text = new Text("W");
        text.setFont(this.getFont());
        double charHeight = text.getLayoutBounds().getHeight();

        return  (int) (heigth / charHeight);
    }

    public void fillWithRandom() {
        int xRange = this.getXSize();
        int yRange = this.getYSize();

        StringBuilder text = new StringBuilder();
        for (int i = 0; i < yRange; i++) {
            for (int j = 0; j < xRange; j++) {
                double f = Math.random() / Math.nextDown(1f);
                int randomCode = (int) (32 * (1f - f) + 125 * f);
                text.append((char) randomCode);
            }
            if (i < yRange - 1) {
                text.append("\n");
            }
        }
        this.setText(text.toString());
    }
}
