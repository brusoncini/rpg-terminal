import java.util.Random;
import java.util.Scanner;

public class Jogo {

    Scanner leitor;
    boolean jogoRodando;
    Jogador jogador;
    Random aleatorio;

    public Jogo() {
        this.leitor = new Scanner(System.in);
        this.jogoRodando = true;
        this.jogador = null;
        this.aleatorio = new Random();
    }

    public void iniciar() {
        while (jogoRodando) {
            this.mostrarMenu();
            int opcao = this.leitor.nextInt();
            this.leitor.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("Saindo do jogo...");
                    this.jogoRodando = false;
                    break;
                case 1:
                    this.novoJogo();
                    break;
                case 2:
                    this.verStatus();
                    break;
                case 3:
                    this.explorar();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void mostrarMenu() {
        System.out.println("=== RPG EM TERMINAL ===");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status");
        System.out.println("3 - Explorar!");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    public void novoJogo() {
        System.out.print("Digite o nome do personagem: ");
        String nome = this.leitor.nextLine();

        this.jogador = new Jogador(nome);

        System.out.println("Novo jogo iniciado com sucesso.");
        System.out.println("Personagem criado: " + this.jogador.nome);
    }

    public void verStatus() {
        if (this.jogador == null) {
            System.out.println("Nenhum personagem foi criado ainda.");
        } else {
            this.jogador.mostrarStatus();
        }
    }

    public void explorar() {
        if (this.jogador == null) {
            System.out.println("Crie um personagem antes de explorar.");
            return;
        }

        System.out.println(this.jogador.nome + " saiu para explorar...");
        int evento = this.aleatorio.nextInt(3);

        if (evento == 0) {
            System.out.println("Um inimigo apareceu no caminho!");
        } else if (evento == 1) {
            System.out.println("Você encontrou uma poção!");
            this.jogador.vida = this.jogador.vida + 20;

            if (this.jogador.vida > 100) {
                this.jogador.vida = 100;
            }

            System.out.println("A vida de " + this.jogador.nome + " foi recuperada.");
        } else {
            System.out.println(this.jogador.nome + " andou bastante, mas não encontrou nada.");
        }
    }
}

