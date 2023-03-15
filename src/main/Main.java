package main;

import java.sql.*;
import static java.awt.SystemColor.text;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Main extends Application {

    Stage window;
    TableView<Resources> table;
    TableView tablee;
    Scene scene1;
    Scene scene2;
    Scene scene3;
    ObservableList<Resources> data;
    VBox panel;
    String url = "jdbc:mysql://localhost:3307/l";
    String user = "root";
    String password = "1234";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs;
    String sql;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        conn = DriverManager.getConnection(url, user, password);

        //  stmt = conn.createStatement();
        // String sql = "select * from l";
        // "insert into person(fname,lname,age)\n" +
//"    VALUES(\"ahmad\",\"naser\",20);";
        //stmt.executeUpdate(sql);
        sql = "SELECT * FROM person";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        data = FXCollections.observableArrayList();

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            int age = rs.getInt("age");
            int id = rs.getInt("id");
            Resources person = new Resources(fname, lname, age, id);
            data.add(person);
        }
        if (rs.next() == false) {
            System.out.println("nullll");
        }
        panel = new VBox();
        table = new TableView<>();
        table.setEditable(true);

        table.setItems(data);

        TableColumn<Resources, String> fName = new TableColumn<Resources, String>("Fname");
        fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        //fName.setCellFactory(TextFieldTableCell.forTableColumn());
        //fName.setEditable(true);
        fName.setMinWidth(200);

        TableColumn<Resources, String> lName = new TableColumn<Resources, String>("lName");
        lName.setCellValueFactory(new PropertyValueFactory<>("lName"));
        //lName.setCellFactory(TextFieldTableCell.forTableColumn());
        //lName.setEditable(true);
        lName.setMinWidth(200);

        TableColumn<Resources, Integer> age = new TableColumn<Resources, Integer>("age");
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
       // age.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
       // age.setEditable(true);

        TableColumn<Resources, Void> deleteCol = new TableColumn<>("Action1");
        TableColumn<Resources, Void> editCol = new TableColumn<>("Action2");

        deleteCol.setCellFactory(column -> {
            return new TableCell<Resources, Void>() {
                Button delete = new Button("delete");

                {
                    delete.setOnAction(setOnAction -> {
                        // Resources person = getTableView().getItems().get(getIndex());
                        Resources selectedObject = table.getSelectionModel().getSelectedItem();
                        if (selectedObject != null) {
                            try {
                                //System.out.println(selectedObject.getFName());
                                data.remove(selectedObject);
                                sql = "DELETE FROM person where id=?";
                                PreparedStatement pstmt = conn.prepareStatement(sql);
                                pstmt.setInt(1, selectedObject.getId());
                                pstmt.executeUpdate();
                               // stmt = conn.createStatement();
                                //stmt.executeUpdate(sql);
                                table.refresh();
                            } catch (SQLException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(delete);
                    }
                }
            };
        });
        editCol.setCellFactory(column -> {
            return new TableCell<Resources, Void>() {
                Button edit = new Button("edit");

                {
                    edit.setOnAction(setOnAction -> {
                        HBox root = new HBox();
                        TextField fname = new TextField();
                        TextField lname = new TextField();
                        TextField age = new TextField();
                        TextField id = new TextField();
                        fname.setMinWidth(100);

                        Button save = new Button("save");
                        save.setOnAction(e -> {
                            updateIte(fname, lname,age);
                        });
                        Button back = new Button("back");
                        back.setOnAction(e -> {
                           table.refresh();

                            window.setScene(scene1);
                            window.show();
                        });

                        root.getChildren().addAll(fname, lname, age, save, back);

                        scene2 = new Scene(root, 650, 500);

                        window.setScene(scene2);
                        window.show();

                       // updateIte(fname.toString(), lname.toString(), Integer.parseInt(age.getText()));
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(edit);
                    }
                }
            };
        });

        table.getColumns().add(fName);
        table.getColumns().add(lName);
        table.getColumns().add(age);
        table.getColumns().add(deleteCol);
        table.getColumns().add(editCol);

        //table.setItems(getResources());
        Button addNew = new Button("add new");
        //Label edit = new Label("click on the cell to edit it");
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(addNew);

        panel.setStyle("-fx-font-size: 17px;-fx-color:red;-fx-color-text: blue; -fx-font-weight: bold;");

        panel.getChildren().addAll(table, hbox);
        scene1 = new Scene(panel,650, 500);
        window = new Stage();
        window.setTitle("table");

        addNew.setOnAction(e -> {

            changeScene();

        });

        window.setScene(scene1);
        window.show();

    }

   private void updateIte(TextField fname, TextField lname, TextField age) {
    Resources selectedObject = table.getSelectionModel().getSelectedItem();
    if (selectedObject != null) {
        String sql = "UPDATE person SET fname=?, lname=?, age=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fname.getText());
            pstmt.setString(2, lname.getText());
            pstmt.setInt(3, Integer.parseInt(age.getText()));
            pstmt.setInt(4, selectedObject.getId());
            pstmt.executeUpdate();
            Resources updatedResource = new Resources(fname.getText(), lname.getText(), Integer.parseInt(age.getText()), selectedObject.getId());
            data.set(data.indexOf(selectedObject), updatedResource);
            fname.clear();
            lname.clear();
            age.clear();
            table.refresh();
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    private void addNewRow(TextField fname, TextField lname, TextField age) throws SQLException {
        Resources person = new Resources(fname.getText(), lname.getText(), Integer.parseInt(age.getText()));
        sql = "insert into person (fname, lname, age) values (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, fname.getText());
        pstmt.setString(2, lname.getText());
        pstmt.setInt(3, Integer.parseInt(age.getText()));
        pstmt.executeUpdate();
        table.getItems().add(person);
        fname.clear();
        lname.clear();
        age.clear();

    }

    private void changeScene() {

        HBox root = new HBox();
        TextField fname = new TextField();
        TextField lname = new TextField();
        TextField age = new TextField();
        TextField id = new TextField();
        fname.setPromptText("first name");
        fname.setMinWidth(100);
        lname.setPromptText("lname");
        age.setPromptText("age");

        Button save = new Button("save");
        save.setOnAction(e -> {
            try {
                addNewRow(fname, lname, age);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Button back = new Button("back");
        back.setOnAction(e -> {

            window.setScene(scene1);
            window.show();
        });

        root.getChildren().addAll(fname, lname, age, save, back);

        scene2 = new Scene(root, 500, 500);

        window.setScene(scene2);
        window.show();

    }
}

/* public ObservableList  getResources(){

                ObservableList <Resources>  data = FXCollections.observableArrayList(
		 new Resources("mahmood", "ramadan", 22),
		 new Resources("nader", "hazem", 70),
		 new Resources("ahmad", "sami", 20),
		 new Resources("hana", "hasan", 50),
		 new Resources("ameed","abd", 15) );
                 return data;}     */
