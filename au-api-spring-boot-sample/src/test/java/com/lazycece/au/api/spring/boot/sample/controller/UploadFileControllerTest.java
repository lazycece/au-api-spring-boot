package com.lazycece.au.api.spring.boot.sample.controller;

import org.junit.jupiter.api.Test;

/**
 * @author lazycece
 */
public class UploadFileControllerTest {

    private String token = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5SnpkV0lpT2lKeVR6QkJRbGhPZVVGRU1XcGlNakIxWWtkR05tVlhUbXhaTWxWMVdWaFZkVmxZUW5CTWJrNTNZMjFzZFZwNU5XbGlNamt3VEc1T2FHSllRbk5hVXpWc1ltNVNjR1JJYTNWYVJ6bDZUR3hXZWxwWVNsUmtWMHB4V2xkT01GZE5VSFpUUjJNNWVFWlZRMEZCUmsxQlFXZ3hZekpXZVdKdFJuUmFXRkZCUld0NGNWbFlXbWhNTW5ob1ltMWpkbFV6VW5saFZ6VnVUek5vZDJSQlFVbGlSMFkyWlZkT2JGa3lWVDBpTENKcGMzTWlPaUpVVDB0RlRpMUpVMU5WUlZJaUxDSmxlSEFpT2pFM09EVXhORGMzTWpNc0ltbGhkQ0k2TVRjNE5URTBOVGt5TTMwLmp1ME1mbWZkSllmX2JrZ1ptNXk4Z2F0dXp5WXBEd1BEVG9XY0F5Y2Z1cW8=";

    @Test
    public void testUpload() throws Exception {
        String filePath = ClassLoader.getSystemResource("application.yml").getFile();
        HttpHelper.getInstance().token(token).doUpload("/upload/file", filePath, "file", null, String.class);
    }
}
