package com.hello.denis;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9122125709896866661L;
	private int i;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        PrintWriter pw =  response.getWriter();
		pw.write("Start ");
		final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.getResponse().getWriter().println("async ");
        asyncContext.start(new StockPriceRunner(asyncContext));

        pw.write("Done ");
	}

	private class StockPriceRunner implements Runnable {
        AsyncContext asyncContext;
        
        public StockPriceRunner(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }
 
        @Override
        public void run() {
            try {
    			Thread.sleep(5000);
                asyncContext.getResponse().getWriter().println("hello " +  ++i + " ");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                asyncContext.complete();
            }
        }
    }
}
