package com.rafaelfelix.cursospring.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rafaelfelix.cursospring.domain.Cidade;
import com.rafaelfelix.cursospring.domain.Cliente;
import com.rafaelfelix.cursospring.domain.Endereco;
import com.rafaelfelix.cursospring.domain.enums.Perfil;
import com.rafaelfelix.cursospring.domain.enums.TipoCliente;
import com.rafaelfelix.cursospring.dto.ClienteDTO;
import com.rafaelfelix.cursospring.dto.ClienteNewDTO;
import com.rafaelfelix.cursospring.repositories.ClienteRepository;
import com.rafaelfelix.cursospring.repositories.EnderecoRepository;
import com.rafaelfelix.cursospring.security.UserSS;
import com.rafaelfelix.cursospring.services.exceptions.AuthorizationException;
import com.rafaelfelix.cursospring.services.exceptions.DataIntegrityException;
import com.rafaelfelix.cursospring.services.exceptions.FileException;
import com.rafaelfelix.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private S3Service s3Servive;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String imgPrefix;
	
	@Value("${img.profile.size}")
	private Integer imgSize;
	
	@Value("${img.profiles.path}")
	private String imgProfilesPath;
	
	@Value("${img.file.extension}")
	private String imgFileExtension;
	
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if((user == null || !user.hasRole(Perfil.ADMIN)) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> findAll(){
		List<Cliente> listObj = repo.findAll();
		if(listObj == null || listObj.isEmpty()) {
			throw new ObjectNotFoundException("Nenhum objeto encontrado!");
		}
		
		return listObj;
	}
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if((user == null || !user.hasRole(Perfil.ADMIN)) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente obj = repo.findByEmail(email);
		if(obj == null) {
			throw new ObjectNotFoundException(
						"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		
		return obj;
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = repo.save(cliente);
		enderecoRepo.saveAll(cliente.getEnderecos());
		
		return cliente;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente newCliente = this.find(cliente.getId());
		updateData(newCliente, cliente);
		return repo.saveAndFlush(newCliente);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Falha ao deletar objeto. Existe(m) registro(s) relacionado(s)");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), bCrypt.encode(objDTO.getSenha()));
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemeto(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() != null || !objDTO.getTelefone2().isEmpty()) {
			cli.getTelefones().add(objDTO.getTelefone2());
		} else if(objDTO.getTelefone3() != null || !objDTO.getTelefone3().isEmpty()) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente newCliente, Cliente oldCliente) {
		newCliente.setNome(oldCliente.getNome());
		newCliente.setEmail(oldCliente.getEmail());
	}
	
	/*public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquareImage(jpgImage);
		jpgImage = imageService.resize(jpgImage, imgSize);
		
		String fileName = imgPrefix.concat(user.getId().toString());
		File jpgFile = imageService.getFile(jpgImage, fileName);
		
		URI uri = cloudinaryService.uploadFile(jpgFile, fileName);
		
		// Deleta a imagem temporária criada
		jpgFile.delete();
		
		return uri;
	}*/
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		final URI uri;
		
		try {
			UserSS user = UserService.authenticated();
			if(user == null) {
				throw new AuthorizationException("Acesso negado");
			}
		
			BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
			jpgImage = imageService.cropSquareImage(jpgImage);
			jpgImage = imageService.resize(jpgImage, imgSize);
		
			String fileName = imgProfilesPath.concat(imgPrefix.concat(user.getId().toString())).concat(imgFileExtension);
			InputStream jpgFile = imageService.getInputStream(jpgImage, "jpg");
			uri = s3Servive.uploadFile(jpgFile, fileName, "image", null);
		
			// Deleta a imagem temporária criada
			jpgFile.close();
			jpgImage.flush();
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}
		
		return uri;
	}
}
