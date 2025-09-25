# App_Contatos
O aplicativo Contatos foi desenvolvido com o objetivo de demonstrar, de forma prática e didática, a construção de um sistema simples de gerenciamento de informações em dispositivos Android.

Ele permite cadastrar, listar, alterar e excluir contatos em um banco de dados local SQLite, aplicando conceitos fundamentais de programação mobile.
Perspectiva Acadêmica: o projeto abrange conceitos essenciais como banco de dados SQLite, DAO, Activities, Intents, Adapter e ListView.
Perspectiva Comercial: embora simples, pode ser visto como um MVP de uma ferramenta de organização de contatos, com aplicação em pequenos negócios e como protótipo de sistemas de agenda.

Arquitetura da Aplicação:
A aplicação segue uma divisão em três camadas principais:

- Interface do Usuário (UI): layouts XML e Activities (MainActivity, CadastroContatoActivity).
- Lógica de Negócio: ContatoAdapter para exibição da lista.
- Persistência de Dados: DbHelper (SQLite) e ContatoDAO (operações CRUD).

Estrutura de Classes:
- Contato: classe modelo.
- DbHelper: criação e manutenção do banco.
- ContatoDAO: métodos de inserção, alteração, exclusão, listagem e verificação.
- ContatoAdapter: adaptação da lista.
- MainActivity: tela principal.
- CadastroContatoActivity: tela de cadastro/alteração/exclusão.

Banco de Dados:
O app utiliza SQLite com uma única tabela:

Tabela: contatos
- id (INTEGER, chave primária, autoincremento)
- nome (TEXT, obrigatório, único)

Operações principais:
- inserirContato: insere um novo registro, verificando duplicidade.
- alterarContato: atualiza nome existente.
- excluirContatoPorNome: remove contato específico.
- listarContatos: retorna todos os registros.
- contatoExiste: verifica se já existe contato.

