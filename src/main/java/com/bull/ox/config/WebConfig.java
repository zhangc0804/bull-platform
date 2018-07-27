package com.bull.ox.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

@WebListener
public class WebConfig implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("##########################sessionCreated");
        HttpSession session = httpSessionEvent.getSession();
        Enumeration<String> enumeration = session.getAttributeNames();
        while(enumeration.hasMoreElements()){
            System.out.println("session-----------------");
            String key = enumeration.nextElement();
            System.out.println(key);
            System.out.println(session.getAttribute(key));
            System.out.println("session-----------------");
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
