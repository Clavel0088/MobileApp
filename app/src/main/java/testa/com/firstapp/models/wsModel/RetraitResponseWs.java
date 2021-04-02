package testa.com.firstapp.models.wsModel;

import android.os.Parcel;
import android.os.Parcelable;

import testa.com.firstapp.models.Retrait;

/**
 * Created by Phael on 31/03/2021.
 */

public class RetraitResponseWs implements Parcelable {
    private  int code;
    private String message;
    private Retrait data;
    private boolean error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Retrait getData() {
        return data;
    }

    public void setData(Retrait data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeParcelable(this.data, flags);
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
    }

    public RetraitResponseWs() {
    }

    protected RetraitResponseWs(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(Retrait.class.getClassLoader());
        this.error = in.readByte() != 0;
    }

    public static final Parcelable.Creator<RetraitResponseWs> CREATOR = new Parcelable.Creator<RetraitResponseWs>() {
        @Override
        public RetraitResponseWs createFromParcel(Parcel source) {
            return new RetraitResponseWs(source);
        }

        @Override
        public RetraitResponseWs[] newArray(int size) {
            return new RetraitResponseWs[size];
        }
    };
}
