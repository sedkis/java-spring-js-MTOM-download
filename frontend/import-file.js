// handles sending a file to REST endpoint
// Setup an HTML form that will pop up a box
// and allow the user to choose a file, then
// on submit pass it to this method
function uploadFile(file) {

    // file gets converted into MultiPartFile in backend
    var fd = new FormData();    
    fd.append('file', file);    

    var uploadUrl = appConfigurationService.basePath + '/attachments/upload';

    $http.post(uploadUrl, fd, {    
        // undefined tells the Spring marshaller to leave the content as is.
        // otherwise, it will convert the byte[] to base64 and you will
        // incur extra 33% overhead 
        headers: {'Content-Type': undefined} 
    }).then(function (response) {
        alert('successfully added attachment');    
    }, function(err){
        //handle
    });
}