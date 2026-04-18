public class Jogador {

    String nome;
    int vida;
    int ataque;
    int defesa;

    public Jogador(String nome) {
        this.nome = nome;
        this.vida = 100;
        this.ataque = 10;
        this.defesa = 5;
    }

    public void mostrarStatus() {
        System.out.println("=== STATUS DO JOGADOR ===");
        System.out.println("Nome: " + this.nome);
        System.out.println("Vida: " + this.vida);
        System.out.println("Ataque: " + this.ataque);
        System.out.println("Defesa: " + this.defesa);
    }

}
