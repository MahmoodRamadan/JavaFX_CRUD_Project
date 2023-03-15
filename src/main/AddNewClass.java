/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static java.awt.SystemColor.window;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author smile
 */
public class AddNewClass extends HBox {

    TextField fname=new TextField();
        TextField lname= new TextField();
        TextField age=new TextField();
        

    public AddNewClass() {
    
                fname.setPromptText("first name");
                fname.setMinWidth(100);
                 lname.setPromptText("lname");
                age.setPromptText("age");
                
                Button save =new Button("save");

                getChildren().addAll(fname,lname,age,save);

                HBox panel2 = new HBox();
		 panel2.getChildren().add(panel2);
                 
                
                
                
    }
    
    
}
