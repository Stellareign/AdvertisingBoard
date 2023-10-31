//package ru.skypro.homework.config;
//
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class BasicAuthCorsFilter extends OncePerRequestFilter {
//
//    /**
//     * Фильтр для обработки HTTP запросов, наследуется от класса OncePerRequestFilter
//     * кот. гарантирует, что фильтр будет вызываться один раз для каждого запроса
//     * пределяется метод doFilterInternal, который выполняет основную работу фильтрации.
//     * В методе устанавливается заголовок "Access-Control-Allow-Credentials" со значением "true", что позволяет
//     * передавать учётные данные (например, куки) при кросс-доменных запросах
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param filterChain
//     * @throws ServletException
//     * @throws IOException
//     */
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest,
//                                    HttpServletResponse httpServletResponse,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//    }
//}
