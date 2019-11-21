package co.edu.uniquindio.hela.filter;

import java.io.IOException;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import co.edu.uniquindio.hela.bean.SessionBean;

/**
 * Filtro de lo relacionado a productos
 * @author mateo,AnaMaria
 * @version 1.0
 */
@WebFilter("/productos/*")
public class FiltroProductos implements Filter {
	
	@Inject
	private BeanManager beanManager;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)

			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		request.getSession(false);
		String loginURL = request.getContextPath() + "/inicio/UniMarket.xhtml";
		Bean<?> bean = beanManager.getBeans("sessionBean").iterator().next();
		CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
		SessionBean seguridadBean = (SessionBean) beanManager.getReference(bean,
				bean.getBeanClass(), ctx);
		boolean autenticado = seguridadBean != null && seguridadBean.isAutenticado();
		if (autenticado) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(loginURL);
		}
	}

}
