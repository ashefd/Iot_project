import iot.sensor.Format.Uplink;

import java.util.Base64;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Application connects to LO and consumes LoRa messages.
 */
public class Client {

    final static String DEST = "v3/projet-iot-polytech22@ttn/devices/eui-70b3d57ed004cbe4/up";

    /**
     * Basic "MqttCallback" that handles messages as JSON Lora messages,
     * and decode them
     */
    public static class SimpleMqttCallback implements MqttCallbackExtended {

        private MqttClient mqttClient;
        private Gson gson;

        public SimpleMqttCallback(MqttClient mqttClient) {
            this.mqttClient = mqttClient;
            this.gson = new GsonBuilder().setPrettyPrinting().create();
        }

        public void connectionLost(Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("Connection lost");
        }

        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            System.out.println("Received message - " + mqttMessage);
            // Decoding the LoRa message
            try {
                String mqttPayloadString = new String(mqttMessage.getPayload());

                /*
                * // pretty printing
                * JsonParser parser = new JsonParser();
                * JsonElement el = parser.parse(mqttPayloadString);
                * System.out.println(gson.toJson(el));
                */

                // accès sous-objets
                JsonObject mqttPayload = gson.fromJson(mqttPayloadString, JsonObject.class);
                String message = mqttPayload.getAsJsonObject("uplink_message").get("frm_payload").getAsString();
                byte[] decoder = Base64.getDecoder().decode(message);
                Uplink msg = Uplink.parseFrom(decoder);
                System.out.println(msg.getBattery());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            // nothing
        }

        public void connectComplete(boolean b, String s) {
            System.out.println("Connection is established");
            try {
                subscribeToRouter(mqttClient, DEST);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        private void subscribeToRouter(MqttClient mqttClient, String routingKey) throws MqttException {
            // Subscribe to commands
            System.out.printf("Consuming from Router with filter '%s'...%n", routingKey);
            mqttClient.subscribe(routingKey);
            System.out.println("... subscribed.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String API_KEY = "NNSXS.MOHN2ZC3FHEM6LV5XVO27ZQMYGGGNXM62MPXRWA.4D4BQBQWCQMGTAPU2B4DWCYQTB36UPCJAF2BR33R2PYO6HAQ3JKA";

        String SERVER = "tcp://eu1.cloud.thethings.network:1883";
        String APP_ID = "app:" + UUID.randomUUID().toString();
        int KEEP_ALIVE_INTERVAL = 30;// Must be <= 50

        MqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(SERVER, APP_ID, new MemoryPersistence());

            // register callback (to handle received commands
            mqttClient.setCallback(new SimpleMqttCallback(mqttClient));

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName("projet-iot-polytech22@ttn");
            connOpts.setPassword(API_KEY.toCharArray()); // passing API key value as password
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(KEEP_ALIVE_INTERVAL);
            connOpts.setAutomaticReconnect(true);

            // Connection
            System.out.printf("Connecting to broker: %s ...%n", SERVER);
            mqttClient.connect(connOpts);
            System.out.println("... connected.");

            synchronized (mqttClient) {
                mqttClient.wait();
            }

        } catch (MqttException me) {
            me.printStackTrace();

        } finally {
            // close client
            if (mqttClient != null && mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
