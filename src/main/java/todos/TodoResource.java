package todos;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import java.util.List;
import java.util.UUID;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.resteasy.annotations.Form;

@Path("/todos")
public class TodoResource {

  @CheckedTemplate
  public static class Templates {

    public static native TemplateInstance list(List<Todo> todos);

    public static native TemplateInstance item(Todo todo);
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
    return Templates.list(todos).data("itemsLeft", Todo.countActive());
  }

  @POST
  @Transactional
  public Response add(@Form Todo todo, @HeaderParam("HX-Request") boolean hxRequest) {
    Todo.persist(todo);
    if (hxRequest) {
      return Response.ok(Templates.item(todo)).header("HX-Trigger", "clear-add-todo").build();
    }
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
  public Object toggle(@PathParam("id") UUID id, @HeaderParam("HX-Request") boolean hxRequest) {
    Todo todo = Todo.findById(id);
    todo.completed = !todo.completed;
    if (hxRequest) {
      return Templates.item(todo);
    }
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @POST
  @Path("{id}/delete")
  @Transactional
  public Response delete(@PathParam("id") UUID id, @HeaderParam("HX-Request") boolean hxRequest) {
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
