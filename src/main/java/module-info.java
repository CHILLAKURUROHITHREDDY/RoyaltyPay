module transaction.royaltypay {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
//    requires mysql.connector.java;


    opens transaction.royaltypay to javafx.fxml;
    exports transaction.royaltypay;
}