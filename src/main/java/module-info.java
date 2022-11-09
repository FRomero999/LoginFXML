module com.mycompany.loginfxml {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens com.mycompany.loginfxml to javafx.fxml;
    exports com.mycompany.loginfxml;
}
