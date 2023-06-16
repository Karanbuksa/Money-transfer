package com.example.moneytransferproj.dataclasses;

import java.util.Objects;

public class ConfirmOperation {

    private String code;

    public ConfirmOperation(String code) {
        this.code = code;
    }

    public ConfirmOperation() {
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfirmOperation that)) return false;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "ConfirmOperation{" +
                "code='" + code + '\'' +
                '}';
    }
}
