/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TrPestolu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author CIELOUNOEE
 */
public class produccion {
    private long id = 0;
    private Date feCongelacion;
    private Date feRecepcion;
    private Date feProduccion;
    private String talla = "";
    private double cantidad = 0;
    private String lote = "";
    private long idProducto = 0;
    private long idCompania = 0;
    private boolean impreso = false;
    private long idEmbarcacion = 0;

    private static Conexiones conexion = Conexiones.getInstance();
    private Model model = Model.getInstance();
    
    public void insertar (produccion prod) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("INSERT INTO produccion VALUES(?,?,?,?,?,?,?,?,?,current_date,?)");

            pstm.setLong(1, prod.getId());
            pstm.setDate(2, new java.sql.Date(prod.getFeCongelacion().getTime()));
            pstm.setDate(3, new java.sql.Date(prod.getFeRecepcion().getTime()));
            pstm.setString(4, prod.getTalla());
            pstm.setDouble(5, prod.getCantidad());
            pstm.setString(6, prod.getLote());
            pstm.setLong(7, prod.getIdProducto());
            pstm.setLong(8, prod.getIdCompania());
            pstm.setBoolean(9, prod.isImpreso());
            //pstm.setDate(10, new java.sql.Date(prod.getFeProduccion().getTime()));
            pstm.setLong(10, prod.getIdEmbarcacion());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! produccion.insertar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }
    
    public void actualizar(produccion prod) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pstm = null;

        try {
            pstm = conexion.getConexion().prepareStatement("UPDATE produccion SET fechacongelacion=?, fecharecepcion=?, talla=?, cantidad=?, lote=?, idproducto=?, idcompania=?, idembarcacion=? WHERE id=?");

            pstm.setDate(1, new java.sql.Date(prod.getFeCongelacion().getTime()));
            pstm.setDate(2, new java.sql.Date(prod.getFeRecepcion().getTime()));
            pstm.setString(3, prod.getTalla());
            pstm.setDouble(4, prod.getCantidad());
            pstm.setString(5, prod.getLote());
            pstm.setLong(6, prod.getIdProducto());
            pstm.setLong(7, prod.getIdCompania());
            pstm.setLong(8, prod.getIdEmbarcacion());
            pstm.setLong(9, prod.getId());
            
            pstm.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException("! produccion.actualizar() ¡\n" + ex.getMessage());
        } finally {
            model.close(pstm, null);
        }
    }

    public produccion get(long cia, long barco, long producto, String lote) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        produccion p = new produccion();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM produccion WHERE idcompania=? and idembarcacion=? and idproducto=? and lote=?");
            pst.setLong(1, cia);
            pst.setLong(2, barco);
            pst.setLong(3, producto);
            pst.setString(4, lote.toUpperCase());

            rs = pst.executeQuery();

            while (rs.next()) {
                p = produccion.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return p;
    }
    
    public produccion getLote(long cia, String lote) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        produccion p = new produccion();

        try {
            pst = conexion.getConexion().prepareStatement("SELECT * FROM produccion WHERE idcompania=? and lote=?");
            pst.setLong(1, cia);
            pst.setString(2, lote.toUpperCase());

            rs = pst.executeQuery();

            while (rs.next()) {
                p = produccion.load(rs);
            }
        } finally {
            model.close(pst, null);
        }

        return p;
    }

    public boolean existe(long cia, long barco, long producto, String lote) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conexion.getConexion().prepareStatement("SELECT COUNT(*) FROM produccion WHERE idcompania=? and idembarcacion=? and idproducto=? and lote=?");
            pst.setLong(1, cia);
            pst.setLong(2, barco);
            pst.setLong(3, producto);
            pst.setString(4, lote.toUpperCase());

            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new SQLException("! produccion.existe() ¡\n" + ex.getMessage());
        } finally {
            model.close(pst, rs);
        }

        return true;
    }
    
    public boolean existeLote(long cia, String lote) throws SQLException {
        if (conexion == null) {
            throw new SQLException("sin conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conexion.getConexion().prepareStatement("SELECT COUNT(*) FROM produccion WHERE idcompania=? and lote=?");
            pst.setLong(1, cia);
            pst.setString(2, lote.toUpperCase());

            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new SQLException("! produccion.existe() ¡\n" + ex.getMessage());
        } finally {
            model.close(pst, rs);
        }

        return true;
    }
    
    public static produccion load(ResultSet rs) throws SQLException {
        produccion prod = new produccion();

        prod.setId(rs.getLong(1));
        prod.setFeCongelacion(rs.getDate(2));
        prod.setFeRecepcion(rs.getDate(3));
        prod.setTalla(rs.getString(4));
        prod.setCantidad(rs.getDouble(5));
        prod.setLote(rs.getString(6));
        prod.setIdProducto(rs.getLong(7));
        prod.setIdCompania(rs.getLong(8));
        prod.setImpreso(rs.getBoolean(9));
        prod.setFeProduccion(rs.getDate(10));
        prod.setIdEmbarcacion(rs.getLong(11));
                
        return prod;
    }

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
     * @return the feCongelacion
     */
    public Date getFeCongelacion() {
        return feCongelacion;
    }

    /**
     * @param feCongelacion the feCongelacion to set
     */
    public void setFeCongelacion(Date feCongelacion) {
        this.feCongelacion = feCongelacion;
    }

    /**
     * @return the feRecepcion
     */
    public Date getFeRecepcion() {
        return feRecepcion;
    }

    /**
     * @param feRecepcion the feRecepcion to set
     */
    public void setFeRecepcion(Date feRecepcion) {
        this.feRecepcion = feRecepcion;
    }

    /**
     * @return the feProduccion
     */
    public Date getFeProduccion() {
        return feProduccion;
    }

    /**
     * @param feProduccion the feProduccion to set
     */
    public void setFeProduccion(Date feProduccion) {
        this.feProduccion = feProduccion;
    }

    /**
     * @return the talla
     */
    public String getTalla() {
        return talla;
    }

    /**
     * @param talla the talla to set
     */
    public void setTalla(String talla) {
        this.talla = talla;
    }

    /**
     * @return the cantidad
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the idProducto
     */
    public long getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the idCompania
     */
    public long getIdCompania() {
        return idCompania;
    }

    /**
     * @param idCompania the idCompania to set
     */
    public void setIdCompania(long idCompania) {
        this.idCompania = idCompania;
    }

    /**
     * @return the impreso
     */
    public boolean isImpreso() {
        return impreso;
    }

    /**
     * @param impreso the impreso to set
     */
    public void setImpreso(boolean impreso) {
        this.impreso = impreso;
    }

    /**
     * @return the idEmbarcacion
     */
    public long getIdEmbarcacion() {
        return idEmbarcacion;
    }

    /**
     * @param idEmbarcacion the idEmbarcacion to set
     */
    public void setIdEmbarcacion(long idEmbarcacion) {
        this.idEmbarcacion = idEmbarcacion;
    }
}
