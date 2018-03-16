public class MyWebService {    
    /**
    * @param payload WSDL object
    * @param soapAction soapAction taken from WSDL
    */
    public JAXBElement<Object> sendAndReceive(Object payload, String soapAction) throws SoapConnectionException {
        try {                        
            return (JAXBElement<Object>) new MtomClient().marshalSendAndReceive(payload, message -> {
                // important part
                // With MTOM, if Soap Action isn't explicitly defined,
                // You'll get a 404 error 
                try {
                    SoapMessage soapMessage = (SoapMessage) message;                    
                    soapMessage.setSoapAction(soapAction);                    
                } catch (Exception e) {
                    // handle
                    // log error and abort
                }
            });                    
        } catch (final SoapMessageCreationException e) {
            // handle
        } catch (final WebServiceClientException e) {
            // handle
        } catch (final XmlMappingException e) {
            // handle            
        }
    }
}