package org.editice.snipper.web.interceptor;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.editice.snipper.client.constants.Delimiters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


/**
 *
 * @author tinglang (editice@gmail.com)
 * @since 2015/10/25  22:13
 */
public class SnipperInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SnipperInterceptor.class);

    static List<String> modules = Lists.newArrayList();

    private static final String BLOG = "blog";

    private static final String ABOUT = "about";
    static {
        modules.add(BLOG);
        modules.add(ABOUT);
    }

    public SnipperInterceptor() {
    }

    private String mappingURL;//利用正则映射到需要拦截的路径

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(uri==null){
            return true;
        }
        if(uri.contains("statics")){
            return true;
        }
        request.setCharacterEncoding("UTF-8");
        genAttributes(request);
        return true;
    }

    private void genAttributes(HttpServletRequest request) throws Exception{
        for(Object s: request.getParameterMap().keySet()){
            request.setAttribute(s.toString(),request.getParameter(s.toString()));
        }

        String queryString=request.getQueryString();
        if (!StringUtils.isBlank(queryString)) {
            String[] strs = queryString.split(Delimiters.AND);
            for (String r : strs) {
                String[] tmp = r.split(Delimiters.EQUAL);
                if (tmp.length == 2) {
                    request.setAttribute(tmp[0], URLDecoder.decode(tmp[1], "UTF-8"));
                }
                else {
                    request.setAttribute(tmp[0], "");
                }
            }
        }
    }


    //在业务处理器处理请求执行完成后,生成视图之前执行的动作
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(modelAndView==null){
            return;
        }
        Map<String,Object> model = modelAndView.getModel();
        //用于提供给前端用的模块唯一标识
        model.put("_module",getModuleName(request.getRequestURI()));
    }

    private String getModuleName(String uri){
        for(String module: modules){
            if(StringUtils.contains(uri,module)){
                return module;
            }
        }
        return null;
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }


}

