module com.smartdash.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.jfree.jfreechart;
    requires java.desktop;
    requires javafx.media;


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

    exports com.smartdash.project.IA;
    exports com.smartdash.project.mvc.modele.objet;
    exports com.smartdash.project.mvc.modele.objet.piques;
    exports com.smartdash.project.mvc.vue.VueInterface;
    opens com.smartdash.project.mvc.vue.VueInterface to javafx.fxml;
    exports com.smartdash.project.terrainAleatoire;
    opens com.smartdash.project.terrainAleatoire to javafx.fxml;
}