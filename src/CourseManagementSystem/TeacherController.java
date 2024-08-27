/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseManagementSystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
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
public class TeacherController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField T_ID;

    @FXML
    private TextField age;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_remove;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Data3, Integer> col_T_id;

    @FXML
    private TableColumn<Data3, String> col_age;

    @FXML
    private TableColumn<Data3, String> col_course;

    @FXML
    private TableColumn<Data3, Integer> col_first;

    @FXML
    private TableColumn<Data3, String> col_last;

    @FXML
    private TableColumn<Data3, String> col_sex;

    @FXML
    private TextField course;

    @FXML
    private TextField first;

    @FXML
    private TextField last;

    @FXML
    private ComboBox<?> sex;

    @FXML
    private TableView<Data3> table_views;

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

    private String[] comboSex = {"Male", "Female"};

    public void comboBoxSex() {
        List<String> list = new ArrayList<>();
        for (String data3 : comboSex) {
            list.add(data3);
        }
        ObservableList dataList = FXCollections.observableArrayList(list);
        sex.setItems(dataList);
    }

    public void defualtId() {
        T_ID.setStyle("-fx-border-width:2px; -fx-background-color:#fff;");
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

    public ObservableList<Data3> teacherDataList() {
        connect = connectDb();
        ObservableList<Data3> datalist = FXCollections.observableArrayList();
        String sql = "SELECT * FROM teacher";
        try {
            prepared = connect.prepareStatement(sql);
            result = prepared.executeQuery();
            Data3 data3;
            while (result.next()) {
                data3 = new Data3(result.getInt("T_ID"), result.getString("first"), result.getString("last"),
                        result.getInt("age"), result.getString("sex"),
                        result.getString("course"));

                datalist.add(data3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datalist;
    }
    private ObservableList<Data3> showlist1;

    public void showData() {
        showlist1 = teacherDataList();

        col_T_id.setCellValueFactory(new PropertyValueFactory<>("T_ID"));
        col_first.setCellValueFactory(new PropertyValueFactory<>("first"));
        col_last.setCellValueFactory(new PropertyValueFactory<>("last"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_course.setCellValueFactory(new PropertyValueFactory<>("course"));

        table_views.setItems(showlist1);
    }

    public void teacherSelectData() {
        Data3 data3 = table_views.getSelectionModel().getSelectedItem();
        int num = table_views.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }
        T_ID.setText(String.valueOf(data3.getT_ID()));
        first.setText(data3.getFirst());
        last.setText(data3.getLast());
        age.setText(String.valueOf(data3.getAge()));
        sex.getSelectionModel().clearSelection();
        course.setText(data3.getCourse());

    }

    public void teacherAdd() {

        connect = connectDb();

        try {
            if (T_ID.getText().isEmpty() || first.getText().isEmpty() || last.getText().isEmpty()
                    || age.getText().isEmpty() || sex.getSelectionModel().getSelectedItem() == null
                    || course.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "please fill all blank fields");

            } else {
                String checkData = "SELECT T_ID FROM teacher WHERE T_ID = " + T_ID.getText();

                prepared = connect.prepareStatement(checkData);
                result = prepared.executeQuery();

                if (result.next()) {
                    JOptionPane.showMessageDialog(null, "ID: " + T_ID.getText() + "is already taken");

                } else {
                    JOptionPane.showMessageDialog(null, "You Successfuly added a teacher");

                    String insertData = "INSERT INTO teacher (T_ID,first,last,age,sex,course)"
                            + "VALUES(?,?,?,?,?,?)";
                    prepared = connect.prepareStatement(insertData);
                    prepared.setString(1, T_ID.getText());
                    prepared.setString(2, first.getText());
                    prepared.setString(3, last.getText());
                    prepared.setString(4, age.getText());
                    prepared.setString(5, (String) sex.getSelectionModel().getSelectedItem());
                    prepared.setString(6, course.getText());
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
        T_ID.setText("");
        first.setText("");
        last.setText("");
        age.setText("");
        sex.getSelectionModel().clearSelection();
        course.setText("");
    }

    public void Remove() {
        connect = connectDb();
        if (T_ID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "please select a teacher to remove!!");
        } else {

            try {
                JOptionPane.showConfirmDialog(null, "are you shure you want to remove this teacher ");
                String sql = "DELETE FROM teacher WHERE T_ID = " + T_ID.getText();

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

        String sql = " UPDATE teacher SET "
                + " first = '" + first.getText()
                + "', last = '" + last.getText()
                + "', age = '" + age.getText()
                + "', sex = '" + sex.getSelectionModel().getSelectedItem()
                + "', course = '" + course.getText()
                + "' WHERE T_ID = " + T_ID.getText();

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
        comboBoxSex();
        defualtId();
        showData();
    }

}
