package com.acs576.g2.bowlingleaguescoringapi.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * The type Transaction id injection filter.
 * Author @ParthManaktala
 */
@Component
@Order(0)
public class TransactionIdInjectionFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "x-request-id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Try to get the transaction ID from the request header
        String transactionId = httpServletRequest.getHeader(REQUEST_ID_HEADER);

        // If not present or blank, generate a new one
        if (!StringUtils.hasText(transactionId)) {
            transactionId = UUID.randomUUID().toString();
        }

        // Set the transaction ID in the MDC context
        MDC.put("transactionId", transactionId);

        // Ensure the transaction ID is also present in the response headers
        httpServletResponse.setHeader(REQUEST_ID_HEADER, transactionId);

        try {
            chain.doFilter(request, response);
        } finally {
            // Clear the MDC after the request is processed to prevent memory leaks
            MDC.remove("transactionId");
        }
    }
}
