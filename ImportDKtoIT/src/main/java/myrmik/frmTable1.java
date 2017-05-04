package myrmik;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;



/**
 * This model work with any database table. You should only set an object of java.sql.ResultSet into it.
 * This table cannot insert data into database.
 */



//DatabaseTableModel

public class frmTable1 extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> columnNames = new ArrayList<String>();
    private ArrayList<Class> columnTypes = new ArrayList<Class>();
    private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

    @Override
    public int getRowCount() {
        synchronized (data) {
            return data.size();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        synchronized (data) {
            return data.get(row).get(col);
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    @Override
    public Class getColumnClass(int col) {
        return columnTypes.get(col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object obj, int row, int col) {
        synchronized (data) {
            data.get(row).set(col, obj);
        }
    }

    /**
     * Core of the model. Initializes column names, types, data from ResultSet.
     *
     * @param rs ResultSet from which all information for model is token.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setDataSource(ResultSet rs) throws SQLException, ClassNotFoundException {
        ResultSetMetaData rsmd = rs.getMetaData();
        columnNames.clear();
        columnTypes.clear();
        data.clear();

        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            columnNames.add(rsmd.getColumnName(i + 1));
            Class type = Class.forName(rsmd.getColumnClassName(i + 1));
            columnTypes.add(type);
        }
        fireTableStructureChanged();
        while (rs.next()) {
            ArrayList rowData = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class)
                    rowData.add(rs.getString(i + 1));
                else
                    rowData.add(rs.getObject(i + 1));
            }
            synchronized (data) {
                data.add(rowData);
                this.fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        }
    }

    //***TEST***
    public static void main(String[] args) {
        String DB_NAME = "iot_test";

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            String url = "database_url";                //your data
//            String user = "user_name";                  //your data
//            String password = "password";               //your data
//            String query = "select * from table_name";  //your data
//            Connection con = DriverManager.getConnection("jdbc:mysql://" + url, user, password);


            String connectionString =
                    "jdbc:sqlserver://192.168.10.8:1433;"
                            + "database=" + DB_NAME + ";"
                            + "user=sa;"
                            + "password=291263;"
                            + "loginTimeout=30;";

            Connection con = DriverManager.getConnection(connectionString);

            String query = "select TOP 1000 * from DOG order by KDOG";  //your data
            //String query = "select TOP 1000 UNDOG,ORG,KDGT,KDOG from DOG order by KDOG";  //your data

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            //DatabaseTableModel model = new DatabaseTableModel();
            frmTable1 model = new frmTable1();
            model.setDataSource(rs);


            con.close();


            JTable table = new JTable(model);

            JPanel panel = new JPanel(new BorderLayout());

            JScrollPane scrollPane1 = new JScrollPane(table);
            scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


            panel.add(scrollPane1, BorderLayout.CENTER);
            JFrame frame = new JFrame("Database Table Model");
            frame.setLocationRelativeTo(null);
            frame.setSize(500, 400);
            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}