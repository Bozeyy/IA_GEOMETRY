module com.smartdash.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.jfree.jfreechart;
    requires java.desktop;


    opens com.smartdash.project to javafx.fxml;
    exports com.smartdash.project;
    exports com.smartdash.project.demo;
    opens com.smartdash.project.demo to javafx.fxml;
}