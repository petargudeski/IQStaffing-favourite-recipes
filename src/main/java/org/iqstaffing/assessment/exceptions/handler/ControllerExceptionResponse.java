package org.iqstaffing.assessment.exceptions.handler;

class ControllerExceptionResponse {

    private final String detail;

    ControllerExceptionResponse(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

}
