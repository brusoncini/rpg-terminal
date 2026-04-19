# ⚔️ RPG em Terminal

![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-blue)
![Versão](https://img.shields.io/badge/versão-1.0.0-brightgreen)
![Windows 11](https://img.shields.io/badge/Windows-11-0078D6?logo=windows&logoColor=white)

Projeto desenvolvido em **Java**, utilizando princípios de **programação orientada a objetos**.  
O jogo acontece no terminal e permite criar um personagem, explorar o mapa, enfrentar inimigos comuns, elites e um chefe final, além de usar itens, ganhar ouro e evoluir de nível.


## 🎯 Objetivo

O objetivo do programa é simular um RPG em terminal com exploração, combate e progressão de personagem.  
Durante a partida, o jogador pode encontrar eventos aleatórios, coletar itens e ouro, lutar contra inimigos e tentar derrotar o chefe final para vencer o jogo.


## 🛠️ Funcionalidades e implementação

- Criação de personagem com atributos iniciais
- Sistema de exploração com eventos aleatórios
- Combate por turnos
- Inimigos comuns, elites e chefe final
- Sistema de itens:
    - **Poção**
    - **Bomba**
    - **Escudo**
- Sistema de ouro e loja
- Progressão por experiência e nível
- Golpe especial do chefe final
- Organização do código em pacotes utilizando POO


## 🗂️ Organização do projeto

O projeto foi organizado em pacotes para separar melhor as responsabilidades das classes.

### Entidades
Contém as classes principais do domínio do jogo:

- `Jogador`: representa o personagem do jogador, com atributos, inventário e progressão.
- `Inimigo`: representa os inimigos enfrentados durante o jogo.
- `Inventario`: controla a quantidade de poções, bombas e escudos.
- `RaridadeDoInimigo`: enum que define se o inimigo é comum, elite ou chefe.

### Jogo
Contém a lógica principal e a estrutura do jogo:

- `Jogo`: controla o fluxo principal, menu, exploração e estado geral da partida.
- `FabricaDeInimigos`: cria os inimigos comuns, elites e o chefe.

### Serviços
Contém serviços responsáveis por partes específicas do sistema:

- `Combate`: controla batalhas, ataques, fuga, uso de itens e recompensas.
- `Loja`: controla a compra de itens com ouro.

### Classe principal
- `Main`: inicia a execução do programa.


## Estrutura orientada a objetos

A organização do projeto foi pensada para aplicar conceitos de orientação a objetos, como:

- separação de responsabilidades
- encapsulamento de atributos e comportamentos
- uso de classes para representar entidades do jogo
- uso de serviços para centralizar regras específicas, como combate e loja
- uso de enum para classificar tipos de inimigo


## ⚔️ Sistema de combate

O combate acontece em turnos.  
O jogador pode escolher entre:

1. atacar
2. fugir
3. usar item

Os inimigos atacam após a ação do jogador, exceto quando são derrotados.  
O chefe final possui uma chance de usar um ataque especial chamado **Golpe Devastador**, que causa dano extra.


## 📈 Sistema de progressão

Ao derrotar inimigos, o jogador recebe experiência e ouro.

- Inimigos comuns dão recompensa básica
- Inimigos de elite dão recompensa maior
- O chefe final encerra o jogo quando é derrotado

Ao acumular experiência suficiente, o jogador sobe de nível e recebe melhorias em seus atributos, como:

- aumento de vida máxima
- aumento de ataque
- aumento de defesa
- recuperação total de vida ao subir de nível


## 📦 Itens

### Poção
Recupera parte da vida do jogador, respeitando o limite da vida máxima.

### Bomba
Causa dano direto ao inimigo durante o combate.

### Escudo
Ativa uma proteção temporária que reduz o próximo ataque recebido.


## 💻 Como executar

Este projeto foi desenvolvido em **Java 22** e pode ser executado no terminal, desde que o Java esteja instalado corretamente.

### No Windows:
1. Compile os arquivos `.java` na pasta raiz do projeto usando `javac`
```bash
javac -d out src\Main.java src\entidades\*.java src\jogo\*.java src\servicos\*.java
```

2. Execute a classe `Main`
```bash
java -cp out Main
```
3. Use as opções do menu para jogar


## 🎮 Fluxo básico do jogo

O programa permite:

- criar um novo personagem
- visualizar o status atual
- explorar o mapa
- entrar na loja
- enfrentar inimigos
- usar itens
- evoluir de nível
- derrotar o chefe final


## ✍️ Observações

- O jogo usa eventos aleatórios, então cada partida pode ter um fluxo diferente.
- O chefe final só pode aparecer quando o jogador atinge o nível necessário.
- O sistema foi desenvolvido com foco em prática de orientação a objetos e organização de código.


## ☕ Linguagem utilizada

- Java


## 👩🏻‍💻 Feito por

Desenvolvido com ❤️ por [Bruna Soncini](www.linkedin.com/in/brunasoncini/).
