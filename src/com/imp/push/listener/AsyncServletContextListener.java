package com.imp.push.listener;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.*;

/**
 * Created by (IMP)郑和明
 * Date is 2017/3/4
 */
@WebListener
public class AsyncServletContextListener implements ServletContextListener {
    private BlockingQueue<AsyncContext> queue = new LinkedBlockingDeque<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {


//        ExecutorService executorService = Executors.newScheduledThreadPool(20);
        ServletContext servletContext = sce.getServletContext();
//        servletContext.setAttribute("executorService", executorService);
        servletContext.setAttribute("queue", queue);
        servletContext.log("contextInitialized");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (true) {
                        synchronized (this) {
                            Thread.sleep((int) (Math.random() * 10000));
                            AsyncContext asyncContext = queue.take();
                            asyncContext.getResponse().getWriter().print("IMP访问===" + Math.random());
                            asyncContext.complete();
                        }
                    }

                } catch (Exception e) {
                    servletContext.log(e.getMessage());

                }
            }
        }).start();


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ExecutorService executorService = (ExecutorService) sce.getServletContext().getAttribute("executorService");
//        executorService.shutdownNow();
    }
}
