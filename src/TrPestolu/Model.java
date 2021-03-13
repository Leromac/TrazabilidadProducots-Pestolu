package TrPestolu;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import com.csvreader.CsvReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author LRA
 */
public class Model {

    private static Conexiones conexion = Conexiones.getInstance();
    private static Model model;

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    public void ConectarPostgres() throws SQLException {
        try {
            /*if (conexion == null) {
             conexion = new Conexiones();
             }*/

            if (conexion.getConexion() == null) {
                conexion.Conectar();
            }
        } catch (SQLException ex) {
            throw new SQLException("$" + ex);
        }
    }

    public void DesconectarPostgres() throws SQLException {
        /*try {
         conexion.Desconectar();
         } catch (SQLException ex) {
         throw new SQLException("$");
         }*/
    }

    public void close(PreparedStatement dtm, ResultSet rs) throws SQLException {
        try {
            if (dtm != null) {
                dtm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ex) {
            throw new SQLException("! close ¡\n" + ex.getMessage());
        }
    }

    public String getFormatoFechaLote() throws SQLException {
        if (conexion == null) {
            throw new SQLException("No Existe Conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        String rpt = "";

        try {
            pst = conexion.getConexion().prepareStatement("select TO_CHAR(current_date, 'DDMMYY')");

            rs = pst.executeQuery();

            if (rs.next()) {
                rpt = rs.getString(1);
            }
        } catch (Exception ex) {
            throw new SQLException("! getFormatoFechaLote ¡\n" + ex.getMessage());
        } finally {
            close(pst, rs);
        }

        return rpt;
    }
    
    public long getConsecutivo(String tabla) throws SQLException {
        if (conexion == null) {
            throw new SQLException("No Existe Conexion");
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        long rpt = 0;

        try {
            pst = conexion.getConexion().prepareStatement("SELECT max(id+1) FROM " + tabla);

            rs = pst.executeQuery();

            if (rs.next()) {
                rpt = rs.getLong(1);
            }
        } catch (Exception ex) {
            throw new SQLException("! getConsecutivo ¡\n" + ex.getMessage());
        } finally {
            close(pst, rs);
        }

        return rpt;
    }

    public String accionAgregarCompania(String nit, String rs, String pais, String ciudad, String dir, String regs) throws Exception {
        compania c = new compania();
        String error = "";

        if (nit != null && !nit.equalsIgnoreCase("") && rs != null && !rs.equalsIgnoreCase("") && pais != null && !pais.equalsIgnoreCase("") && ciudad != null && !ciudad.equalsIgnoreCase("") && regs != null && !regs.equalsIgnoreCase("")) {
            if (c.existe(nit) == false) {
                c.setId(this.getConsecutivo("compania"));
                c.setNit(nit.toUpperCase());
                c.setRazonSocial(rs.toUpperCase());
                c.setPais(pais.toUpperCase());
                c.setCiudad(ciudad.toUpperCase());
                c.setDireccion(dir.toUpperCase());
                c.setRegistroSanitario(regs.toUpperCase());

                c.insertar(c);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }

    public String accionActualizarCompania(String nit, String rs, String pais, String ciudad, String dir, String regs) throws Exception {
        compania c = new compania();
        String error = "";

        if (nit != null && !nit.equalsIgnoreCase("") && rs != null && !rs.equalsIgnoreCase("") && pais != null && !pais.equalsIgnoreCase("") && ciudad != null && !ciudad.equalsIgnoreCase("") && regs != null && !regs.equalsIgnoreCase("")) {
            if (c.existe(nit) == true) {
                c.setNit(nit.toUpperCase());
                c.setRazonSocial(rs.toUpperCase());
                c.setPais(pais.toUpperCase());
                c.setCiudad(ciudad.toUpperCase());
                c.setDireccion(dir.toUpperCase());
                c.setRegistroSanitario(regs.toUpperCase());

                c.actualizar(c);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }

    public String accionAgregarProducto(String codigo, String nombre, String especie) throws Exception {
        productos p = new productos();
        String error = "";

        if (codigo != null && !codigo.equalsIgnoreCase("") && nombre != null && !nombre.equalsIgnoreCase("") && especie != null && !especie.equalsIgnoreCase("")) {
            if (p.existe(codigo) == false) {
                p.setId(this.getConsecutivo("productos"));
                p.setCodigo(codigo);
                p.setNombre(nombre);
                p.setEspecie(especie);

                p.insertar(p);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }

    public String accionActualizarProducto(String codigo, String nombre, String especie) throws Exception {
        productos p = new productos();
        String error = "";

        if (codigo != null && !codigo.equalsIgnoreCase("") && nombre != null && !nombre.equalsIgnoreCase("") && especie != null && !especie.equalsIgnoreCase("")) {
            if (p.existe(codigo) == true) {
                p.setCodigo(codigo);
                p.setNombre(nombre);
                p.setEspecie(especie);

                p.actualizar(p);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }

    public String accionAgregarProduccion(String compania, String codemb, String codproducto, String lote, String talla, String peso, String fcong, String fvenc) throws Exception {
        produccion p = new produccion();
        //indicaciones ind = new indicaciones();
        String error = "";

        if (compania != null && !compania.equalsIgnoreCase("") && codemb != null && !codemb.equalsIgnoreCase("") && codproducto != null && !codproducto.equalsIgnoreCase("") && talla != null && !talla.equalsIgnoreCase("") && peso != null && !peso.equalsIgnoreCase("") && lote != null && !lote.equalsIgnoreCase("") && fcong != null && !fcong.equalsIgnoreCase("") && fvenc != null && !fvenc.equalsIgnoreCase("")) {
            if (p.existe(Long.parseLong(compania), Long.parseLong(codemb), Long.parseLong(codproducto), lote) == false) {
                java.util.Date d1 = new java.util.Date(fcong);
                java.util.Date d2 = new java.util.Date(fvenc);
                
                p.setId(this.getConsecutivo("produccion"));
                p.setIdCompania(Long.parseLong(compania));
                p.setIdProducto(Long.parseLong(codproducto));
                p.setIdEmbarcacion(Long.parseLong(codemb));
                p.setTalla(talla);
                p.setLote(lote);
                p.setCantidad(Double.parseDouble(peso));
                p.setFeRecepcion(d2);
                p.setFeCongelacion(d1);
                p.setImpreso(false);

                p.insertar(p);

                /*if (control.equalsIgnoreCase("off")) {
                    for (int i = 0; i < lind.size(); i++) {
                        ind.setId(p.getId());
                        ind.setIndicacion(String.valueOf(lind.get(i)));

                        ind.insertar(ind);
                    }
                }else{
                    indicacionesMaestras im = new indicacionesMaestras();

                    lind=im.getTodos();
                    
                    for (int i = 0; i < lind.size(); i++) {
                        im = (indicacionesMaestras) lind.get(i);

                        if (im.getIndicacion() != null && !im.getIndicacion().equalsIgnoreCase("")) {
                            ind.setId(p.getId());
                            ind.setIndicacion(im.getIndicacion());

                            ind.insertar(ind);
                        }
                    }
                }*/
            
                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }

    public String accionModificarProduccion(long id, String compania, String codemb, String codproducto, String lote, String talla, String peso, String fcong, String fvenc, String control) throws Exception {
        produccion p = new produccion();
        //indicaciones ind = new indicaciones();
        String error = "";

        if (compania != null && !compania.equalsIgnoreCase("") && codemb != null && !codemb.equalsIgnoreCase("") && codproducto != null && !codproducto.equalsIgnoreCase("") && talla != null && !talla.equalsIgnoreCase("") && peso != null && !peso.equalsIgnoreCase("") && lote != null && !lote.equalsIgnoreCase("") && fcong != null && !fcong.equalsIgnoreCase("") && fvenc != null && !fvenc.equalsIgnoreCase("") && control != null && !control.equalsIgnoreCase("")) {
            if (p.existe(Long.parseLong(compania), Long.parseLong(codemb), Long.parseLong(codproducto), lote) == true) {
                java.util.Date d1 = new java.util.Date(fcong);
                java.util.Date d2 = new java.util.Date(fvenc);
                
                p.setId(id);
                p.setIdCompania(Long.parseLong(compania));
                p.setIdProducto(Long.parseLong(codproducto));
                p.setIdEmbarcacion(Long.parseLong(codemb));
                p.setTalla(talla);
                p.setLote(lote);
                p.setCantidad(Double.parseDouble(peso));
                p.setFeRecepcion(d2);
                p.setFeCongelacion(d1);
                p.setImpreso(false);
                
                p.actualizar(p);
                
                //ind.eliminar(p.getId());
                
                /*if (control.equalsIgnoreCase("off")) {
                    for (int i = 0; i < lind.size(); i++) {
                        ind.setId(p.getId());
                        ind.setIndicacion(String.valueOf(lind.get(i)));

                        ind.insertar(ind);
                    }
                }else{
                    indicacionesMaestras im = new indicacionesMaestras();

                    lind=im.getTodos();
                    
                    for (int i = 0; i < lind.size(); i++) {
                        im = (indicacionesMaestras) lind.get(i);

                        if (im.getIndicacion() != null && !im.getIndicacion().equalsIgnoreCase("")) {
                            ind.setId(p.getId());
                            ind.setIndicacion(im.getIndicacion());

                            ind.insertar(ind);
                        }
                    }
                }*/
            
                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }
  
    public String accionAgregarUsuario(String cia, String nombre, String usuario, String clave, String rol) throws Exception {
        usuarios u = new usuarios();
        String error = "";

        if (cia != null && !cia.equalsIgnoreCase("") && nombre != null && !nombre.equalsIgnoreCase("") && usuario != null && !usuario.equalsIgnoreCase("") && clave != null && !clave.equalsIgnoreCase("") && rol != null && !rol.equalsIgnoreCase("")) {
            if (u.existe(usuario, Long.parseLong(cia)) == false) {
                u.setId(this.getConsecutivo("usuarios"));
                u.setNombre(nombre);
                u.setUsuario(usuario);
                u.setClave(clave);
                u.setRol(rol);
                u.setCompania(Long.parseLong(cia));

                u.insertar(u);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }

    public String accionActualizarUsuario(String cia, String nombre, String usuario, String clave, String rol) throws Exception {
        usuarios u = new usuarios();
        String error = "";

        if (cia != null && !cia.equalsIgnoreCase("") && nombre != null && !nombre.equalsIgnoreCase("") && usuario != null && !usuario.equalsIgnoreCase("") && clave != null && !clave.equalsIgnoreCase("") && rol != null && !rol.equalsIgnoreCase("")) {
            if (u.existe(usuario, Long.parseLong(cia)) == true) {
                u.setUsuario(usuario);
                u.setNombre(nombre);
                u.setClave(clave);
                u.setRol(rol);
                u.setCompania(Long.parseLong(cia));

                u.actualizar(u);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }
    
    public String ConvertirFecha(String fecha) throws SQLException {
        String res = "", a = "", b = "", c = "";
        StringTokenizer stk = new StringTokenizer(fecha, "-");
        int i = 0;
        while (stk.hasMoreTokens()) {
            if (i == 0) {
                a = stk.nextElement().toString();
            }
            if (i == 1) {
                b = stk.nextElement().toString();
            }
            if (i == 2) {
                c = stk.nextElement().toString();
            }

            i++;
        }

        res = c + "/" + b + "/" + a;

        return res;
    }
    
    public String ConvertirFechaSaveProd(String fecha) throws SQLException {
        String res = "", a = "", b = "", c = "";
        StringTokenizer stk = new StringTokenizer(fecha, "/");
        int i = 0;
        while (stk.hasMoreTokens()) {
            if (i == 0) {
                a = stk.nextElement().toString();
            }
            if (i == 1) {
                b = stk.nextElement().toString();
            }
            if (i == 2) {
                c = stk.nextElement().toString();
            }

            i++;
        }

        res = b + "/" + a + "/" + c;

        return res;
    }
    
    public String accionAgregarEmbarcacion(String codigo, String nombre, String nid) throws Exception {
        embarcaciones e = new embarcaciones();
        String error = "";

        if (codigo != null && !codigo.equalsIgnoreCase("") && nombre != null && !nombre.equalsIgnoreCase("") && nid != null && !nid.equalsIgnoreCase("")) {
            if (e.existe(codigo) == false) {
                e.setId(this.getConsecutivo("embarcaciones"));
                e.setCodigo(codigo);
                e.setNombre(nombre);
                e.setIdentificacion(nid);

                e.insertar(e);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }
    
    public String accionActualizarEmbarcacion(String codigo, String nombre, String nid, long id) throws Exception {
        embarcaciones e = new embarcaciones();
        String error = "";

        if (codigo != null && !codigo.equalsIgnoreCase("") && nombre != null && !nombre.equalsIgnoreCase("") && nid != null && !nid.equalsIgnoreCase("")) {
            if (e.existe(codigo) == true) {
                e.setId(id);
                e.setCodigo(codigo);
                e.setNombre(nombre);
                e.setIdentificacion(nid);

                e.actualizar(e);

                error = "0";
            } else {
                error = "2";
            }
        } else {
            error = "1";
        }

        return error;
    }
}
