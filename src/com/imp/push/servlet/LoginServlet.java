package com.imp.push.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;


/**
 * Created by (IMP)郑和明
 * Date is 2017/3/4
 */
@WebServlet(urlPatterns = "/LoginServlet",asyncSupported = true)
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet");
        AsyncContext asyncContext = req.startAsync();
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                log("onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                log("onTimeout");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                log("onError");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                log("onStartAsync");
            }
        });
        asyncContext.setTimeout(10000);

        BlockingQueue<AsyncContext> queue = (BlockingQueue<AsyncContext>) req.getServletContext().getAttribute("queue");

        queue.offer(asyncContext);

    }
}
