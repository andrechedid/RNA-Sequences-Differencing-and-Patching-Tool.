/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idpaprojectfinal;

import com.jfoenix.controls.JFXTextField;
import static idpaprojectfinal.IDPAProjectFinal.window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author PC-A
 */
public class EditScriptController implements Initializable {

    @FXML
    private TextField destPath;
    @FXML
    private TextField sourcePath;
    @FXML
    private TextArea sourceSeq;
    @FXML
    private TextArea destSeq;
    @FXML
    private TextField EDistance;
    @FXML
    private TextField Similarity;
    @FXML
    private TextField sLength;
    @FXML
    private TextField dLength;
    @FXML
    private TextArea saveFile;
    @FXML
    private TextArea editScriptOutput;
    
    private static ArrayList<String> editScriptSteps=new ArrayList<String>();
    
    private static FileChooser fc=new FileChooser(),fcS=new FileChooser();
    private static File sourceFile,destFile,Output;
    private static ArrayList<Character>sourceU=new ArrayList<Character>(),destinationU=new ArrayList<Character>();
    private static String source,destination,oSource,oDestination,toDo,ES,XMLES,S="",D="";
    @FXML
    private JFXTextField delCost;
    @FXML
    private JFXTextField insCost;
    
    @FXML
    private JFXTextField UpdCost;
    
