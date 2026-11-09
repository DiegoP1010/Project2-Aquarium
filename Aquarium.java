import edu.cymbal.graphics.CanvasAPI;
import edu.cymbal.graphics.Graphics;
import edu.cymbal.graphics.PainterAPI;
import java.awt.Color;

public class Aquarium {
    public static void main(String[] args) {
        CanvasAPI canvas = Graphics.createCanvas(800, 600, "My Aquarium");
        PainterAPI painter = canvas.getPainter();
        painter.setSpeed(0);

        // 1. Draw Decorations (Vertical Tall Seaweed & Simple Treasure Chest)
        drawDecorations(painter);

        // 2. Draw Creature Type 1: Two Boxy Fish (different sizes & colors)
        drawBoxyFish(painter, -200, 40, 100, new Color(255, 215, 80), new Color(100, 170, 80));
        drawBoxyFish(painter, -310, 185, 60, Color.CYAN, Color.BLUE);

        // 3. Draw Creature Type 2: Two Triangle Fish (different sizes & colors)
        drawTriangleFish(painter, 170, 60, 110, new Color(55, 30, 130));
        drawTriangleFish(painter, 30, 195, 65, Color.ORANGE);

        // 4. Animate the 4 bubbles moving up and down without flashing
        double offset = 0;
        double step = 3;

        while (true) {
            drawAnimatedBubbles(painter, offset, false);

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                break;
            }

            // Erase old bubble positions cleanly before drawing the next frame
            drawAnimatedBubbles(painter, offset, true);

            offset += step;
            if (offset >= 24 || offset <= -24) {
                step = -step;
            }
        }
    }

    /**
     * Shape Method 1: Draws a filled rectangle with top-left corner at (x, y).
     */
    public static void drawRectangle(PainterAPI painter, double x, double y, double width, double height, Color fillColor) {
        painter.liftPen();
        painter.moveTo(x, y);
        painter.setPenColor(Color.BLACK);
        painter.setPenWidth(1.5f);
        painter.lowerPen();
        painter.startFill(fillColor);

        for (int i = 0; i < 2; i++) {
            painter.forward(width);
            painter.turnRight(90);
            painter.forward(height);
            painter.turnRight(90);
        }

        painter.finishFill();
        painter.liftPen();
    }

    /**
     * Shape Method 2: Draws a filled left-pointing triangle starting at its left tip (x, y).
     */
    public static void drawLeftTriangle(PainterAPI painter, double x, double y, double size, Color fillColor) {
        painter.liftPen();
        painter.moveTo(x, y);
        painter.setPenColor(Color.BLACK);
        painter.setPenWidth(1.5f);
        painter.lowerPen();
        painter.startFill(fillColor);

        painter.turnLeft(30);
        for (int i = 0; i < 3; i++) {
            painter.forward(size);
            painter.turnRight(120);
        }
        painter.turnRight(30);

        painter.finishFill();
        painter.liftPen();
    }

    /**
     * Shape Method 3: Draws a filled circle centered at (x, y).
     */
    public static void drawCircle(PainterAPI painter, double x, double y, double radius, Color fillColor) {
        drawCircleWithOutline(painter, x, y, radius, fillColor, Color.BLACK);
    }

    /**
     * Helper Method: Draws a filled circle with a custom outline color.
     */
    public static void drawCircleWithOutline(PainterAPI painter, double x, double y, double radius, Color fillColor, Color outlineColor) {
        painter.liftPen();
        painter.moveTo(x, y);
        painter.setPenColor(outlineColor);
        painter.setPenWidth(1.5f);
        painter.lowerPen();
        painter.startFill(fillColor);

        painter.circle(radius);

        painter.finishFill();
        painter.liftPen();
    }

    /**
     * Creature Method 1: Draws a boxy fish using drawRectangle and drawCircle.
     */
    public static void drawBoxyFish(PainterAPI painter, double x, double y, double size, Color bodyColor, Color finColor) {
        // Square body
        drawRectangle(painter, x, y, size, size, bodyColor);

        // Angled side fin
        painter.liftPen();
        painter.moveTo(x + size * 0.42, y - size * 0.65);
        painter.setPenColor(Color.BLACK);
        painter.lowerPen();
        painter.startFill(finColor);
        painter.turnRight(30);
        for (int i = 0; i < 2; i++) {
            painter.forward(size * 0.14);
            painter.turnRight(90);
            painter.forward(size * 0.28);
            painter.turnRight(90);
        }
        painter.turnLeft(30);
        painter.finishFill();
        painter.liftPen();

        // Eye
        drawCircle(painter, x + size * 0.22, y - size * 0.25, size * 0.06, Color.BLACK);
    }

    /**
     * Creature Method 2: Draws a triangle fish using drawLeftTriangle and drawCircle.
     */
    public static void drawTriangleFish(PainterAPI painter, double x, double y, double size, Color bodyColor) {
        drawLeftTriangle(painter, x, y, size, bodyColor);
        drawCircle(painter, x + size * 0.28, y + size * 0.08, size * 0.06, Color.WHITE);
    }

    /**
     * Decoration Method 1: Draws a simple brown treasure chest with a lid line and gold latch.
     */
    public static void drawTreasureChest(PainterAPI painter, double x, double y, double width, double height) {
        // Main brown chest box
        drawRectangle(painter, x, y, width, height, new Color(101, 58, 18));

        // Horizontal lid line across the upper part of the chest
        painter.liftPen();
        painter.moveTo(x, y - height * 0.30);
        painter.setPenColor(Color.BLACK);
        painter.setPenWidth(2.0f);
        painter.lowerPen();
        painter.forward(width);
        painter.liftPen();

        // Small gold square latch in the center
        drawRectangle(painter, x + width * 0.44, y - height * 0.22, width * 0.12, height * 0.18, new Color(255, 215, 0));
    }

    /**
     * Decoration Method 2: Draws 3 vertical wavy green seaweed stalks and calls drawTreasureChest.
     */
    public static void drawDecorations(PainterAPI painter) {
        // Simple treasure chest in the bottom-right corner
        drawTreasureChest(painter, 185, -175, 195, 115);

        // Loop 1: Draw 3 tall vertical seaweed stalks between the yellow square fish and purple triangle fish
        double[] stalkX = {-60, 15, 90};
        int[] waveSegments = {4, 5, 4};

        painter.setPenColor(new Color(45, 115, 30));
        painter.setPenWidth(4.5f);

        for (int s = 0; s < stalkX.length; s++) {
            painter.liftPen();
            painter.moveTo(stalkX[s], -298);
            painter.lowerPen();

            // Turn 90 degrees left so the turtle faces straight up (North)
            painter.turnLeft(90);

            for (int w = 0; w < waveSegments[s]; w++) {
                painter.turnLeft(35);
                painter.forward(45);
                painter.turnRight(70);
                painter.forward(45);
                painter.turnLeft(35);
            }

            // Reset heading back to East (0 degrees) for future shapes
            painter.turnRight(90);
            painter.liftPen();
        }
    }

    /**
     * Draws (or erases) the 4 light-blue bubbles that bob up and down.
     */
    public static void drawAnimatedBubbles(PainterAPI painter, double verticalOffset, boolean erase) {
        Color fillColor = erase ? Color.WHITE : new Color(205, 225, 245);
        Color outlineColor = erase ? Color.WHITE : Color.BLACK;
        double radius = erase ? 29 : 25;

        double[] bubbleX = {-250, -320, -10, 310};
        double[] bubbleBaseY = {210, -80, 230, 210};

        // Loop 2: Draw each of the 4 bubbles with alternating up/down motion
        for (int i = 0; i < 4; i++) {
            double direction = (i % 2 == 0) ? 1.0 : -1.0;
            drawCircleWithOutline(painter, bubbleX[i], bubbleBaseY[i] + (verticalOffset * direction), radius, fillColor, outlineColor);
        }
    }
}
