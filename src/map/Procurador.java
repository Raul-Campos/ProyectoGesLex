package map;
// Generated 24-abr-2020 13:48:41 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Procurador generated by hbm2java
 */
public class Procurador  implements java.io.Serializable {


     private String dniProcurador;
     private String nombre;
     private String apellidos;
     private String direccion;
     private int telefono;
     private String email;
     private Set expedientes = new HashSet(0);

    public Procurador() {
    }

	
    public Procurador(String dniProcurador, String nombre, String apellidos, String direccion, int telefono, String email) {
        this.dniProcurador = dniProcurador;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    public Procurador(String dniProcurador, String nombre, String apellidos, String direccion, int telefono, String email, Set expedientes) {
       this.dniProcurador = dniProcurador;
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.direccion = direccion;
       this.telefono = telefono;
       this.email = email;
       this.expedientes = expedientes;
    }
   
    public String getDniProcurador() {
        return this.dniProcurador;
    }
    
    public void setDniProcurador(String dniProcurador) {
        this.dniProcurador = dniProcurador;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public int getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public Set getExpedientes() {
        return this.expedientes;
    }
    
    public void setExpedientes(Set expedientes) {
        this.expedientes = expedientes;
    }




}


