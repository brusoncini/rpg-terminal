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
        int evento = this.aleatorio.nextInt(5);

        if (evento == 0) {
            if (this.jogador.nivel >= 3) {
                int sorteioChefe = this.aleatorio.nextInt(4);

                if (sorteioChefe == 0) {
                    Inimigo chefe = this.criarChefe();
                    System.out.println("O CHEFÃO apareceu!");
                    System.out.println("Um " + chefe.nome + " surgiu diante de " + this.jogador.nome + "!");
                    this.lutar(chefe);
                    return;
                }
            }

            Inimigo inimigo = this.criarInimigoAleatorio();
            System.out.println("Um " + inimigo.nome + " apareceu no caminho!");
            this.lutar(inimigo);

        } else if (evento == 1) {
            System.out.println(this.jogador.nome + " encontrou uma poção!");
            this.jogador.quantidadePocoes = this.jogador.quantidadePocoes + 1;
            System.out.println("Agora " + this.jogador.nome + " tem " + this.jogador.quantidadePocoes + " poção(ões).");

        } else if (evento == 2) {
            System.out.println(this.jogador.nome + " encontrou uma bomba!");
            this.jogador.quantidadeBombas = this.jogador.quantidadeBombas + 1;
            System.out.println("Agora " + this.jogador.nome + " tem " + this.jogador.quantidadeBombas + " bomba(s).");

        } else if (evento == 3) {
            System.out.println(this.jogador.nome + " encontrou um escudo!");
            this.jogador.quantidadeEscudos = this.jogador.quantidadeEscudos + 1;
            System.out.println("Agora " + this.jogador.nome + " tem " + this.jogador.quantidadeEscudos + " escudo(s).");

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
            System.out.println("3 - Usar item");
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

            } else if (opcaoCombate == 3) {
                this.abrirMenuDeItens(inimigo);

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

            if (inimigo.raridade == RaridadeDoInimigo.CHEFE) {
                System.out.println("O chefão foi derrotado!");
                this.jogador.ganharExperiencia(30);
            } else {
                this.jogador.ganharExperiencia(10);
            }

        } else {
            this.ataqueDoInimigo(inimigo);
        }
    }

    public void ataqueDoInimigo(Inimigo inimigo) {
        int danoRecebido = inimigo.ataque - this.jogador.defesa;

        if (this.jogador.escudoAtivo) {
            danoRecebido = danoRecebido - 5;
            System.out.println("O escudo de " + this.jogador.nome + " reduziu ainda mais o dano!");
            this.jogador.escudoAtivo = false;
        }

        if (danoRecebido < 0) {
            danoRecebido = 0;
        }

        System.out.println("O " + inimigo.nome + " atacou " + this.jogador.nome + "!");
        System.out.println("A defesa de " + this.jogador.nome + " reduziu o dano.");

        this.jogador.vida = this.jogador.vida - danoRecebido;

        if (this.jogador.vida < 0) {
            this.jogador.vida = 0;
        }

        System.out.println(this.jogador.nome + " recebeu " + danoRecebido + " de dano.");
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

    public void abrirMenuDeItens(Inimigo inimigo) {
        System.out.println();
        System.out.println("=== ITENS ===");
        System.out.println("1 - Poção (" + this.jogador.quantidadePocoes + ")");
        System.out.println("2 - Bomba (" + this.jogador.quantidadeBombas + ")");
        System.out.println("3 - Escudo (" + this.jogador.quantidadeEscudos + ")");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcaoItem = this.leitor.nextInt();
        this.leitor.nextLine();

        if (opcaoItem == 1) {
            this.usarPocao(inimigo);

        } else if (opcaoItem == 2) {
            this.usarBomba(inimigo);

        } else if (opcaoItem == 3) {
            this.usarEscudo(inimigo);

        } else if (opcaoItem == 0) {
            System.out.println("Voltando ao combate...");

        } else {
            System.out.println("Opção inválida no menu de itens.");
        }
    }

    public void usarPocao(Inimigo inimigo) {
        if (this.jogador.quantidadePocoes <= 0) {
            System.out.println(this.jogador.nome + " não tem poções.");
            return;
        }

        if (this.jogador.vida == 100) {
            System.out.println(this.jogador.nome + " já está com a vida cheia.");
            return;
        }

        this.jogador.quantidadePocoes = this.jogador.quantidadePocoes - 1;
        this.jogador.vida = this.jogador.vida + 20;

        if (this.jogador.vida > 100) {
            this.jogador.vida = 100;
        }

        System.out.println(this.jogador.nome + " usou uma poção!");
        System.out.println("A vida de " + this.jogador.nome + " foi recuperada.");
        System.out.println("Vida atual: " + this.jogador.vida);
        System.out.println("Poções restantes: " + this.jogador.quantidadePocoes);

        if (inimigo.vida > 0) {
            System.out.println("Enquanto " + this.jogador.nome + " usava a poção, o " + inimigo.nome + " atacou!");
            this.ataqueDoInimigo(inimigo);
        }
    }

    public void usarBomba(Inimigo inimigo) {
        if (this.jogador.quantidadeBombas <= 0) {
            System.out.println(this.jogador.nome + " não tem bombas.");
            return;
        }

        this.jogador.quantidadeBombas = this.jogador.quantidadeBombas - 1;

        int danoDaBomba = 15;
        inimigo.vida = inimigo.vida - danoDaBomba;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println(this.jogador.nome + " usou uma bomba!");
        System.out.println("A bomba causou " + danoDaBomba + " de dano no " + inimigo.nome + "!");
        System.out.println("O " + inimigo.nome + " ficou com " + inimigo.vida + " de vida.");
        System.out.println("Bombas restantes: " + this.jogador.quantidadeBombas);

        if (inimigo.vida <= 0) {
            System.out.println(this.jogador.nome + " venceu a batalha!");

            if (inimigo.raridade == RaridadeDoInimigo.CHEFE) {
                System.out.println("O chefão foi derrotado!");
                this.jogador.ganharExperiencia(30);
            } else {
                this.jogador.ganharExperiencia(10);
            }

        } else {
            System.out.println("O " + inimigo.nome + " sobreviveu e contra-atacou!");
            this.ataqueDoInimigo(inimigo);
        }
    }

    public void usarEscudo(Inimigo inimigo) {
        if (this.jogador.quantidadeEscudos <= 0) {
            System.out.println(this.jogador.nome + " não tem escudos.");
            return;
        }

        if (this.jogador.escudoAtivo) {
            System.out.println(this.jogador.nome + " já está com um escudo ativo.");
            return;
        }

        this.jogador.quantidadeEscudos = this.jogador.quantidadeEscudos - 1;
        this.jogador.escudoAtivo = true;

        System.out.println(this.jogador.nome + " usou um escudo!");
        System.out.println("O próximo ataque recebido será reduzido.");
        System.out.println("Escudos restantes: " + this.jogador.quantidadeEscudos);

        if (inimigo.vida > 0) {
            System.out.println("O " + inimigo.nome + " atacou mesmo assim!");
            this.ataqueDoInimigo(inimigo);
        }
    }

    public Inimigo criarInimigoAleatorio() {
        int tipoInimigo = this.aleatorio.nextInt(3);

        if (tipoInimigo == 0) {
            return new Inimigo("Goblin", RaridadeDoInimigo.COMUM, 30, 8);
        } else if (tipoInimigo == 1) {
            return new Inimigo("Lobo", RaridadeDoInimigo.COMUM, 40, 6);
        } else {
            return new Inimigo("Esqueleto", RaridadeDoInimigo.COMUM, 25, 10);
        }
    }

    public Inimigo criarChefe() {
        return new Inimigo("Dragão", RaridadeDoInimigo.CHEFE, 80, 15);
    }

}