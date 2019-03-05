package com.rafaelfelix.cursospring.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.rafaelfelix.cursospring.services.exceptions.EncodingException;

public class URL {
	
	public static String decodeStringParam(String param) {
		try {
			return URLDecoder.decode(param, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new EncodingException("Falha ao tentar decodifcar o parametro. Verifique o log para mais detalhes");
		}
	}
	
	public static List<Integer> decodeIntList(String ids){
		return Arrays.asList(ids.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

}
