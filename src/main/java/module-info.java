module com.seanmlee.wgu.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.seanmlee.wgu.inventorymanagement to javafx.fxml;
    opens com.seanmlee.wgu.inventorymanagement.Controller;
    opens com.seanmlee.wgu.inventorymanagement.Model;
    exports com.seanmlee.wgu.inventorymanagement;
    exports com.seanmlee.wgu.inventorymanagement.Controller;
    exports com.seanmlee.wgu.inventorymanagement.Model;
}