package idpaprojectfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IDPAProjectFinal extends Application{

    public static Stage window;
    public static FileChooser.ExtensionFilter xmlFiles;
    
    public static void main(String[] args) {
        xmlFiles=new FileChooser.ExtensionFilter("XML Files", "*.xml");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window=stage;
        Parent root=FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene=new Scene(root);
        window.setTitle("RNA Sequences Differencing and Patching Tool");
        window.setResizable(false);
        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }
}