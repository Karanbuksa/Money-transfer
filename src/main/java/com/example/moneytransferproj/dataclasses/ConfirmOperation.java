package com.example.moneytransferproj.dataclasses;

import java.util.Objects;

public class ConfirmOperation {
    private String operationId;
    private String code;

    public ConfirmOperation(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public ConfirmOperation() {
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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
        return Objects.equals(operationId, that.operationId) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, code);
    }

    @Override
    public String toString() {
        return "ConfirmOperation{" +
                "operationId='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
