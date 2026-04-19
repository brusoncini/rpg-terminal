public class Jogador {
    String nome;
    int vida;
    int vidaMaxima;
    int ataque;
    int defesa;
    int nivel;
    int experiencia;
    int ouro;
    boolean escudoAtivo;
    Inventario inventario;

    public Jogador(String nome) {
        this.nome = nome;
        vida = 100;
        vidaMaxima = 100;
        ataque = 10;
        defesa = 5;
        nivel = 1;
        experiencia = 0;
        ouro = 0;
        escudoAtivo = false;
        inventario = new Inventario();
    }

    public void mostrarStatus() {
        System.out.println(this);
        System.out.println(inventario.toString());
    }

    public void ganharExperiencia(int quantidade) {
        experiencia = experiencia + quantidade;
        System.out.println(nome + " ganhou " + quantidade + " de experiência!");

        while (experiencia >= 20) {
            subirNivel();
        }
    }

    public void ganharOuro(int quantidade) {
        ouro = ouro + quantidade;
        System.out.println(nome + " ganhou " + quantidade + " de ouro!");
    }

    public void subirNivel() {
        nivel = nivel + 1;
        experiencia = experiencia - 20;
        vidaMaxima = vidaMaxima + 20;
        vida = vidaMaxima;
        ataque = ataque + 2;
        defesa = defesa + 1;

        System.out.println(nome + " subiu para o nível " + nivel + "!");
        System.out.println("Os atributos foram aumentados.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== STATUS DO JOGADOR ===\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Vida: ").append(vida).append("/").append(vidaMaxima).append("\n");
        sb.append("Ataque: ").append(ataque).append("\n");
        sb.append("Defesa: ").append(defesa).append("\n");
        sb.append("Nível: ").append(nivel).append("\n");
        sb.append("Experiência: ").append(experiencia).append("/20");

        return sb.toString();
    }
}