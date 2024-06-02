package fiap.globalsolution.resources;

import fiap.globalsolution.models.Denuncia;
import fiap.globalsolution.repositories.DenunciaRepository;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/denunciar")
public class DenunciaResource {
    private final DenunciaRepository denunciaRepository;

    public DenunciaResource() {
        denunciaRepository = new DenunciaRepository();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarDenuncia(Denuncia denuncia) {
        if(denunciaRepository.validarDados(denuncia)){
            denunciaRepository.salvarDenuncia(denuncia);

            return Response.status(Response.Status.CREATED)
                    .entity("Denúncia criada com sucesso!")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados inválidos! Verifique os campos obrigatórios.")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
        }
    }
}
