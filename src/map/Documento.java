package map;
// Generated 22-may-2020 18:32:02 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Documento generated by hbm2java
 */
public class Documento  implements java.io.Serializable {


     private DocumentoId id;
     private Expediente expediente;
     private Date fecha;
     private String descripcion;
     private byte[] pdf;
     private String aportadoPor;

    public Documento() {
    }

	
    public Documento(DocumentoId id, Expediente expediente, Date fecha, byte[] pdf, String aportadoPor) {
        this.id = id;
        this.expediente = expediente;
        this.fecha = fecha;
        this.pdf = pdf;
        this.aportadoPor = aportadoPor;
    }
    public Documento(DocumentoId id, Expediente expediente, Date fecha, String descripcion, byte[] pdf, String aportadoPor) {
       this.id = id;
       this.expediente = expediente;
       this.fecha = fecha;
       this.descripcion = descripcion;
       this.pdf = pdf;
       this.aportadoPor = aportadoPor;
    }
   
    public DocumentoId getId() {
        return this.id;
    }
    
    public void setId(DocumentoId id) {
        this.id = id;
    }
    public Expediente getExpediente() {
        return this.expediente;
    }
    
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public byte[] getPdf() {
        return this.pdf;
    }
    
    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
    public String getAportadoPor() {
        return this.aportadoPor;
    }
    
    public void setAportadoPor(String aportadoPor) {
        this.aportadoPor = aportadoPor;
    }

    @Override
    public String toString() {
        return id.getNombre();
    }

}


