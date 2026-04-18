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

            System.out.println();
        }
    }

    public void mostrarMenu() {
        System.out.println("=== RPG EM TERMINAL ===");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status");
        System.out.println("3 - Explorar");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    public void novoJogo() {
        System.out.print("Digite o nome do personagem: ");
        String nome = this.leitor.nextLine();

        this.jogador = new Jogador(nome);

        System.out.println("Novo jogo iniciado!");
        System.out.println("Personagem criado com sucesso.");
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
            Inimigo inimigo = new Inimigo("Goblin", 30, 8);
            System.out.println("Um " + inimigo.nome + " apareceu no caminho!");
            this.lutar(inimigo);

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

    public void lutar(Inimigo inimigo) {
        System.out.println("A batalha começou!");

        while (this.jogador.vida > 0 && inimigo.vida > 0) {
            System.out.println();
            System.out.println("=== COMBATE ===");
            System.out.println(this.jogador.nome + " - Vida: " + this.jogador.vida);
            System.out.println(inimigo.nome + " - Vida: " + inimigo.vida);
            System.out.println("1 - Atacar");
            System.out.println("2 - Fugir");
            System.out.print("Escolha: ");

            int opcaoCombate = this.leitor.nextInt();
            this.leitor.nextLine();

            if (opcaoCombate == 1) {
                this.atacarInimigo(inimigo);

            } else if (opcaoCombate == 2) {
                boolean fugiu = this.tentarFugir(inimigo);
                if (fugiu) {
                    return;
                }

            } else {
                System.out.println("Opção inválida no combate.");
            }
        }
    }

    public void atacarInimigo(Inimigo inimigo) {
        System.out.println(this.jogador.nome + " atacou o " + inimigo.nome + "!");
        inimigo.vida = inimigo.vida - this.jogador.ataque;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("O " + inimigo.nome + " ficou com " + inimigo.vida + " de vida.");

        if (inimigo.vida <= 0) {
            System.out.println(this.jogador.nome + " venceu a batalha!");
        } else {
            this.ataqueDoInimigo(inimigo);
        }
    }

    public void ataqueDoInimigo(Inimigo inimigo) {
        System.out.println("O " + inimigo.nome + " atacou " + this.jogador.nome + "!");
        this.jogador.vida = this.jogador.vida - inimigo.ataque;

        if (this.jogador.vida < 0) {
            this.jogador.vida = 0;
        }

        System.out.println(this.jogador.nome + " ficou com " + this.jogador.vida + " de vida.");

        if (this.jogador.vida <= 0) {
            System.out.println(this.jogador.nome + " foi derrotado.");
            this.jogoRodando = false;
        }
    }

    public boolean tentarFugir(Inimigo inimigo) {
        int fuga = this.aleatorio.nextInt(2);

        if (fuga == 0) {
            System.out.println(this.jogador.nome + " conseguiu fugir da batalha!");
            return true;
        } else {
            System.out.println(this.jogador.nome + " tentou fugir, mas não conseguiu!");
            System.out.println("O " + inimigo.nome + " aproveitou para atacar!");
            this.ataqueDoInimigo(inimigo);
            return false;
        }
    }
}