public class Jogador {
    String nome;
    int vida;
    int ataque;
    int defesa;
    int nivel;
    int experiencia;
    int quantidadePocoes;
    int quantidadeBombas;
    int quantidadeEscudos;
    boolean escudoAtivo;

    public Jogador(String nome) {
        this.nome = nome;
        this.vida = 100;
        this.ataque = 10;
        this.defesa = 5;
        this.nivel = 1;
        this.experiencia = 0;
        this.quantidadePocoes = 2;
        this.quantidadeBombas = 1;
        this.quantidadeEscudos = 1;
        this.escudoAtivo = false;
    }

    public void mostrarStatus() {
        System.out.println("=== STATUS DO JOGADOR ===");
        System.out.println("Nome: " + this.nome);
        System.out.println("Vida: " + this.vida);
        System.out.println("Ataque: " + this.ataque);
        System.out.println("Defesa: " + this.defesa);
        System.out.println("Nível: " + this.nivel);
        System.out.println("Experiência: " + this.experiencia);
        System.out.println("Poções: " + this.quantidadePocoes);
        System.out.println("Bombas: " + this.quantidadeBombas);
        System.out.println("Escudos: " + this.quantidadeEscudos);
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