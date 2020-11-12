package database;

import static database.PassDB.PASS;
import static database.PassDB.UNAME;
import static database.PassDB.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author amitm
 */
public class ConnectDB {
//    Declare Variables...
    private Connection con;
    private PreparedStatement pst;
    private DefaultTableModel dftm;
 
//    Initialize constructor ConnectDB()...
    public ConnectDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, UNAME, PASS);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }        
    }
    
//    Implements insertData() method...
    public void insertData(String fName, String lName, String phone, String email, String city, String zip, String state){
        String query = "INSERT INTO personal_info (fname, lname, phone, email, city, zip, state) VALUES (?, ?, ?, ?, ?, ?, ?)";        
        try {
            pst = con.prepareStatement(query);
            pst.setString(1, fName);
            pst.setString(2, lName);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, city);
            pst.setString(6, zip);
            pst.setString(7, state);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Details inserted sucessfully!");
        
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
//    Implements updateData() method...
   public void updateData(String fName, String lName, String phone, String email, String city, String zip, String state, Integer id){
        String query = "UPDATE personal_info SET fname=?, lname=?, phone=?, email=?, city=?, zip=?, state=? WHERE id=?";        
        try {
            pst = con.prepareStatement(query);
            pst.setString(1, fName);
            pst.setString(2, lName);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, city);
            pst.setString(6, zip);
            pst.setString(7, state);
            pst.setInt(8, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Details updated sucessfully!");
        
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
//    Implements deleteData() method...
   public void deleteData(Integer id){
       String query = "DELETE FROM personal_info WHERE id=?";
        try {
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Details deleted Successfully!");
            
        } catch (SQLException e) {
            System.err.println(e);
        }
   }
   
//   Implements sortData() method...
   public void sortData(JTable table, String item, String order){
       String query = "SELECT * FROM personal_info ORDER BY " + item + " " + order;
       executeQuery(query, table);
   }
   
//   Implements searchData() method...
   public void searchData(JTable table, String colName, String searchItem){
       String query = "SELECT * FROM personal_info WHERE " + colName + " LIKE " + "'%" + searchItem + "%'";
       executeQuery(query, table);
   }
    
//    Implements showData() method...
    public void showData(JTable table){
        String query = "SELECT * FROM personal_info";
        executeQuery(query, table);
    }
    
//    Implements executeQuery() method...
//    This method is only use for to show data, to sort data and to search data...
    private void executeQuery(String query, JTable table){
        try {
            pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            dftm = (DefaultTableModel)table.getModel();
            dftm.setRowCount(0);
            
            while(rs.next()){
                Vector vector = new Vector();
                for(int i=1; i<=col; i++){
                    vector.add(rs.getInt("id"));
                    vector.add(rs.getString("fname"));
                    vector.add(rs.getString("lname"));
                    vector.add(rs.getString("phone"));
                    vector.add(rs.getString("email"));
                    vector.add(rs.getString("city"));
                    vector.add(rs.getString("zip"));
                    vector.add(rs.getString("state"));
                }
                dftm.addRow(vector);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

}
