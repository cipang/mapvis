package mapvis.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mapvis.common.datatype.INode;
import mapvis.common.datatype.MPTreeImp;
import mapvis.common.datatype.NodeUtils;
import mapvis.models.ConfigurationConstants;
import mapvis.treeGenerator.RandomTreeGenerator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This Controller is responsible for the generation of the
 * RandomTree according to the UI.
 */
public class RandomTreeSettingsController implements Initializable, IDatasetGeneratorController {
    @FXML
    private TextField weightField;
    @FXML
    private TextField depthField;
    @FXML
    private TextField spanField;
    @FXML
    private TextField seedField;
    @FXML
    private TextArea infoArea;
    @FXML
    private VBox vBox;
    @FXML
    private Button generateTreeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init DatasetSelectionController");
    }

    public MPTreeImp<INode> generateTree(ActionEvent event) {
        int span = ConfigurationConstants.DEFAULT_RANDOM_TREE_SPAN, weight = ConfigurationConstants.DEFAULT_RANDOM_TREE_WEIGHT, depth = ConfigurationConstants.DEFAULT_RANDOM_TREE_DEPTH, seed = ConfigurationConstants.DEFAULT_RANDOM_TREE_SEED;
        try {
            span = Integer.parseInt(spanField.getText());
        }
        catch (NumberFormatException ignored) { }
        try {
            weight = Integer.parseInt(weightField.getText());
        }
        catch (NumberFormatException ignored) {  }
        try {
            depth = Integer.parseInt(depthField.getText());
        }
        catch (NumberFormatException ignored) {  }
        try {
            seed = Integer.parseInt(seedField.getText());
        }
        catch (NumberFormatException ignored) {  }

        RandomTreeGenerator randomTreeGenerator = new RandomTreeGenerator(seed);
        MPTreeImp<INode> genTree = randomTreeGenerator.getTree(depth, span, weight);
        NodeUtils.populateSize(genTree.getRoot(), genTree);
        return genTree;
    }

    public void setVisible(boolean isVisible){
        vBox.setVisible(isVisible);
        vBox.setManaged(isVisible);
    }

    @Override
    public String toString() {
        return "Random Tree Generator";
    }
}
