package com.castanheira.escola.filter;

import com.castanheira.escola.jsf.entities.Login;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {
    
    @Inject 
    private Login usuarioLogin;
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        if (!usuarioLogin.isLogado()
                && !request.getRequestURI().endsWith("/faces/login.xhtml")
                && !request.getRequestURI()
                        .contains("/javax.faces.resource/")) {
            response.sendRedirect(request.getContextPath()
                    + "/faces/login.xhtml");
        } else {
            if (usuarioLogin.isLogado()) {
                if (!verificaPermissao(request)) {
                    response.sendRedirect(request.getContextPath()
                        + "/faces/index.xhtml");            
                }
            }
            chain.doFilter(req, res);
        }
    }
    @Override
    public void init(FilterConfig config) throws ServletException {
    }
    @Override
    public void destroy() {
    }
    
    public boolean verificaPermissao(HttpServletRequest request){
        if (usuarioLogin.getPerfil().equalsIgnoreCase("Professor")) {
            if ( !request.getRequestURI().endsWith("/faces/index.xhtml") &&
                    !request.getRequestURI().contains("/boletim/")) {
                return false;
            }
        }
        return true;
    }
    
}