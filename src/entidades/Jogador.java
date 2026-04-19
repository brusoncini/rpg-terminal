package entidades;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getOuro() {
        return ouro;
    }

    public void setOuro(int ouro) {
        this.ouro = ouro;
    }

    public boolean isEscudoAtivo() {
        return escudoAtivo;
    }

    public void setEscudoAtivo(boolean escudoAtivo) {
        this.escudoAtivo = escudoAtivo;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public void mostrarStatus() {
        System.out.println(this);
        System.out.println();
        System.out.println(inventario.toString());
    }

    public void curar(int cura) {
        vida = vida + cura;
    }

    public void tomarDano(int dano) {
        vida = vida - dano;
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

    public void gastarOuro(int quantidade) {
        ouro = ouro - quantidade;
        System.out.println("Ouro restante: " + ouro);
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