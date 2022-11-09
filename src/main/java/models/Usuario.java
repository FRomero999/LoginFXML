
package models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author FranciscoRomeroGuill
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer id;
    private String alias;
    private String password;
    
    @OneToMany( mappedBy="usuario", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Actividad> actividades;

    public Integer getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", alias=" + alias + ", password=" + password + ", actividades=" + actividades + '}';
    }
    

}
