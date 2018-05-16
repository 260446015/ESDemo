package com.ydw.es.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author ydw
 */
@Data
@Component
public class User {
    private String name;
    private int age;
}
