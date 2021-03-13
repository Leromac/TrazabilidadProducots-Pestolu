/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrPestolu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author CIELOUNOEE
 */
public class indicacionesMaestras {
    private long id = 0;
    private String indicacion = "";

    private static Conexiones conexion = Conexiones.getInstance();
    private Model model = Model.getInstance();
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the indicacion
     */
    public String getIndicacion() {
        return indicacion;
    }

    /**
     * @param indicacion the indicacion to set
     */
    public void setIndicacion(String indicacion) {
        this.indicacion = indicacion;
    }
    
    public void insertar (indicacionesMaestras ind) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("INSERT INTO indicacionesmaestras VALUES(?,?)");

            pstm.setLong(1, ind.getId());
            pstm.setString(2, ind.getIndicacion().toUpperCase());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! indicacionesMaestras.insertar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public void actualizar(indicacionesMaestras ind) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("UPDATE indicacionesmaestras SET indicacion=? WHERE id=?");

            pstm.setString(1, ind.getIndicacion().toUpperCase());
            pstm.setLong(2, ind.getId());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! indicacionesMaestras.actualizar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public List getTodos() throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        //indicacionesMaestras im = new indicacionesMaestras();
        List l = new LinkedList();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM indicacionesmaestras");
            
            rs = pst.executeQuery();

            while (rs.next()) {
                l.add(indicacionesMaestras.load(rs));
            }
        } finally {
            model.close(pst, null);
        }

        return l;
    }
    
    public static indicacionesMaestras load(ResultSet rs) throws SQLException {
        indicacionesMaestras ind = new indicacionesMaestras();

        ind.setId(rs.getLong(1));
        ind.setIndicacion(rs.getString(2));
                
        return ind;
    }
}
