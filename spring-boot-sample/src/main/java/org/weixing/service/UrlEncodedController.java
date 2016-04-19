package org.weixing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weixing
 */
@RestController
@RequestMapping(value = "/urlencoded")
public class UrlEncodedController {
    private static final Logger logger = LoggerFactory.getLogger(UrlEncodedController.class);

    public static class Message {
        private String value;

        public Message() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
            , produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Message postMessage(@RequestBody MultiValueMap body) {
        logger.info("body: {}", body);

        ObjectMapper objectMapper = new ObjectMapper();

        Message message = objectMapper.convertValue(body.toSingleValueMap(), Message.class);
        message = postMessage(message);
        return message;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public Message postMessage(@RequestBody Message message) {
        logger.info("{}", message);
        message.setValue("xxx");
        return message;
    }
}
