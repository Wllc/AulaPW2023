package ufrn.edu.br.aula.dominio;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/aula")
public class Filtro implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Passou pelo filtro!");

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        Boolean logado = (Boolean) session.getAttribute("logado");

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
