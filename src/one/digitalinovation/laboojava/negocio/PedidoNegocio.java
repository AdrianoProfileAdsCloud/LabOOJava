package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Cliente;
import one.digitalinovation.laboojava.entidade.Cupom;
import one.digitalinovation.laboojava.entidade.Pedido;
import one.digitalinovation.laboojava.entidade.Produto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Classe para manipular a entidade {@link Pedido}.
 * 
 * @author thiago leite
 */
public class PedidoNegocio {

	/**
	 * {@inheritDoc}.
	 */
	private Banco bancoDados;

	/**
	 * Construtor.
	 * 
	 * @param banco Banco de dados para ter armazenar e ter acesso os pedidos
	 */
	public PedidoNegocio(Banco banco) {
		this.bancoDados = banco;
	}

	private double calcularTotal(List<Produto> produtos, Cupom cupom) {

		double total = 0.0;
		for (Produto produto : produtos) {
			total += produto.calcularFrete();
		}

		if (cupom != null) {
			return total * (1 - cupom.getDesconto());
		} else {
			return total;
		}

	}

	/**
	 * Salva um novo pedido sem cupom de desconto.
	 * 
	 * @param novoPedido Pedido a ser armazenado
	 */
	public void salvar(Pedido novoPedido,Optional<Cliente>clienteLogado) {
		salvar(novoPedido, null, clienteLogado);
	}

	/**
	 * Salva um novo pedido com cupom de desconto.
	 * 
	 * @param novoPedido Pedido a ser armazenado
	 * @param cupom      Cupom de desconto a ser utilizado
	 */
	public void salvar(Pedido novoPedido, Cupom cupom,Optional<Cliente>clienteLogado) {

		String codigo = "PE%4d%2d%04d";
		LocalDate hoje = LocalDate.now();
		codigo = String.format(codigo, hoje.getYear(), hoje.getMonthValue(), bancoDados.getPedidos().length);

		novoPedido.setCodigo(codigo);
		novoPedido.setCliente(clienteLogado.get());
		novoPedido.setTotal(calcularTotal(novoPedido.getProdutos(), cupom));
		bancoDados.adicionarPedido(novoPedido);
		System.out.println("Pedido Salvo com Suceso.");
	}

	/**
	 * Exclui um pedido a partir de seu código de rastreio.
	 * 
	 * @param codigo Código do pedido
	 */
	public void excluir(String codigo) {

		for (int i = 0; i < bancoDados.getPedidos().length; i++) {

			Pedido pedido = bancoDados.getPedidos()[i];
			if (pedido.getCodigo().equals(codigo)) {
				bancoDados.removerPedido(pedido);
				System.out.println("Pedido excluido com sucesso");
				break;
			}
		}
	}

	/**
	 * Lista todos os pedidos realizados.
	 */

	public void listarTodos() {

		if (bancoDados.getPedidos().length == 0) {
			System.out.println("Não existem pedidos realizados");
		} else {

			for (Pedido pedidos : bancoDados.getPedidos()) {
				System.out.println(pedidos.toString());
			}
		}
	}

	/*
	 * Author: Adrano Aparecido da Silva
	 */
	 public Optional<Pedido> consultarPedidoPorCodigo(String codigo) {

	        for (Pedido pedido: bancoDados.getPedidos()) {

	            if (pedido.getCodigo().equalsIgnoreCase(codigo)) {
	                return Optional.ofNullable(pedido);
	            }
	        }

	        return Optional.empty();
	    }
	 
}
