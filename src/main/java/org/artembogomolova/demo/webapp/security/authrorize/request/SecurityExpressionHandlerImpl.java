package org.artembogomolova.demo.webapp.security.authrorize.request;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

@Service
public class SecurityExpressionHandlerImpl implements SecurityExpressionHandler<FilterInvocation> {
    @Override
    public ExpressionParser getExpressionParser() {
        return new SpelExpressionParser();
    }

    @Override
    public EvaluationContext createEvaluationContext(Authentication authentication, FilterInvocation invocation) {
        return null;
    }
}
