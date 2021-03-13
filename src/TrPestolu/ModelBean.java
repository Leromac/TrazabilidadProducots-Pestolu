package TrPestolu;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//import javax.mail.MessagingException;

/**
 *
 * @author LRA
 */
public class ModelBean {

    private Model model = Model.getInstance();

    public void Conectar() throws SQLException {
        model.ConectarPostgres();
    }

    public void Desconectar() throws SQLException {
        model.DesconectarPostgres();
    }

    public String getFormatoFechaLote() throws SQLException {
        return model.getFormatoFechaLote();
    }
    public long getConsecutivo(String tabla) throws SQLException {
        return model.getConsecutivo(tabla);
    }
    public String accionAgregarCompania(String nit, String rs, String pais, String ciudad, String dir, String regs) throws Exception {
        return model.accionAgregarCompania(nit, rs, pais, ciudad, dir, regs);
    }
    
    public String accionActualizarCompania(String nit, String rs, String pais, String ciudad, String dir, String regs) throws Exception {
        return model.accionActualizarCompania(nit, rs, pais, ciudad, dir, regs);
    }
    
    public String accionAgregarProducto(String codigo, String nombre, String especie) throws Exception {
        return model.accionAgregarProducto(codigo, nombre, especie);
    }
    
    public String accionActualizarProducto(String codigo, String nombre, String especie) throws Exception {
        return model.accionActualizarProducto(codigo, nombre, especie);
    }
    
    public String accionAgregarProduccion(String compania, String codemb, String codproducto, String lote, String talla, String peso, String fcong, String fvenc) throws Exception {
        return model.accionAgregarProduccion(compania, codemb, codproducto, lote, talla, peso, fcong, fvenc);
    }
    
    public String accionAgregarUsuario(String cia, String nombre, String usuario, String clave, String rol) throws Exception {
        return model.accionAgregarUsuario(cia, nombre, usuario, clave, rol);
    }
    
    public String accionActualizarUsuario(String cia, String nombre, String usuario, String clave, String rol) throws Exception {
        return model.accionActualizarUsuario(cia, nombre, usuario, clave, rol);
    }
    
    public String accionModificarProduccion(long id, String compania, String codemb, String codproducto, String lote, String talla, String peso, String fcong, String fvenc, String control) throws Exception {
        return model.accionModificarProduccion(id, compania, codemb, codproducto, lote, talla, peso, fcong, fvenc, control);
    }
    
     public String ConvertirFecha(String fecha) throws SQLException {
         return model.ConvertirFecha(fecha);
     }
     
     public String ConvertirFechaSaveProd(String fecha) throws SQLException {
         return model.ConvertirFechaSaveProd(fecha);
     }
     
     public String accionAgregarEmbarcacion(String codigo, String nombre, String nid) throws Exception {
         return model.accionAgregarEmbarcacion(codigo, nombre, nid);
     }
     
     public String accionActualizarEmbarcacion(String codigo, String nombre, String nid, long id) throws Exception {
         return model.accionActualizarEmbarcacion(codigo, nombre, nid, id);
     }
}
