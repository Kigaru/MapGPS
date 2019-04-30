package sample;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.LinkedList;
import java.util.Set;

public class Graphics {
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Image background;

    public Graphics(Canvas canvas, Image image, Graph graph){ //add map image to constructor?
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.background = drawMap(graph.getNodes(), graph.getEdges(), image);
        graphicsContext.drawImage(background, 0, 0);
    }

    public void restoreImage(){
        graphicsContext.drawImage(background, 0,0);
    }

    public void drawPath(LinkedList<Edge> path, Node origin){
        Paint orgPaint = graphicsContext.getStroke();
        double orgStrokeWidth = graphicsContext.getLineWidth();

        graphicsContext.setStroke(Color.RED);
        graphicsContext.setLineWidth(orgStrokeWidth*3);
        graphicsContext.setTextAlign(TextAlignment.CENTER.CENTER);
        graphicsContext.setTextBaseline(VPos.BOTTOM);
        graphicsContext.setFont(Font.font(16));


        graphicsContext.drawImage(background, 0, 0);
        graphicsContext.beginPath();
        graphicsContext.moveTo(origin.getX(),origin.getY());
        graphicsContext.fillText(
                origin.getName(),
                origin.getX(),
                origin.getY()
        );

        for(Edge e: path) {
            origin = e.getTheOtherNode(origin);
            graphicsContext.fillText(
                    origin.getName(),
                    origin.getX(),
                    origin.getY()
            );
            graphicsContext.lineTo(origin.getX(),origin.getY());
        }
        graphicsContext.stroke();
        graphicsContext.closePath();

        graphicsContext.setLineWidth(orgStrokeWidth);
        graphicsContext.setStroke(orgPaint);
    }

    private Image drawMap(LinkedList<Node> nodes, Set<Edge> edges, Image image) {
        graphicsContext.drawImage(image, 0, 0); //Defaulting to 0 (see no need for anything else rn)
        for (Node n : nodes) drawNode(n, 5);
        for (Edge e : edges) drawEdge(e);

        return canvas.snapshot(null, null);
    }

    private void drawNode(Node n, int radius) {
        graphicsContext.strokeArc(n.getX() - radius, n.getY() - radius, radius * 2, radius * 2,0, 360, ArcType.OPEN);
    }

    private void drawEdge(Edge e) {
        graphicsContext.strokeLine(e.getOrigin().getX(),e.getOrigin().getY(),e.getDestination().getX(),e.getDestination().getY());
    }

    private void drawName(Node node){

    }
}
