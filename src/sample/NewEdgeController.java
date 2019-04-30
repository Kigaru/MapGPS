package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewEdgeController {
    public ChoiceBox<sample.Node> choiceBoxFrom;
    public ChoiceBox<sample.Node> choiceBoxTo;
    public TextField edgeLength;
    public TextField edgeDiff;
    public TextField edgeSafety;
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
        fillLists();
    }

    private void fillLists() {
        for (sample.Node n : graph.getNodes()) {
            choiceBoxFrom.getItems().add(n);
            choiceBoxTo.getItems().add(n);
        }
    }

    @FXML
    private void addEdge(ActionEvent actionEvent) {
        sample.Node from = choiceBoxFrom.getSelectionModel().getSelectedItem();
        sample.Node to = choiceBoxTo.getSelectionModel().getSelectedItem();
        int length = Integer.valueOf(edgeLength.getText());
        int diff = Integer.valueOf(edgeDiff.getText());
        int safety = Integer.valueOf(edgeSafety.getText());

        graph.addEdge(new Edge(from, to, length, diff, safety));

        clearInput();
    }

    private void clearInput() {
        edgeLength.clear();
        edgeDiff.clear();
        edgeSafety.clear();

        choiceBoxFrom.requestFocus();
    }

    @FXML
    private void goToMainScene(ActionEvent actionEvent) {
        sourceController.redrawMap();
        Stage sourceStage = ((Stage) ((Node)actionEvent.getSource()).getScene().getWindow());
        sourceStage.setScene(sourceScene);
    }
}
