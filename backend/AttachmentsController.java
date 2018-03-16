    /**
     * Creates and sends a SOAP request with an MTOM / XOP attachment
     *
     * @param: 
     * @param: 
     */    
    @ApiOperation(value = "This method allows the user to submit an attachment to the CSPS system.",
            notes = "Transmits to CNH: <br>CNH Dealer number<br>Claim ID<br>Claim Line Number<br>Note",
            tags = {BASE_URL_CNH_CSPS})
    @PreAuthorize("hasAnyRole('ROLE_ID_USER')")
    @RequestMapping(value = "/claimmanagement/uploadAttachment", method = RequestMethod.POST)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public com.cdk.heavyequipment.oem.cnh.csps.claimmanagement.CodeDescription uploadAttachmentMTOM(
            @RequestParam Integer claimId,
            @RequestParam Integer claimLineNumber,
            @RequestParam Integer attachmentNumber,
            @RequestParam Boolean overwrite,
            @RequestParam MultipartFile file)
            throws Exception {
        return cspsService.uploadAttachment(new CSPSUploadAttachmentRequestDTO(claimId, claimLineNumber, attachmentNumber, overwrite, file.getOriginalFilename(), file.getBytes()));
    }

    /**
     * This method requests a download of an existing attachment on a claim line
     *
     * @return The WSDL Generated response object
     */
    @ApiOperation(value = "This method allows the user to download an attachment from the CSPS system.",
            notes = "Transmits to CNH: <br>CNH Dealer number<br>Claim ID<br>Claim Line Number<br>Attachment Number",
            tags = {BASE_URL_CNH_CSPS})
    @PreAuthorize("hasAnyRole('ROLE_ID_USER')")
    @RequestMapping(value = "/claimmanagement/downloadAttachment", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadAttachment(
            @RequestParam Integer claimId,
            @RequestParam Integer claimLineNumber,
            @RequestParam Integer attachmentNumber)
            throws Exception {
        DownloadAttachmentResponseType response = cspsService.downloadAttachment(claimId, claimLineNumber, attachmentNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData(response.getFileName(), response.getFileName());
        headers.setContentType(org.springframework.http.MediaType.valueOf(EmailUtils.getContentType(response.getFileName())));

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(org.apache.commons.io.IOUtils.toByteArray(response.getFile().getInputStream()));
    }