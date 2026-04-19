package jogo;

import entidades.Inimigo;
import entidades.Jogador;
import servicos.Combate;
import servicos.Loja;

import java.util.Random;
import java.util.Scanner;

public class Jogo {
    Scanner leitor;
    boolean jogoRodando;
    boolean jogoVencido;
    Jogador jogador;
    Random aleatorio;

    public Jogo() {
        leitor = new Scanner(System.in);
        jogoRodando = true;
        jogoVencido = false;
        jogador = null;
        aleatorio = new Random();
    }

    public int lerOpcao() {
        while (!leitor.hasNextInt()) {
            System.out.println("Digite apenas números.");
            leitor.nextLine();
            System.out.print("Escolha: ");
        }

        int opcao = leitor.nextInt();
        leitor.nextLine();
        return opcao;
    }

    public void mostrarSeparador() {
        System.out.println("==============================");
    }

    public void mostrarTitulo(String titulo) {
        System.out.println();
        mostrarSeparador();
        System.out.println(titulo);
        mostrarSeparador();
    }

    public void mostrarStatusRapido() {
        if (jogador != null) {
            System.out.println("Vida: " + jogador.getVida() + "/" + jogador.getVidaMaxima()
                    + " | Ouro: " + jogador.getOuro()
                    + " | XP: " + jogador.getExperiencia() + "/20");
        }
    }

    public void mostrarRecompensas(int experiencia, int ouro) {
        mostrarTitulo("=== RECOMPENSAS ===");
        System.out.println("Experiência ganha: " + experiencia);
        System.out.println("Ouro ganho: " + ouro);
    }

    public void iniciar() {
        while (jogoRodando) {
            mostrarMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 0:
                    System.out.println("Saindo do jogo...");
                    jogoRodando = false;
                    break;

                case 1:
                    novoJogo();
                    break;

                case 2:
                    verStatus();
                    break;

                case 3:
                    explorar();
                    break;

                case 4:
                    Loja loja = new Loja(jogador, leitor);
                    loja.abrirLoja();
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

            System.out.println();
        }

        if (jogoVencido) {
            mostrarTitulo("=== VOCÊ VENCEU ===");
            System.out.println("Você derrotou o chefão e venceu!");
        }
    }

