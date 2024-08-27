/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseManagementSystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nurusan
 */
public class DashBoredController implements Initializable {

    @FXML
    private BorderPane about_brdr;

    @FXML
    private Button btn_Teachers;

    @FXML
    private Button btn_about;

    @FXML
    private Button btn_courses;

    @FXML
    private Button btn_help;

    @FXML
    private Button btn_students;

    @FXML
    private BorderPane help_brdr1;

    @FXML
    private BorderPane manage_brdr;

    @FXML
    private Button manage_btn;

    @FXML
    void swich(ActionEvent event) {
        if (event.getSource() == manage_btn) {
            manage_brdr.setVisible(true);
            about_brdr.setVisible(false);
            help_brdr1.setVisible(false);
        } else if (event.getSource() == btn_about) {
            manage_brdr.setVisible(false);
            about_brdr.setVisible(true);
            help_brdr1.setVisible(false);
        } else if (event.getSource() == btn_help) {
            manage_brdr.setVisible(false);
            about_brdr.setVisible(false);
            help_brdr1.setVisible(true);
        }
    }

    @FXML
    public void ManageStudent() {
        try {
            btn_students.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("Student.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ManageCourse() {
        try {
            btn_courses.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("course.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ManageTeachers(ActionEvent event) {
        try {
            btn_Teachers.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("teacher.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
