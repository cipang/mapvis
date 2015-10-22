package mapvis.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import mapvis.common.datatype.MPTreeImp;
import mapvis.common.datatype.Node;
import mapvis.treeGenerator.TreeGenerator;
import mapvis.treeGenerator.UDCParser;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by dacc on 10/20/2015.
 */
public class UDCTreeSettingsController implements Initializable, IDatasetGeneratorController {

    @FXML
    private CheckBox dumpToFileCheckbox;

    @FXML
    private VBox vBox;

    private UDCParser parser;
    private TreeGenerator treeGenerator;
    private Yaml yaml;
    private BufferedWriter yamlWriter;


    public UDCTreeSettingsController(){
        parser = new UDCParser();
        treeGenerator = new TreeGenerator();
        yaml = new Yaml();
    }

    @Override
    public void setVisible(boolean isVisible) {
        vBox.setVisible(isVisible);
    }

    @Override
    public MPTreeImp<Node> generateTree(ActionEvent event) {
        parser.configure("D:/downloads/datasets/Libraries/UDC/udcsummary-skos.rdf");
        Node udcNodes = parser.generateUDCCathegories();
        treeGenerator.configure(udcNodes);
        Node connectedUDCTree = treeGenerator.genTree();

        if(isDumpToFileEnabled.get()){
            System.out.println("Dumping files!");
            if(yamlWriter == null){
                try {
                    yamlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("udc.yaml"), "utf-8"));
                    yaml.dump(connectedUDCTree, yamlWriter);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("Dumping Tree failed! " + e.getMessage());
                } catch (FileNotFoundException e) {
                    System.out.println("Dumping Tree failed! File not found: " + e.getMessage());
                }
            }
        }

        return MPTreeImp.from(connectedUDCTree);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init FilesystemTreeSettingsController");
        dumpToFileCheckbox.selectedProperty().bindBidirectional(isDumpToFileEnabled);
    }

    public boolean getIsDumpToFileEnabled() {
        return isDumpToFileEnabled.get();
    }

    public BooleanProperty isDumpToFileEnabledProperty() {
        return isDumpToFileEnabled;
    }

    private BooleanProperty isDumpToFileEnabled = new SimpleBooleanProperty(true);

    @Override
    public String toString() {
        return "UDC Tree Generator";
    }
}