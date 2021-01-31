package todos;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    return Templates.list().data("todos", Todo.listAll()).data("all", true);
  }

  @GET
  @Path("/active")
  public TemplateInstance active() {
    return Templates.list().data("todos", Todo.findActive()).data("active", true);
  }

  @GET
  @Path("/completed")
  public TemplateInstance completed() {
    return Templates.list().data("todos", Todo.findCompleted()).data("completed", "true");
  }

  @POST
  @Transactional
  public Response add(@Form Todo todo) {
    Todo.persist(todo);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @Transactional
  @POST
  @Path("/toggle-all")
  public Response toggle() {
    boolean allCompleted = Todo.findActive().isEmpty();
    Todo.updateAllCompleted(!allCompleted);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @POST
  @Path("{id}/delete")
  @Transactional
  public Response delete(@PathParam("id") UUID id) {
    Todo.deleteById(id);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }
}
