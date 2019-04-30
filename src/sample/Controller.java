package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;


public class Controller {

    @FXML
    private ChoiceBox<Node> fromChoice, toChoice;
    @FXML
    private ChoiceBox<String> criteriaChoice;
    @FXML
    private Canvas canvas;

    private Graphics gfx;

    private File imageFile;
    private Graph graph;
    private Image image;


    @FXML
    private void initialize(){
        imageFile = new File("WhiteSquare.png");

        loadImage();
    }


    @FXML
    private void loadImage() {
        graph = new Graph();

        if (imageFile != null) {
            image = new Image(imageFile.toURI().toString());

            //hardcode nodes for now i guess


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

            graph.addEdge(new Edge(townA, townB, 5, 3, 11));
            graph.addEdge(new Edge(townB, townC, 3, 5, 23));
            graph.addEdge(new Edge(townA, townC, 7, 7, 1));
            graph.addEdge(new Edge(townD, townC, 4, 12, 3));
            graph.addEdge(new Edge(townC, townE, 2, 1, 2));
            graph.addEdge(new Edge(townC, townF, 5, 84, 55));
            graph.addEdge(new Edge(townD, townF, 4, 13,4));
            graph.addEdge(new Edge(townE, townF, 9, 123, 87));
            graph.addEdge(new Edge(townF, townH, 6, 1, 5));
            graph.addEdge(new Edge(townF, townG, 7, 8, 23));
            graph.addEdge(new Edge(townG, townH, 14, 88, 9));


            for (Node n : graph.getNodes()) {
                fromChoice.getItems().add(n);
                toChoice.getItems().add(n);
            }

            criteriaChoice.getItems().addAll("Length", "Difficulty", "Safety");
            criteriaChoice.getSelectionModel().selectFirst();

            //////////////////////////////////////// GFX
            gfx = new Graphics(canvas, image, graph);
        }
    }

    @FXML
    private void calculatePath() {
        if(fromChoice.getValue() != null && toChoice.getValue() != null) {
//            gfx.restoreImage();
            LinkedList<Edge> path = graph.dijkstra(fromChoice.getValue(), toChoice.getValue(), criteriaChoice.getSelectionModel().getSelectedIndex());

            gfx.drawPath(path, fromChoice.getValue());
        }
    }

    @FXML
    private void test(ActionEvent actionEvent) {

    }

    @FXML
    private void clearPath(ActionEvent actionEvent) {
        gfx.restoreImage();
    }

    @FXML
    private void newNode(ActionEvent actionEvent) throws IOException {
        Stage sourceStage = (Stage)Stage.getWindows().filtered(window -> window.isShowing()).get(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newNodeWindow.fxml"));
        Parent root = loader.load();
        NewNodeController newNodeController = loader.getController();
        newNodeController.setMainStageController(this);
        newNodeController.setMainScene(sourceStage.getScene());
        newNodeController.setGraph(graph);
        sourceStage.setTitle("Add new Node");
        sourceStage.setScene(new Scene(root, 600, 300));
    }

    public void redrawMap(){
        gfx.redraw();
    }
}
