/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidades.Genero;
import entidades.Pelicula;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author ivanb
 */
@ManagedBean(name = "peliBean")
@ViewScoped
public class PeliculaBean {
    
    private Long idPelicula;  
    private String nombrePelicula;  
    private int annoPelicula;  
    private Date fechaRegistro;  
    private Long idGenero;  // Cambiado el nombre a 'idGenero'  

    private List<Pelicula> peliculas;  
    private List<Genero> generos; // Cambio la forma de acceder a los géneros   

    private Pelicula nuevaPelicula;  
    private String busqueda;  
    private List<Pelicula> peliculasFiltradas;  
    private Pelicula peliculaSeleccionada;


    public PeliculaBean() {  
        nuevaPelicula = new Pelicula();  
        cargarPeliculas();  
        cargarGeneros();  
        peliculasFiltradas = peliculas;  
    }  
    
    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("idPelicula")) {
            Long idPelicula = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPelicula"));
            this.peliculaSeleccionada = getPeliculaById(idPelicula);
            this.idPelicula = peliculaSeleccionada.getIdPelicula();
            this.nombrePelicula = peliculaSeleccionada.getNombrePelicula();
            this.annoPelicula = peliculaSeleccionada.getAnnoPelicula();
            this.idGenero = peliculaSeleccionada.getGenero().getIdGenero();
        }
    }


    // Getters y Setters  

    public Long getIdPelicula() {  
        return idPelicula;  
    }  

    public void setIdPelicula(Long idPelicula) {  
        this.idPelicula = idPelicula;  
    }  

    public String getNombrePelicula() {  
        return nombrePelicula;  
    }  

    public void setNombrePelicula(String nombrePelicula) {  
        this.nombrePelicula = nombrePelicula;  
    }  

    public int getAnnoPelicula() {  
        return annoPelicula;  
    }  

    public void setAnnoPelicula(int annoPelicula) {  
        this.annoPelicula = annoPelicula;  
    }  

    public Date getFechaRegistro() {  
        return fechaRegistro;  
    }  

    public void setFechaRegistro(Date fechaRegistro) {  
        this.fechaRegistro = fechaRegistro;  
    }  

    public Long getIdGenero() {  
        return idGenero;  
    }  

    public void setIdGenero(Long idGenero) {  
        this.idGenero = idGenero;  
    }  

    public List<Pelicula> getPeliculas() {  
        return peliculas;  
    }  

    public void setPeliculas(List<Pelicula> peliculas) {  
        this.peliculas = peliculas;  
    }  

    public List<Genero> getGeneros() { // Getter para géneros  
        return generos;  
    }  

    public void setGeneros(List<Genero> generos) {  
        this.generos = generos;  
    }  

    public Pelicula getNuevaPelicula() {  
        return nuevaPelicula;  
    }  

    public void setNuevaPelicula(Pelicula nuevaPelicula) {  
        this.nuevaPelicula = nuevaPelicula;  
    }  

    public String getBusqueda() {  
        return busqueda;  
    }  

    public void setBusqueda(String busqueda) {  
        this.busqueda = busqueda;  
    }  

    public List<Pelicula> getPeliculasFiltradas() {  
        return peliculasFiltradas;  
    }  

    public void setPeliculasFiltradas(List<Pelicula> peliculasFiltradas) {  
        this.peliculasFiltradas = peliculasFiltradas;  
    }  

    public Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }

    public void setPeliculaSeleccionada(Pelicula peliculaSeleccionada) {
        this.peliculaSeleccionada = peliculaSeleccionada;
    }

    // Método para cargar películas desde la base de datos  
    public void cargarPeliculas() {  
      Session sesion = null;  
      try {  
          sesion = HibernateUtil.getSessionFactory().openSession();  
          Query query = sesion.createQuery("FROM Pelicula p JOIN FETCH p.genero");  
          peliculas = query.list();  
          if (peliculas.isEmpty()) {  
              System.out.println("No se encontraron películas.");  
          } else {  
              System.out.println("Películas cargadas: " + peliculas.size());  
          }  
      } catch (Exception e) {  
          // Manejo más detallado de la excepción  
          System.err.println("Error cargando películas: " + e.getMessage());  
      } finally {  
          if (sesion != null) {  
              sesion.close();  
          }  
      }  
  } 

    // Método para cargar géneros desde la base de datos  
    public void cargarGeneros() {  
        Session sesion = null;  
        try {  
            sesion = HibernateUtil.getSessionFactory().openSession();  
            Query query = sesion.createQuery("FROM Genero");  
            generos = query.list();  
            // Comprueba la lista  
            if (generos.isEmpty()) {  
                System.out.println("No se encontraron géneros.");  
            } else {  
                System.out.println("Géneros cargados: " + generos.size());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (sesion != null) {  
                sesion.close();  
            }  
        }  
    }  
    
    // Método para obtener una película por ID
    public Pelicula getPeliculaById(Long idPelicula) {
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            return (Pelicula) sesion.get(Pelicula.class, idPelicula);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }
    
   // Método para editar una película
    public String editarPelicula(Pelicula pelicula) {
        this.peliculaSeleccionada = pelicula;
        this.idPelicula = pelicula.getIdPelicula();
        this.nombrePelicula = pelicula.getNombrePelicula();
        this.annoPelicula = pelicula.getAnnoPelicula();
        this.idGenero = pelicula.getGenero().getIdGenero();
        return "editarPelicula?faces-redirect=true&idPelicula=" + idPelicula;
    }

    // Método para guardar  una película
    public String guardarPelicula() {
        if (peliculaSeleccionada != null) {
            // Actualizar la película seleccionada
            peliculaSeleccionada.setNombrePelicula(nombrePelicula);
            peliculaSeleccionada.setAnnoPelicula(annoPelicula);
            peliculaSeleccionada.setGenero((Genero) HibernateUtil.getSessionFactory().openSession().get(Genero.class, idGenero));
            Session sesion = null;
            Transaction tx = null;
            try {
                sesion = HibernateUtil.getSessionFactory().openSession();
                tx = sesion.beginTransaction();
                sesion.update(peliculaSeleccionada);
                tx.commit();
                cargarPeliculas();
                FacesMessage msg = new FacesMessage("Película actualizada con éxito!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "index";
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                FacesMessage msg = new FacesMessage("Error al actualizar la película: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            } finally {
                if (sesion != null) {
                    sesion.close();
                }
            }
        } else {
            // Crear una nueva película
            Pelicula nuevaPelicula = new Pelicula();
            nuevaPelicula.setNombrePelicula(nombrePelicula);
            nuevaPelicula.setAnnoPelicula(annoPelicula);
            nuevaPelicula.setGenero((Genero) HibernateUtil.getSessionFactory().openSession().get(Genero.class, idGenero));
            Session sesion = null;
            Transaction tx = null;
            try {
                sesion = HibernateUtil.getSessionFactory().openSession();
                tx = sesion.beginTransaction();
                sesion.save(nuevaPelicula);
                tx.commit();
                cargarPeliculas();
                FacesMessage msg = new FacesMessage("Película creada con éxito!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "index";
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                FacesMessage msg = new FacesMessage("Error al crear la película: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            } finally {
                if (sesion != null) {
                    sesion.close();
                }
            }
        }
    }
    
    // Método para eliminar una película
    public String eliminarPelicula(Pelicula pelicula) {
        Session sesion = null;
        Transaction tx = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            tx = sesion.beginTransaction();
            sesion.delete(pelicula);
            tx.commit();
            cargarPeliculas(); // Recargar la lista
            FacesMessage msg = new FacesMessage("Película eliminada con éxito!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "index?faces-redirect=true"; // Redireccionar a la página principal
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            FacesMessage msg = new FacesMessage("Error al eliminar la película: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        return null;
    }

    // Método para buscar películas  
    public void buscarPeliculas() {
        // Si la búsqueda está vacía o nula, mostramos todas las películas
        if (busqueda == null || busqueda.trim().isEmpty()) {
            peliculasFiltradas = peliculas; // Mostrar todas las películas
            return;
        }

        // Inicia la sesión
        Session sesion = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();

            // Verifica si la búsqueda contiene solo texto o números
            boolean esNumero = false;
            try {
                Long.parseLong(busqueda); // Intentar convertir a número
                esNumero = true;
            } catch (NumberFormatException e) {
                esNumero = false;
            }

            String hql = "FROM Pelicula p "
                       + "JOIN FETCH p.genero g "
                       + "WHERE ";

            // Si es un número, buscamos por ID o año; si no, buscamos por nombre y género
            if (esNumero) {
                hql += "(p.idPelicula = :idPelicula OR p.annoPelicula = :annoPelicula)";
            } else {
                hql += "(p.nombrePelicula LIKE :nombre "
                     + "OR g.nombreGenero LIKE :nombreGenero)";
            }

            // Creamos la consulta
            Query query = sesion.createQuery(hql);

            // Establecer parámetros de la consulta
            if (esNumero) {
                query.setParameter("idPelicula", Long.parseLong(busqueda)); // Búsqueda por ID de película
                query.setParameter("annoPelicula", Integer.parseInt(busqueda)); // Búsqueda por año
            } else {
                query.setParameter("nombre", "%" + busqueda + "%"); // Búsqueda por nombre de película
                query.setParameter("nombreGenero", "%" + busqueda + "%"); // Búsqueda por nombre de género
            }

            // Ejecutar la consulta y obtener las películas filtradas
            peliculasFiltradas = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null) {
                sesion.close(); // Asegúrate de cerrar la sesión en el bloque finally
            }
        }
    }
    
   // Método para actualizar una película
    public String actualizarPelicula() {
        Session sesion = null;
        Transaction tx = null;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            tx = sesion.beginTransaction();
            Pelicula pelicula = peliculaSeleccionada; // Utiliza la instancia seleccionada
            if (pelicula != null) {
                pelicula.setNombrePelicula(nombrePelicula);
                pelicula.setAnnoPelicula(annoPelicula);
                pelicula.setGenero((Genero) HibernateUtil.getSessionFactory().openSession().get(Genero.class, idGenero));
                sesion.update(pelicula);
                tx.commit();
                cargarPeliculas();
                FacesMessage msg = new FacesMessage("Película actualizada con éxito!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "index";
            } else {
                FacesMessage msg = new FacesMessage("No se encontró la película para actualizar.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            FacesMessage msg = new FacesMessage("Error al actualizar la película: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

}
    

