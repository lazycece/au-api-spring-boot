package com.lazycece.au.api.spring.boot.sample.entity.dos;

import com.lazycece.au.api.token.Subject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lazycece
 */
@Getter
@Setter
public class UserSubject implements Subject {
    private String username;
}
