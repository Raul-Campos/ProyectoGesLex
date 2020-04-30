package map;
// Generated 30-abr-2020 16:47:02 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Perito generated by hbm2java
 */
public class Perito  implements java.io.Serializable {


     private String dniPerito;
     private String nombre;
     private String apellidos;
     private String direccion;
     private String provincia;
     private int telefono;
     private String email;
     private Set expedientes = new HashSet(0);

    public Perito() {
    }

	
    public Perito(String dniPerito, String nombre, String apellidos, String direccion, String provincia, int telefono, String email) {
        this.dniPerito = dniPerito;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.provincia = provincia;
        this.telefono = telefono;
        this.email = email;
    }
    public Perito(String dniPerito, String nombre, String apellidos, String direccion, String provincia, int telefono, String email, Set expedientes) {
       this.dniPerito = dniPerito;
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.direccion = direccion;
       this.provincia = provincia;
       this.telefono = telefono;
       this.email = email;
       this.expedientes = expedientes;
    }
   
    public String getDniPerito() {
        return this.dniPerito;
    }
    
    public void setDniPerito(String dniPerito) {
        this.dniPerito = dniPerito;
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
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
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


