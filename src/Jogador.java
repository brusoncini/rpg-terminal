public class Jogador {
    String nome;
    int vida;
    int ataque;
    int defesa;
    int nivel;
    int experiencia;
    int quantidadePocoes;

    public Jogador(String nome) {
        this.nome = nome;
        this.vida = 100;
        this.ataque = 10;
        this.defesa = 5;
        this.nivel = 1;
        this.experiencia = 0;
    }

    public void mostrarStatus() {
        System.out.println("=== STATUS DO JOGADOR ===");
        System.out.println("Nome: " + this.nome);
        System.out.println("Vida: " + this.vida);
        System.out.println("Ataque: " + this.ataque);
        System.out.println("Defesa: " + this.defesa);
        System.out.println("Nível: " + this.nivel);
        System.out.println("Experiência: " + this.experiencia);
    }

    public void ganharExperiencia(int quantidade) {
        this.experiencia = this.experiencia + quantidade;
        System.out.println(this.nome + " ganhou " + quantidade + " de experiência!");

        if (this.experiencia >= 20) {
            this.subirNivel();
        }
    }

    public void subirNivel() {
        this.nivel = this.nivel + 1;
        this.experiencia = this.experiencia - 20;
        this.vida = 100;
        this.ataque = this.ataque + 2;
        this.defesa = this.defesa + 1;

        System.out.println(this.nome + " subiu para o nível " + this.nivel + "!");
        System.out.println("Os atributos foram aumentados.");
    }
}