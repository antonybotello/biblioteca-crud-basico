module com.usta {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.usta to javafx.fxml;
    exports com.usta;
}
