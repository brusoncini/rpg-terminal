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
            System.out.println("Vida: " + jogador.vida + "/" + jogador.vidaMaxima
                    + " | Ouro: " + jogador.ouro
                    + " | XP: " + jogador.experiencia + "/20");
        }
    }

    public void mostrarRecompensas(int experiencia, int ouro) {
        mostrarTitulo("=== RECOMPENSAS ===");
        System.out.println("Experiência ganha: " + experiencia);
        System.out.println("Ouro ganho: " + ouro);
    }

    public void derrotarInimigo(Inimigo inimigo) {
        mostrarTitulo("=== BATALHA ENCERRADA ===");
        System.out.println(jogador.nome + " venceu a batalha!");

        if (inimigo.raridade == RaridadeDoInimigo.CHEFE) {
            System.out.println("O chefão foi derrotado!");
            System.out.println("Parabéns! " + jogador.nome + " venceu o jogo!");
            jogoVencido = true;
            jogoRodando = false;

        } else if (inimigo.raridade == RaridadeDoInimigo.ELITE) {
            System.out.println("Um inimigo de elite foi derrotado!");
            jogador.ganharExperiencia(30);
            jogador.ganharOuro(30);
            mostrarRecompensas(30, 30);

        } else {
            jogador.ganharExperiencia(10);
            jogador.ganharOuro(10);
            mostrarRecompensas(10, 10);
        }
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
                    abrirLoja();
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
        System.out.println(jogador.nome + " saiu para explorar...");

        int evento = aleatorio.nextInt(6);

        if (evento == 0) {

            if (jogador.nivel >= 5) {
                int sorteioChefe = aleatorio.nextInt(4);

                if (sorteioChefe == 0) {
                    Inimigo chefe = criarChefe();
                    System.out.println("O CHEFÃO apareceu!");
                    System.out.println("Um " + chefe.nome + " surgiu diante de " + jogador.nome + "!");
                    lutar(chefe);
                    return;
                }
            }

            if (jogador.nivel >= 3) {
                int sorteioElite = aleatorio.nextInt(3);

                if (sorteioElite == 0) {
                    Inimigo elite = criarElite();
                    System.out.println("Um inimigo de ELITE apareceu!");
                    System.out.println("Um " + elite.nome + " surgiu diante de " + jogador.nome + "!");
                    lutar(elite);
                    return;
                }
            }

            Inimigo inimigo = criarInimigoAleatorio();
            System.out.println("Um " + inimigo.nome + " apareceu no caminho!");
            lutar(inimigo);

        } else if (evento == 1) {
            System.out.println(jogador.nome + " encontrou um baú misterioso!");
            abrirBau();

        } else if (evento == 2) {
            cairEmArmadilha();

        } else if (evento == 3) {
            encontrarCuraPequena();

        } else if (evento == 4) {
            encontrarOuro();

        } else {
            System.out.println(jogador.nome + " andou bastante, mas não encontrou nada.");
        }
    }

    public void lutar(Inimigo inimigo) {
        mostrarTitulo("=== BATALHA ===");
        System.out.println("A batalha começou!");

        while (jogador.vida > 0 && inimigo.vida > 0) {
            System.out.println();
            System.out.println("=== COMBATE ===");
            System.out.println(jogador.nome + " - Vida: " + jogador.vida + "/" + jogador.vidaMaxima);
            System.out.println(inimigo.nome + " - Vida: " + inimigo.vida);
            System.out.println("1 - Atacar");
            System.out.println("2 - Fugir");
            System.out.println("3 - Usar item");
            System.out.print("Escolha: ");

            int opcaoCombate = lerOpcao();

            if (opcaoCombate == 1) {
                atacarInimigo(inimigo);

            } else if (opcaoCombate == 2) {
                boolean fugiu = tentarFugir(inimigo);
                if (fugiu) {
                    return;
                }

            } else if (opcaoCombate == 3) {
                abrirMenuDeItens(inimigo);

            } else {
                System.out.println("Opção inválida no combate.");
            }
        }
    }

    public void cairEmArmadilha() {
        int danoDaArmadilha = 10;

        jogador.vida = jogador.vida - danoDaArmadilha;

        if (jogador.vida < 0) {
            jogador.vida = 0;
        }

        System.out.println(jogador.nome + " caiu em uma armadilha!");
        System.out.println(jogador.nome + " recebeu " + danoDaArmadilha + " de dano.");
        System.out.println("Vida atual: " + jogador.vida);

        if (jogador.vida <= 0) {
            System.out.println(jogador.nome + " não resistiu à armadilha.");
            jogoRodando = false;
        }
    }

    public void encontrarCuraPequena() {
        if (jogador.vida == jogador.vidaMaxima) {
            System.out.println(jogador.nome + " encontrou uma fonte mágica de cura, mas já estava com a vida cheia.");
            return;
        }

        int cura = 15;
        jogador.vida = jogador.vida + cura;

        if (jogador.vida > jogador.vidaMaxima) {
            jogador.vida = jogador.vidaMaxima;
        }

        System.out.println(jogador.nome + " encontrou uma fonte mágica de cura!");
        System.out.println(jogador.nome + " recuperou " + cura + " de vida.");
        System.out.println("Vida atual: " + jogador.vida + "/" + jogador.vidaMaxima);
    }

    public void abrirBau() {
        int itemDoBau = aleatorio.nextInt(3);

        if (itemDoBau == 0) {
            System.out.println(jogador.nome + " abriu o baú e encontrou uma poção!");
            jogador.inventario.adicionarPocao();
            System.out.println("Agora " + jogador.nome + " tem " + jogador.inventario.quantidadePocoes + " poção(ões).");

        } else if (itemDoBau == 1) {
            System.out.println(jogador.nome + " abriu o baú e encontrou uma bomba!");
            jogador.inventario.adicionarBomba();
            System.out.println("Agora " + jogador.nome + " tem " + jogador.inventario.quantidadeBombas + " bomba(s).");

        } else {
            System.out.println(jogador.nome + " abriu o baú e encontrou um escudo!");
            jogador.inventario.adicionarEscudo();
            System.out.println("Agora " + jogador.nome + " tem " + jogador.inventario.quantidadeEscudos + " escudo(s).");
        }
    }

    public void encontrarOuro() {
        int quantidadeDeOuro = aleatorio.nextInt(16) + 5;

        jogador.ganharOuro(quantidadeDeOuro);

        System.out.println(jogador.nome + " encontrou " + quantidadeDeOuro + " moedas de ouro!");
        System.out.println("Agora " + jogador.nome + " tem " + jogador.ouro + " de ouro.");
    }

    public void abrirLoja() {
        if (jogador == null) {
            System.out.println("Crie um personagem antes de entrar na loja.");
            return;
        }

        boolean lojaAberta = true;

        while (lojaAberta) {
            mostrarTitulo("=== LOJA ===");
            System.out.println("Ouro atual: " + jogador.ouro);
            System.out.println("1 - Comprar poção (10 ouro)");
            System.out.println("2 - Comprar bomba (15 ouro)");
            System.out.println("3 - Comprar escudo (20 ouro)");
            System.out.println("0 - Sair da loja");
            System.out.print("Escolha: ");

            int opcaoLoja = lerOpcao();

            if (opcaoLoja == 1) {
                comprarPocao();

            } else if (opcaoLoja == 2) {
                comprarBomba();

            } else if (opcaoLoja == 3) {
                comprarEscudo();

            } else if (opcaoLoja == 0) {
                System.out.println("Saindo da loja...");
                lojaAberta = false;

            } else {
                System.out.println("Opção inválida na loja.");
            }
        }
    }

    public void comprarPocao() {
        int preco = 10;

        if (jogador.ouro < preco) {
            System.out.println("Ouro insuficiente para comprar poção.");
            return;
        }

        jogador.ouro = jogador.ouro - preco;
        jogador.inventario.adicionarPocao();

        System.out.println(jogador.nome + " comprou uma poção!");
        System.out.println("Ouro restante: " + jogador.ouro);
    }

    public void comprarBomba() {
        int preco = 15;

        if (jogador.ouro < preco) {
            System.out.println("Ouro insuficiente para comprar bomba.");
            return;
        }

        jogador.ouro = jogador.ouro - preco;
        jogador.inventario.adicionarBomba();

        System.out.println(jogador.nome + " comprou uma bomba!");
        System.out.println("Ouro restante: " + jogador.ouro);
    }

    public void comprarEscudo() {
        int preco = 20;

        if (jogador.ouro < preco) {
            System.out.println("Ouro insuficiente para comprar escudo.");
            return;
        }

        jogador.ouro = jogador.ouro - preco;
        jogador.inventario.adicionarEscudo();

        System.out.println(jogador.nome + " comprou um escudo!");
        System.out.println("Ouro restante: " + jogador.ouro);
    }


    public void atacarInimigo(Inimigo inimigo) {
        System.out.println(jogador.nome + " atacou o " + inimigo.nome + "!");
        inimigo.vida = inimigo.vida - jogador.ataque;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("O " + inimigo.nome + " ficou com " + inimigo.vida + " de vida.");

        if (inimigo.vida <= 0) {
            derrotarInimigo(inimigo);
        } else {
            ataqueDoInimigo(inimigo);
        }
    }

    public void ataqueDoInimigo(Inimigo inimigo) {
        int danoRecebido = inimigo.ataque - jogador.defesa;

        if (jogador.escudoAtivo) {
            danoRecebido = danoRecebido - 5;
            System.out.println("O escudo de " + jogador.nome + " reduziu ainda mais o dano!");
            jogador.escudoAtivo = false;
        }

        if (danoRecebido < 0) {
            danoRecebido = 0;
        }

        System.out.println("O " + inimigo.nome + " atacou " + jogador.nome + "!");
        System.out.println("A defesa de " + jogador.nome + " reduziu o dano.");

        jogador.vida = jogador.vida - danoRecebido;

        if (jogador.vida < 0) {
            jogador.vida = 0;
        }

        System.out.println(jogador.nome + " recebeu " + danoRecebido + " de dano.");
        System.out.println(jogador.nome + " ficou com " + jogador.vida + " de vida.");

        if (jogador.vida <= 0) {
            mostrarTitulo("=== FIM DE JOGO ===");
            System.out.println(jogador.nome + " foi derrotado.");
            System.out.println("Seu progresso terminou aqui.");
            jogoRodando = false;
        }
    }

    public boolean tentarFugir(Inimigo inimigo) {
        int fuga = aleatorio.nextInt(2);

        if (fuga == 0) {
            System.out.println(jogador.nome + " conseguiu fugir da batalha!");
            return true;
        } else {
            System.out.println(jogador.nome + " tentou fugir, mas não conseguiu!");
            System.out.println("O " + inimigo.nome + " aproveitou para atacar!");
            ataqueDoInimigo(inimigo);
            return false;
        }
    }

    public void abrirMenuDeItens(Inimigo inimigo) {
        System.out.println();
        System.out.println("=== ITENS ===");
        System.out.println("1 - Poção (" + jogador.inventario.quantidadePocoes + ")");
        System.out.println("2 - Bomba (" + jogador.inventario.quantidadeBombas + ")");
        System.out.println("3 - Escudo (" + jogador.inventario.quantidadeEscudos + ")");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcaoItem = lerOpcao();

        if (opcaoItem == 1) {
            usarPocao(inimigo);

        } else if (opcaoItem == 2) {
            usarBomba(inimigo);

        } else if (opcaoItem == 3) {
            usarEscudo(inimigo);

        } else if (opcaoItem == 0) {
            System.out.println("Voltando ao combate...");

        } else {
            System.out.println("Opção inválida no menu de itens.");
        }
    }

    public void usarPocao(Inimigo inimigo) {
        if (!jogador.inventario.temPocao()) {
            System.out.println(jogador.nome + " não tem poções.");
            return;
        }

        if (jogador.vida == jogador.vidaMaxima) {
            System.out.println(jogador.nome + " já está com a vida cheia.");
            return;
        }

        jogador.inventario.usarPocao();
        jogador.vida = jogador.vida + 20;

        if (jogador.vida > jogador.vidaMaxima) {
            jogador.vida = jogador.vidaMaxima;
        }

        System.out.println(jogador.nome + " usou uma poção!");
        System.out.println("A vida de " + jogador.nome + " foi recuperada.");
        System.out.println("Vida atual: " + jogador.vida + "/" + jogador.vidaMaxima);
        System.out.println("Poções restantes: " + jogador.inventario.quantidadePocoes);

        if (inimigo.vida > 0) {
            System.out.println("Enquanto " + jogador.nome + " usava a poção, o " + inimigo.nome + " atacou!");
            ataqueDoInimigo(inimigo);
        }
    }

    public void usarBomba(Inimigo inimigo) {
        if (!jogador.inventario.temBomba()) {
            System.out.println(jogador.nome + " não tem bombas.");
            return;
        }

        jogador.inventario.usarBomba();

        int danoDaBomba = 15;
        inimigo.vida = inimigo.vida - danoDaBomba;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println(jogador.nome + " usou uma bomba!");
        System.out.println("A bomba causou " + danoDaBomba + " de dano no " + inimigo.nome + "!");
        System.out.println("O " + inimigo.nome + " ficou com " + inimigo.vida + " de vida.");
        System.out.println("Bombas restantes: " + jogador.inventario.quantidadeBombas);

        if (inimigo.vida <= 0) {
            derrotarInimigo(inimigo);
        } else {
            System.out.println("O " + inimigo.nome + " sobreviveu e contra-atacou!");
            ataqueDoInimigo(inimigo);
        }
    }


    public void usarEscudo(Inimigo inimigo) {
        if (!jogador.inventario.temEscudo()) {
            System.out.println(jogador.nome + " não tem escudos.");
            return;
        }

        if (jogador.escudoAtivo) {
            System.out.println(jogador.nome + " já está com um escudo ativo.");
            return;
        }

        jogador.inventario.usarEscudo();
        jogador.escudoAtivo = true;

        System.out.println(jogador.nome + " usou um escudo!");
        System.out.println("O próximo ataque recebido será reduzido.");
        System.out.println("Escudos restantes: " + jogador.inventario.quantidadeEscudos);

        if (inimigo.vida > 0) {
            System.out.println("O " + inimigo.nome + " atacou mesmo assim!");
            ataqueDoInimigo(inimigo);
        }
    }

    public Inimigo criarInimigoAleatorio() {
        int tipoInimigo = aleatorio.nextInt(3);

        if (tipoInimigo == 0) {
            return new Inimigo("Goblin", RaridadeDoInimigo.COMUM, 30, 8);
        } else if (tipoInimigo == 1) {
            return new Inimigo("Lobo", RaridadeDoInimigo.COMUM, 40, 6);
        } else {
            return new Inimigo("Esqueleto", RaridadeDoInimigo.COMUM, 25, 10);
        }
    }

    public Inimigo criarElite() {
        int tipoInimigo = aleatorio.nextInt(2);
        if (tipoInimigo == 0) {
            return new Inimigo("Capitão Orc", RaridadeDoInimigo.ELITE, 80, 15);
        } else {
            return new Inimigo("Cavaleiro Sombrio", RaridadeDoInimigo.ELITE, 80, 15);
        }
    }

    public Inimigo criarChefe() {
        return new Inimigo("Rei Dragão", RaridadeDoInimigo.CHEFE, 95, 20);
    }
}