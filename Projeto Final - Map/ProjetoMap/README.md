# Projeto Final MAP 2026.1 — Mini-Framework para Jogos de Cartas

## 📦 Pré-requisitos
- **JDK 17** ou superior (necessário por causa de `record`, `switch expression`, `var`, etc.)
- **Maven 3.8+** (opcional, mas recomendado)
  - Caso não tenha Maven, pode usar o wrapper `mvnw` (abaixo) ou compilar via `javac` diretamente.

### Verificando a instalação
```bash
java --version     # deve aparecer java 17+
javac --version    # compilador java
mvn --version      # maven
```

---

## 🚀 Como compilar e executar

### Usando Maven (recomendado)

```bash
# 1. Entrar na pasta do projeto
cd ProjetoMap

# 2. Compilar o projeto + rodar testes automatizados
mvn clean test

# 3. Empacotar em JAR
mvn clean package

# 4a. Rodar Blackjack (executa JAR)
java -jar target/projeto-map-2026-1-1.0.0.jar

# 4b. Rodar Super Trunfo diretamente pela classe main
java -cp target/projeto-map-2026-1-1.0.0.jar com.map.games.supertrunfo.MainSuperTrunfo

# 5. Gerar Javadoc (saída em target/site/apidocs/)
mvn javadoc:javadoc
```

### Rodar apenas testes
```bash
mvn test
```
Saída esperada: `Tests run: 20+, Failures: 0, Errors: 0`

---

## 🎮 Jogos disponíveis

| Jogo | Classe main | Tema | Estratégias usadas |
|------|-------------|------|--------------------|
| **Blackjack (21)** | `com.map.games.blackjack.MainBlackjack` | Baralho tradicional, 21 pontos | Humano Console vs IA Aleatória |
| **Super Trunfo** | `com.map.games.supertrunfo.MainSuperTrunfo` | Carros esportivos (32 cartas, 5 atributos) | Humano Console vs IA Aleatória |

---

## 📁 Estrutura do projeto
```
ProjetoMap/
├── pom.xml                          # Configuração Maven + dependências
├── README.md                        # Este arquivo
│
├── docs/
│   └── RelatorioProjeto.md          # RELATÓRIO COMPLETO (leia!)
│                                      - Diagrama de classes
│                                      - Padrões GoF usados (4+)
│                                      - Princípios SOLID e GRASP
│                                      - Pontos de extensão (7+)
│                                      - Justificativas completas
│
└── src/
    ├── main/java/com/map/
    │   ├── framework/                # ⭐ FRAMEWORK REUTILIZÁVEL (100% genérico)
    │   │   ├── GameFacade.java       # API pública do framework
    │   │   ├── exception/            # Exceções customizadas
    │   │   ├── card/                 # Carta, Baralho, Embaralhamento (Strategy + Factory Method)
    │   │   ├── player/               # Jogador, Mao, Estrategias (Strategy)
    │   │   └── game/                 # Partida (Template Method), Regras, Observer
    │   │
    │   └── games/                    # 🎲 APLICAÇÕES CLIENTE (jogos concretos)
    │       ├── blackjack/            # Blackjack (21)
    │       └── supertrunfo/          # Super Trunfo de carros
    │
    └── test/java/com/map/tests/      # ✅ Testes automatizados JUnit
        ├── MaoTest.java
        ├── BaralhoTradicionalTest.java
        ├── BlackjackUtilsTest.java
        ├── JogadorEstrategiaTest.java
        └── SuperTrunfoCartaTest.java
```

---

## 🎯 Padrões GoF aplicados (4+)

| Padrão | Onde é usado |
|--------|-------------|
| **Factory Method** | `Baralho.criarCartas()` → `BaralhoTradicional`, `BaralhoSuperTrunfo` |
| **Strategy (×3)** | `EmbaralhadorStrategy`, `EstrategiaJogador`, `RegrasJogo<B>` |
| **Template Method** | `Partida.jogarPartida()` (final) com steps fixos + hooks |
| **Observer** | `Partida` (Subject) + `PartidaListener` (Observer) |

Para detalhes completos, leia `docs/RelatorioProjeto.md`.

Jogos: 

O projeto tem duas aplicações de jogo:

com.map.games.blackjack.MainBlackjack → Blackjack (21)
com.map.games.supertrunfo.MainSuperTrunfo → Super Trunfo de carros
O JAR padrão do Maven (projeto-map-2026-1-1.0.0.jar) roda o MainBlackjack, então o jogo principal é Blackjack.

Como funciona o Blackjack aqui
Dois jogadores: Você e o Dealer (IA).
Cada um recebe 2 cartas iniciais.

Em cada turno, o jogador pode:
HIT → comprar mais uma carta
STAND → parar com a mão atual
O objetivo é chegar o mais perto possível de 21 pontos, sem ultrapassar.
Como alguém ganha ou perde
Se a mão passar de 21 pontos, o jogador estoura e perde.
Se um dos jogadores fizer Blackjack natural (21 com as duas cartas iniciais), ele vence automaticamente.
Se ambos estourarem, é empate.
Se só um estourar, o outro vence.
Se nenhum estourar, ganha quem tiver mais pontos.
Se ambos tiverem a mesma pontuação, é empate.

Como funciona o Super Trunfo (aplicação alternativa)
Dois jogadores, cada um com 5 cartas iniciais.
Um jogador escolhe um atributo como:
potência, velocidade, aceleração, preço ou cilindradas
Cada um joga a primeira carta da mão
A carta com melhor valor no atributo escolhido vence a rodada
Há uma carta de Super Trunfo que vence qualquer outra carta salvo exceção de atributos mínimos
Quem vence a rodada ganha 1 ponto
O jogo termina quando alguém fica sem cartas ou depois de 10 rodadas
O vencedor é quem tiver mais pontos no final.