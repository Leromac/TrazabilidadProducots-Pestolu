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
public class productos {
    private long id = 0;
    private String nombre = "";
    private String especie = "";
    private String codigo = "";

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
     * @return the especie
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * @param especie the especie to set
     */
    public void setEspecie(String especie) {
        this.especie = especie;
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
    
    public void insertar (productos prod) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("INSERT INTO productos VALUES(?,?,?,?)");

            pstm.setLong(1, prod.getId());
            pstm.setString(2, prod.getNombre().toUpperCase());
            pstm.setString(3, prod.getEspecie().toUpperCase());
            pstm.setString(4, prod.getCodigo().toUpperCase());
            
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! productos.insertar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public void actualizar(productos p) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("UPDATE productos SET nombre=?, especie=? WHERE codigo=?");

            pstm.setString(1, p.getNombre().toUpperCase());
            pstm.setString(2, p.getEspecie().toUpperCase());
            pstm.setString(3, p.getCodigo().toUpperCase());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! productos.actualizar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }

    public productos get(String codigo) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        productos p = new productos();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM productos WHERE codigo=?");
            pst.setString(1, codigo.toUpperCase());

            rs = pst.executeQuery();

            while (rs.next()) {
                p = productos.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return p;
    }

    public productos getById(long id) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        productos p = new productos();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM productos WHERE id=?");
            pst.setLong(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                p = productos.load(rs);
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
            pst = conexion.getConexion().prepareStatement("SELECT * FROM productos");
            
            rs = pst.executeQuery();

            while (rs.next()) {
                l.add(productos.load(rs));
            }
        } finally {
            model.close(pst, null);
        }

        return l;
    }
    
    public boolean existe(String cod) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conexion.getConexion().prepareStatement("SELECT COUNT(*) FROM productos WHERE codigo=?");
            pst.setString(1, cod.toUpperCase());

            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new SQLException("! producto.existe() ¡\n" + ex.getMessage());
        } finally {
            model.close(pst, rs);
        }

        return true;
    }
    
    public static productos load(ResultSet rs) throws SQLException {
        productos prod = new productos();

        prod.setId(rs.getLong(1));
        prod.setNombre(rs.getString(2));
        prod.setEspecie(rs.getString(3));
        prod.setCodigo(rs.getString(4));;
                        
        return prod;
    }
}
