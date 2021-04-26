/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idpaprojectfinal;

import static idpaprojectfinal.IDPAProjectFinal.window;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
 * @author PC-A
 */
public class MainPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void patching(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("Patching.fxml"));
        Scene scene=new Scene(root);
        window.setTitle("RNA Sequences Patching Tool");
        window.setResizable(false);
        window.setScene(scene);
        window.centerOnScreen();
    }

    @FXML
    private void editScript(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("EditScript.fxml"));
        Scene scene=new Scene(root);
        window.setTitle("RNA Sequences Differencing Tool");
        window.setResizable(false);
        window.setScene(scene);
        window.centerOnScreen();
    }
    
}
