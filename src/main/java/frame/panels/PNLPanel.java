package frame.panels;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class PNLPanel extends JPanel{

    private Connection connection;

    public PNLPanel(Connection connection){
        this.connection = connection;
        setLayout(new BorderLayout());
    }
}
