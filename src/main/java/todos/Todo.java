package todos;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jboss.resteasy.annotations.jaxrs.FormParam;

@Entity
@Table(name = "todos")
public class Todo extends PanacheEntityBase {

  @Id
  @GeneratedValue
  public UUID id;

  @FormParam
  public String title;

  public Boolean completed = Boolean.FALSE;
}
