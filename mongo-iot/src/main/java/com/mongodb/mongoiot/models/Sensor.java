package com.mongodb.mongoiot.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Sensor {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private Integer dataCenterId;
    private Integer serverId;
    private String sensorType;
    private Date createdAt = new Date();
    private Float temperature;
    private Integer spaceAvailableGB;
    private Integer spaceUsedGB;
    private Boolean broken = false;

    public ObjectId getId() {
        return id;
    }

    public Sensor setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public Integer getDataCenterId() {
        return dataCenterId;
    }

    public Sensor setDataCenterId(Integer dataCenterId) {
        this.dataCenterId = dataCenterId;
        return this;
    }

    public Integer getServerId() {
        return serverId;
    }

    public Sensor setServerId(Integer serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getSensorType() {
        return sensorType;
    }

    public Sensor setSensorType(String sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Sensor setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Sensor setTemperature(Float temperature) {
        this.temperature = temperature;
        return this;
    }

    public Integer getSpaceAvailableGB() {
        return spaceAvailableGB;
    }

    public Sensor setSpaceAvailableGB(Integer spaceAvailableGB) {
        this.spaceAvailableGB = spaceAvailableGB;
        return this;
    }

    public Integer getSpaceUsedGB() {
        return spaceUsedGB;
    }

    public Sensor setSpaceUsedGB(Integer spaceUsedGB) {
        this.spaceUsedGB = spaceUsedGB;
        return this;
    }

    public Boolean getBroken() {
        return broken;
    }

    public Sensor setBroken(Boolean broken) {
        this.broken = broken;
        return this;
    }

    @Override
    public String toString() {
        return "Sensor{" + "id=" + id + ", dataCenterId=" + dataCenterId + ", serverId=" + serverId + ", sensorType='" + sensorType + '\'' + ", createdAt=" + createdAt + ", temperature=" + temperature + ", spaceAvailableGB=" + spaceAvailableGB + ", spaceUsedGB=" + spaceUsedGB + ", broken=" + broken + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Sensor sensor = (Sensor) o;
        return Objects.equals(id, sensor.id) && Objects.equals(dataCenterId, sensor.dataCenterId) && Objects.equals(serverId,
                                                                                                                    sensor.serverId) && Objects
                .equals(sensorType, sensor.sensorType) && Objects.equals(createdAt, sensor.createdAt) && Objects.equals(
                temperature, sensor.temperature) && Objects.equals(spaceAvailableGB, sensor.spaceAvailableGB) && Objects.equals(
                spaceUsedGB, sensor.spaceUsedGB) && Objects.equals(broken, sensor.broken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCenterId, serverId, sensorType, createdAt, temperature, spaceAvailableGB, spaceUsedGB,
                            broken);
    }

}