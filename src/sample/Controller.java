package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class Controller {

    public TextField txtfld_key;
    public Label lbl_selectedFile;
    public TextArea txtarea;
    FileChooser fc = new FileChooser();
    File file = null;

    public void browseFiles(ActionEvent actionEvent) {
        file = fc.showOpenDialog(new Stage());
        if (file != null) {
            lbl_selectedFile.setText(file.getName());
        } else {
            lbl_selectedFile.setText("Please select a file!");
        }
    }
    public void showContent(ActionEvent actionEvent) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line, data = "";
            while ((line = reader.readLine()) != null) {
                data = data + line + "\n";
            }
            txtarea.setText(data);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void encrypt(ActionEvent actionEvent) {
    }
    public void decrypt(ActionEvent actionEvent) {
    }
}
