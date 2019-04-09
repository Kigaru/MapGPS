package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;


public class Controller {

    @FXML
    ChoiceBox<Node> fromChoice, toChoice;

    @FXML
    ImageView imageView;

    File imageFile;
    Graph graph;
    Image image;
    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image...");
        imageFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (imageFile != null) {
            image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);




            //hardcode nodes for now i guess
            graph = new Graph();

            Node townA = new Node("Town A", 94,55);
            Node townB = new Node("Town B", 356,47);
            Node townC = new Node("Town C", 237,118);
            Node townD = new Node("Town D", 70,183);
            Node townE = new Node("Town E", 404,228);
            Node townF = new Node("Town F", 199,286);
            Node townG = new Node("Town G", 396,367);
            Node townH = new Node("Town H", 127,417);


            new Edge(townA,townB,5);
            new Edge(townB,townC,3);
            new Edge(townA,townC,7);
            new Edge(townD,townC,4);
            new Edge(townC,townE,2);
            new Edge(townC,townF,5);
            new Edge(townD,townF,4);
            new Edge(townE,townF,9);
            new Edge(townF,townH,6);
            new Edge(townF,townG,7);
            new Edge(townG,townH,14);

            graph.addAllNodes(townA, townB, townC, townD, townE, townF, townG, townH);

            for(Node n: graph.getNodes()) { //TODO display of nodes to not contain a "Node: " in choice boxes
                fromChoice.getItems().add(n);
                toChoice.getItems().add(n);
            }



            BufferedImage awtImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            Graphics graphics = awtImage.getGraphics();
            graphics.setColor(Color.BLACK);
            for(Node n : graph.getNodes()) drawNode(graphics,n);

            graphics.dispose(); //IMPORTANT TO PREVENT MEMORY LEAKS
            image = SwingFXUtils.toFXImage(awtImage,null);
            imageView.setImage(image);

        }
    }

    /**
     * Draws a circle of radius 5 around a node.
     * @param imageGraphics The graphics instance (awt) of the image
     * @param node The node to draw a circle around
     */
    private void drawNode(Graphics imageGraphics,Node node) {
            imageGraphics.drawOval(node.getX(),node.getY(),10,10);
    }

    public void findPath() {
        if(fromChoice.getValue() != null && toChoice.getValue() != null) {
            LinkedList<Edge> path = graph.dijkstra(fromChoice.getValue(), toChoice.getValue());

            BufferedImage awtImage = SwingFXUtils.fromFXImage(image, null);
            Graphics graphics = awtImage.getGraphics();

            graphics.setColor(Color.RED);

            for(Edge e: path) {
                graphics.drawLine(e.getOrigin().getX(),e.getOrigin().getY(),e.getDestination().getX(),e.getDestination().getY());
            }


            graphics.dispose(); //IMPORTANT TO PREVENT MEMORY LEAKS

            imageView.setImage(SwingFXUtils.toFXImage(awtImage,null));



        }
    }



}