package myrmik;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;




public class frmTable2 extends JPanel {

    public frmTable2(int width, int hight, Connection conn) throws SQLException {
        MAX_HEIGHT = hight;
        MAX_WIDTH = width;
        setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));


        myTableModel = new MyTableModel(conn, "Shop");
        table = new JTable(myTableModel);
        myTableModel.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {

            }
        });

        pane = new JScrollPane(table);
        stat = conn.createStatement();

        add(pane, BorderLayout.CENTER);
        add(idLabel);
        add(idField);
        add(addressLabel);
        add(addressField);
        add(nameLabel);
        add(nameField);

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addRecord();
                } catch (SQLException ex) {
                    Logger.getLogger(frmTable2.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        });
        delButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delRecord();
                } catch (SQLException ex) {
                    Logger.getLogger(frmTable2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        add(addButton);

        add(delButton);
    }

    private void addRecord() throws SQLException {
        stat.executeUpdate("INSERT INTO mydb.shop (shop_id, name, address) " +
                "VALUES  (" + Integer.valueOf(idField.getText()) + ",'"
                + nameField.getText() + "','"
                + addressField.getText() + "');");
        myTableModel.reload();
        table.repaint();


    }

    private void delRecord() throws SQLException {
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        Object value = table.getValueAt(row, column);

        stat.executeUpdate("DELETE FROM mydb.shop WHERE "
                + table.getColumnName(column) + "='"
                + value +"'");
        myTableModel.reload();
        table.repaint();

    }

    public class MyTableModel extends AbstractTableModel {

        protected Connection connection;
        protected String tName;
        protected ResultSetMetaData rm;
        protected ResultSet result;
        protected Vector row;
        protected int col;
        protected Statement stat;
        protected ArrayList<String> columnNames = new ArrayList<String>();

        public MyTableModel(Connection c, String tableName) throws SQLException {
            this.tName = tableName;
            this.connection = c;
            this.
                    reload();
        }

        public void reload() throws SQLException {
            stat = connection.createStatement();
            result = stat.executeQuery("SELECT * FROM " + tName);
            rm = result.getMetaData();
            row = new Vector();

            try {
                col = rm.getColumnCount();
                for (int i = 1; i <= col; i++) {
                    columnNames.add(rm.getColumnName(i));
                }
                while (result.next()) {
                    for (int i = 1; i <= col; i++) {
                        row.addElement(result.getObject(i));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(frmTable2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public int getRowCount() {
            return row.size() / col;
        }

        @Override
        public int getColumnCount() {
            return col;
        }

        @Override
        public String getColumnName(int c) {

            return columnNames.get(c);

        }

        @Override
        public boolean isCellEditable(int r, int c) {
            return true;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            return row.get(rowIndex * col + columnIndex);
        }

        @Override
        public void setValueAt(Object obj, int rowIndex, int columnIndex) {
            int element = rowIndex * col + columnIndex;
            //TODO поменять свойства forign ключей в guys.table
            boolean isString = obj.getClass().getSimpleName().equals("String");

            if (isString) {
                try {
                    stat.executeUpdate("UPDATE "
                            + tName
                            + " SET "
                            + tName + "."
                            + columnNames.get(columnIndex)
                            + "='"
                            + obj
                            + "' WHERE "
                            + tName + "."
                            + columnNames.get(columnIndex)
                            + "='"
                            + row.get(element) + "'");
                    row.remove(element);
                    row.add(element, obj);
                } catch (SQLException ex) {
                    Logger.getLogger(frmTable2.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(table, "Введены неправильные данные");
                }
            }
            else {
                try {
                    stat.executeUpdate("UPDATE "
                            + tName
                            + " SET "
                            + tName + "."
                            + columnNames.get(columnIndex)
                            + "="
                            + obj
                            + " WHERE "
                            + tName + "."
                            + columnNames.get(columnIndex)
                            + "="
                            + row.get(element));
                    row.remove(element);
                    row.add(element, obj);
                } catch (SQLException ex) {
                    Logger.getLogger(frmTable2.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(table, "Введены неправильные данные");
                }
            }
        }
    }
    private MyTableModel myTableModel;
    private Statement stat;
    private JScrollPane pane;
    private JLabel idLabel = new JLabel("Номер магазина");
    private JLabel nameLabel = new JLabel("Название магазина");
    private JLabel addressLabel = new JLabel("Адрес магазина");
    private JTextField idField = new JTextField(10);
    private JTextField nameField = new JTextField(45);
    private JTextField addressField = new JTextField(45);
    private JButton addButton = new JButton("Добавить запись");
    private JButton delButton = new JButton("Удалить запись");
    private JTable table;
    private static int MAX_HEIGHT;
    private static int MAX_WIDTH;
}