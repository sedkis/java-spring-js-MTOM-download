public Class AttachmentsService {

    @Autowired
    MyWebService MyWebService;

    /**
     * Creates and sends a SOAP request with an MTOM / XOP attachment to the web server to store
     *
     * @param attachmentDto contains the content and filename to be uploaded
     */            
    public void uploadAttachmentMTOM(AttachmentDTO attachmentDto) {        
        // from() static method in UploadWSDL class that acts as a converter
        UploadWSDL uploadRequest = UploadWSDL.from(attachmentDto);
        myWebService.sendAndReceive(uploadRequest, "soapActionFromWSDL");
    }
    /**
     * Requests a byte array of an attachment on the web server     
     * 
     * @param fileName is the filename of the file being requested
     * @return 
     */        
    public ResponseEntity<byte[]> downloadAttachment(String fileName) {
        // from() static method in DownloadWSDL class that acts as a converter
        DownloadWSDL downloadRequest = DownloadWSDL.from(filename); 
        return myWebService.sendAndReceive(downloadRequest, "soapActionFromWSDL_2");
    }
}