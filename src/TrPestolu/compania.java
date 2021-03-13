/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrPestolu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author CIELOUNOEE
 */
public class compania {

    private long id = 0;
    private String razonSocial = "";
    private String nit = "";
    private String ciudad = "";
    private String direccion = "";
    private String pais = "";
    private String registroSanitario = "";
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
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the registroSanitario
     */
    public String getRegistroSanitario() {
        return registroSanitario;
    }

    /**
     * @param registroSanitario the registroSanitario to set
     */
    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }

    public void insertar(compania cia) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("INSERT INTO compania VALUES(?,?,?,?,?,?,?)");

            pstm.setLong(1, cia.getId());
            pstm.setString(2, cia.getRazonSocial().toUpperCase());
            pstm.setString(3, cia.getNit().toUpperCase());
            pstm.setString(4, cia.getCiudad().toUpperCase());
            pstm.setString(5, cia.getDireccion().toUpperCase());
            pstm.setString(6, cia.getPais().toUpperCase());
            pstm.setString(7, cia.getRegistroSanitario().toUpperCase());

            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! compania.insertar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }

    public void actualizar(compania cia) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("UPDATE compania SET razonsocial=?, pais=?, ciudad=?, direccion=?, registrosanitario=? WHERE nit=?");

            pstm.setString(1, cia.getRazonSocial().toUpperCase());
            pstm.setString(2, cia.getPais().toUpperCase());
            pstm.setString(3, cia.getCiudad().toUpperCase());
            pstm.setString(4, cia.getDireccion().toUpperCase());
            pstm.setString(5, cia.getRegistroSanitario().toUpperCase());
            pstm.setString(6, cia.getNit().toUpperCase());

            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! compania.actualizar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }

    public compania get(String prv) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        compania c = new compania();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM compania WHERE nit=?");
            pst.setString(1, prv.toUpperCase());

            rs = pst.executeQuery();

            while (rs.next()) {
                c = compania.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return c;
    }

    public compania getById(long id) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        compania c = new compania();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM compania WHERE id=?");
            pst.setLong(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                c = compania.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return c;
    }
    
    public boolean existe(String nit) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conexion.getConexion().prepareStatement("SELECT COUNT(*) FROM compania WHERE nit=?");
            pst.setString(1, nit.toUpperCase());

            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new SQLException("! compania.existe() ¡\n" + ex.getMessage());
        } finally {
            model.close(pst, rs);
        }

        return true;
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
            pst = conexion.getConexion().prepareStatement("SELECT * FROM compania");
            
            rs = pst.executeQuery();

            while (rs.next()) {
                l.add(compania.load(rs));
            }
        } finally {
            model.close(pst, null);
        }

        return l;
    }
    
    public static compania load(ResultSet rs) throws SQLException {
        compania cia = new compania();

        cia.setId(rs.getLong(1));
        cia.setRazonSocial(rs.getString(2));
        cia.setNit(rs.getString(3));
        cia.setCiudad(rs.getString(4));
        cia.setDireccion(rs.getString(5));
        cia.setPais(rs.getString(6));
        cia.setRegistroSanitario(rs.getString(7));

        return cia;
    }
}
