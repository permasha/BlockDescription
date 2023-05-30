package ru.permasha.blockdescription.database;

import ru.permasha.blockdescription.BlockDescription;

import java.sql.*;
import java.util.logging.Level;

public abstract class Database {

    BlockDescription plugin;
    Connection connection;
    public String table = "blocks_table";

    public Database(BlockDescription instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE location = ?");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }

    /**
     * Delete a location specified by the string
     *
     * @param string location
     */
    public String removeLocation(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM " + table + " WHERE location = '"+string+"';");
            ps.executeQuery();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, sqlConnectionClose(), ex);
            }
        }
        return "";
    }

    /**
     * Get JSONArray attributes of string location
     *
     * @param string location
     */
    public String getEnteredAttributes(String string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE location = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("location").equalsIgnoreCase(string.toLowerCase())){
                    return rs.getString("attributes");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, sqlConnectionClose(), ex);
            }
        }
        return null;
    }

    /**
     * Set JSONArray attributes of string location
     *
     * @param location, attributes
     */
    public void setAttributes(String location, String attributes) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (location,attributes) VALUES(?,?)");
            ps.setString(1, location.toLowerCase());

            ps.setString(2, attributes);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, sqlConnectionClose(), ex);
            }
        }
    }

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            errorClose(ex);
        }
    }

    private String sqlConnectionExecute(){
        return "Couldn't execute SQL statement: ";
    }
    private String sqlConnectionClose(){
        return "Failed to close SQL connection: ";
    }

    private void errorExecute(Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement: ", ex);
    }
    private void errorClose(Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
    }
}
