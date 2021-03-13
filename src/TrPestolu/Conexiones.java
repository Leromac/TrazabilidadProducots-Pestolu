package TrPestolu;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author LRA
 */
public class Conexiones {

    private static Conexiones conexiones;
    private Connection conexion;

    /**
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * @param conexion the conexion to set
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public static Conexiones getInstance() {
        if (conexiones == null) {
            conexiones = new Conexiones();
        }

        return conexiones;
    }

    public void Conectar() throws SQLException {
        if (this.getConexion() != null) {
            return;
        }

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            javax.sql.DataSource datasource = (javax.sql.DataSource) envContext.lookup("jdbc/Postgres");
            this.setConexion(datasource.getConnection());
            //con = datasource.getConnection();
        } catch (Exception ex) {
            throw new SQLException("! ERROR CONECTAR ¡\n" + ex.getMessage());
        }
    }

    public void Desconectar() throws SQLException {
        try {
            conexion.close();
        } catch (Exception ex) {
            throw new SQLException("! ERROR DESCONECTAR ¡\n" + ex.getMessage());
        }

    }
}
