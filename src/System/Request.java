package System;

import Enums.Status;
import java.util.Objects;
import java.util.UUID; // Import for generating unique IDs

public class Request extends Notification {
    private String id;
    private String topic;
    private Boolean isSigned;
    private Status status;

    public Request() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Request(String topic, Boolean isSigned, Status status) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID for the request
        this.topic = topic;
        this.isSigned = isSigned;
        this.status = status;
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
        return Objects.equals(id, request.id) &&
                Objects.equals(topic, request.topic) &&
                Objects.equals(isSigned, request.isSigned) &&
                status == request.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, topic, isSigned, status);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", isSigned=" + isSigned +
                ", status=" + status +
                '}';
    }
}
