/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseManagementSystem;

import static CourseManagementSystem.StudentController.connectDb;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Nurusan
 */
public class CourseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField C_ID;

    @FXML
    private TableColumn<Data2, Integer> col_C_ID;

    @FXML
    private TableColumn<Data2, String> col_courseName;

    @FXML
    private TableColumn<Data2, Integer> col_creditHour;

    @FXML
    private TextField courseName;

    @FXML
    private TextField creditHour;

    @FXML
    private Button btn_home;

    @FXML
    private TableView<Data2> table_view2;

    public void home(ActionEvent event) {
        try {
            btn_home.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("DashBored.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection connect;
    private PreparedStatement prepared;
    private Statement statement;
    private ResultSet result;

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/manage", "root", "");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Data2> studentDataList() {
        connect = connectDb();
        ObservableList<Data2> datalist = FXCollections.observableArrayList();
        String sql = "SELECT * FROM course";
        try {
            prepared = connect.prepareStatement(sql);
            result = prepared.executeQuery();
            Data2 data2;
            while (result.next()) {
                data2 = new Data2(result.getInt("C_ID"), result.getString("courseName"), result.getInt("creditHour"));

                datalist.add(data2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datalist;
    }
    private ObservableList<Data2> showlist;

    public void showData() {
        showlist = studentDataList();

        col_C_ID.setCellValueFactory(new PropertyValueFactory<>("C_ID"));
        col_courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        col_creditHour.setCellValueFactory(new PropertyValueFactory("creditHour"));
        table_view2.setItems(showlist);
    }

    public void courseSelectData() {
        Data2 data2 = table_view2.getSelectionModel().getSelectedItem();
        int num = table_view2.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }
        C_ID.setText(String.valueOf(data2.getC_ID()));
        courseName.setText(data2.getCourseName());
        creditHour.setText(String.valueOf(data2.getCreditHour()));

    }

    public void addCourse() {
        connect = connectDb();

        try {
            if (C_ID.getText().isEmpty() || courseName.getText().isEmpty()
                    || creditHour.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "please fill all blank fields");

            } else {
                String checkData = "SELECT C_ID FROM course WHERE C_ID = " + C_ID.getText();

                prepared = connect.prepareStatement(checkData);
                result = prepared.executeQuery();

                if (result.next()) {
                    JOptionPane.showMessageDialog(null, "ID: " + C_ID.getText() + "is already taken");

                } else {
                    JOptionPane.showMessageDialog(null, "You Successfuly added a course");

                    String insertData = "INSERT INTO course (C_ID,courseName,creditHour)"
                            + "VALUES(?,?,?)";
                    prepared = connect.prepareStatement(insertData);
                    prepared.setString(1, C_ID.getText());
                    prepared.setString(2, courseName.getText());
                    prepared.setString(3, creditHour.getText());

                    prepared.executeUpdate();

                    showData();
                    clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        C_ID.setText("");
        courseName.setText("");
        creditHour.setText("");

    }

    public void Remove() {
        connect = connectDb();
        if (C_ID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "please select a course to remove!!");
        } else {

            try {
                JOptionPane.showConfirmDialog(null, "are you shure you want to remove this course ");
                String sql = "DELETE FROM course WHERE C_ID = " + C_ID.getText();

                prepared = connect.prepareStatement(sql);
                prepared.executeUpdate();

                JOptionPane.showMessageDialog(null, "successfully deleted");

                showData();
                clear();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {
        connect = connectDb();

        String sql = " UPDATE course SET "
                + " courseName = '" + courseName.getText()
                + "', creditHour = '" + creditHour.getText()
                + "' WHERE C_ID = " + C_ID.getText();

        try {

            JOptionPane.showConfirmDialog(null, "are you shure you want to update ");

            prepared = connect.prepareStatement(sql);
            prepared.executeUpdate();

            JOptionPane.showMessageDialog(null, "successfully updated");

            showData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showData();
    }

}
