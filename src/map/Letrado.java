package map;
// Generated 26-may-2020 18:05:19 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Letrado generated by hbm2java
 */
public class Letrado  implements java.io.Serializable {


     private String dniLetrado;
     private String nombre;
     private String apellidos;
     private String colegio;
     private String direccion;
     private int telefono;
     private String email;
     private Set expedientes = new HashSet(0);

    public Letrado() {
    }

	
    public Letrado(String dniLetrado, String nombre, String apellidos, String colegio, String direccion, int telefono, String email) {
        this.dniLetrado = dniLetrado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.colegio = colegio;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    public Letrado(String dniLetrado, String nombre, String apellidos, String colegio, String direccion, int telefono, String email, Set expedientes) {
       this.dniLetrado = dniLetrado;
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.colegio = colegio;
       this.direccion = direccion;
       this.telefono = telefono;
       this.email = email;
       this.expedientes = expedientes;
    }
   
    public String getDniLetrado() {
        return this.dniLetrado;
    }
    
    public void setDniLetrado(String dniLetrado) {
        this.dniLetrado = dniLetrado;
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
    public String getColegio() {
        return this.colegio;
    }
    
    public void setColegio(String colegio) {
        this.colegio = colegio;
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

    @Override
    public String toString() {
        return dniLetrado;
    }


}


