public Class AttachmentsController {

    @Autowired
    AttachmentsService attachmentService;

    /**
     * Creates and sends a SOAP request with an MTOM / XOP attachment to the web server to store
     *
     * @param file that includes the name and content
     */        
    @RequestMapping(value = "/attachments/upload", method = RequestMethod.POST)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadAttachmentMTOM(@RequestParam MultipartFile file) {        
        webService.uploadAttachmentMTOM(new AttachmentDTO(            
            file.getOriginalFilename(), 
            file.getBytes())
        );
    }

    /**
     * Requests a byte array of an attachment on the web server     
     * 
     * @param fileName the name of the file on the server
     * @return an http response containing the contents and name of the requested file
     *          or a 500 error
     */    
    @RequestMapping(value = "/attachments/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadAttachment(@RequestParam String fileName) {
        WSDLDownloadResponse response = webService.downloadAttachment(fileName);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData(response.getFileName(), response.getFileName());        
        MediaType contentType = MediaType.valueOf(fileUtils.getContentType(response.getFileName())))

        return ResponseEntity
                .ok()
                .headers(headers)
                .setContentType(contentType)
                .body(IOUtils.toByteArray(response.getFile().getInputStream()));
    }
}