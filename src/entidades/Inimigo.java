package entidades;

public class Inimigo {
    String nome;
    RaridadeDoInimigo raridade;
    int vida;
    int ataque;

    public Inimigo(String nome, RaridadeDoInimigo raridade, int vida, int ataque) {
        this.nome = nome;
        this.raridade = raridade;
        this.vida = vida;
        this.ataque = ataque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RaridadeDoInimigo getRaridade() {
        return raridade;
    }

    public void setRaridade(RaridadeDoInimigo raridade) {
        this.raridade = raridade;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void tomarDano(int dano) {
        vida = vida - dano;
    }
}
