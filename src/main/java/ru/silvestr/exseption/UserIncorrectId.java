package ru.silvestr.exseption;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserIncorrectId {

    private String info;

    public UserIncorrectId() {
    }

    public UserIncorrectId(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
