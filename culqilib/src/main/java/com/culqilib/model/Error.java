package com.culqilib.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Error implements Parcelable {

    private String code;
    private String merchantMessage;
    private String userMessage;
    private String type;
    private String param;
    private String object;

    public Error() {
    }

    public Error(String code, String merchantMessage, String userMessage, String type, String param, String object) {
        this.code = code;
        this.merchantMessage = merchantMessage;
        this.userMessage = userMessage;
        this.type = type;
        this.param = param;
        this.object = object;
    }

    private Error(Parcel in) {
        code = in.readString();
        merchantMessage = in.readString();
        userMessage = in.readString();
        type = in.readString();
        param = in.readString();
        object = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(merchantMessage);
        dest.writeString(userMessage);
        dest.writeString(type);
        dest.writeString(param);
        dest.writeString(object);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Error> CREATOR = new Creator<Error>() {
        @Override
        public Error createFromParcel(Parcel in) {
            return new Error(in);
        }

        @Override
        public Error[] newArray(int size) {
            return new Error[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMerchantMessage() {
        return merchantMessage;
    }

    public void setMerchantMessage(String merchantMessage) {
        this.merchantMessage = merchantMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
