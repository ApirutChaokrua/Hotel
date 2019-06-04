package staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.control.ChoiceBox;

public class NewStaffUI implements Initializable {
    StaffSystem staffSystem= new StaffSystem();
    ArrayList<Staff> userArrayList = staffSystem.getStaff();
    ArrayList<UserNoButton>userNoButtons= StaffList.userNoButtons;
    ObservableList<String> cursors = FXCollections.observableArrayList("User","Admin");
    FileChooser fileChooser = new FileChooser();
    File file = new File("src/img/icon/photoUser.png");;
    @FXML
    private Label userID;

    @FXML
    private JFXButton btnCancel = new JFXButton();

    @FXML
    private JFXButton btnSave= new JFXButton();

    @FXML
    private TextField firstName = new TextField();

    @FXML
    private TextField lastName = new TextField();

    @FXML
    private TextField country = new TextField();

    @FXML
    private TextField tel = new TextField();

    @FXML
    private TextField email = new TextField();

    @FXML
    private TextArea address = new TextArea();

    @FXML
    private TextField userName = new TextField();

    @FXML
    private TextField idCard = new TextField();

    @FXML
    private ChoiceBox<String> userType = new ChoiceBox<String>();

    @FXML
    private TextField pass= new TextField();

    @FXML
    private ImageView image = new ImageView();

    @FXML private JFXButton btnImage = new JFXButton();

    @FXML
    private TextField passWord= new TextField();
    @FXML
    private TextField role = new TextField();
    @FXML private Label chUser = new Label();

    @FXML private Label chEmail = new Label();

    @FXML
    private JFXButton changeIm=new JFXButton();

    @FXML
    private ImageView ima = new ImageView();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        chUser.setText("");
        chEmail.setText("");
        if(event.getSource()==btnSave) {
            boolean ch1=true,ch2=true,ch3=true,ch4=true,ch5=true,ch6=true,ch7=true,ch8=true;

            for(int i=0;i<userArrayList.size();i++){
                if(userArrayList.get(i).getUserName().equals(userName.getText()))ch1=false;
                if(userArrayList.get(i).getEmail().equals(email.getText()))ch2=false;
            }
            if (userName.getText().equals(""))ch3=false;
            if (email.getText().equals(""))ch4=false;
            if (firstName.getText().equals(""))ch5=false;
            if(lastName.getText().equals(""))ch6=false;
            if(passWord.getText().equals(""))ch8=false;
            if((ch1&&ch2&&ch3&&ch4&&ch5&&ch6&&ch7&&ch8)||(userArrayList.size()==0)) {
                Staff a = new Staff(Integer.toString(StaffList.employeeId), userName.getText(), firstName.getText(), lastName.getText(),
                        idCard.getText(), country.getText(), tel.getText(), email.getText(), address.getText(),
                        userType.getValue(), role.getText(), new Button(), new Button(), pass.getText(), passWord.getText(),file.toURI().toString());
                UserNoButton b = new UserNoButton(Integer.toString(StaffList.employeeId), userName.getText(), firstName.getText(), lastName.getText(),
                        idCard.getText(), country.getText(), tel.getText(), email.getText(), address.getText(),
                        userType.getValue(), role.getText(), pass.getText(), passWord.getText(),file.toURI().toString());
                StaffSystem staffSystem = new StaffSystem();
                staffSystem.newStaff(a,b);
                System.out.println("New User");
                StaffList.employeeId++;
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();
                StaffPageUI mu = new StaffPageUI();
                mu.update();
            }
            if(ch3==false||ch4==false||ch5==false||ch6==false||ch7==false||ch8==false){
                chUser.setText("Please enter : Username,Password,Fristname,Lastname and Email ");
            }else {
                if (ch1 == false) chUser.setText("This username already taken.");
                if (ch2 == false) chEmail.setText("This email already taken.");
            }

        }else if(event.getSource()==btnCancel){
            System.out.println("Cancel");
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }else if(event.getSource()==btnImage){
            //image.setImage(new Image("src/staff/logo.png"));
        }else if(event.getSource()==changeIm){
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            file = fileChooser.showOpenDialog(stage);
            System.out.println(file.toURI().toString());
            String a = file.toURI().toString();
            ima.setImage(new Image(a));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setOnAction(this::handleButtonAction);
        btnCancel.setOnAction(this::handleButtonAction);
        btnImage.setOnAction(this::handleButtonAction);
        userID.setText(Integer.toString(StaffList.employeeId));
        userType.getItems().addAll("User","Admin");
        userType.setValue("User");
        changeIm.setOnAction(this::handleButtonAction);
    }
}
