package entity;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {

    private String sendId;
    private String receiveId;
    private String msgType;
    private String msgContent;
    private byte[] file;

    public Message() {
    }

    public Message(String sendId, String receiveId, String msgType, String msgContent) {
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.msgType = msgType;
        this.msgContent = msgContent;
    }
    public Message(String sendId, String receiveId, String msgType, String msgContent, byte[] file) {
        this.sendId = sendId;
        this.receiveId = receiveId;
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.file = file;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }



    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sendId='" + sendId + '\'' +
                ", receiveId='" + receiveId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", file=" + Arrays.toString(file) +
                '}';
    }
}
