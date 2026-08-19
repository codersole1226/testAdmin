package com.codersole.knowledgeserver;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String traceId =
                UUID.randomUUID()
                        .toString()
                        .replace("-", "");

        try {

            MDC.put(
                    "traceId",
                    traceId
            );

            chain.doFilter(
                    request,
                    response
            );

        } finally {

            MDC.remove("traceId");
        }
    }
}
