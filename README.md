## SOAP MTOM / XOP template using Java 8, Spring, JAXB
Uploading and Downloading files is a pain in the ass.  It's all hack attempts at creating fake HTML forms and fake clicking them through JavaScript just to send a specific type of file to a specific type of REST endpoint.

I had a lot of trouble figuring out the best way to do this.  So I put this here for others that are looking for a quick how-to accept and send binary files through an HTTP end point, and more specifically, to send MTOM / XOP attachments in a SOAP request.

This isn't a completely running example obviously.  It is a skeleton of everything you need if you want to send or receive MTOM / XOP SOAP requests.

# Backend
Pretty straight forward service oriented architecture.  Controller accepts a request. First, Spring unmarshalles the file into a "MultiPartFile".  Controller then converts request into DTO then passes it so Service.  This is where business logic goes.  Then it is passed to a WebService layer where the outgoing request is made.  

# MTOM
[MTOM or the Message Transmission Optimization Mechanism](https://en.wikipedia.org/wiki/Message_Transmission_Optimization_Mechanism) is a low overhead way of sending attachments in a SOAP message

There are only two things that you have to do in addition to normal SOAP messages:
1.  Enable MTOM.  There are several ways to do this.  In this example, inside the MtomClient.java class, you can see it is enabled through the JaxBMarshaller
2.  Add a SoapAction.  One would expect this to happen automatically with the marshaller.  That is not the case.  Instead, you have to manually set the SOAPAction.  Inside the MyWebService.java send() method, you can see I've added a WebServiceMessageCallback that adds it. This method gets called once the SOAP request is ready to be accepted and will tell the server where to send the message.  The SOAPACtion is explicitly defined in the WSDL and it can be taken from there and hard-coded.

# Front end
This is the hackiest bit.  There are no natural ways to download a file through HTML and JavaScript.  It takes a lot of parsing and converting, and some fake HTML forms mixed with some real ones.  Someone should create a framework to make this better.  
We create an object of type FormData and add a file to it.  [There are some good resources for creating HTML forms that accept a file online.](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/file).  We just have to call the method in import-file.js when the user hits "submit" and pass the file that is selected.
