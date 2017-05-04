package com.example;

/**
 * Created by asus on 29.04.2017.
 */
public interface MessageRender {
    public void render();
    public void setMessageProvider( MessageProvider provider);
    public MessageProvider getMessageProvider();
}
