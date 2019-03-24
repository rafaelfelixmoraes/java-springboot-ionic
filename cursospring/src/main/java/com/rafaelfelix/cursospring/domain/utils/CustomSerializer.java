package com.rafaelfelix.cursospring.domain.utils;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomSerializer extends JsonSerializer<Double>{
	
	private static final DecimalFormat decimalFormat = new DecimalFormat("##,###,##0.00");

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(decimalFormat.format(value));
		
	}

}
