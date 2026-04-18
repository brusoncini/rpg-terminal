import java.util.Scanner;

public class Jogo {

    Scanner leitor;
    boolean jogoRodando;
    Jogador jogador;

    public Jogo() {
        this.leitor = new Scanner(System.in);
        this.jogoRodando = true;
        this.jogador = null;
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
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void mostrarMenu() {
        System.out.println("=== RPG EM TERMINAL ===");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status");
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
}

