import java.util.Scanner;

public class Jogo {

    Scanner leitor;
    boolean jogoRodando;

    public Jogo() {
        this.leitor = new Scanner(System.in);
        this.jogoRodando = true;
    }

    public void iniciar() {
        while (jogoRodando) {
            this.mostrarMenu();
            int opcao = this.leitor.nextInt();

            switch (opcao) {
                case 0:
                    System.out.println("Saindo do jogo...");
                    this.jogoRodando = false;
                    break;
                case 1:
                    System.out.println("Novo jogo iniciado!");
                    break;
                case 2:
                    System.out.println("Status ainda não disponível.");
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
}

