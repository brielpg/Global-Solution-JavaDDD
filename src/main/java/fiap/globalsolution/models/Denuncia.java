package fiap.globalsolution.models;

public class Denuncia {
    private String denuncia;
    private String cidade;
    private String descricao_local;
    private String comentario;
    private String coordenadas;
    private int id_usuario;

    public Denuncia() {
    }

    public Denuncia(String denuncia, String cidade, String descricao_local, String comentario, String coordenadas, int id_usuario) {
        this.denuncia = denuncia;
        this.cidade = cidade;
        this.descricao_local = descricao_local;
        this.comentario = comentario;
        this.coordenadas = coordenadas;
        this.id_usuario = id_usuario;
    }

    public String getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(String denuncia) {
        this.denuncia = denuncia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDescricao_local() {
        return descricao_local;
    }

    public void setDescricao_local(String descricao_local) {
        this.descricao_local = descricao_local;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
