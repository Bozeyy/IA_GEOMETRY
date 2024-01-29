module com.smartdash.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.jfree.jfreechart;
    requires java.desktop;


    opens com.smartdash.project to javafx.fxml;
    exports com.smartdash.project.demo;
    opens com.smartdash.project.demo to javafx.fxml;
    exports com.smartdash.project.mvc.vue;
    opens com.smartdash.project.mvc.vue to javafx.fxml;
    exports com.smartdash.project.mvc.modele;
    opens com.smartdash.project.mvc.modele to javafx.fxml;
    exports com.smartdash.project.mvc.controller;
    opens com.smartdash.project.mvc.controller to javafx.fxml;
    exports com.smartdash.project;
    exports com.smartdash.project.mvc.vue.VuePique;
    opens com.smartdash.project.mvc.vue.VuePique to javafx.fxml;
}