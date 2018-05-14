package com.mongodb.analyser;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

public class Report {

    private ObjectId id;
    private Integer dataCenterId;
    private Double averageTemperature;
    private Date createdAt = new Date();

    public Report() {
    }

    public Report(Integer dataCenterId, Double averageTemperature) {
        this.dataCenterId = dataCenterId;
        this.averageTemperature = averageTemperature;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Report setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ObjectId getId() {
        return id;
    }

    public Report setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Integer getDataCenterId() {
        return dataCenterId;
    }

    public Report setDataCenterId(Integer dataCenterId) {
        this.dataCenterId = dataCenterId;
        return this;
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public Report setAverageTemperature(Double averageTemperature) {
        this.averageTemperature = averageTemperature;
        return this;
    }

    @Override
    public String toString() {
        return "Report{dataCenterId=" + dataCenterId + ", averageTemperature=" + averageTemperature + ", createdAt=" + createdAt + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Objects.equals(dataCenterId, report.dataCenterId) && Objects.equals(
                averageTemperature, report.averageTemperature) && Objects.equals(createdAt, report.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCenterId, averageTemperature, createdAt);
    }

}