package org.weixing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
            , produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Map post(@RequestBody MultiValueMap body) {
        logger.info("body: {}", body);
        return body;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Message postMessage(@RequestBody Message message) {
        logger.info("{}", message);
        return message;
    }
}
