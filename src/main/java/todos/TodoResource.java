package todos;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.annotations.Form;

@Path("/todos")
public class TodoResource {

  @CheckedTemplate
  public static class Templates {

    public static native TemplateInstance list();
  }

  @GET
  public TemplateInstance list() {
    return showList(Todo.listAll()).data("all", true);
  }

  @GET
  @Path("/active")
  public TemplateInstance active() {
    return showList(Todo.listActive()).data("active", true);
  }

  @GET
  @Path("/completed")
  public TemplateInstance completed() {
    return showList(Todo.listCompleted()).data("completed", true);
  }

  private TemplateInstance showList(List<Todo> todos) {
    return Templates.list().data("todos", todos).data("itemsLeft", Todo.countActive());
  }

  @POST
  @Transactional
  public Response add(@Form Todo todo) {
    Todo.persist(todo);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @POST
  @Path("{id}")
  @Transactional
  public Response edit(@PathParam("id") UUID id, @Form Todo todo) {
    Todo dbTodo = Todo.findById(id);
    dbTodo.title = todo.title;
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @Transactional
  @POST
  @Path("/toggle-all")
  public Response toggle() {
    boolean allCompleted = Todo.countActive() == 0;
    Todo.updateAllCompleted(!allCompleted);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @Transactional
  @POST
  @Path("/{id}/toggle")
  public Response toggle(@PathParam("id") UUID id) {
    Todo todo = Todo.findById(id);
    todo.completed = !todo.completed;
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @POST
  @Path("{id}/delete")
  @Transactional
  public Response delete(@PathParam("id") UUID id) {
    Todo.deleteById(id);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @POST
  @Path("/clear-completed")
  @Transactional
  public Response deleteCompleted() {
    Todo.deleteCompleted();
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }
}
