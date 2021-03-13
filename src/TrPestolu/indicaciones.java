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
public class indicaciones {
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
     * @return the ind1
     */
    public String getIndicacion() {
        return indicacion;
    }

    /**
     * @param ind1 the ind1 to set
     */
    public void setIndicacion(String indicacion) {
        this.indicacion = indicacion;
    }

    public void insertar (indicaciones ind) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("INSERT INTO indicaciones VALUES(?,?)");

            pstm.setLong(1, ind.getId());
            pstm.setString(2, ind.getIndicacion());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! indicaciones.insertar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public void actualizar(indicaciones ind) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("UPDATE indicaciones SET indicacion=? WHERE id=?");

            pstm.setString(1, ind.getIndicacion().toUpperCase());
            pstm.setLong(2, ind.getId());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! indicaciones.actualizar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public void eliminar(long id) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("DELETE from indicaciones WHERE idproduccion=?");
            pstm.setLong(1, id);
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! indicaciones.eliminar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public List getByOp(long id) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        //indicacionesMaestras im = new indicacionesMaestras();
        List l = new LinkedList();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM indicaciones where idproduccion=?");
            pst.setLong(1, id);
            
            rs = pst.executeQuery();

            while (rs.next()) {
                l.add(indicaciones.load(rs));
            }
        } finally {
            model.close(pst, null);
        }

        return l;
    }
    
    public static indicaciones load(ResultSet rs) throws SQLException {
        indicaciones ind = new indicaciones();

        ind.setId(rs.getLong(1));
        ind.setIndicacion(rs.getString(2));
        
        return ind;
    }
}
