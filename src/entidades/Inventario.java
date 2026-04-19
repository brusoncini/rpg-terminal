package entidades;

public class Inventario {
    int quantidadePocoes;
    int quantidadeBombas;
    int quantidadeEscudos;

    public Inventario() {
        this.quantidadePocoes = 2;
        this.quantidadeBombas = 1;
        this.quantidadeEscudos = 1;
    }

    public int getQuantidadePocoes() {
        return quantidadePocoes;
    }

    public void setQuantidadePocoes(int quantidadePocoes) {
        this.quantidadePocoes = quantidadePocoes;
    }

    public int getQuantidadeBombas() {
        return quantidadeBombas;
    }

    public void setQuantidadeBombas(int quantidadeBombas) {
        this.quantidadeBombas = quantidadeBombas;
    }

    public int getQuantidadeEscudos() {
        return quantidadeEscudos;
    }

    public void setQuantidadeEscudos(int quantidadeEscudos) {
        this.quantidadeEscudos = quantidadeEscudos;
    }

    public void adicionarPocao() {
        this.quantidadePocoes = this.quantidadePocoes + 1;
    }

    public void adicionarBomba() {
        this.quantidadeBombas = this.quantidadeBombas + 1;
    }

    public void adicionarEscudo() {
        this.quantidadeEscudos = this.quantidadeEscudos + 1;
    }

    public boolean temPocao() {
        return this.quantidadePocoes > 0;
    }

    public boolean temBomba() {
        return this.quantidadeBombas > 0;
    }

    public boolean temEscudo() {
        return this.quantidadeEscudos > 0;
    }

    public void usarPocao() {
        this.quantidadePocoes = this.quantidadePocoes - 1;
    }

    public void usarBomba() {
        this.quantidadeBombas = this.quantidadeBombas - 1;
    }

    public void usarEscudo() {
        this.quantidadeEscudos = this.quantidadeEscudos - 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== INVENTÁRIO ===\n");
        sb.append("Poções : ").append(quantidadePocoes).append("\n");
        sb.append("Bombas : ").append(quantidadeBombas).append("\n");
        sb.append("Escudos: ").append(quantidadeEscudos);

        return sb.toString();
    }
}