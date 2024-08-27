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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Nurusan
 */
public class StudentController implements Initializable {

     @FXML
    private TextField age;

    @FXML
    private TableColumn<Data, Integer> col_age;

    @FXML
    private TableColumn<Data, String> col_course;

    @FXML
    private TableColumn<Data, String> col_department;

    @FXML
    private TableColumn<Data, String> col_First;

    @FXML
    private TableColumn<Data, String> col_Last;

    @FXML
    private TableColumn<Data, Integer> col_phone;

    @FXML
    private TableColumn<Data, Integer> col_sID;

    @FXML
    private TableColumn<Data, String> col_sex;

    @FXML
    private TextArea course;

    @FXML
    private TextField department;

     @FXML
    private TextField first;

    @FXML
    private TextField last;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<?> sex;

    @FXML
    private TextField S_ID;
    
    @FXML
    private Button home_btn;

    @FXML
    private TableView<Data> table_view;
    
     @FXML
    void home(ActionEvent event) {
             try{
                home_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("DashBored.fxml"));
                Stage stage=new Stage();
                Scene scene = new Scene(root);
        
                stage.setScene(scene);
                stage.show();
             }catch(Exception e){e.printStackTrace();}
    }
    
    private String[] comboSex={"Male","Female"};
    public void comboBoxSex(){
        List<String> list = new ArrayList<>();
        for(String data:comboSex){
            list.add(data);
        }
        ObservableList dataList =FXCollections.observableArrayList(list);
        sex.setItems(dataList);
    }
    public void defualtId(){
        S_ID.setStyle("-fx-border-width:2px; -fx-background-color:#fff;");
    }
    
    private Connection connect;
    private PreparedStatement prepared;
    private Statement statement;
    private ResultSet result;
    
    public static Connection connectDb(){
        try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connect =DriverManager.getConnection("jdbc:mysql://localhost/manage", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
    public ObservableList<Data> studentDataList(){
        connect =connectDb();
        ObservableList<Data> datalist= FXCollections.observableArrayList();
        String sql="SELECT * FROM student";
        try{
            prepared =connect.prepareStatement(sql);
            result =prepared.executeQuery();
            Data data;
            while(result.next()){
                data = new Data(result.getInt("S_ID"),result.getString("first"),result.getString("last"),
                        result.getInt("age"),result.getString("sex"),
                        result.getInt("phone"),result.getString("department"),result.getString("course"));
                            
                        datalist.add(data);
            }
        }catch(Exception e){e.printStackTrace();}
        return datalist;
    }
    private ObservableList<Data> showlist;
    public void showData(){
        showlist= studentDataList();
        
        col_sID.setCellValueFactory(new PropertyValueFactory<>("S_ID"));
        col_First.setCellValueFactory(new PropertyValueFactory<>("first"));        
        col_Last.setCellValueFactory(new PropertyValueFactory<>("last"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        table_view.setItems(showlist);
    }
   
    public void studentSelectData(){
        Data data = table_view.getSelectionModel().getSelectedItem();
        int num=table_view.getSelectionModel().getSelectedIndex();
        
        if((num-1)< - 1) 
            return;
        S_ID.setText(String.valueOf(data.getS_ID()));
        first.setText(data.getFirst()); 
        last.setText(data.getLast()); 
        age.setText(String.valueOf(data.getAge()));
        sex.getSelectionModel();
        phone.setText(String.valueOf(data.getPhone()));
        department.setText(data.getDepartment());
        course.setText(data.getCourse());
        
   }
    
    public void studentAdd(){
        
        connect=connectDb();
        
        try{
            if(S_ID.getText().isEmpty() || first.getText().isEmpty() || last.getText().isEmpty()
                    || age.getText().isEmpty() || sex.getSelectionModel().getSelectedItem() ==null
                    || phone.getText().isEmpty() || department.getText().isEmpty()
                    || course.getText().isEmpty()){
             JOptionPane.showMessageDialog(null,"please fill all blank fields");

            }else{
                String checkData = "SELECT S_ID FROM student WHERE S_ID = "+ S_ID.getText();
                
                prepared = connect.prepareStatement(checkData);
                result= prepared.executeQuery();
                
                if(result.next()){
                 JOptionPane.showMessageDialog(null,"ID: "+ S_ID.getText()+ "is already taken");

                }else{
                   JOptionPane.showMessageDialog(null,"You Successfuly added a student");
    
                   String insertData = "INSERT INTO student (S_ID,first,last,age,sex,phone,department,course)"
                              +"VALUES(?,?,?,?,?,?,?,?)";
                   prepared=connect.prepareStatement(insertData);
                   prepared.setString(1, S_ID.getText());
                   prepared.setString(2, first.getText());
                   prepared.setString(3, last.getText());                   
                   prepared.setString(4, age.getText());
                   prepared.setString(5, (String)sex.getSelectionModel().getSelectedItem());
                   prepared.setString(6, phone.getText());
                   prepared.setString(7, department.getText());
                   prepared.setString(8, course.getText());
               
                   prepared.executeUpdate();
               
                   showData();
                   clear();
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void update(){
        connect= connectDb();
        
        String sql= " UPDATE student SET "
                +" first = '" + first.getText()
                + "', last = '" + last.getText()
                + "', age = '" + age.getText() 
                + "', sex = '" + sex.getSelectionModel().getSelectedItem()
                + "', phone = '" + phone.getText() 
                + "', department = '" + department.getText() 
                + "', course = '" + course.getText()
                + "' WHERE S_ID = " + S_ID.getText();
        
        try{
            
            JOptionPane.showConfirmDialog(null,"are you shure you want to update ");
           
            prepared=connect.prepareStatement(sql);
            prepared.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"successfully updated");
            
            showData();
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void Remove(){
        connect =connectDb();
        if(S_ID.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"please select a student to remove!!");
        }else{
            
        try{
            JOptionPane.showConfirmDialog(null,"are you shure you want to remove this student ");
        String sql = "DELETE FROM student WHERE S_ID = "+ S_ID.getText();
                
                prepared = connect.prepareStatement(sql);
                prepared.executeUpdate();
           
                JOptionPane.showMessageDialog(null,"successfully deleted");
            
            showData();
            clear();
        }catch(Exception e){e.printStackTrace();}
        
        }
    }
    public void clear(){
        S_ID.setText("");
        first.setText("");
        last.setText("");
        age.setText("");
        sex.getSelectionModel().clearSelection();
        phone.setText("");
        department.setText("");
        course.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxSex();
        defualtId();
        showData();
        
        
    }    
    
}
