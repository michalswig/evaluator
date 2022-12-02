package com.mike.evaluator;

public class VariableValue {
    private String value;
    private DataType dataType;

    public VariableValue(String value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public DataType getDataType() {
        return dataType;
    }
}
