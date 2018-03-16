import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * This builds an outgoing request with the MTOM option enabled
 * 
 */
public class MtomClient extends WebServiceTemplate {

    private static Jaxb2Marshaller MARSHALLER   = buildMarshaller("my_context_path");
    private static Jaxb2Marshaller UNMARSHALLER = buildMarshaller("my_context_path_2");

    MtomClient(String contextPath) {
        super(MARSHALLER, UNMARSHALLER);
        // This is the 2nd important thing that allows you to send MTOM
        // Enabling MTOM 
        MARSHALLER.setMtomEnabled(true);
        UNMARSHALLER.setMtomEnabled(true);
    }

    private static Jaxb2Marshaller buildMarshaller(String contextPath) {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(contextPath);
        return marshaller;
    }
}
