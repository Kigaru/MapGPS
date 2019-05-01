package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;


public class Controller {

    @FXML
    private CheckMenuItem allRoutesCheck;
    @FXML
    private ListView<Node> waypointListView, avoidListView;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ChoiceBox<Node> fromChoice, toChoice, extraChoiceBox;
    @FXML
    private ChoiceBox<String> criteriaChoice;
    @FXML
    private Canvas canvas;

    private final double MIN_SCALE_FACTOR = .2;
    private final double MAX_SCALE_FACTOR = 1.5;

    private Graphics gfx;
    private File imageFile;
    private Graph graph;
    private Image image;

    private EventHandler<MouseEvent> listDeletionHandler = mouseEvent -> {
        MouseButton button = mouseEvent.getButton();
        if(button==MouseButton.SECONDARY){
            {
                ListView<Node> list = ((ListView<Node>) mouseEvent.getSource());
                Node toRemove = list.getSelectionModel().getSelectedItem();
                list.getItems().remove(toRemove);
            }
        }
    };

    @FXML
    private void initialize(){
        imageFile = new File("src/sample/gotMap.jpg");

        waypointListView.setOnMouseClicked(listDeletionHandler);
        avoidListView.setOnMouseClicked(listDeletionHandler);
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

        //Zooming
        canvas.addEventFilter(ScrollEvent.ANY, e->{
            boolean atLimit = false;
            double delta = 1.1;
            double factor = canvas.getScaleX();

            if (e.getDeltaY() < 0) {
                factor /= delta;
            } else {
                factor *= delta;
            }

            if (factor < MIN_SCALE_FACTOR || factor > MAX_SCALE_FACTOR) {
                atLimit = true;
                factor = factor < MIN_SCALE_FACTOR ? MIN_SCALE_FACTOR : MAX_SCALE_FACTOR;
            }

            canvas.setScaleX(factor);
            canvas.setScaleY(factor);

            if(!atLimit){
                scrollPane.setHvalue(e.getX()/image.getWidth());
                scrollPane.setVvalue(e.getY()/image.getHeight());
            }

            e.consume();
        });

//        canvas.setOnMouseClicked(e -> {
//            System.out.println("["+e.getX()+", "+e.getY()+"]"); //Can be used to get places on click maybe
//        });

    }

    @FXML
    private void calculatePath() {
        Node origin = fromChoice.getValue();
        Node destination = toChoice.getValue();

        if(origin != null && destination != null) {
            LinkedList<Edge> path;

            int criteria = criteriaChoice.getSelectionModel().getSelectedIndex();

            if(waypointListView.getItems().size() > 0)
                path = pathThroughWaypoints();
            else
                path = graph.dijkstra(origin, destination, criteria, avoidListView.getItems());

            float length = 0;
            float diff = 0;
            float safety = 0;

            for (Edge e:path) {
                length += e.getWeight()[0];
                diff += e.getWeight()[1];
                safety += e.getWeight()[2];
            }

            Stage stage = ((Stage)canvas.getScene().getWindow());

            if(path.size() < 1)
                stage.setTitle("No valid route found.");
            else
                stage.setTitle("The route from " + origin.getName() +
                     " to " + destination.getName() + " is approximately " +
                    length * 1.82 + " miles long. It's difficulty score is: " + diff +
                    ". It's danger score is: " + safety + ".");

            gfx.drawPath(path, fromChoice.getValue());
        }
    }

    private LinkedList<Edge> pathThroughWaypoints() {
        ArrayList<Node> waypoints = new ArrayList<>();
        LinkedList<Edge> path = new LinkedList<>();

        waypoints.add(fromChoice.getValue());
        for (Node n: waypointListView.getItems()) {
            waypoints.add(n);
        }
        waypoints.add(toChoice.getValue());

        for (int i = 0; i < waypoints.size() - 1; i++) {
            LinkedList<Edge> dijkstra = graph.dijkstra(waypoints.get(i),
                    waypoints.get(i + 1),
                    criteriaChoice.getSelectionModel().getSelectedIndex(),
                    avoidListView.getItems());

            for (Edge e : dijkstra) {
                path.add(e);
            }
        }

        return path;
    }

    @FXML
    private void clearPath(ActionEvent actionEvent) {
        waypointListView.getItems().clear();
        avoidListView.getItems().clear();
        gfx.resetImage();
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
        if(gfx == null) gfx = new Graphics(canvas, image, graph, allRoutesCheck.isSelected());
        else gfx.redraw(allRoutesCheck.isSelected());
    }

    public void addToChoiceBoxes(Node n) {
        ObservableList<Node> list = fromChoice.getItems();
        list.add(n);
        list.sort(Comparator.comparing(Node::getName));

        fromChoice.setItems(list);
        toChoice.setItems(list);
        extraChoiceBox.setItems(list);
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
        scrollPane.setHvalue(x/image.getWidth());
        scrollPane.setVvalue(y/image.getHeight());
    }

    @FXML
    private void addToAvoidList(ActionEvent actionEvent) {
        Node n = extraChoiceBox.getSelectionModel().getSelectedItem();
        avoidListView.getItems().add(n);
    }

    @FXML
    private void addToWaypointList(ActionEvent actionEvent) {
        Node n = extraChoiceBox.getSelectionModel().getSelectedItem();
        waypointListView.getItems().add(n);
    }

    @FXML
    private void test() {

    }
}
