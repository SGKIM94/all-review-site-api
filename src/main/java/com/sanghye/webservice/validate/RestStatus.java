package com.sanghye.webservice.validate;

public class RestStatus {
    private boolean status;

    RestStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RestResponse [status=" + status + "]";
    }
}
