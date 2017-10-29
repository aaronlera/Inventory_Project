
package oop;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * @author ghel_loe
 */
public class SQLite {
    static java.sql.Connection conn  = null;
static java.sql.Statement stmt = null;
//static String url = "jdbc:sqlite:C:/Users/ghel_loe/Documents/NetBeansProjects/OOPFinals/src/oop/ghelopangit.sqlite";
static String error = "";
static File temp = new File("ghelopangit.sqlite");
static String url = "jdbc:sqlite:" + temp.getAbsolutePath().replace("\\","\\\\");
  //  static String url = "jdbc:sqlite:C:\\Users\\Pads\\Documents\\NetBeansProjects\\InventoryPM\\src\\com\\inventory\\app\\inventory.sqlite";


public static boolean openDB(){
    boolean result = false;
    try{
        Class.forName("org.sqlite.JDBC");
        conn = java.sql.DriverManager.getConnection(url);

        System.out.println("OK! SQLite DB session connected.");
        result = true;
    }
    catch(Exception e){
        error = e.getMessage();
        System.out.println("Open DB Error:" + e.getMessage());
    } 
    return result;
}

public static boolean closeDB(){
    boolean result = false;
    try{
        conn.close();
        
        System.out.println("OK! SQLite DB session closed.");
        result = true;
    }
    catch(Exception e){
        error = e.getMessage();
        System.out.println("Close DB Error: " + e.getMessage());
    }
    return result;
} 

public static boolean create(String table, String values){
    boolean result = false;
    try{
        SQLite.stmt = conn.createStatement();
        String query = "INSERT INTO "+ table +" VALUES(" + values + ")";
        stmt.executeUpdate(query);
        //You can include exception handling here. (e.g. Duplicate Data, etc.)
        result = true;
    }
    catch(Exception e){
        System.out.println("Create Error: " + e.getMessage());
    }
    return result;
}
public static boolean update(String table, String set, String productID){
    boolean result = false;
    try{
        SQLite.stmt = conn.createStatement();
        String query = "UPDATE "+ table +" SET "+ set + " WHERE productID LIKE '%"+ productID+"%'";
        stmt.executeUpdate(query);
        //You can include exception handling here. (e.g. Duplicate Data, etc.)
        result = true;
    }
    catch(Exception e){
        System.out.println("Update Error: " + e.getMessage());
        
    }
    return result;
}

public static boolean update(String table, String set, int id){
    boolean result = false;
    try{
        SQLite.stmt = conn.createStatement();
        String query = "UPDATE "+ table +" SET " + set + " WHERE id=" + id;
        stmt.executeUpdate(query);
        //You can include exception handling here. (e.g. Duplicate Data, etc.)
        result = true;
    }
    catch(Exception e){
        System.out.println("Create Error: " + e.getMessage());
    }
    return result;
}



public static boolean delete(String table, String prodID){
     boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "DELETE FROM "+ table +" WHERE productID LIKE '%"+prodID+"%'";
            stmt.executeUpdate(query);
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    } 

public static boolean delete(String table, int id){
    boolean result = false;
    try{
        SQLite.stmt = conn.createStatement();
        String query = "DELETE FROM "+ table + " WHERE id=" + id;
        stmt.executeUpdate(query);
        result = true;
    }
    catch(Exception e){
        System.out.println("Create Error: " + e.getMessage());
    }
    return result;
} 
public static String[][] read(String table){
    String[][] records = null;
    try{
        SQLite.stmt = conn.createStatement();
        
        //Count total rows
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + table);
        int totalRows = rs.getInt(1);
        
        //Count total columns
        rs = stmt.executeQuery("SELECT * FROM " + table);
        ResultSetMetaData rsmd = rs.getMetaData();
        int totalColumns = rsmd.getColumnCount();
        
        //Initialize 2D Array "records" with totalRows by totalColumns
        records = new String[totalRows][totalColumns];
        
        //Retrieve the record and store it to 2D Array "records"
        int row=0;
        while(rs.next()){                
            for(int col=0,index=1;col<totalColumns;col++,index++){
                records[row][col] = rs.getObject(index).toString();
            }
            row++;
        }            
    }
    catch(Exception e){
        System.out.println("Read Error: " + e.getMessage());
    }
    return records;
}
public static void main(String [] args){

    if(SQLite.openDB()){
    int id = 4; //If you receive an error, probably sqlite detect duplicate ID value.
    String task = "Task 4";
    String isdone = "NO";
    if(create("task","'"+id+"'"+","+"'"+task+"'"+","+"'"+isdone+"'")){
        System.out.println("Inserted successfully!");
    }
    SQLite.closeDB();
}   
        
 if(SQLite.openDB()){
    int id = 4;
    String isdone = "YES";
    if(update("task", "ISDONE='"+isdone+"'", id)){
        System.out.println("Updated successfully!");
    }
    SQLite.closeDB();
} 
 
 if(SQLite.openDB()){
    int id = 4;
    if(delete("task", id)){
        System.out.println("Deleted successfully!");
    }
    SQLite.closeDB();
} 
 
 if(SQLite.openDB()){
    String[][] r = read("task");
    System.out.println("Task ID:" + r[0][0]);
    System.out.println("Task NAME:" + r[0][1]);
    System.out.println("Task ISDONE?:" + r[0][2]);
    SQLite.closeDB();
}
}
}




