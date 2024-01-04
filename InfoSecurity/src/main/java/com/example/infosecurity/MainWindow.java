package com.example.infosecurity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    ObservableList<Objectkey> tableObject = FXCollections.observableArrayList(
            new Objectkey("O", "A"),
            new Objectkey("O", "A"),
            new Objectkey("S", "A"),
            new Objectkey("M", "U")
    );

    ObservableList<Subjects> table = FXCollections.observableArrayList(
            new Subjects("enter r into ", tableObject),
            new Subjects("Delete r from", tableObject),
            new Subjects("Create subject", tableObject),
            new Subjects("Destroy subject", tableObject),
            new Subjects("Create object", tableObject),
            new Subjects("Destroy object", tableObject)
    );

    @FXML
    private Label lbl_output;

    @FXML
    private RadioButton radioButton_admin;

    @FXML
    private RadioButton radioButton_user;

    @FXML
    private TableColumn<Objectkey, String> key;

    @FXML
    private TableColumn<Objectkey, String> object;

    @FXML
    private TableColumn<Subjects, String> subject;

    @FXML
    private TableView<Objectkey> tableObjects;

    @FXML
    private TableView<Subjects> tableSubjects;

    @FXML
    private TextField textfield_key;

    @FXML
    private TextField textfield_object;

    @FXML
    private TextField textfield_subject;

    private void changeSubjectCellEvent(TableColumn.CellEditEvent editEvent) {
        table.get(indexS).setSubject(editEvent.getNewValue().toString());
    }

    @FXML
    void addObjectKey(MouseEvent event) {
        if (!radioButton_user.isSelected() & !radioButton_admin.isSelected()) {
            lbl_output.setText("Вы не выбрали роль");
            return;
        }
        String object = textfield_object.getText().trim();
        String key = textfield_key.getText().trim();
        if (object.isEmpty() || key.isEmpty())return;
        if (key.equals("A") || key.equals("U")) {
            tableObjects.getItems().add(new Objectkey(object, key));
            textfield_object.clear();
            textfield_key.clear();
        } else lbl_output.setText("Вы неверно ввели 'Право доступа', данное поле должно содержать 'A'(права администратора) либо 'U'(права пользователя)");
    }

    @FXML
    void addSubject(MouseEvent event) {
        if (radioButton_user.isSelected() || !radioButton_admin.isSelected()) {
            lbl_output.setText("Вы не можете выполнить это действие");
            return;
        }
        String subject = textfield_subject.getText().trim();
        if (subject.isEmpty()) {
            return;
        }
        table.add(new Subjects(subject, tableObject));
        textfield_subject.clear();
    }

    @FXML
    void deleteObjectKey(MouseEvent event) {
        if (radioButton_user.isSelected() || !radioButton_admin.isSelected()) {
            lbl_output.setText("Вы не можете выполнить это действие");
            return;
        }
        if (index >= 0) tableObjects.getItems().remove(index);
    }

    @FXML
    void deleteSubject(MouseEvent event) {
        if (radioButton_user.isSelected() || !radioButton_admin.isSelected()) {
            lbl_output.setText("Вы не можете выполнить это действие");
            return;
        }
        if (indexS >= 0) table.remove(indexS);
    }


    @FXML
    void check(MouseEvent event) {
        if (radioButton_user.isSelected() || radioButton_admin.isSelected()) {
            if (index >= 0 && indexS >= 0) {
                if (radioButton_user.isSelected() && tableObjects.getItems().get(index).getKey().equals("A")) {
                    lbl_output.setText("Вам запрещено выполнять операцию '" +
                            tableSubjects.getItems().get(indexS).getSubject()
                            + "' по отношению к объекту '" +
                            tableObjects.getItems().get(index).getObject()
                            + "'");
                } else lbl_output.setText("Разрешено");
            } else lbl_output.setText("Вы ничего не выбрали");
        } else lbl_output.setText("Для проверки выберите роль!");
    }

    int indexS = -1;

    @FXML
    void getSelectedSubject(MouseEvent event) {
        indexS = tableSubjects.getSelectionModel().getSelectedIndex();
        if (indexS <= -1) {
            return;
        }
        updateObjects(table.get(indexS).getTable());
    }

    int index = -1;

    @FXML
    void getSelectedObject(MouseEvent event) {
        index = tableObjects.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateSubjects();
    }

    private void updateSubjects() {
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        tableSubjects.setItems(table);

        tableSubjects.setEditable(true);
        subject.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void updateObjects(ObservableList<Objectkey> table) {
        object.setCellValueFactory(new PropertyValueFactory<>("object"));
        key.setCellValueFactory(new PropertyValueFactory<>("key"));

        tableObjects.setItems(table);
    }
}
