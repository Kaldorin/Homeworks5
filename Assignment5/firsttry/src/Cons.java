import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * The endpoint that consumes messages off of the queue. Happens to be runnable.
 *
 * @author syntx
 */
public class Cons extends Masin implements Runnable, Consumer {

    public Cons(String endPointName) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        super(endPointName);
    }

    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer " + consumerTag + " registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException {
        Map map = (HashMap) SerializationUtils.deserialize(body);
        System.out.println("Message Number " + map.get("message number") + " received.");

    }

    public void handleCancel(String consumerTag) {
    }

    public void handleCancelOk(String consumerTag) {
    }

    public void handleRecoverOk(String consumerTag) {
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {
    }
}