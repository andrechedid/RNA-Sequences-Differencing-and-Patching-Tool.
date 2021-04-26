/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idpaprojectfinal;

import com.jfoenix.controls.JFXRadioButton;
import static idpaprojectfinal.IDPAProjectFinal.window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import  javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author PC-A
 */
public class PatchingController implements Initializable {

    private static Scanner scan;
    private static String steps, seq, source, destination, ES, code;
    private static int pos, len;
    private static char toAdd, ran;
    private static ArrayList<Character> toModify = new ArrayList<Character>();
    @FXML
    private TextField seqPath;
    @FXML
    private TextArea sequence;
    @FXML
    private TextArea editScript;
    @FXML
    private TextField editScriptPath;

    private static FileChooser fc = new FileChooser();
    private static File sequenceFile, editScriptFile;

    private ToggleGroup tg = new ToggleGroup();

    @FXML
    private JFXRadioButton SourceButton;
    @FXML
    private JFXRadioButton DestButton;
    @FXML
    private TextArea output;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fc.getExtensionFilters().add(IDPAProjectFinal.xmlFiles);
        SourceButton.setToggleGroup(tg);
        DestButton.setToggleGroup(tg);
    }

    private static void patchSToD(String source) throws ParserConfigurationException, SAXException, IOException {
        int counter = 0;
        len = source.length();
        ES = readES(editScriptFile);
        ES = ES.substring(4, ES.length() - 1);
        scan = new Scanner(ES);
        scan.useDelimiter("\\),");
        while (scan.hasNext()) {
            steps = scan.next();
            if (scan.hasNext()) {
                System.out.println("Updated Sequence after " + steps + ")");
            } else {
                System.out.println("Updated Sequence after " + steps);
            }
            char charStep = steps.charAt(0);
            switch (charStep) {
                case ('I'):
                    steps = steps.substring(steps.indexOf("(D") + 1);
                    pos = Integer.parseInt(steps.substring(steps.indexOf("D") + 1, steps.indexOf(",")));
                    toAdd = steps.charAt(steps.indexOf(",") + 1);
                    toModify.add(pos - 1, toAdd);
                    counter++;
                    break;
                case ('D'):
                    steps = steps.substring(steps.indexOf("(S") + 1);
                    pos = Integer.parseInt(steps.substring(steps.indexOf("S") + 1, steps.indexOf(",")));
                    toAdd = steps.charAt(steps.indexOf(",") + 1);
                    toModify.remove(pos - 1 + counter);
                    counter--;
                    break;
                default:
                    steps = steps.substring(steps.indexOf("(D") + 1);
                    pos = Integer.parseInt(steps.substring(steps.indexOf("D") + 1, steps.indexOf(",")));
                    steps = steps.substring(steps.indexOf(",") + 1);
                    toAdd = steps.charAt(0);
                    toModify.set(pos - 1, toAdd);
                    break;
            }
            System.out.println("Sequence: ");
            printList(toModify);
            System.out.println("------------------------");
        }
        System.out.println("Patching is complete");
        updSource();
    }

    private static void patchDtoS(String destination) throws ParserConfigurationException, SAXException, IOException {
        int counter = 0;
        len = destination.length();
        ES = readES(editScriptFile);
        ES = ES.substring(4, ES.length() - 1);
        scan = new Scanner(ES);
        scan.useDelimiter("\\),");
        while (scan.hasNext()) {
            steps = scan.next();
            if (scan.hasNext()) {
                System.out.println("Updated Sequence after " + steps + ")");
            } else {
                System.out.println("Updated Sequence after " + steps);
            }
            char charStep = steps.charAt(0);
            switch (charStep) {
                case ('I'):
                    steps = steps.substring(steps.indexOf("(D") + 1);
                    pos = Integer.parseInt(steps.substring(steps.indexOf("D") + 1, steps.indexOf(",")));
                    toAdd = steps.charAt(steps.indexOf(",") + 1);
                    toModify.remove(pos - 1 + counter);
                    counter--;
                    break;
                case ('D'):
                    steps = steps.substring(steps.indexOf("(S") + 1);
                    pos = Integer.parseInt(steps.substring(steps.indexOf("S") + 1, steps.indexOf(",")));
                    toAdd = steps.charAt(steps.indexOf(",") + 1);
                    toModify.add(pos - 1, toAdd);
                    counter++;
                    break;
                default:
                    steps = steps.substring(steps.indexOf("(D") + 1);
                    pos = Integer.parseInt(steps.substring(steps.indexOf("D") + 1, steps.indexOf(",")));
                    steps = steps.substring(steps.indexOf(",") + 1);
                    toAdd = steps.charAt(2);
                    toModify.set(pos - 1 + counter, toAdd);
                    break;
            }
            System.out.println("Sequence: ");
            printList(toModify);
            System.out.println("------------------------");
        }
        System.out.println("Patching is complete");
        updDest();
    }

    private static void fillSource() {
        toModify.clear();
        for (int i = 0; i < len; i++) {
            toModify.add(source.charAt(i));
        }
    }

    private static void fillDest() {
        toModify.clear();
        for (int i = 0; i < len; i++) {
            toModify.add(destination.charAt(i));
        }
    }

    private static void updSource() {
        source = "";
        for (int i = 0; i < toModify.size(); i++) {
            source += toModify.get(i);
        }
    }

    private static void updDest() {
        destination = "";
        for (int i = 0; i < toModify.size(); i++) {
            destination += toModify.get(i);
        }
    }

    private static void printList(ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
        }
        System.out.println();
    }

    public static void randomize(String code) {
        int counter = 0;
        for (int i = 0; i < toModify.size(); i++) {
            switch (toModify.get(i)) {
                case ('R'):
                    ran = code.charAt(counter);
                    switch (ran) {
                        case ('0'):
                            toModify.set(i, 'G');
                            counter++;
                            break;
                        case ('1'):
                            toModify.set(i, 'A');
                            counter++;
                            break;
                        default:
                            break;
                    }
                    break;
                case ('M'):
                    ran = code.charAt(counter);
                    switch (ran) {
                        case ('0'):
                            toModify.set(i, 'A');
                            counter++;
                            break;
                        case ('1'):
                            toModify.set(i, 'C');
                            counter++;
                            break;
                        default:
                            break;
                    }
                    break;
                case ('S'):
                    ran = code.charAt(counter);
                    switch (ran) {
                        case ('0'):
                            toModify.set(i, 'G');
                            counter++;
                            break;
                        case ('1'):
                            toModify.set(i, 'C');
                            counter++;
                            break;
                        default:
                            break;
                    }
                    break;
                case ('V'):
                    ran = code.charAt(counter);
                    switch (ran) {
                        case ('0'):
                            toModify.set(i, 'G');
                            counter++;
                            break;
                        case ('1'):
                            toModify.set(i, 'A');
                            counter++;
                            break;
                        case ('2'):
                            toModify.set(i, 'C');
                            counter++;
                            break;
                        default:
                            break;
                    }
                    break;
                case ('N'):
                    ran = code.charAt(counter);
                    switch (ran) {
                        case ('0'):
                            toModify.set(i, 'G');
                            counter++;
                            break;
                        case ('1'):
                            toModify.set(i, 'U');
                            counter++;
                            break;
                        case ('2'):
                            toModify.set(i, 'A');
                            counter++;
                            break;
                        case ('3'):
                            toModify.set(i, 'C');
                            counter++;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private static String readXML(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);
        return d.getElementsByTagName("sequence").item(0).getTextContent();
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(root);
        window.setTitle("RNA Sequences Differencing and Patching Tool");
        window.setResizable(false);
        window.setScene(scene);
        window.centerOnScreen();
    }

    @FXML
    private void sequence(ActionEvent event) throws ParserConfigurationException, SAXException, IOException {
        seqPath.setText("");
        sequence.setText("");
        if (editScriptPath.getText().equalsIgnoreCase("")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Choose EditScriptFile First!");
            a.showAndWait();
        } else {
            if ((sequenceFile = fc.showOpenDialog(window)) == null) {

            } else if (SourceButton.isSelected()) {
                try {
                    if (!(source=readXML(sequenceFile)).equalsIgnoreCase(readSource(editScriptFile))) {
                            Alert a=new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("Sequence or File Not Valid (check buttons and inputed seq)");
                            a.showAndWait();
                    } else {
                        len = source.length();
                        code = readStoD(editScriptFile);
                        fillSource();
                        randomize(code);
                        updSource();
                        sequence.setText(source);
                        seqPath.setText(sequenceFile.getName());
                    }
                        }catch(Exception e){
                            Alert a=new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("Sequence or File Not Valid (check buttons and inputed seq)");
                            a.showAndWait();
                        }
            } else if (DestButton.isSelected()) {
                try {
                    if (!(destination=readXML(sequenceFile)).equalsIgnoreCase(readDest(editScriptFile))) {
                        Alert a=new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Sequence or File Not Valid (check buttons and inputed seq)");
                        a.showAndWait();
                    } else {
                        len = destination.length();
                        code = readDtoS(editScriptFile);
                        fillDest();
                        randomize(code);
                        updDest();
                        sequence.setText(destination);
                        seqPath.setText(sequenceFile.getName());
                    }
                } catch (Exception e) {
                            Alert a=new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText("Sequence or File Not Valid (check buttons and inputed seq)");
                            a.showAndWait();
                }
            }
        }
    }

    @FXML
    private void editScript(ActionEvent event) throws ParserConfigurationException, SAXException, IOException {
        editScript.setText("");
        editScriptPath.setText("");
        seqPath.setText("");
        sequence.setText("");
        if ((editScriptFile = fc.showOpenDialog(window)) == null) {

        } else {
            try {
                if ((ES = readES(editScriptFile)).equalsIgnoreCase("")) {

                } else {
                    editScript.setText(ES);
                    editScriptPath.setText(editScriptFile.getName());
                }
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No EditScript Found Choose a supported File!");
                a.showAndWait();
            }
        }
    }

    private static String readDtoS(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);
        return d.getElementsByTagName("DtoS").item(0).getTextContent();
    }

    private static String readSource(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);
        return d.getElementsByTagName("SourceSeq").item(0).getTextContent();
    }

    private static String readDest(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);
        return d.getElementsByTagName("DestinationSeq").item(0).getTextContent();
    }

    private static String readStoD(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);
        return d.getElementsByTagName("StoD").item(0).getTextContent();
    }

    private static String readES(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);
        return d.getElementsByTagName("EditScript").item(0).getTextContent();
    }

    @FXML
    private void Patch(ActionEvent event) throws ParserConfigurationException, SAXException, IOException {
        if(seqPath.getText().equalsIgnoreCase("") || editScriptPath.getText().equalsIgnoreCase("")){
            Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Pls choose both files!");
                a.showAndWait();
        }else{
            try {
            if (SourceButton.isSelected() && output.getText().equalsIgnoreCase("")) {
                patchSToD(source);
                output.setText(source);
            } else if(DestButton.isSelected() && output.getText().equalsIgnoreCase("")){
                patchDtoS(destination);
                output.setText(destination);
            }else{
                
            }
        } catch (Exception e) {

        }
        }
        
    }

    @FXML
    private void clearSeq(ActionEvent event) {
        seqPath.setText("");
        sequence.setText("");
        output.setText("");
    }

}
