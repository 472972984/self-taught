package indi.repo.springboot.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
 */
@Slf4j
public class RemoveFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("RemoveFilter coming here .... ");
        String[] filterStrings = {"TestFilter"};

        String name = request.getParameter("name");
        if (name != null) {
            try {
                Field field = chain.getClass().getDeclaredField("filters");
                field.setAccessible(true);
                FilterConfig[] filters = (FilterConfig[]) field.get(chain);
                int k = 0;
                for (int i = 0; i < filters.length; i++) {
                    if (filters[i] != null) {
                        Field field2 = filters[i].getClass().getDeclaredField("filterDef");
                        field2.setAccessible(true);
                        Field field3 = field2.get(filters[i]).getClass().getDeclaredField("filterClass");
                        field3.setAccessible(true);
                        String filterClass = (String) field3.get(field2.get(filters[i]));

                        for (String str1 : filterStrings) {
                            if (filterClass.endsWith(str1)) {
                                filters[i] = null;
                                k++;
                                break;
                            }
                        }

                        field3.setAccessible(false);
                        field2.setAccessible(false);
                    }
                }

                int index = 0;
                for (int i = 0; i < filters.length; i++) {
                    if (index == 0 && filters[i] == null) {
                        index = i;
                    } else if (index != 0 && filters[i] != null) {
                        filters[index] = filters[i];
                        filters[i] = null;
                        i = index;
                        index = 0;
                    }
                }

                field.setAccessible(false);
                Field n = chain.getClass().getDeclaredField("n");
                n.setAccessible(true);
                n.set(chain, n.getInt(chain) - k);
                n.setAccessible(false);
            } catch (Exception e) {
                log.error("出现异常：", e);
                System.out.println(e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
