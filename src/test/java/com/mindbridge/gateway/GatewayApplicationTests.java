package com.mindbridge.gateway;

import com.mindbridge.gateway.filter.LoggingFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootTest
class GatewayApplicationTests {

	@Autowired
	private LoggingFilter loggingFilter;

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
}
