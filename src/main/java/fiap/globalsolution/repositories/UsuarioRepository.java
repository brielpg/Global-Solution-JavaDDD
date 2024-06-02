package fiap.globalsolution.repositories;

import fiap.globalsolution.infrastructure.ConexaoDatabase;
import fiap.globalsolution.models.Usuario;
import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.util.Map.entry;

public class UsuarioRepository {
    public static final Logger LOGGER = LogManager.getLogger(UsuarioRepository.class);
    private final ConexaoDatabase conexaoDB = new ConexaoDatabase();

    public static final String TABLE_NAME = "T_BW_USUARIO";

    public static final Map<String, String> TABLE_COLUMNS = Map.ofEntries(
            entry("NOME", "nome"),
            entry("SOBRENOME", "sobrenome"),
            entry("CPF", "cpf"),
            entry("EMAIL", "email"),
            entry("TELEFONE", "telefone"),
            entry("PAIS", "pais"),
            entry("SENHA", "senha")
    );

    // VERIFICAR LOGIN DO USUARIO
    public Optional<Usuario> verificarLogin(String email, String senha) {
        try (Connection connection = conexaoDB.obterConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND senha = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Usuario(
                            resultSet.getString(TABLE_COLUMNS.get("NOME")),
                            resultSet.getString(TABLE_COLUMNS.get("SOBRENOME")),
                            resultSet.getString(TABLE_COLUMNS.get("CPF")),
                            resultSet.getString(TABLE_COLUMNS.get("EMAIL")),
                            resultSet.getString(TABLE_COLUMNS.get("TELEFONE")),
                            resultSet.getString(TABLE_COLUMNS.get("PAIS")),
                            resultSet.getString(TABLE_COLUMNS.get("SENHA"))
                    ));
                }
            }
            LOGGER.info("Logado com sucesso");
        } catch (SQLException e) {
            LOGGER.error("Erro ao realizar o login!");
        }
        return Optional.empty();
    }

    // CADASTRAR USUARIO
    public void cadastrar(Usuario usuario) {
        try (Connection connection = conexaoDB.obterConexao()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO %s(%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?)"
                            .formatted(TABLE_NAME,
                                    TABLE_COLUMNS.get("NOME"),
                                    TABLE_COLUMNS.get("SOBRENOME"),
                                    TABLE_COLUMNS.get("CPF"),
                                    TABLE_COLUMNS.get("EMAIL"),
                                    TABLE_COLUMNS.get("TELEFONE"),
                                    TABLE_COLUMNS.get("PAIS"),
                                    TABLE_COLUMNS.get("SENHA"))
            );
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getSobrenome());
            preparedStatement.setString(3, usuario.getCpf());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setString(5, usuario.getTelefone());
            preparedStatement.setString(6, usuario.getPais());
            preparedStatement.setString(7, usuario.getSenha());
            preparedStatement.executeUpdate();
            LOGGER.info("Cadastrado com sucesso!");
        } catch (SQLException e) {
            LOGGER.error("Erro ao cadastrar o usuário!");
        }
    }

    // Validacao
    public Boolean validarDados(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getSobrenome() == null || usuario.getCpf() == null || usuario.getTelefone() == null || usuario.getPais() == null || usuario.getSenha() == null) {
            return false;
        }

        return usuario.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
