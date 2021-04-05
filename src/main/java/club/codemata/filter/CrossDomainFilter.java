package club.codemata.filter; /**
 * @ClassName ${NAME}
 * @author DengJie
 * @version 1.0
 * @Description TODO
 * @createTime 2021/03/19 12:08:00
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrossDomainFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    /**
     * @author DengJie
     * @description 设置跨域请求
     * @Date 2021/3/19 12:10
     * @Param [javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain]
     * @return void
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type,Token,Accept, Connection, User-Agent, Cookie");
        res.setHeader("Access-Control-Max-Age", "3628800");
        chain.doFilter(request, res);
    }
}
