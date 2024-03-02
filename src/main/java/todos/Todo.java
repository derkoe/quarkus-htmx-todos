package todos;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
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

  @CreationTimestamp
  @Column(name = "created_timestamp")
  public Timestamp createdTimestamp;

  public boolean isNotCompleted() {
    return !completed;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public static List<Todo> listAll() {
    return findAll(Sort.ascending("createdTimestamp")).list();
  }

  public static List<Todo> listActive() {
    return list("completed=false", Sort.ascending("createdTimestamp"));
  }

  public static List<Todo> listCompleted() {
    return list("completed=true", Sort.ascending("createdTimestamp"));
  }

  public static void updateAllCompleted(boolean completed) {
    update("completed = ?1", completed);
  }

  public static void deleteCompleted() {
    delete("completed = true");
  }

  public static int countActive() {
    return (int) count("completed != true");
  }
}
