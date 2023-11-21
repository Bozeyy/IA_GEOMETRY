module com.example.projetfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetfx to javafx.fxml;
    exports com.example.projetfx;
}