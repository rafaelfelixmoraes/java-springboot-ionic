package com.rafaelfelix.cursospring.resources;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rafaelfelix.cursospring.dto.EnderecoDTO;

/**
 * Classe de controle dos serviços relacionados a Endereço
 * 
 * @author rafaelfelixmoraes
 *
 */

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnderecoResource.class);
	
	private static RestTemplate template = new RestTemplate();
	private static final String cepPublicAPI = "https://viacep.com.br/ws/{cep}/json";
	
	/**
	 * Método que realiza a pesquisa de um endereço através do CEP
	 * 
	 * @param cep O Cep do endereço a ser pesquisado
	 * @return {@link HttpStatus}: <b><i>OK(200)</i></b> com o objeto {@link EnderecoDTO}
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAddressByCep(@RequestParam(value = "cep") String cep) {
		// URI (URL) parameters
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("cep", cep);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(cepPublicAPI);
		
		EnderecoDTO endereco = new EnderecoDTO();
		try {
			ResponseEntity<EnderecoDTO> response = template.getForEntity(builder.buildAndExpand(uriParams).toUri(), EnderecoDTO.class);
			endereco = response.getBody();
		} catch(HttpServerErrorException ex) {
			LOGGER.error("Causa: ".concat(ex.getCause().toString()).concat(". Detalhe: ").concat(ex.getMessage()));
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Ocorreu um erro ao tentar chamar a API pública");
		}
		return ResponseEntity.ok(endereco);
	}

}
