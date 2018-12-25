//package com.philips.backend.security;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.StringTokenizer;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter; 
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;
//
//import org.apache.commons.codec.binary.Base64;
//
//
//
//
//
//@Provider
//public class SecurityFilter implements ContainerRequestFilter {
//	private static String AUTHORIZATION_HEADER = "Authorization"; 
//	private static String AUTHORIZATION_HEADER_PREFIX = "Basic"; 
//
//
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//		
//		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER); 
//		if(authHeader != null && authHeader.size() > 0 ) {
//			String authToken = authHeader.get(0);
//			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
//			String decodedString = new String(Base64.decodeBase64(authToken)); 
//			StringTokenizer tokenizer = new StringTokenizer(decodedString, ":"); 
//			String userName = tokenizer.nextToken(); 
//			String password = tokenizer.nextToken(); 
//			if(userName.equals("philips") && password.equals("123"))
//			{
//				return; 
//			}
//		}
//		
//		Response unAthResponse = Response.status(Response.Status.UNAUTHORIZED)
//				.entity("User is unauthorized")
//				.build(); 
//		requestContext.abortWith(unAthResponse);
//		
//	}
//
//}
