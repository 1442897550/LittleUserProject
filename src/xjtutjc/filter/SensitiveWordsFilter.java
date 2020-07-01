package xjtutjc.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
/*
敏感词汇过滤器
 */

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //增强getPARAMETER
        ServletRequest o = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getParameter")){
//                    System.out.println("getparameter");
                    //增强返回值
                    //获取返回值
                    String value = (String) method.invoke(req, args);
                    if (value!=null){
                        for (String s : list) {
                            if (value.contains(s)){
                                value = value.replaceAll(s,"***");
                            }
                        }
                    }
//                    System.out.println(value);
                    return value;
                }
                //parameterMap
                if (method.getName().equals("getParameterMap")){
                    System.out.println("getmap");
                    Map<String,String[]> invoke = (Map<String, String[]>) method.invoke(req, args);
                    if (invoke!=null){
                        Set<String> strings = invoke.keySet();
                        System.out.println(strings);
                        for (String string : strings) {
                            String[] strings1 = invoke.get(string);
                            for (String s : strings1) {
                                for (String s1 : list) {
                                    System.out.println(s);
                                    if (s.contains(s1)){

                                        s = s.replaceAll(s1,"***");
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(invoke);
                    return invoke;
                }
                //paramaterValues
                return method.invoke(req,args);
            }

        });
        chain.doFilter(o, resp);
    }
    private List<String> list = new ArrayList<String>();
    public void init(FilterConfig config) throws ServletException {
        //加载文件


        try {
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            //读取文件
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            //将文件每一行添加到list中
            String line = null;
            while ((line=br.readLine())!=null){
                list.add(line);
            }
            br.close();
            System.out.println(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
