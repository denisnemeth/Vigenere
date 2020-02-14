package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class Controller {

    public TextField txtfld_key;
    public Label lbl_selectedFile;
    public TextArea txtarea;
    public Button btn_encrypt;
    public Button btn_decrypt;
    public Button btn_content;
    public Label lbl_encryptSuccess;
    public Label lbl_decryptSuccess;
    public Label lbl_key;
    FileChooser fc = new FileChooser();
    File file = null;

    public void browseFiles(ActionEvent actionEvent) {
        file = fc.showOpenDialog(new Stage());
        if (file != null) {
            lbl_selectedFile.setText(file.getName());
            btn_content.setDisable(false);
            txtfld_key.setDisable(false);
            lbl_key.setVisible(true);
        } else {
            lbl_selectedFile.setText("Please select a file!");
            btn_content.setDisable(true);
            txtfld_key.setDisable(true);
            lbl_key.setVisible(false);
            btn_encrypt.setDisable(true);
            btn_decrypt.setDisable(true);
            lbl_encryptSuccess.setVisible(false);
            lbl_decryptSuccess.setVisible(false);
            txtarea.setText("");
        }
    }
    public void enableButtons(KeyEvent keyEvent) {
        if (!txtfld_key.getText().trim().isEmpty()) {
            btn_encrypt.setDisable(false);
            btn_decrypt.setDisable(false);
        } else {
            btn_encrypt.setDisable(true);
            btn_decrypt.setDisable(true);
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
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedWriter writer;
            if (file.getName().contains("_decrypt.txt")) {
                String[] splitName = file.getName().split("_");
                writer = new BufferedWriter(new FileWriter(splitName[0] + ".txt"));
            } else if (file.getName().contains("_encrypt.txt")) {
                writer = new BufferedWriter(new FileWriter(file.getName()));
            } else {
                String[] splitName = file.getName().split("\\.");
                writer = new BufferedWriter(new FileWriter(splitName[0] + "_encrypt.txt"));
            }
            String key = txtfld_key.getText().toUpperCase();
            StringBuilder encryptedText = new StringBuilder();
            int pos = 0, startValue, endValue;
            while (((startValue = reader.read()) != -1) && pos <= key.length()) {
                if (pos == key.length()) {
                    pos = 0;
                }
                if (startValue < 65 || (startValue > 90 && startValue < 97) || startValue > 122) {
                    endValue = startValue;
                    pos--;
                } else {
                    endValue = startValue + ((key.charAt(pos)) - 65);
                    if (startValue <= 90 && endValue > 90 || endValue > 122) {
                        endValue -= 26;
                    }
                }
                pos++;
                encryptedText.append((char) endValue);
            }
            lbl_encryptSuccess.setVisible(true);
            writer.write(encryptedText.toString());
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void decrypt(ActionEvent actionEvent) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedWriter writer;
            if (file.getName().contains("_encrypt.txt")) {
                String[] splitName = file.getName().split("_");
                writer = new BufferedWriter(new FileWriter(splitName[0] + ".txt"));
            } else if (file.getName().contains("_decrypt.txt")) {
                writer = new BufferedWriter(new FileWriter(file.getName()));
            } else {
                String[] splitName = file.getName().split("\\.");
                writer = new BufferedWriter(new FileWriter(splitName[0] + "_decrypt.txt"));
            }
            String key = txtfld_key.getText().toUpperCase();
            StringBuilder encryptedText = new StringBuilder();
            int pos = 0, startValue, endValue;
            while (((startValue = reader.read()) != -1) && pos <= key.length()) {
                if (pos == key.length()) {
                    pos = 0;
                }
                if (startValue < 65 || (startValue > 90 && startValue < 97) || startValue > 122) {
                    endValue = startValue;
                    pos--;
                } else {
                    endValue = startValue - ((key.charAt(pos)) - 65);
                    if (endValue < 65 || startValue >= 97 && endValue < 97) {
                        endValue += 26;
                    }
                }
                pos++;
                encryptedText.append((char) endValue);
            }
            lbl_decryptSuccess.setVisible(true);
            writer.write(encryptedText.toString());
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
