package com.example.writetext.entity;

public class MeasBean {


    private int measId;
    private String measName;
    private String deviceName;
    private String channelName;
    private String identificationCode;        //PMIS DeviceID
    private int cetDeviceId;
    private int channelId;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public int getCetDeviceId() {
        return cetDeviceId;
    }

    public void setCetDeviceId(int cetDeviceId) {
        this.cetDeviceId = cetDeviceId;
    }

    public MeasBean() {
    }


    public int getMeasId() {
        return measId;
    }

    public void setMeasId(int measId) {
        this.measId = measId;
    }

    public String getMeasName() {
        return measName;
    }

    public void setMeasName(String measName) {
        this.measName = measName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