    public void mostrarMenu() {
        mostrarTitulo("=== RPG EM TERMINAL ===");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status");
        System.out.println("3 - Explorar");
        System.out.println("4 - Loja");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    public void novoJogo() {
        System.out.print("Digite o nome do personagem: ");
        String nome = leitor.nextLine();

        jogador = new Jogador(nome);

        System.out.println("Novo jogo iniciado!");
        System.out.println("Personagem criado com sucesso.");
    }

    public void verStatus() {
        if (jogador == null) {
            System.out.println("Nenhum personagem foi criado ainda.");
        } else {
            jogador.mostrarStatus();
        }
    }

    public void explorar() {
        if (jogador == null) {
            System.out.println("Crie um personagem antes de explorar.");
            return;
        }

        mostrarTitulo("=== EXPLORAÇÃO ===");
        mostrarStatusRapido();
        System.out.println(jogador.getNome() + " saiu para explorar...");

        int evento = aleatorio.nextInt(6);

        if (evento == 0) {

            if (jogador.getNivel() >= 5) {
                int sorteioChefe = aleatorio.nextInt(3);

                if (sorteioChefe == 0) {
                    Inimigo chefe = FabricaDeInimigos.criarChefe();
                    System.out.println("O CHEFÃO apareceu!");
                    System.out.println("Um " + chefe.getNome() + " surgiu diante de " + jogador.getNome() + "!");
                    lutar(chefe);
                    return;
                }
            }

            if (jogador.getNivel() >= 3) {
                int sorteioElite = aleatorio.nextInt(3);

                if (sorteioElite == 0) {
                    Inimigo elite = FabricaDeInimigos.criarElite();
                    System.out.println("Um inimigo de ELITE apareceu!");
                    System.out.println("Um " + elite.getNome() + " surgiu diante de " + jogador.getNome() + "!");
                    lutar(elite);
                    return;
                }
            }

            Inimigo inimigo = FabricaDeInimigos.criarInimigoAleatorio();
            System.out.println("Um " + inimigo.getNome() + " apareceu no caminho!");
            lutar(inimigo);

        } else if (evento == 1) {
            System.out.println(jogador.getNome() + " encontrou um baú misterioso!");
            abrirBau();

        } else if (evento == 2) {
            cairEmArmadilha();

        } else if (evento == 3) {
            encontrarCuraPequena();

        } else if (evento == 4) {
            encontrarOuro();

        } else {
            System.out.println(jogador.getNome() + " andou bastante, mas não encontrou nada.");
        }
    }

    public void lutar(Inimigo inimigo) {
        Combate combate = new Combate(jogador, leitor, aleatorio);
        combate.lutar(inimigo);

        if (combate.jogadorMorreu()) {
            jogoRodando = false;
        }

        if (combate.chefeDerrotado()) {
            jogoVencido = true;
            jogoRodando = false;
        }
    }

    public void cairEmArmadilha() {
        int danoDaArmadilha = 10;

        jogador.tomarDano(danoDaArmadilha);

        if (jogador.getVida() < 0) {
            jogador.setVida(0);
        }

        System.out.println(jogador.getNome() + " caiu em uma armadilha!");
        System.out.println(jogador.getNome() + " recebeu " + danoDaArmadilha + " de dano.");
        System.out.println("Vida atual: " + jogador.getVida() + "/" + jogador.getVidaMaxima());

        if (jogador.getVida() <= 0) {
            System.out.println(jogador.getNome() + " não resistiu à armadilha.");
            mostrarTitulo("=== FIM DE JOGO ===");
            jogoRodando = false;
        }
    }

    public void encontrarCuraPequena() {
        if (jogador.getVida() == jogador.getVidaMaxima()) {
            System.out.println(jogador.getNome() + " encontrou uma fonte mágica de cura, mas já estava com a vida cheia.");
            return;
        }

        // Cura 10% da vida do jogador
        int cura = jogador.getVidaMaxima() / 10;

        if (jogador.getVida() > jogador.getVidaMaxima()) {
            jogador.setVida(jogador.getVidaMaxima());
        }

        jogador.curar(cura);

        System.out.println(jogador.getNome() + " encontrou uma fonte mágica de cura!");
        System.out.println(jogador.getNome() + " recuperou " + cura + " de vida.");
        System.out.println("Vida atual: " + jogador.getVida() + "/" + jogador.getVidaMaxima());
    }

    public void abrirBau() {
        int itemDoBau = aleatorio.nextInt(3);

        if (itemDoBau == 0) {
            System.out.println(jogador.getNome() + " abriu o baú e encontrou uma poção!");
            jogador.getInventario().adicionarPocao();
            System.out.println("Agora " + jogador.getNome() + " tem " + jogador.getInventario().getQuantidadePocoes() + " poção(ões).");

        } else if (itemDoBau == 1) {
            System.out.println(jogador.getNome() + " abriu o baú e encontrou uma bomba!");
            jogador.getInventario().adicionarBomba();
            System.out.println("Agora " + jogador.getNome() + " tem " + jogador.getInventario().getQuantidadeBombas() + " bomba(s).");

        } else {
            System.out.println(jogador.getNome() + " abriu o baú e encontrou um escudo!");
            jogador.getInventario().adicionarEscudo();
            System.out.println("Agora " + jogador.getNome() + " tem " + jogador.getInventario().getQuantidadeEscudos() + " escudo(s).");
        }
    }

    public void encontrarOuro() {
        int quantidadeDeOuro = aleatorio.nextInt(16) + 5;

        jogador.ganharOuro(quantidadeDeOuro);

        System.out.println(jogador.getNome() + " encontrou " + quantidadeDeOuro + " moedas de ouro!");
        System.out.println("Agora " + jogador.getNome() + " tem " + jogador.getOuro() + " de ouro.");
    }
}