package one.digitalinovation.laboojava.utilidade;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Pedido;
import one.digitalinovation.laboojava.entidade.Livro;
import one.digitalinovation.laboojava.entidade.Produto;
import one.digitalinovation.laboojava.entidade.Caderno;
import one.digitalinovation.laboojava.entidade.Cliente;
import one.digitalinovation.laboojava.entidade.Cupom;
import one.digitalinovation.laboojava.entidade.constantes.Genero;
import one.digitalinovation.laboojava.entidade.constantes.Materia;
import one.digitalinovation.laboojava.negocio.ProdutoNegocio;

import java.util.Optional;
import java.util.Scanner;

/**
 * Classe utilitária para auxiliar na leitura de entradas de dados via teclado.
 * @author thiago leite
 */
public final class LeitoraDados {

	/**
	 * Classe do Java para manipular entradas via teclado.
	 */
	private static Scanner scanner;
	
	static {
		scanner = new Scanner(System.in);
	}

	/**
	 * Ler um dado específico
	 * @return Dado lido
	 */
	public static String lerDado() {
		
		String texto = scanner.nextLine().toUpperCase();
		
		return texto;
	}

	/**
	 * Ler os dados do livro a ser cadastrado.
	 * @return Um livro a partir dos dados de entrada
	 */
	public static Livro lerLivro() {
		String genero = "";

		System.out.println("Cadastrando livro...");
		Livro livro = new Livro();

		System.out.println("Digite o nome");
		String nome = lerDado();
		livro.setNome(nome);

				  System.out.println("Digite o gênero: DRAMA, SUSPENSE, ROMANCE");
					 genero = lerDado();
		
			livro.setGenero(Genero.valueOf(genero.toUpperCase()));			
	
		System.out.println("Digite o preço(padrão 0.0)");
		String preco = lerDado();
		livro.setPreco(Double.parseDouble(preco));

		return livro;
	}

	/**
	 * Ler os dados do caderno a ser cadastrado.
	 * @return Um caderno a partir dos dados de entrada
	 */
	 public static Caderno lerCaderno() {
		 
		 System.out.println("Cadastrando Caderno...");
		 Caderno caderno = new Caderno();
		 
		 System.out.println("Digite a quantidade de Materias.");
		 String materias = lerDado();
		 caderno.setMateria(Materia.valueOf(materias.toUpperCase()));
		 System.out.println("Digite o preço(padrão 0.0)");
			String preco = lerDado();
			caderno.setPreco(Double.parseDouble(preco));
			
		 return caderno;
		 
	 }
	
	/**
	 * Ler os dados do pedido e retorna um objeto a partir destes.
	 * @return Um pedido a partir dos dados de entrada
	 */
	public static Pedido lerPedido(Banco banco) {

		ProdutoNegocio produtoNegocio = new ProdutoNegocio(banco);

		System.out.println("Cadastrando pedido...");
		Pedido pedido = new Pedido();

		String opcao = "S";
		do {

			System.out.println("Digite o código do produto(livro/Caderno)");
			String codigo = lerDado();

			Optional<Produto> resultado = produtoNegocio.consultar(codigo);
			if (resultado.isPresent()) {

				Produto produto = resultado.get();

				System.out.println("Digite a quantidade");
				String quantidade = lerDado();
				produto.setQuantidade(Integer.parseInt(quantidade));

				pedido.getProdutos().add(produto);
			} else {
				System.out.println("Produto inexistente. Escolha um produto válido");
			}

			System.out.println("Deseja selecionar mais um produto? s/n");
			opcao = lerDado().toUpperCase();
		} while("S".equals(opcao));

		return pedido;
	}

	/**
	 * Ler os dados do cupom e retorna um objeto a partir destes.
	 * @return O cupom a partir dos dados de entrada
	 */
	public static Optional<Cupom> lerCupom(Banco banco) {

		System.out.println("Caso queira utilizar algum cupom escolha entre: CUPOM2, CUPOM5, CUPOM7. Se não desejar, deixe em branco.");

		String desconto = lerDado();

		for (Cupom cupom: banco.getCupons()) {
			if (cupom.getCodigo().equalsIgnoreCase(desconto)) {
				return Optional.of(cupom);
			}
		}

		return Optional.empty();
	}
	
	/* Adriano Aparecido da Silva */
	
	public static Cliente lerCliente() {
		
		Cliente cliente = new Cliente();
		
		System.out.println("Informe o CPF ou CNPJ do Cliente:");
		String cpf = lerDado();
		cliente.setCpf(cpf);
		
		System.out.println("Informe o Nome do Cliente:");
		String nome = lerDado();
		cliente.setNome(nome);
		
		return cliente;
		
	}

}
