
package models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author FranciscoRomeroGuill
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer id;
    private String alias;
    private String password;
    

}
