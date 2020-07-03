package com.bmc;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;


public class webserver {

    public static void main(String[] args) throws Exception {
        // 创建 http 服务器, 绑定本地 8080 端口
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8082), 0);

        // 创上下文监听, "/" 表示匹配所有 URI 请求
        httpServer.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                System.out.println("addr: " + httpExchange.getRemoteAddress() +     // 客户端IP地址
                        "; protocol: " + httpExchange.getProtocol() +               // 请求协议: HTTP/1.1
                        "; method: " + httpExchange.getRequestMethod() +            // 请求方法: GET, POST 等
                        "; URI: " + httpExchange.getRequestURI()+                  // 请求 URI
                        "; RequestBody: " + httpExchange.getRequestBody());
               
                	
					String	url = URLDecoder.decode(httpExchange.getRequestURI().toString(),"utf-8");
                	System.out.println("\ndecode url:"+url+"\n");

                // 获取请求头
                String userAgent = httpExchange.getRequestHeaders().getFirst("User-Agent");
                System.out.println("User-Agent: " + userAgent);

                // 响应内容
                byte[] respContents = "Hello World".getBytes("UTF-8");

                // 设置响应头
                httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                // 设置响应code和内容长度
                httpExchange.sendResponseHeaders(200, respContents.length);

                // 设置响应内容
                httpExchange.getResponseBody().write(respContents);

                // 关闭处理器
                httpExchange.close();
            }
        });

        // 创建上下文监听, 处理 URI 以 "/aa" 开头的请求
        httpServer.createContext("/aa", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                byte[] respContents = "Hello World AA".getBytes("UTF-8");

                httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                httpExchange.sendResponseHeaders(200, respContents.length);

                httpExchange.getResponseBody().write(respContents);
                httpExchange.close();
            }
        });

        // 启动服务
        httpServer.start();
    }

}
