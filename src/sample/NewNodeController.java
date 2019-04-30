package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewNodeController {
    public TextField newNodeName;
    public Button btnAddNode;
    public Button btnAddKeyCancel;
    public TextField newNodeX;
    public TextField newNodeY;
    private Controller sourceController;
    private Scene sourceScene;
    private Graph graph;

    public void setMainStageController(Controller controller) {
        this.sourceController = controller;
    }

    public void setMainScene(Scene scene) {
        this.sourceScene = scene;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @FXML
    private void addNode(ActionEvent actionEvent) {
        String name = newNodeName.getText();
        int x = Integer.valueOf(newNodeX.getText());
        int y = Integer.valueOf(newNodeY.getText());

        sample.Node node = new sample.Node(name, x, y);
        graph.addNode(node);
        sourceController.addToChoiceBoxes(node);

        clearInput();
    }

    private void clearInput() {
        newNodeName.clear();
        newNodeX.clear();
        newNodeY.clear();

        newNodeName.requestFocus();
    }

    @FXML
    private void goToMainScene(ActionEvent actionEvent) {
        sourceController.redrawMap();
        Stage sourceStage = ((Stage) ((Node)actionEvent.getSource()).getScene().getWindow());
        sourceStage.setScene(sourceScene);
    }
}
