package one.digitalinovation.laboojava.console;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.*;
import one.digitalinovation.laboojava.negocio.ClienteNegocio;
import one.digitalinovation.laboojava.negocio.PedidoNegocio;
import one.digitalinovation.laboojava.negocio.ProdutoNegocio;
import one.digitalinovation.laboojava.utilidade.LeitoraDados;

import java.util.Optional;

/**
 * Classe responsável por controlar a execução da aplicação.
 * 
 * @author thiago leite
 */
public class Start {

	private static Optional<Cliente> clienteLogado = null;

	public static Optional<Cliente> getClienteLogado() {
		return clienteLogado;
	}

	private static String conultaPedidoPorCodigo;
	private static String consultaLivroPorNome;
	private static String consultaCadernoPorMateria;

	private static Banco banco = new Banco();

	private static ClienteNegocio clienteNegocio = new ClienteNegocio(banco);
	private static PedidoNegocio pedidoNegocio = new PedidoNegocio(banco);
	private static ProdutoNegocio produtoNegocio = new ProdutoNegocio(banco);
	private static Cliente cliente = new Cliente();
	private static String cpf = "";
	private static String inicioSistema = "";

	/**
	 * Método utilitário para inicializar a aplicação.
	 * 
	 * @param args Parâmetros que podem ser passados para auxiliar na execução.
	 */
	public static void main(String[] args) {
		String opcao = "";

		// Inicio o sistema chama o método boas Vindas

		boasVindas();


		while (true) {

			System.out.println("Selecione uma opção:");
			System.out.println("1 - Adicionar Cliente");
			System.out.println("2 - Excluir Cliente");
			System.out.println("3 - Listar Clientes");
			System.out.println("4 - Cadastrar Livro");
			System.out.println("5 - Excluir Livro");
			System.out.println("6 - Cadastrar Caderno");
			System.out.println("7 - Excluir Caderno");
			System.out.println("8 - Fazer pedido");
			System.out.println("9 - Excluir pedido");
			System.out.println("10 - Listar produtos");
			System.out.println("11 - Listar pedidos");
			System.out.println("12 - Consultar Pedido por código");
			System.out.println("13 - Consultar Livro por Nome");
			System.out.println("14 - Consultar Caderno por Máteria");
			System.out.println("15 - Logof");
			System.out.println("16 - Sair");

			opcao = LeitoraDados.lerDado();

			switch (opcao) {
			case "1":
				System.out.println("1 - Cadastrar Cliente");
				cliente = LeitoraDados.lerCliente();
				verificaAntesdeAdicionarCliente(cliente);
				break;
			case "2":
				System.out.println("2 - Excluir Cliente");
				String cpf = LeitoraDados.lerDado();
				clienteNegocio.excluirCliente(cpf);
				break;	
			case "3":
				System.out.println("Clientes Cadastrados");
				clienteNegocio.listarClientes();
				break;
			case "4":
				System.out.println("2 - Cadastrar Livro");
				Livro livro = LeitoraDados.lerLivro();
				produtoNegocio.salvar(livro);
				break;
			case "5":
				System.out.println("3 - Excluir Livro");
				System.out.println("Digite o código do livro");
				String codigoLivro = LeitoraDados.lerDado();
				produtoNegocio.excluir(codigoLivro);
				break;
			case "6":
				Caderno caderno = LeitoraDados.lerCaderno();
				produtoNegocio.salvar(caderno);
				break;
			case "7":
				System.out.println("Digite o código do caderno");
				String codigoCaderno = LeitoraDados.lerDado();
				produtoNegocio.excluir(codigoCaderno);
				break;
			case "8":
				Pedido pedido = LeitoraDados.lerPedido(banco);
				Optional<Cupom> cupom = LeitoraDados.lerCupom(banco);

				if (cupom.isPresent()) {
					pedidoNegocio.salvar(pedido, cupom.get());
				} else {
					pedidoNegocio.salvar(pedido);
				}
				break;
			case "9":
				System.out.println("Digite o código do pedido");
				String codigoPedido = LeitoraDados.lerDado();
				pedidoNegocio.excluir(codigoPedido);
				break;
			case "10":
				System.out.println("7 - Listar produtos");
				produtoNegocio.listarTodos();
				break;
			case "11":
				System.out.println("8 - Listar pedidos");
				pedidoNegocio.listarTodos();
				break;
			case "12":
				System.out.println("9 - Consultar Pedido por código");
				conultaPedidoPorCodigo = LeitoraDados.lerDado();
				exibeConsultaPedidoPorCodigo();
				break;
			case "13":
				System.out.println("10 -Consultar Livro por Nome");
				consultaLivroPorNome = LeitoraDados.lerDado();
				exibeConsultaLivroPorNome();
				break;
			case "14":
				System.out.println("11 - Consultar Caderno por Máteria");
				consultaCadernoPorMateria = LeitoraDados.lerDado();
				exibeConsultaCadernoPorMateria();
				break;
			case "15":
				System.out.println("LOGOF");
				System.out.println(String.format("Volte sempre %s!", clienteLogado.get().getNome()));
				boasVindas();
				break;
			case "16":
				System.out.println("Sair da Aplicação!");
				System.out.println("Aplicação encerrada.");
				System.exit(0);
				break;
			default:
				System.out.println("Opção inválida.");
				break;
			}
		}
	}

