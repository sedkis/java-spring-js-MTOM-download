## SOAP MTOM / XOP template using Java 8, Spring, JAXB
Uploading and Downloading files is a pain in the ass.  It feels like all these hack attempts at creating fake HTML forms and fake clicking them through JavaScript just to send a specific type of file to a specific type of REST endpoint.

This isn't a complete example obviously.  It is a skeleton of everything you need if you want to send or receive MTOM / XOP SOAP requests.

# MTOM
[MTOM or the Message Transmission Optimization Mechanism](https://en.wikipedia.org/wiki/Message_Transmission_Optimization_Mechanism) is a low overhead way of sending attachments in a SOAP message

There are only two things that you have to do in addition to normal SOAP messages:
1.  Enable MTOM.  There are several ways to do this.  Inside the MtomClient.java class, you can see it is enabled through the JaxBMarshaller
2.  Add a SoapAction.  One would expect this to happen automatically with the marshaller.  That is not the case.  Instead, you have to manually set the SOAPAction.  Inside the MyWebService.java send() method, you can see I've added a WebServiceMessageCallback that adds it. This method gets called once the SOAP request is ready to be accepted and will tell the server where to send the message.
