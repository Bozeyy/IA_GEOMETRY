module com.smartdash.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.jfree.jfreechart;
    requires java.desktop;


    opens com.smartdash.project to javafx.fxml;
    exports com.smartdash.project.demo;
    opens com.smartdash.project.demo to javafx.fxml;
    exports com.smartdash.project.graphique;
    opens com.smartdash.project.graphique to javafx.fxml;
    exports com.smartdash.project.vue;
    opens com.smartdash.project.vue to javafx.fxml;
    exports com.smartdash.project.modele;
    opens com.smartdash.project.modele to javafx.fxml;
    exports com.smartdash.project.controller;
    opens com.smartdash.project.controller to javafx.fxml;
}