package com.example.pontosturisticos.modelos;

public class PontosTuristicos {

    // Atributos da classe
    private String id;
    private String nome;
    private Double distancia;
    private String imagem;

    public PontosTuristicos(String id, String nome, Double distancia, String imagem) {
        this.id = id;
        this.nome = nome;
        this.distancia = distancia;
        this.imagem = imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
