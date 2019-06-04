package staff;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.event.ActionEvent;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
public class EditStaffUI implements Initializable {

    StaffSystem staffSystem= new StaffSystem();
    ArrayList<Staff> userArrayList = staffSystem.getStaff();
    ArrayList<UserNoButton>userNoButtons= StaffList.userNoButtons;
    int userCur = StaffList.userCur;
    FileChooser fileChooser = new FileChooser();
    File file;

    @FXML
    private JFXButton changImg = new JFXButton();
    @FXML
    private Label customerID = new Label();
    @FXML
    private JFXButton btnCancel=new JFXButton();
    @FXML
    private JFXButton btnSave = new JFXButton();
    @FXML
    private TextField firstName;//
    @FXML
    private TextField lastName;
    @FXML
    private TextField country;//
    @FXML
    private TextField tel;//
    @FXML
    private TextField email;//
    @FXML
    private TextArea address;//
    @FXML
    private TextField passWord;
    @FXML
    private Label chEmail = new Label();
    @FXML
    private TextField role = new TextField();
    @FXML
    private ChoiceBox<String> userType = new ChoiceBox<String>();
    @FXML
    private ImageView ima = new ImageView();
    @FXML

    private void handleButtonAction(ActionEvent event) {
        boolean ch1=true;
        chEmail.setText("");
        if(event.getSource()==btnSave) {
            for(int i=0;i<userArrayList.size();i++){
                if(userArrayList.get(i).getEmail().equals(email.getText())&& i!=userCur){
                    ch1=false;
                    chEmail.setText("This email already taken.");
                    break;
                }
            }
            if(ch1) {
                userArrayList.get(userCur).setFirstName(firstName.getText());
                userArrayList.get(userCur).setEmail(email.getText());
                userArrayList.get(userCur).setTel(tel.getText());
                userArrayList.get(userCur).setCountry(country.getText());
                userArrayList.get(userCur).setAddress(address.getText());
                userArrayList.get(userCur).setLastName(lastName.getText());
                userArrayList.get(userCur).setPassWord(passWord.getText());
                userArrayList.get(userCur).setRole(role.getText());
                userArrayList.get(userCur).setUserType(userType.getValue());
                userArrayList.get(userCur).setImage(file.toURI().toString());

                userNoButtons.get(userCur).setFirstName(firstName.getText());
                userNoButtons.get(userCur).setEmail(email.getText());
                userNoButtons.get(userCur).setTel(tel.getText());
                userNoButtons.get(userCur).setCountry(country.getText());
                userNoButtons.get(userCur).setAddress(address.getText());
                userNoButtons.get(userCur).setLastName(lastName.getText());
                userNoButtons.get(userCur).setPassWord(passWord.getText());
                userNoButtons.get(userCur).setRole(role.getText());
                userNoButtons.get(userCur).setUserType(userType.getValue());
                userNoButtons.get(userCur).setImage(file.toURI().toString());
            }

            System.out.println("Save");
        }else if(event.getSource()==btnCancel){
            System.out.println("Cancel");
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }else if(event.getSource()==changImg){
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            file = fileChooser.showOpenDialog(stage);
            System.out.println(file.toURI().toString());
            String a = file.toURI().toString();
            ima.setImage(new Image(a));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstName.setText(userArrayList.get(userCur).getFirstName());
        email.setText(userArrayList.get(userCur).getEmail());
        tel.setText(userArrayList.get(userCur).getTel());
        country.setText(userArrayList.get(userCur).getCountry());
        address.setText(userArrayList.get(userCur).getAddress());
        lastName.setText(userArrayList.get(userCur).getLastName());
        btnSave.setOnAction(this::handleButtonAction);
        btnCancel.setOnAction(this::handleButtonAction);
        customerID.setText(userArrayList.get(userCur).getEmployeeId());
        passWord.setText(userArrayList.get(userCur).getPassWord());
        role.setText(userArrayList.get(userCur).getRole());
        userType.getItems().addAll("User","Admin");
        userType.setValue(userArrayList.get(userCur).getUserType());
        changImg.setOnAction(this::handleButtonAction);
        ima.setImage(new Image(userArrayList.get(userCur).getImage()));
    }
}
