# Jackut

Simulação de uma rede social inspirada no Orkut, desenvolvida em Java para a disciplina de **Programação 2 (P2)** do Instituto de Computação da **UFAL**. O projeto cobre as user stories de 1 a 9 e é validado pela ferramenta de testes de aceitação **EasyAccept**.

## Visão geral

O Jackut permite criar usuários, abrir sessões, editar perfis e estabelecer diferentes relações entre pessoas: amizade, fã/ídolo, paquera e inimizade. Além disso, oferece comunidades com mensagens, troca de recados privados e remoção de contas com limpeza em cascata. Todo o estado sobrevive ao encerramento e à reabertura do sistema por meio de persistência em disco.

## Funcionalidades por user story

| US | Funcionalidade |
|----|----------------|
| US1 | Criação de conta, abertura de sessão e consulta de atributos de perfil |
| US2 | Edição de perfil |
| US3 | Amizade (convites e confirmação) |
| US4 | Recados entre usuários |
| US5 / US6 | Comunidades (criação, descrição, dono, membros, adesão) |
| US7 | Mensagens de comunidade |
| US8 | Fã/ídolo, paquera e inimigo |
| US9 | Remoção de conta com limpeza em cascata |

## Arquitetura

O projeto segue uma arquitetura em camadas, com responsabilidades bem separadas:

```
Facade  →  Sistema  →  Services  →  Repository  →  Models / Persistence
```

- **`Facade`** — ponto de entrada do EasyAccept. Não contém regra de negócio; apenas adapta a interface dos testes e delega ao `Sistema`.
- **`Sistema`** — orquestrador enxuto. Resolve a sessão/usuário de cada operação e encaminha ao serviço responsável.
- **Serviços** (`service`) — concentram as regras de negócio: `AmizadeService`, `RecadoService`, `ComunidadeService`, `MensagemService`, `RelacionamentoService`, `RemocaoService`, além do `SessaoManager`.
- **Repositórios** (`repository`) — `UsuarioRepository` e `ComunidadeRepository` encapsulam o armazenamento e as regras de unicidade.
- **Modelos** (`models`) — entidades de domínio: `Usuario`, `Comunidade`, `Recado`, `Sessao`.
- **Persistência** (`persistence`) — `PersistenciaManager` grava e recupera o agregado serializável `Dados` (usuários + comunidades).
- **Exceções** (`exceptions`) — `JackutException extends RuntimeException` como base, com 22 subclasses de domínio, cada uma com a mensagem fixada no construtor.

### Estrutura de pacotes

```
src/br/ufal/ic/p2/jackut/
├── Facade.java
├── Sistema.java
├── package-info.java
├── exceptions/      (JackutException + 22 subclasses)
├── models/          (Usuario, Comunidade, Recado, Sessao)
├── repository/      (UsuarioRepository, ComunidadeRepository)
├── service/         (7 serviços + SessaoManager)
└── persistence/     (Dados, PersistenciaManager)
```

## Requisitos

- **JDK 21**
- **EasyAccept** (`lib/easyaccept.jar`)

## Compilação

**PowerShell (Windows):**

```powershell
Get-ChildItem -Recurse src\*.java | ForEach-Object { $_.FullName } | Set-Content sources.txt
javac -encoding UTF-8 -cp "lib\easyaccept.jar" -d bin "@sources.txt"
```

**Git Bash / WSL:**

```bash
javac -encoding UTF-8 -cp lib/easyaccept.jar -d bin $(find src -name "*.java")
```

> Use `-encoding UTF-8` na **compilação** (`javac`) e `-Dfile.encoding=UTF-8` na **execução** (`java`).

## Execução dos testes

Rodar a suíte completa, via `Main`:

```powershell
java "-Dfile.encoding=UTF-8" -cp "bin;lib\easyaccept.jar" Main
```

Rodar um teste individual:

```powershell
java "-Dfile.encoding=UTF-8" -cp "bin;lib\easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.jackut.Facade tests\us1_1.txt
```

Rodar um par de persistência (o `_1` grava o estado, o `_2` o reabre):

```powershell
java "-Dfile.encoding=UTF-8" -cp "bin;lib\easyaccept.jar" easyaccept.EasyAccept br.ufal.ic.p2.jackut.Facade tests\us5_1.txt tests\us5_2.txt
```

No Git Bash / WSL, troque o separador de classpath de `;` para `:` e as barras de `\` para `/`.

## Status dos testes

**18/18 arquivos · 473 asserts OK · sem falhas.**

| US | Asserts |
|----|---------|
| US1 | 24 |
| US2 | 49 |
| US3 | 56 |
| US4 | 55 |
| US5 | 44 |
| US6 | 35 |
| US7 | 84 |
| US8 | 102 |
| US9 | 24 |

## Persistência

O estado é serializado no arquivo `jackut_dados.ser` ao encerrar o sistema (`encerrarSistema`) e recarregado na inicialização. O comando `zerarSistema` apaga esse arquivo e reinicia o estado. Mensagens de comunidade e recados privados são mantidos em filas (FIFO) independentes dentro de cada usuário.

## Detalhes de implementação

- **Bloqueio bidirecional de inimigos:** `Usuario.validarNaoInimigo()` é verificado nas operações de adicionar amigo, adicionar ídolo, adicionar paquera e enviar recado.
- **Paquera correspondida:** dispara um recado automático do sistema (com remetente `null`) para ambos os usuários, de modo que sobrevive à remoção de contas.
- **Remoção em cascata:** comunidades das quais o usuário é dono são removidas (com saída dos membros), recados enviados são purgados das caixas dos destinatários e todas as referências em relações de terceiros são limpas.
- **Encapsulamento:** as entidades retornam cópias ou listas imutáveis, sem expor as coleções internas.

## Documentação

Todas as classes, métodos, parâmetros, retornos e exceções estão documentados em Javadoc. Cada pacote possui um arquivo `package-info.java` com a descrição da sua responsabilidade.
