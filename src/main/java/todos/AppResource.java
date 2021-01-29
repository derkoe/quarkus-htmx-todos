package todos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/")
public class AppResource {

  @GET
  public Response home() {
    return Response.status(Status.TEMPORARY_REDIRECT).header("Location", "/todos").build();
  }
}