    private static double ED;
    private static DecimalFormat Sdf=new DecimalFormat("#0.000"),Ddf=new DecimalFormat("#0");
    private static int UpdCostInt,updCostUsed,rows,columns,sLen,dLen,random,delCostInt,insCostInt;
    private static Random rnd=new Random();
    private static int[][]arr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fc.getExtensionFilters().add(IDPAProjectFinal.xmlFiles);
    }    

    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene=new Scene(root);
        window.setTitle("RNA Sequences Differencing and Patching Tool");
        window.setResizable(false);
        window.setScene(scene);
        window.centerOnScreen();
    }

    @FXML
    private void sourceFile(ActionEvent event) throws ParserConfigurationException, SAXException, IOException{
        editScriptOutput.setText("");
        EDistance.setText("");
        Similarity.setText("");
        sLength.setText("");
        sourcePath.setText("");
        sourceSeq.setText("");
        if((sourceFile=fc.showOpenDialog(window))==null){
            
        }else
            try{
                if((source=readXML(sourceFile)).equalsIgnoreCase("")){
        }else{
        sourcePath.setText(sourceFile.getName());
        oSource=source;
        sLen=source.length();
        sLength.setText(sLen + "");
        fillSource();
        randomizeSource();
        updSource();
        sourceSeq.setText(oSource + "\n --> \n" + source);
        }
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Sequence not found! Try another file");
                a.showAndWait();
            }
    }

    @FXML
    private void destFile(ActionEvent event) throws ParserConfigurationException, SAXException, IOException {
        editScriptOutput.setText("");
        EDistance.setText("");
        Similarity.setText("");
        dLength.setText("");
        destPath.setText("");
        destSeq.setText("");
        if((destFile=fc.showOpenDialog(window))==null){
            
        }else 
            try{
            if((destination=readXML(destFile)).equalsIgnoreCase("")){
            
        }else{
        destPath.setText(destFile.getName());
        oDestination=destination;
        dLen=destination.length();
        dLength.setText(dLen + "");
        fillDestination();
        randomizeDest();
        updDest();
        destSeq.setText(oDestination + "\n --> \n" + destination);
        }
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Sequence not found! Try another file");
                a.showAndWait();
            }
    }

    private void randomizeDest(){
        D="";
        for(int i=0;i<dLen;i++){
            char charI=destinationU.get(i);
            if(charI=='N'){
                random=rnd.nextInt(4);
                switch(random){
                    case(0):
                        destinationU.set(i, 'G');
                        break;
                    case(1):
                        destinationU.set(i, 'U');
                        break;
                    case(2):
                        destinationU.set(i, 'A');
                        break;
                    case(3):
                        destinationU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                D+=random;
            }else if(charI=='V'){
                random=rnd.nextInt(3);
                switch(random){
                    case(0):
                        destinationU.set(i, 'G');
                        break;
                    case(1):
                        destinationU.set(i, 'A');
                        break;
                    case(2):
                        destinationU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                D+=random;
            }else if(charI=='S'){
                random=rnd.nextInt(2);
                switch(random){
                    case(0):
                        destinationU.set(i, 'G');
                        break;
                    case(1):
                        destinationU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                D+=random;
            }else if(charI=='M'){
                random=rnd.nextInt(2);
                switch(random){
                    case(0):
                        destinationU.set(i, 'A');
                        break;
                    case(1):
                        destinationU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                D+=random;
            }else if(charI=='R'){
                random=rnd.nextInt(2);
                switch(random){
                    case(0):
                        destinationU.set(i, 'G');
                        break;
                    case(1):
                        destinationU.set(i, 'A');
                        break;
                    default:
                        break;
                }
                D+=random;
            }
        }
    }
    
    @FXML
    private void save(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
        try{
            if(EDistance.getText().equalsIgnoreCase("")){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Fill out all the required fields then generate an edit script to save!");
                a.showAndWait();
            }else if((Output=fc.showSaveDialog(window))==null){
                
        }else{
        FileWriter fw=new FileWriter(Output);
        BufferedWriter bw=new BufferedWriter(fw);
        PrintWriter pw=new PrintWriter(bw);
        pw.println("<?xml version=" + "\"1.0\"?>");
        pw.println("<Output>");
        pw.println("<SourceSeqPath>" + sourceFile.getAbsolutePath() + "</SourceSeqPath>");
        pw.println("<DestinationSeqPath>" + destFile.getAbsolutePath() + "</DestinationSeqPath>");
        pw.println("<SourceSeq>" + oSource + "</SourceSeq>");
        pw.println("<RandomizedSourceSeq>" + source + "</RandomizedSourceSeq>");
        pw.println("<DestinationSeq>" + oDestination + "</DestinationSeq>");
        pw.println("<RandomizedDestinationSeq>" + destination + "</RandomizedDestinationSeq>");
        pw.println("<EditDistance>Levenshtein Distance = " + ED + "</EditDistance>");
        pw.println("<Similarity>" + Similarity.getText() + "</Similarity>");
        pw.println("<EditScript>" + XMLES + "</EditScript>");
        pw.println("<DelCost>" + delCostInt + "</DelCost>");
        pw.println("<InsCost>" + insCostInt + "</InsCost>");
        pw.println("<UpdCost>" + UpdCostInt + "</UpdCost>");
        pw.println("<StoD>" + S +"</StoD>");
        pw.println("<DtoS>" + D + "</DtoS>");
        pw.println("</Output>");
        pw.close();
        saveFile.setText(Output.getAbsolutePath());
            }
        }catch(Exception e){
        }
    }
    
    private static String readXML(File file) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db=dbf.newDocumentBuilder();
        Document d=db.parse(file);
        return d.getElementsByTagName("sequence").item(0).getTextContent();
    }
    
    private static void updSource(){
        source="";
        for(int i=0;i<sourceU.size();i++){
            source+=sourceU.get(i);
        }
    }
    
    private static void updDest(){
        destination="";
        for(int i=0;i<destinationU.size();i++){
            destination+=destinationU.get(i);
        }
    }
    
    private static void fillSource(){
        sourceU.clear();
        for(int i=0;i<sLen;i++){
            sourceU.add(source.charAt(i));
        }
    }
    
    private static void fillDestination(){
        destinationU.clear();
        for(int i=0;i<dLen;i++){
            destinationU.add(destination.charAt(i));
        }
    }

    public static int getLevDist(){
        rows=sLen + 1;
        columns=dLen + 1;
        arr=new int[rows][columns];
        for(int i=1;i<rows;i++){
            arr[i][0]=arr[i-1][0]+delCostInt;
        }
        for(int i=1;i<columns;i++){
            arr[0][i]=arr[0][i-1]+insCostInt;
        }
        for(int i=1;i<rows;i++){
            for(int j=1;j<columns;j++){
                if(source.charAt(i-1)==destination.charAt(j-1)){
                    updCostUsed=0;
                }else{
                    updCostUsed=UpdCostInt;
                }
                arr[i][j]=Math.min(Math.min(arr[i-1][j]+delCostInt, arr[i][j-1]+insCostInt),arr[i-1][j-1]+updCostUsed);
            }
        }
        return arr[sLen][dLen];
    }
    
    @FXML
    private void generate(ActionEvent event) {
        Similarity.setText("");
        EDistance.setText("");
        editScriptOutput.setText("");
        if(sourcePath.getText().equals("") || destPath.getText().equals("")){
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Please Choose a File");
            a.showAndWait();
        }else
            if(delCost.getText().equals("") || insCost.getText().equals("") || UpdCost.getText().equals("")){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Please provide costs!");
                a.showAndWait();
            }else{
            try{
                if(!(delCost.getText().matches("[0-9]+")) || !(insCost.getText().matches("[0-9]+")) || !(UpdCost.getText().matches("[0-9]+"))){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Supply valid costs!");
                    a.showAndWait();
                }else{
                    delCostInt=Integer.parseInt(delCost.getText());
            insCostInt=Integer.parseInt(insCost.getText());
            UpdCostInt=Integer.parseInt(UpdCost.getText());
            ED=getLevDist();
            EDistance.setText(Ddf.format(ED) + "");
            Similarity.setText(Sdf.format(1/(1+ED)));
            editScriptSteps.clear();
            editScript(arr, sLen, dLen);
            printEScript(editScriptSteps);
            editScriptOutput.setText(ES);
                }
            }catch(Exception e){
                
            }
            }
    }
    
    public static void randomizeSource(){
        S="";
        for(int i=0;i<sLen;i++){
            char charI=sourceU.get(i);
            if(charI=='N'){
                random=rnd.nextInt(4);
                switch(random){
                    case(0):
                        sourceU.set(i, 'G');
                        break;
                    case(1):
                        sourceU.set(i, 'U');
                        break;
                    case(2):
                        sourceU.set(i, 'A');
                        break;
                    case(3):
                        sourceU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                S+=random;
            }else if(charI=='V'){
                random=rnd.nextInt(3);
                switch(random){
                    case(0):
                        sourceU.set(i, 'G');
                        break;
                    case(1):
                        sourceU.set(i, 'A');
                        break;
                    case(2):
                        sourceU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                S+=random;
            }else if(charI=='S'){
                random=rnd.nextInt(2);
                switch(random){
                    case(0):
                        sourceU.set(i, 'G');
                        break;
                    case(1):
                        sourceU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                S+=random;
            }else if(charI=='M'){
                random=rnd.nextInt(2);
                switch(random){
                    case(0):
                        sourceU.set(i, 'A');
                        break;
                    case(1):
                        sourceU.set(i, 'C');
                        break;
                    default:
                        break;
                }
                S+=random;
            }else if(charI=='R'){
                random=rnd.nextInt(2);
                switch(random){
                    case(0):
                        sourceU.set(i, 'G');
                        break;
                    case(1):
                        sourceU.set(i, 'A');
                        break;
                    default:
                        break;
                }
                S+=random;
            }
        }
    }
    
    public static void editScript(int[][] arr,int row,int column){
        if(row==0 && column==0){
        }else if(row==0 && column>0){
            toDo="Insert(D" + column + "," + destination.charAt(column-1) + ")";
            editScriptSteps.add(0,toDo);
            editScript(arr, row, column-1);
        }else if(row>0 && column==0){
            toDo="Delete(S" + row + "," + source.charAt(row-1) + ")";
            editScriptSteps.add(0,toDo);
            editScript(arr, row-1, column);
        }else{
            int currentPos=arr[row][column];
            int insertPrev=arr[row][column-1];
            int deletePrev=arr[row-1][column];
            int updatePrev=arr[row-1][column-1];
            int min=Math.min(Math.min(insertPrev, deletePrev), updatePrev);
            if(min==updatePrev && currentPos==updatePrev){
                editScript(arr, row-1, column-1);
            }else if((currentPos-UpdCostInt)==updatePrev){
                toDo="Update(D" + column + "," + destination.charAt(column-1) + "," + source.charAt(row-1) + ")";
                editScriptSteps.add(0,toDo);
                editScript(arr, row-1, column-1);
            }else if((currentPos-delCostInt)==deletePrev){
                toDo="Delete(S" + row + "," + source.charAt(row-1) + ")";
                editScriptSteps.add(0,toDo);
                editScript(arr, row-1, column);
            }else{
                toDo="Insert(D" + column + "," + destination.charAt(column-1) + ")";
                editScriptSteps.add(0,toDo);
                editScript(arr, row, column-1);
            }
        }
    }
    
    public static void printEScript(ArrayList<String> editScriptSteps){
        ES="";
        XMLES="";
        XMLES=XMLES + "ES:" + "&lt;";
        ES=ES + "ES:<";
        for(int i=0;i<editScriptSteps.size();i++){
            if(i==editScriptSteps.size()-1){
                ES=ES + editScriptSteps.get(i);
                XMLES=XMLES + editScriptSteps.get(i);
            }else{
                ES=ES + editScriptSteps.get(i) + ",";  
                XMLES=XMLES + editScriptSteps.get(i) + ","; 
            }
        }
        ES+=">";
        XMLES+="&gt;";
    }
}
