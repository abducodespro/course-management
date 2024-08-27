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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Nurusan
 */
public class FXMLDocumentController implements Initializable {
    
     @FXML
    private Button btn_login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void login(){
        
    try{       
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://localhost/manage", "root", "");
        String sql="SELECT *FROM admin WHERE username =? and password=?"; 
        prepare=connect.prepareStatement(sql);
        prepare.setString(1,username.getText());
        prepare.setString(2,password.getText());
        result=prepare.executeQuery();
        
        
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"username and password blank");
        }else{
                if(result.next()){            
              
                btn_login.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("DashBored.fxml"));
                Stage stage=new Stage();
                Scene scene = new Scene(root);
        
                stage.setScene(scene);
                stage.show();
                }else{
                   JOptionPane.showMessageDialog(null,"Wrong username and password");
                }
                }
        
    }catch(Exception e){e.printStackTrace();}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
