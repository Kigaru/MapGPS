package sample;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.LinkedList;


public class Controller {

    @FXML
    private ChoiceBox<Node> fromChoice, toChoice;

    @FXML
    private Canvas canvas;

    private File imageFile;
    private Graph graph;
    private Image image;
    private WritableImage routedImage;



    @FXML
    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image...");
        imageFile = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (imageFile != null) {
            image = new Image(imageFile.toURI().toString());

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(image,0,0);

            //hardcode nodes for now i guess
            graph = new Graph();

            Node townA = new Node("Town A", 94, 55);
            Node townB = new Node("Town B", 356, 47);
            Node townC = new Node("Town C", 237, 118);
            Node townD = new Node("Town D", 70, 183);
            Node townE = new Node("Town E", 404, 228);
            Node townF = new Node("Town F", 199, 286);
            Node townG = new Node("Town G", 396, 367);
            Node townH = new Node("Town H", 127, 417);

            graph.addNode(townA, townB, townC, townD, townE, townF, townG);
            graph.addNode(townH);

            graph.addEdge(new Edge(townA, townB, 5));
            graph.addEdge(new Edge(townB, townC, 3));
            graph.addEdge(new Edge(townA, townC, 7));
            graph.addEdge(new Edge(townD, townC, 4));
            graph.addEdge(new Edge(townC, townE, 2));
            graph.addEdge(new Edge(townC, townF, 5));
            graph.addEdge(new Edge(townD, townF, 4));
            graph.addEdge(new Edge(townE, townF, 9));
            graph.addEdge(new Edge(townF, townH, 6));
            graph.addEdge(new Edge(townF, townG, 7));
            graph.addEdge(new Edge(townG, townH, 14));


            for (Node n : graph.getNodes()) {
                fromChoice.getItems().add(n);
                toChoice.getItems().add(n);
            }



            gc.setFill(Color.BLACK);
            for (Node n : graph.getNodes()) drawNode(gc, n, 5);
            for (Edge e : graph.getEdges()) drawEdge(gc, e);

            routedImage = canvas.snapshot(null, null);
        }
    }

    /**
     * Draws a circle of radius 5 around a node.
     * @param imageGraphics The graphics context instance of the image
     * @param node The node to draw a circle around
     */
    private void drawNode(GraphicsContext imageGraphics,Node node, int radius) {
            imageGraphics.strokeArc(node.getX() - radius, node.getY() - radius, radius * 2, radius * 2,0, 360, ArcType.OPEN);
    }
    private void drawEdge(GraphicsContext imageGraphics,Edge edge) {
            imageGraphics.strokeLine(edge.getOrigin().getX(),edge.getOrigin().getY(),edge.getDestination().getX(),edge.getDestination().getY());
    }

    @FXML
    private void drawPath() {
        if(fromChoice.getValue() != null && toChoice.getValue() != null) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(routedImage,0,0);
            LinkedList<Edge> path = graph.dijkstra(fromChoice.getValue(), toChoice.getValue());
            gc.setStroke(Color.RED);
            double orgStrokeWidth = gc.getLineWidth();
            gc.setLineWidth(orgStrokeWidth*3);


            gc.beginPath();
            Node origin = fromChoice.getValue();
            gc.moveTo(origin.getX(),origin.getY());
            for(Edge e: path) {
                origin = e.getTheOtherNode(origin);
                gc.lineTo(origin.getX(),origin.getY());
            }
            gc.stroke();
            gc.closePath();

            gc.setLineWidth(orgStrokeWidth);
        }
    }



}
