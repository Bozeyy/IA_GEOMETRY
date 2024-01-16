module com.smartdash.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.jfree.jfreechart;


    opens com.smartdash.project to javafx.fxml;
    exports com.smartdash.project;
}