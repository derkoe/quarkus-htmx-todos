package todos;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
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

  public static List<Todo> findActive() {
    return list("completed", Boolean.FALSE);
  }

  public static List<Todo> findCompleted() {
    return list("completed", Boolean.TRUE);
  }

  public static void updateAllCompleted(boolean completed) {
    update("completed = ?1", completed);
  }
}
