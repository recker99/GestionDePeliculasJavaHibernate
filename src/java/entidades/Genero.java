package entidades;
// Generated 23-11-2024 12:38:45 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Genero generated by hbm2java
 */
public class Genero  implements java.io.Serializable {


     private Long idGenero;
     private String nombreGenero;
     private Set peliculas = new HashSet(0);

    public Genero() {
    }

	
    public Genero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }
    public Genero(String nombreGenero, Set peliculas) {
       this.nombreGenero = nombreGenero;
       this.peliculas = peliculas;
    }
   
    public Long getIdGenero() {
        return this.idGenero;
    }
    
    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }
    public String getNombreGenero() {
        return this.nombreGenero;
    }
    
    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }
    public Set getPeliculas() {
        return this.peliculas;
    }
    
    public void setPeliculas(Set peliculas) {
        this.peliculas = peliculas;
    }

}

