package com.lazycece.au.api.spring.boot.sample.controller;

import org.junit.jupiter.api.Test;

/**
 * @author lazycece
 */
public class UploadFileControllerTest {

    private String token = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5SnpkV0lpT2lKeVR6QkJRbGhPZVVGRU1XcGlNakIxWWtkR05tVlhUbXhaTWxWMVdWaFZkVmxZUW5CTWJrNTNZMjFzZFZwNU5XbGlNamt3VEc1T2FHSllRbk5hVXpWc1ltNVNjR1JJYTNWYVJ6bDZUR3hXZWxwWVNsUmtWMHB4V2xkT01GZE5VSFpUUjJNNWVFWlZRMEZCUmsxQlFXZ3hZekpXZVdKdFJuUmFXRkZCUld0NGNWbFlXbWhNTW5ob1ltMWpkbFV6VW5saFZ6VnVUek5vZDJSQlFVbGlSMFkyWlZkT2JGa3lWVDBpTENKcGMzTWlPaUpVVDB0RlRpMUpVMU5WUlZJaUxDSmxlSEFpT2pFMk9EVXhOVFUzTnpZc0ltbGhkQ0k2TVRZNE5URTFNemszTm4wLnNTdDlwQ1FKTjNXN0c1Ylkxb3RaWHk0ZEE3blFyWHR2YjRxSFJOOExaX1E";

    @Test
    public void testUpload() throws Exception {
        String filePath = ClassLoader.getSystemResource("application.yml").getFile();
        HttpHelper.getInstance().token(token).doUpload("/upload/file", filePath, "file", null, String.class);
    }
}
