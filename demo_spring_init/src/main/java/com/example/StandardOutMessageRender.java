package com.example;

/**
 * Created by asus on 29.04.2017.
 */
public class StandardOutMessageRender implements MessageRender {

    private MessageProvider messageProvider = null;


    @Override
    public void render() {
        if(messageProvider == null){
            throw new RuntimeException("Not messageProvider: " + StandardOutMessageRender.class.getName());
        }
        System.out.println( messageProvider.getMessage() );
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }
}
