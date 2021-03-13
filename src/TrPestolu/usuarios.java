/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrPestolu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CIELOUNOEE
 */
public class usuarios {
    private long id = 0;
    private String usuario = "";
    private String nombre = "";
    private String clave = "";
    private String rol = "";
    private long compania = 0;

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
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    /**
     * @return the compania
     */
    public long getCompania() {
        return compania;
    }

    /**
     * @param compania the compania to set
     */
    public void setCompania(long compania) {
        this.compania = compania;
    }
    
    public void insertar (usuarios us) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("INSERT INTO usuarios VALUES(?,?,?,?,?,?)");

            pstm.setLong(1, us.getId());
            pstm.setString(2, us.getUsuario().toUpperCase());
            pstm.setString(3, us.getNombre().toUpperCase());
            pstm.setString(4, us.getClave());
            pstm.setString(5, us.getRol().toUpperCase());
            pstm.setLong(6, us.getCompania());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! usuarios.insertar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public void actualizar(usuarios us) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("UPDATE usuarios SET nombre=?, clave=?, rol=?, compania=? WHERE usuario=?");

            pstm.setString(1, us.getNombre().toUpperCase());
            pstm.setString(2, us.getClave());
            pstm.setString(3, us.getRol().toUpperCase());
            pstm.setLong(4, us.getCompania());
            pstm.setString(5, us.getUsuario().toUpperCase());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! usuario.actualizar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public usuarios get(String usuario, long cia) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        usuarios u = new usuarios();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM usuarios WHERE usuario=? and compania=?");
            pst.setString(1, usuario.toUpperCase());
            pst.setLong(2, cia);

            rs = pst.executeQuery();

            while (rs.next()) {
                u = usuarios.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return u;
    }
    
    public boolean existe(String usuario, long cia) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conexion.getConexion().prepareStatement("SELECT COUNT(*) FROM usuarios WHERE usuario=? and compania=?");
            pst.setString(1, usuario.toUpperCase());
            pst.setLong(2, cia);

            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new SQLException("! usuarios.existe() ¡\n" + ex.getMessage());
        } finally {
            model.close(pst, rs);
        }

        return true;
    }

    public static usuarios load(ResultSet rs) throws SQLException {
        usuarios us = new usuarios();

        us.setId(rs.getLong(1));
        us.setUsuario(rs.getString(2));
        us.setNombre(rs.getString(3));
        us.setClave(rs.getString(4));
        us.setRol(rs.getString(5));
        us.setCompania(rs.getLong(6));
        
        return us;
    }
}
