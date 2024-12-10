package System;

import Enums.Status;

import java.util.Objects;

public class Request extends Notification {
    private String topic;
    private Boolean isSigned;
    private Status status;

    public Request() {

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getSigned() {
        return isSigned;
    }

    public void setSigned(Boolean signed) {
        isSigned = signed;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Request request = (Request) o;
        return Objects.equals(topic, request.topic) && Objects.equals(isSigned, request.isSigned) && status == request.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), topic, isSigned, status);
    }

    @Override
    public String toString() {
        return "Request{" +
                "topic='" + topic + '\'' +
                ", isSigned=" + isSigned +
                ", status=" + status +
                '}';
    }
}

