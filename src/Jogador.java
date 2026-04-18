public class Jogador {
    String nome;
    int vida;
    int ataque;
    int defesa;
    int nivel;
    int experiencia;
    boolean escudoAtivo;
    Inventario inventario;

    public Jogador(String nome) {
        this.nome = nome;
        vida = 100;
        ataque = 10;
        defesa = 5;
        nivel = 1;
        experiencia = 0;
        escudoAtivo = false;
        inventario = new Inventario();
    }

    public void mostrarStatus() {
        System.out.println("=== STATUS DO JOGADOR ===");
        System.out.println("Nome: " + nome);
        System.out.println("Vida: " + vida);
        System.out.println("Ataque: " + ataque);
        System.out.println("Defesa: " + defesa);
        System.out.println("Nível: " + nivel);
        System.out.println("Experiência: " + experiencia);
        System.out.println("Poções: " + inventario.quantidadePocoes);
        System.out.println("Bombas: " + inventario.quantidadeBombas);
        System.out.println("Escudos: " + inventario.quantidadeEscudos);
    }

    public void ganharExperiencia(int quantidade) {
        experiencia = experiencia + quantidade;
        System.out.println(nome + " ganhou " + quantidade + " de experiência!");

        if (experiencia >= 20) {
            subirNivel();
        }
    }

    public void subirNivel() {
        nivel = nivel + 1;
        experiencia = experiencia - 20;
        vida = 100;
        ataque = ataque + 2;
        defesa = defesa + 1;

        System.out.println(nome + " subiu para o nível " + nivel + "!");
        System.out.println("Os atributos foram aumentados.");
    }
}