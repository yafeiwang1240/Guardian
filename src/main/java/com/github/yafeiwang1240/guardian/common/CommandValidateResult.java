package com.github.yafeiwang1240.guardian.common;

import com.github.yafeiwang1240.obrien.lang.Lists;

import java.util.ArrayList;
import java.util.List;

public class CommandValidateResult {

    public enum Status{
        SUCCEED,
        FAILED,
    }

    private Status status;
    private List<String> message = Lists.create(ArrayList::new);

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void add(String message) {
        this.message.add(message);
    }
}
