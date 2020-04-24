package map;
// Generated 24-abr-2020 13:48:41 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Expediente generated by hbm2java
 */
public class Expediente  implements java.io.Serializable {


     private Integer codigo;
     private Cliente cliente;
     private Letrado letrado;
     private Procurador procurador;
     private Date fechaCreacion;
     private Date fechaCierre;
     private Set avisos = new HashSet(0);
     private HojaEncargo hojaEncargo;
     private Set vehiculos = new HashSet(0);
     private Set peritos = new HashSet(0);
     private Incidente incidente;
     private Set documentos = new HashSet(0);
     private Set sentencias = new HashSet(0);

    public Expediente() {
    }

	
    public Expediente(Cliente cliente, Letrado letrado, Procurador procurador, Date fechaCreacion) {
        this.cliente = cliente;
        this.letrado = letrado;
        this.procurador = procurador;
        this.fechaCreacion = fechaCreacion;
    }
    public Expediente(Cliente cliente, Letrado letrado, Procurador procurador, Date fechaCreacion, Date fechaCierre, Set avisos, HojaEncargo hojaEncargo, Set vehiculos, Set peritos, Incidente incidente, Set documentos, Set sentencias) {
       this.cliente = cliente;
       this.letrado = letrado;
       this.procurador = procurador;
       this.fechaCreacion = fechaCreacion;
       this.fechaCierre = fechaCierre;
       this.avisos = avisos;
       this.hojaEncargo = hojaEncargo;
       this.vehiculos = vehiculos;
       this.peritos = peritos;
       this.incidente = incidente;
       this.documentos = documentos;
       this.sentencias = sentencias;
    }
   
    public Integer getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Letrado getLetrado() {
        return this.letrado;
    }
    
    public void setLetrado(Letrado letrado) {
        this.letrado = letrado;
    }
    public Procurador getProcurador() {
        return this.procurador;
    }
    
    public void setProcurador(Procurador procurador) {
        this.procurador = procurador;
    }
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Date getFechaCierre() {
        return this.fechaCierre;
    }
    
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    public Set getAvisos() {
        return this.avisos;
    }
    
    public void setAvisos(Set avisos) {
        this.avisos = avisos;
    }
    public HojaEncargo getHojaEncargo() {
        return this.hojaEncargo;
    }
    
    public void setHojaEncargo(HojaEncargo hojaEncargo) {
        this.hojaEncargo = hojaEncargo;
    }
    public Set getVehiculos() {
        return this.vehiculos;
    }
    
    public void setVehiculos(Set vehiculos) {
        this.vehiculos = vehiculos;
    }
    public Set getPeritos() {
        return this.peritos;
    }
    
    public void setPeritos(Set peritos) {
        this.peritos = peritos;
    }
    public Incidente getIncidente() {
        return this.incidente;
    }
    
    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }
    public Set getDocumentos() {
        return this.documentos;
    }
    
    public void setDocumentos(Set documentos) {
        this.documentos = documentos;
    }
    public Set getSentencias() {
        return this.sentencias;
    }
    
    public void setSentencias(Set sentencias) {
        this.sentencias = sentencias;
    }




}