	/**
	 * Procura o usuário na base de dados.
	 * 
	 * @param cpf CPF do usuário que deseja logar na aplicação
	 */

	/*
	 * Autor: Adriano Aparecido da Silva
	 */

	private static void identificarUsuario(String cpf) {

		Optional<Cliente> cliente = clienteNegocio.consultarCliente(cpf);

		cliente.ifPresentOrElse((c) -> {
			System.out.println(String.format("Olá %s! Você está logado.", cliente.get().getNome()));
			clienteLogado = cliente;

		}, () -> {
			System.out.println("Cliente não cadastrado.Criar cadastro");
			System.out.println("1 - Cadastrar Cliente");
			Cliente clienteCad = LeitoraDados.lerCliente();
			verificaAntesdeAdicionarCliente(clienteCad);
		});

	}

	/*
	 * Autor: Adriano Aparecido da Silva
	 */
	private static void exibeConsultaPedidoPorCodigo() {

		Optional<Pedido> pedido = pedidoNegocio.consultarPedidoPorCodigo(conultaPedidoPorCodigo);
		pedido.ifPresentOrElse((p) -> {
			System.out.println(pedido);
		}, () -> {
			System.out.println("Pedido não Localizado");
		});

	}

	/*
	 * Autor: Adriano Aparecido da Silva
	 */
	private static void exibeConsultaLivroPorNome() {

		Optional<Produto> livro = produtoNegocio.consultarLivroPorNome(consultaLivroPorNome);
		livro.ifPresentOrElse((l) -> {
			System.out.println(livro);
		}, () -> {
			System.out.println("Livro não Localizado");
		});

	}

	/*
	 * Autor: Adriano Aparecido da Silva
	 */
	private static void exibeConsultaCadernoPorMateria() {

		Optional<Produto> caderno = produtoNegocio.consultarCadernoPorMateria(consultaCadernoPorMateria);
		caderno.ifPresentOrElse((c) -> {
			System.out.println(caderno);
		}, () -> {
			System.out.println("Caderno não Localizado");
		});

	}

	/*
	 * Autor: Adriano Aparecido da Silva
	 */
	private static void verificaAntesdeAdicionarCliente(Cliente cliente) {

		Optional<Cliente> clienteResultado = clienteNegocio.consultarCliente(cliente.getCpf());
		clienteResultado.ifPresentOrElse((c) -> {
			System.out.println("Cliente já Cadastrado!");
		}, () -> {
			clienteNegocio.adicionarCliente(cliente);
			clienteLogado = Optional.ofNullable(cliente);
		});
	}

	/*
	 * Autor: Adriano Aparecido da Silva
	 */
	private static void boasVindas() {
		System.out.println("Bem vindo ao e-Compras");
		System.out.println("Já e Cliente S/N ?");
		inicioSistema = LeitoraDados.lerDado();

		switch (inicioSistema) {

		case "S": {
			System.out.println("Digite o cpf:");
			cpf = LeitoraDados.lerDado();
			identificarUsuario(cpf);
			//System.out.println(String.format("Olá %s! Você está logado.", clienteLogado.get().getNome()));
			break;
		}
		case "N": {
			System.out.println("Castro de Cliente");
			Cliente cliente = LeitoraDados.lerCliente();
			verificaAntesdeAdicionarCliente(cliente);
			identificarUsuario(cliente.getCpf());
			break;
		}
		}

	}
}
