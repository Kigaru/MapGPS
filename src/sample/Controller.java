package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;


public class Controller {

    @FXML
    private ScrollPane scrollPane;
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
        imageFile = new File("src/sample/gotMap.jpg");

        loadImage();
    }


    @FXML
    private void loadImage() {
        graph = new Graph();

        if (imageFile != null) {
            image = new Image(imageFile.toURI().toString());
            canvas.setHeight(image.getHeight());
            canvas.setWidth(image.getWidth());

            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setHmax(image.getWidth());
            scrollPane.setVmax(image.getHeight());
            scrollPane.setPannable(true);
            System.out.println("scrollPane.isPannable() = " + scrollPane.isPannable());

            graph = loadGraph();

            for (Node n : graph.getNodes()) {
                addToChoiceBoxes(n);
            }
            /*
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
            */

            criteriaChoice.getItems().addAll("Length", "Difficulty", "Safety");
            criteriaChoice.getSelectionModel().selectFirst();

            //////////////////////////////////////// GFX
            redrawMap();
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
        sourceStage.setTitle("Add new ...");
        sourceStage.setScene(new Scene(root, 600, 300));
    }

    @FXML
    private void newEdge(ActionEvent actionEvent) throws IOException {
        Stage sourceStage = (Stage)Stage.getWindows().filtered(window -> window.isShowing()).get(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newEdgeWindow.fxml"));
        Parent root = loader.load();
        NewEdgeController newEdgeController = loader.getController();
        newEdgeController.setMainStageController(this);
        newEdgeController.setMainScene(sourceStage.getScene());
        newEdgeController.setGraph(graph);
        sourceStage.setTitle("Add new ...");
        sourceStage.setScene(new Scene(root, 600, 300));
    }

    public void redrawMap(){
        if(gfx == null) gfx = new Graphics(canvas, image, graph);
        else gfx.redraw();
    }

    public void addToChoiceBoxes(Node n) {
        fromChoice.getItems().add(n);
        toChoice.getItems().add(n);
    }

    @FXML
    private void saveGraph() {
        try {
            XStream xstream = new XStream(new DomDriver());
            ObjectOutputStream out = null;
            out = xstream.createObjectOutputStream(new FileWriter("src/sample/graph.xml"));
            out.writeObject(graph);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Graph loadGraph() {
        try {
            XStream xstream = new XStream(new DomDriver());
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/sample/graph.xml"));
            Graph graph = (Graph) is.readObject();
            is.close();
            return graph;
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("File may not exist... creating a new graph");
        }
        return new Graph();
    }

    public void centerOnNode(ActionEvent actionEvent) {
        int x = ((ChoiceBox<Node>)actionEvent.getSource()).getSelectionModel().getSelectedItem().getX();
        int y = ((ChoiceBox<Node>)actionEvent.getSource()).getSelectionModel().getSelectedItem().getY();
        scrollPane.setHvalue(x);
        scrollPane.setVvalue(y);
    }
}
