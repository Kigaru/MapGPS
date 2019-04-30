package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;


public class Controller {

    @FXML
    private StackPane stackPane;
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

            setUpView();

            graph = loadGraph();
            System.out.println("There are: " + graph.getNodes().size() + " cities in this graph so far...");

            for (Node n : graph.getNodes()) {
                addToChoiceBoxes(n);
            }

            criteriaChoice.getItems().addAll("Length", "Difficulty", "Safety");
            criteriaChoice.getSelectionModel().selectFirst();

            //////////////////////////////////////// GFX
            redrawMap();
        }
    }

    private void setUpView() {
        canvas.setHeight(image.getHeight());
        canvas.setWidth(image.getWidth());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Group zoomGroup = new Group();
        zoomGroup.getChildren().add(canvas);
        scrollPane.setContent(zoomGroup);

        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double delta = 1.1;
                double factor = canvas.getScaleX();

                if (event.getDeltaY() < 0) {
                    factor /= delta;
                } else {
                    factor *= delta;
                }

                if (factor < .2 || factor > 5) {
                    factor = factor < .2 ? .2 : 5;
                }

                canvas.setScaleX(factor);
                canvas.setScaleY(factor);
                
                scrollPane.setHvalue(event.getSceneX()/scrollPane.getWidth());
                scrollPane.setVvalue(event.getSceneY()/scrollPane.getHeight());

                event.consume();
            }});
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
