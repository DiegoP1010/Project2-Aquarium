import edu.cymbal.graphics.CanvasAPI;
import edu.cymbal.graphics.Graphics;
import edu.cymbal.graphics.PainterAPI;
import java.awt.Color;

public class Aquarium {
    public static void main(String[] args) {
        CanvasAPI canvas = Graphics.createCanvas(800, 600, "My Aquarium");
        PainterAPI painter = canvas.getPainter();
        painter.setSpeed(0);

        drawDecorations(painter);

        drawBoxyFish(painter, -200, 40, 100, new Color(255, 215, 80), new Color(100, 170, 80));
        drawBoxyFish(painter, -310, 185, 60, Color.CYAN, Color.BLUE);

        drawTriangleFish(painter, 170, 60, 110, new Color(55, 30, 130));
        drawTriangleFish(painter, 30, 195, 65, Color.ORANGE);

        double offset = 0;
        double step = 3;

        while (true) {
            drawAnimatedBubbles(painter, offset, false);

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                break;
            }

            drawAnimatedBubbles(painter, offset, true);

            offset += step;
            if (offset >= 24 || offset <= -24) {
                step = -step;
            }
        }
    }

   
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

   
    public static void drawCircle(PainterAPI painter, double x, double y, double radius, Color fillColor) {
        drawCircleWithOutline(painter, x, y, radius, fillColor, Color.BLACK);
    }

   
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

   
    public static void drawBoxyFish(PainterAPI painter, double x, double y, double size, Color bodyColor, Color finColor) {
        drawRectangle(painter, x, y, size, size, bodyColor);

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

        
        drawCircle(painter, x + size * 0.22, y - size * 0.25, size * 0.06, Color.BLACK);
    }

  
    public static void drawTriangleFish(PainterAPI painter, double x, double y, double size, Color bodyColor) {
        drawLeftTriangle(painter, x, y, size, bodyColor);
        drawCircle(painter, x + size * 0.28, y + size * 0.08, size * 0.06, Color.WHITE);
    }

   
    public static void drawTreasureChest(PainterAPI painter, double x, double y, double width, double height) {
        drawRectangle(painter, x, y, width, height, new Color(101, 58, 18));

        painter.liftPen();
        painter.moveTo(x, y - height * 0.30);
        painter.setPenColor(Color.BLACK);
        painter.setPenWidth(2.0f);
        painter.lowerPen();
        painter.forward(width);
        painter.liftPen();

        drawRectangle(painter, x + width * 0.44, y - height * 0.22, width * 0.12, height * 0.18, new Color(255, 215, 0));
    }

    
    public static void drawDecorations(PainterAPI painter) {
        drawTreasureChest(painter, 185, -175, 195, 115);

        double[] stalkX = {-60, 15, 90};
        int[] waveSegments = {4, 5, 4};

        painter.setPenColor(new Color(45, 115, 30));
        painter.setPenWidth(4.5f);

        for (int s = 0; s < stalkX.length; s++) {
            painter.liftPen();
            painter.moveTo(stalkX[s], -298);
            painter.lowerPen();

            painter.turnLeft(90);

            for (int w = 0; w < waveSegments[s]; w++) {
                painter.turnLeft(35);
                painter.forward(45);
                painter.turnRight(70);
                painter.forward(45);
                painter.turnLeft(35);
            }

            painter.turnRight(90);
            painter.liftPen();
        }
    }

    
    public static void drawAnimatedBubbles(PainterAPI painter, double verticalOffset, boolean erase) {
        Color fillColor = erase ? Color.WHITE : new Color(205, 225, 245);
        Color outlineColor = erase ? Color.WHITE : Color.BLACK;
        double radius = erase ? 29 : 25;

        double[] bubbleX = {-250, -320, -10, 310};
        double[] bubbleBaseY = {210, -80, 230, 210};

        for (int i = 0; i < 4; i++) {
            double direction = (i % 2 == 0) ? 1.0 : -1.0;
            drawCircleWithOutline(painter, bubbleX[i], bubbleBaseY[i] + (verticalOffset * direction), radius, fillColor, outlineColor);
        }
    }
}
