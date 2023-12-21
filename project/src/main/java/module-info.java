module com.smartdash.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.smartdash.project to javafx.fxml;
    exports com.smartdash.project;
}