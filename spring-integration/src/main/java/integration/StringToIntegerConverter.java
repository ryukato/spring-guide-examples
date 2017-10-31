package integration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.GenericMessage;

/**
 * Created by yoonyoulyoo on 2/21/17.
 */
public class StringToIntegerConverter implements Converter<String, Integer>, MessageConverter {
    public Integer convert(String source){
        return Integer.parseInt(source);
    }

    @Override
    public Object fromMessage(Message<?> message, Class<?> aClass) {

        return Integer.parseInt((String) message.getPayload());
    }

    @Override
    public Message<?> toMessage(Object o, MessageHeaders messageHeaders) {
        Message message = new GenericMessage<String>(String.valueOf((Integer) o));
        message.getHeaders().putAll(messageHeaders);
        return null;
    }
}