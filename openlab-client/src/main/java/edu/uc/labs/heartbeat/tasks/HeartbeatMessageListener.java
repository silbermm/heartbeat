package edu.uc.labs.heartbeat.tasks;

import com.rabbitmq.client.Channel;
import edu.uc.labs.heartbeat.domain.ClientMachine;
import edu.uc.labs.heartbeat.service.HeartbeatService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.apache.log4j.Logger;

public class HeartbeatMessageListener implements ChannelAwareMessageListener {

    public HeartbeatMessageListener(HeartbeatService heartbeatService, AmqpTemplate rabbitTemplate) {
        this.heartbeatService = heartbeatService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        final MessageProperties props = message.getMessageProperties();

        ClientMachine m = heartbeatService.getMachineInfo();
        rabbitTemplate.convertAndSend(props.getReplyTo(), m, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId(props.getCorrelationId());
                return message;
            }
        });
    }
    private static final Logger log = Logger.getLogger(HeartbeatMessageListener.class);
    private HeartbeatService heartbeatService;
    private AmqpTemplate rabbitTemplate;
}
