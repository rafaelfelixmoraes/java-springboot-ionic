package com.rafaelfelix.cursospring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rafaelfelix.cursospring.domain.Categoria;
import com.rafaelfelix.cursospring.domain.Cidade;
import com.rafaelfelix.cursospring.domain.Cliente;
import com.rafaelfelix.cursospring.domain.Endereco;
import com.rafaelfelix.cursospring.domain.Estado;
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
import com.rafaelfelix.cursospring.repositories.PagamentoRepository;
import com.rafaelfelix.cursospring.repositories.PedidoRepository;
import com.rafaelfelix.cursospring.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner{
	
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

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
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
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2)); 
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	}

}

