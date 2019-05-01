package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

public class Graphics {
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Image sourceImage;
    private Image background;
    private Graph graph;

    public Graphics(Canvas canvas, Image image, Graph graph, boolean showAllRoutes){ //add map image to constructor?
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.graph = graph;
        this.sourceImage = image;
        this.background = drawBackgroundMap(graph, sourceImage, showAllRoutes);
    }

    public void take(final Canvas canvas) { //take a screenshot of the canvas and save it to a file -- test purposes
        final WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        final WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), writableImage);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("F:/test.png"));
        } catch (final IOException e) {
            System.out.println(e);
        }
    }

    public void resetImage(){ //call this when resetting routes
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //clear canvas before drawing background again.
        graphicsContext.drawImage(background, 0,0);
    }

    public void redraw(boolean showAllRoutes){ //in case new nodes/edges were added
        long time = System.nanoTime();
        
        double scale = canvas.getScaleX();
        canvas.setScaleX(1);
        canvas.setScaleY(1);
        this.background = drawBackgroundMap(graph, sourceImage, showAllRoutes);
        canvas.setScaleX(scale);
        canvas.setScaleY(scale);

        System.out.println("(System.nanoTime() - time) = " + (System.nanoTime() - time));
    }

    public void drawPath(LinkedList<Edge> path, Node origin){
        Paint orgPaint = graphicsContext.getStroke();
        double orgStrokeWidth = graphicsContext.getLineWidth();

        graphicsContext.setStroke(Color.RED);
        graphicsContext.setLineWidth(orgStrokeWidth*3);
        graphicsContext.setFont(Font.font(18));

        resetImage();
        graphicsContext.beginPath();
        graphicsContext.moveTo(origin.getX(),origin.getY());

        drawStopName(origin);
        for(Edge e: path) {
            origin = e.getTheOtherNode(origin);
            drawStopName(origin);
            graphicsContext.lineTo(origin.getX(),origin.getY());
        }
        graphicsContext.stroke();
        graphicsContext.closePath();

        graphicsContext.setLineWidth(orgStrokeWidth);
        graphicsContext.setStroke(orgPaint);
    }

    private Image drawBackgroundMap(Graph graph, Image image, boolean showAllRoutes) {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //clear canvas before drawing background again.
        graphicsContext.drawImage(image, 0, 0); //Defaulting to 0 (see no need for anything else rn)
        for (Node n : graph.getNodes()) {
            drawNode(n, 10);
//            drawName(n);
        }

        if (showAllRoutes)
            for (Edge e : graph.getEdges()) drawEdge(e);

        return canvas.snapshot(null, null);
    }

    private void drawNode(Node n, int radius) {
        Color color = new Color(0,0,0,.75);
        graphicsContext.setLineWidth(3);
        graphicsContext.setStroke(color);
        graphicsContext.strokeArc(n.getX() - radius, n.getY() - radius, radius * 2, radius * 2,0, 360, ArcType.OPEN);
    }

    private void drawEdge(Edge e) {
        graphicsContext.strokeLine(e.getOrigin().getX(),e.getOrigin().getY(),e.getDestination().getX(),e.getDestination().getY());
    }

    private void drawName(Node node){
        graphicsContext.setTextAlign(TextAlignment.CENTER.CENTER);
        graphicsContext.setTextBaseline(VPos.BOTTOM);
        graphicsContext.setFont(Font.font(16));
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(node.getName(),node.getX(), node.getY() );
    }

    private void drawStopName(Node node){
        graphicsContext.setTextAlign(TextAlignment.CENTER.CENTER);
        graphicsContext.setTextBaseline(VPos.BOTTOM);
        graphicsContext.setFont(Font.font(20));
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillText(node.getName(),node.getX(), node.getY() );
    }

    public void clearPath() {
    }
}
