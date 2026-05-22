package com.mindbridge.gateway;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

import com.mindbridge.gateway.filter.LoggingFilter;

import reactor.core.publisher.Mono;

@SpringBootTest
class GatewayApplicationTests {

	@Autowired
	private LoggingFilter loggingFilter;

	@Autowired
	private RouteDefinitionLocator routeDefinitionLocator;

	@Test
	void contextLoads() {
	}

	@Test
	void filterDebeLoggearRequestYResponse() {
		MockServerHttpRequest request = MockServerHttpRequest
				.get("/api/offers/active")
				.build();

		MockServerWebExchange exchange = MockServerWebExchange.from(request);

		GatewayFilterChain chain = ex -> Mono.empty();

		loggingFilter.filter(exchange, chain).block();
	}

	@Test
	void filterDebeRetornarOrdenNegativo() {
		assert loggingFilter.getOrder() == -1;
	}

	@Test
	void authRoutesDebenReescribirHaciaApiCorrectamente() {
		List<RouteDefinition> routes = routeDefinitionLocator.getRouteDefinitions().collectList().block();

		assertNotNull(routes);

		RouteDefinition userRoute = routes.stream()
				.filter(route -> "user-service".equals(route.getId()))
				.findFirst()
				.orElseThrow();
		RouteDefinition authRoute = routes.stream()
				.filter(route -> "auth-service".equals(route.getId()))
				.findFirst()
				.orElseThrow();

		assertTrue(userRoute.getFilters().stream().anyMatch(filter ->
				"RewritePath".equals(filter.getName())
						&& filter.getArgs().containsValue("/api/$\\{segment}")));

		assertTrue(authRoute.getFilters().stream().anyMatch(filter ->
				"RewritePath".equals(filter.getName())
						&& filter.getArgs().containsValue("/api/auth$\\{segment}")));
	}
}
