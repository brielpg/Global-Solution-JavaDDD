package fiap.globalsolution.repositories;

import fiap.globalsolution.infrastructure.ConexaoDatabase;
import fiap.globalsolution.models.Denuncia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import static java.util.Map.entry;

public class DenunciaRepository {
    public static final Logger LOGGER = LogManager.getLogger(UsuarioRepository.class);
    private final ConexaoDatabase conexaoDB = new ConexaoDatabase();

    public static final String TABLE_NAME = "T_BW_DENUNCIA";

    public static final Map<String, String> TABLE_COLUMNS = Map.ofEntries(
            entry("DENUNCIA", "denuncia"),
            entry("CIDADE", "cidade"),
            entry("DESCRICAO", "descricao_local"),
            entry("COMENTARIO", "comentario"),
            entry("COORDENADAS", "coordenadas")
            );

    public void salvarDenuncia(Denuncia denuncia) {
        try (Connection connection = conexaoDB.obterConexao()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO %s(%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
                            .formatted(TABLE_NAME,
                                    TABLE_COLUMNS.get("DENUNCIA"),
                                    TABLE_COLUMNS.get("CIDADE"),
                                    TABLE_COLUMNS.get("DESCRICAO"),
                                    TABLE_COLUMNS.get("COMENTARIO"),
                                    TABLE_COLUMNS.get("COORDENADAS")
            ));
            preparedStatement.setString(1, denuncia.getDenuncia());
            preparedStatement.setString(2, denuncia.getCidade());
            preparedStatement.setString(3, denuncia.getDescricao_local());
            preparedStatement.setString(4, denuncia.getComentario());
            preparedStatement.setString(5, denuncia.getCoordenadas());
            preparedStatement.executeUpdate();
            LOGGER.info("Denúncia salva com sucesso!");
        } catch (SQLException e) {
            LOGGER.error("Erro ao salvar Denúncia!");
        }
    }

    public Boolean validarDados(Denuncia denuncia){
        return denuncia.getComentario() != null && denuncia.getCoordenadas() != null && denuncia.getCidade() != null
                && denuncia.getDescricao_local() != null && denuncia.getDenuncia() != null;
    }
}
