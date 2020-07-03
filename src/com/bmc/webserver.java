package com.bmc;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;


public class webserver {

    public static void main(String[] args) throws Exception {
        // ���� http ������, �󶨱��� 8080 �˿�
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8082), 0);

        // �������ļ���, "/" ��ʾƥ������ URI ����
        httpServer.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                System.out.println("addr: " + httpExchange.getRemoteAddress() +     // �ͻ���IP��ַ
                        "; protocol: " + httpExchange.getProtocol() +               // ����Э��: HTTP/1.1
                        "; method: " + httpExchange.getRequestMethod() +            // ���󷽷�: GET, POST ��
                        "; URI: " + httpExchange.getRequestURI()+                  // ���� URI
                        "; RequestBody: " + httpExchange.getRequestBody());
               
                	
					String	url = URLDecoder.decode(httpExchange.getRequestURI().toString(),"utf-8");
                	System.out.println("\ndecode url:"+url+"\n");

                // ��ȡ����ͷ
                String userAgent = httpExchange.getRequestHeaders().getFirst("User-Agent");
                System.out.println("User-Agent: " + userAgent);

                // ��Ӧ����
                byte[] respContents = "Hello World".getBytes("UTF-8");

                // ������Ӧͷ
                httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                // ������Ӧcode�����ݳ���
                httpExchange.sendResponseHeaders(200, respContents.length);

                // ������Ӧ����
                httpExchange.getResponseBody().write(respContents);

                // �رմ�����
                httpExchange.close();
            }
        });

        // ���������ļ���, ���� URI �� "/aa" ��ͷ������
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

        // ��������
        httpServer.start();
    }

}
