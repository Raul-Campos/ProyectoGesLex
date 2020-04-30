package map;
// Generated 30-abr-2020 16:47:02 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Sentencia generated by hbm2java
 */
public class Sentencia  implements java.io.Serializable {


     private SentenciaId id;
     private Expediente expediente;
     private Date fechaPublicacion;
     private String descripcion;
     private byte[] pdf;

    public Sentencia() {
    }

	
    public Sentencia(SentenciaId id, Expediente expediente, Date fechaPublicacion, byte[] pdf) {
        this.id = id;
        this.expediente = expediente;
        this.fechaPublicacion = fechaPublicacion;
        this.pdf = pdf;
    }
    public Sentencia(SentenciaId id, Expediente expediente, Date fechaPublicacion, String descripcion, byte[] pdf) {
       this.id = id;
       this.expediente = expediente;
       this.fechaPublicacion = fechaPublicacion;
       this.descripcion = descripcion;
       this.pdf = pdf;
    }
   
    public SentenciaId getId() {
        return this.id;
    }
    
    public void setId(SentenciaId id) {
        this.id = id;
    }
    public Expediente getExpediente() {
        return this.expediente;
    }
    
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
    public Date getFechaPublicacion() {
        return this.fechaPublicacion;
    }
    
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
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




}


