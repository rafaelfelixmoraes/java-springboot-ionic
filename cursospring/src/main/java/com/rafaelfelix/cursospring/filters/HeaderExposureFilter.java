package com.rafaelfelix.cursospring.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter {

	  public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
	    httpResponse.addHeader(EXPOSE_HEADERS, "Location");
	    chain.doFilter(request, response);
	}
	  
}
