package com.BookStoreGateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.*;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of("/customer-service/register", "/customer-service/login",
			"/authentication/register", "/authentication/generattoken", "/eureka"

	);

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

}
