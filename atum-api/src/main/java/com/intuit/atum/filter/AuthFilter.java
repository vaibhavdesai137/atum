package com.intuit.atum.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Filter init done...");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest hReq = (HttpServletRequest) request;
		HttpServletResponse hRes = (HttpServletResponse) response;
		String url = getFullUrl(hReq);
		String method = hReq.getMethod();

		// TODO: Implement auth logic
		System.out.println("Skipping auth for url: " + url + ", method: " + method);
		chain.doFilter(request, response);
	}

	public void destroy() {
		System.out.println("Filter destroy done...");
	}

	public static String getFullUrl(HttpServletRequest request) {

		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append("?").append(queryString).toString();
		}
	}

}