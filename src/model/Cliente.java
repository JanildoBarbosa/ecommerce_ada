package model;

public class Cliente {
    private String nome;
    private int documentoIdentificacao;

    public Cliente(String nome, int documentoIdentificacao) {
        this.nome = nome;
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDocumentoIdentificacao() {
        return documentoIdentificacao;
    }

    public void setDocumentoIdentificacao(int documentoIdentificacao) {
        this.documentoIdentificacao = documentoIdentificacao;
    }

    public void mostrarInformacoes() {
        System.out.println("Cliente: " + nome + " | Documento: " + documentoIdentificacao);
    }

}
