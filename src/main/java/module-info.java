module com.usta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.usta to javafx.fxml;
    exports com.usta;
}
