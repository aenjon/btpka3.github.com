package me.test.ons

import com.aliyun.openservices.ons.api.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OnsConsumer {
    static Logger log = LoggerFactory.getLogger(OnsConsumer)

    static void main(String[] args) {

        Properties props = new Properties()
        props.load(new FileInputStream(new File(System.getProperty("user.home"), ".aliyun")))

        def accessKeyId = props.accessKeyId
        def accessKeySecret = props.accessKeySecret

        def consumerId = "c1"
        def topic = "btpka3"
        def tag = "*"

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, consumerId);
        properties.put(PropertyKeyConst.AccessKey, accessKeyId);
        properties.put(PropertyKeyConst.SecretKey, accessKeySecret);
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(topic, tag, new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                log.info("${consumerId} - Receive: " + message);
                return Action.CommitMessage;
            }
        });
        consumer.start();
        log.info "Consumer ${consumerId} started"
    }
}
