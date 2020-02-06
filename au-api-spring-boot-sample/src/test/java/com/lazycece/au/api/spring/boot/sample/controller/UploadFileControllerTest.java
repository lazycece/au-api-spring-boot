package com.lazycece.au.api.spring.boot.sample.controller;

import org.junit.jupiter.api.Test;

/**
 * @author lazycece
 */
public class UploadFileControllerTest {

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyTzBBQlhOeUFEMWpiMjB1YkdGNmVXTmxZMlV1WVhVdVlYQnBMbk53Y21sdVp5NWliMjkwTG5OaGJYQnNaUzVsYm5ScGRIa3VaRzl6TGxWelpYSlRkV0pxWldOMFdNUHZTR2M5eEZVQ0FBRk1BQWgxYzJWeWJtRnRaWFFBRWt4cVlYWmhMMnhoYm1jdlUzUnlhVzVuTzNod2RBQUliR0Y2ZVdObFkyVT0iLCJpc3MiOiJUT0tFTi1JU1NVRVIiLCJleHAiOjE1ODA5OTA1MTYsImlhdCI6MTU4MDk4ODcxNn0.Lb5uaSPuvgPAHXTjD2yhAXgmGkV0ld7Wi6JZeDI6N3E";

    @Test
    public void testUpload() throws Exception {
        String filePath = ClassLoader.getSystemResource("application.yml").getFile();
        HttpHelper.getInstance().token(token).doUpload("/upload/file", filePath, "file", null, String.class);
    }
}
