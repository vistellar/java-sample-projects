package org.weix;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author weixing
 */
public class UniqueLogName {

    public static final Logger logger = LoggerFactory.getLogger(UniqueLogName.class);

    public static void main(String[] args) {
        MDC.put("log-dir", "/tmp");
        MDC.put("log-name", "xxx");

        logger.info("xxx");
    }
}
