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
}
