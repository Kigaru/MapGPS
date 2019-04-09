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

            Node townA = new Node("Town A", 20,20);
            Node townB = new Node("Town B", 100,100);
            Node townC = new Node("Town C", 150,50);

            Edge townAtoB = new Edge(townA,townB,5);
            Edge townBtoC = new Edge(townB,townC,3);
            graph.addAllNodes(townA, townB, townC);


            BufferedImage awtImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            Graphics graphics = awtImage.getGraphics();
            graphics.setColor(Color.BLACK);
//            graphics.drawLine(4,6,20,32);
            drawAllNodes(graphics,townA,townB,townC);

            graphics.dispose(); //IMPORTANT TO PREVENT MEMORY LEAKS

            imageView.setImage(SwingFXUtils.toFXImage(awtImage,null));

        }
    }

    private void drawAllNodes(Graphics imageGraphics,Node... n) {
        for(Node node: n) {
            imageGraphics.drawOval(node.getX(),node.getY(),10,10);

        }
    }

    public void findPath() {
        if(fromChoice.getValue() != null && toChoice.getValue() != null) {
            graph.dijkstra(fromChoice.getValue(), toChoice.getValue());
        }
    }



}
