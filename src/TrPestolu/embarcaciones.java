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
public class embarcaciones {
    private long id = 0;
    private String codigo = "";
    private String nombre = "";
    private String identificacion = "";

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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public void insertar (embarcaciones emb) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("INSERT INTO embarcaciones VALUES(?,?,?,?)");

            pstm.setLong(1, emb.getId());
            pstm.setString(2, emb.getCodigo().toUpperCase());
            pstm.setString(3, emb.getNombre().toUpperCase());
            pstm.setString(4, emb.getIdentificacion().toUpperCase());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! embarcaciones.insertar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public void actualizar(embarcaciones emb) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("UPDATE embarcaciones SET codigo=?, nombre=?, identificacion=? WHERE id=?");

            pstm.setString(1, emb.getCodigo().toUpperCase());
            pstm.setString(2, emb.getNombre().toUpperCase());
            pstm.setString(3, emb.getIdentificacion().toUpperCase());
            pstm.setLong(4, emb.getId());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! embarcaciones.actualizar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }

    public embarcaciones get(String codigo) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        embarcaciones emb = new embarcaciones();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM embarcaciones WHERE codigo=?");
            pst.setString(1, codigo.toUpperCase());
            
            rs = pst.executeQuery();

            while (rs.next()) {
                emb = embarcaciones.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return emb;
    }

    public boolean existe(String codigo) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conexion.getConexion().prepareStatement("SELECT COUNT(*) FROM embarcaciones WHERE codigo=?");
            pst.setString(1, codigo.toUpperCase());
            
            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new SQLException("! embarcaciones.existe() ¡\n" + ex.getMessage());
        } finally {
            model.close(pst, rs);
        }

        return true;
    }
    
    public embarcaciones getById(long id) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        embarcaciones p = new embarcaciones();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM embarcaciones WHERE id=?");
            pst.setLong(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                p = embarcaciones.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return p;
    }
    
    public List getTodos() throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        //productos p = new productos();
        List l = new LinkedList();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM embarcaciones");
            
            rs = pst.executeQuery();

            while (rs.next()) {
                l.add(embarcaciones.load(rs));
            }
        } finally {
            model.close(pst, null);
        }

        return l;
    }
    
    public static embarcaciones load(ResultSet rs) throws SQLException {
        embarcaciones emb = new embarcaciones();

        emb.setId(rs.getLong(1));
        emb.setCodigo(rs.getString(2));
        emb.setNombre(rs.getString(3));
        emb.setIdentificacion(rs.getString(4));
                
        return emb;
    }
}
