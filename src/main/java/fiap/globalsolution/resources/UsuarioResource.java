package fiap.globalsolution.resources;

import fiap.globalsolution.models.Usuario;
import fiap.globalsolution.repositories.UsuarioRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Optional;

@Path("globalsolution")
public class UsuarioResource {
    private final UsuarioRepository usuarioRepository;

    public UsuarioResource(){
        usuarioRepository = new UsuarioRepository();
    }

    //
    // ENDPOINTS
    //
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUsuario(Usuario usuario){
        Optional<Usuario> usuarioOptional = usuarioRepository.verificarLogin(usuario.getEmail(), usuario.getSenha());
        if (usuarioOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(usuarioOptional.get())
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(Usuario usuario){
        if (usuarioRepository.validarDados(usuario)) {
            usuarioRepository.cadastrar(usuario);
            return Response.status(Response.Status.CREATED)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos! Verifique os campos obrigatórios.")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .build();
        }
    }

    //
    // OPTIONS
    //
    @OPTIONS
    @Path("/login")
    public Response handlePreflightLogin(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    @OPTIONS
    public Response handlePreflightCadastro(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }
}
