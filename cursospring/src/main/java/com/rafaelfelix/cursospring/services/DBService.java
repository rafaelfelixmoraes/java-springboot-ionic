package com.rafaelfelix.cursospring.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.Categoria;
import com.rafaelfelix.cursospring.domain.Cidade;
import com.rafaelfelix.cursospring.domain.Cliente;
import com.rafaelfelix.cursospring.domain.Endereco;
import com.rafaelfelix.cursospring.domain.Estado;
import com.rafaelfelix.cursospring.domain.ItemPedido;
import com.rafaelfelix.cursospring.domain.Pagamento;
import com.rafaelfelix.cursospring.domain.PagamentoComBoleto;
import com.rafaelfelix.cursospring.domain.PagamentoComCartao;
import com.rafaelfelix.cursospring.domain.Pedido;
import com.rafaelfelix.cursospring.domain.Produto;
import com.rafaelfelix.cursospring.domain.enums.EstadoPagamento;
import com.rafaelfelix.cursospring.domain.enums.TipoCliente;
import com.rafaelfelix.cursospring.repositories.CategoriaRepository;
import com.rafaelfelix.cursospring.repositories.CidadeRepository;
import com.rafaelfelix.cursospring.repositories.ClienteRepository;
import com.rafaelfelix.cursospring.repositories.EnderecoRepository;
import com.rafaelfelix.cursospring.repositories.EstadoRepository;
import com.rafaelfelix.cursospring.repositories.ItemPedidoRepository;
import com.rafaelfelix.cursospring.repositories.PagamentoRepository;
import com.rafaelfelix.cursospring.repositories.PedidoRepository;
import com.rafaelfelix.cursospring.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Escolar");
		Categoria cat5 = new Categoria(null, "Móveis");
		Categoria cat6 = new Categoria(null, "Eletrodomésticos");
		Categoria cat7 = new Categoria(null, "Eletrônicos");
		Categoria cat8 = new Categoria(null, "Games e Acessórios");
		Categoria cat9 = new Categoria(null, "Roupas, calçados e acessórios");
		Categoria cat10 = new Categoria(null, "Automóveis e acessórios");

		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		Produto prod4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto prod5 = new Produto(null, "Toalha", 50.00);
		Produto prod6 = new Produto(null, "Colcha", 200.00);
		Produto prod7 = new Produto(null, "TV True color", 1200.00);
		Produto prod8 = new Produto(null, "Roçadeira", 800.00);
		Produto prod9 = new Produto(null, "Abajour", 100.00);
		Produto prod10 = new Produto(null, "Pendente", 100.00);
		Produto prod11 = new Produto(null, "Shampoo", 90.00);
		Produto prod12 = new Produto(null, "Game The Division 2 - Xbox One", 100.00);
		Produto prod13 = new Produto(null, "Controle Wireless Xbox one Preto", 200.00);
		Produto prod14 = new Produto(null, "Tenis Nike Shocks Vermelho", 500.00);
		Produto prod15 = new Produto(null, "Pneu Pirelli Cinturato 165/70", 160.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38230834", cli1, cid1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32:05"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35:44"), cli1, end2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00:00"), null);
		ped2.setPagamento(pagto2);

		ItemPedido ip1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.00);

		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2, prod4));
		cat3.getProdutos().addAll(Arrays.asList(prod5, prod6));
		cat4.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3, prod7));
		cat5.getProdutos().addAll(Arrays.asList(prod8));
		cat6.getProdutos().addAll(Arrays.asList(prod9, prod10));
		cat8.getProdutos().addAll(Arrays.asList(prod12, prod13));
		cat9.getProdutos().addAll(Arrays.asList(prod14));
		cat10.getProdutos().addAll(Arrays.asList(prod15));

		prod1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		prod3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod4.getCategorias().addAll(Arrays.asList(cat2));
		prod5.getCategorias().addAll(Arrays.asList(cat3));
		prod6.getCategorias().addAll(Arrays.asList(cat3));
		prod7.getCategorias().addAll(Arrays.asList(cat4));
		prod8.getCategorias().addAll(Arrays.asList(cat5));
		prod9.getCategorias().addAll(Arrays.asList(cat6));
		prod10.getCategorias().addAll(Arrays.asList(cat6));
		prod11.getCategorias().addAll(Arrays.asList(cat7));
		prod12.getCategorias().addAll(Arrays.asList(cat8));
		prod13.getCategorias().addAll(Arrays.asList(cat8));
		prod14.getCategorias().addAll(Arrays.asList(cat9));
		prod15.getCategorias().addAll(Arrays.asList(cat10));

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		prod1.getItens().addAll(Arrays.asList(ip1));
		prod2.getItens().addAll(Arrays.asList(ip3));
		prod3.getItens().addAll(Arrays.asList(ip2));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10,
												prod11, prod12, prod13, prod14, prod15));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
