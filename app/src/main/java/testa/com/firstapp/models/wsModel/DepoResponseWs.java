package testa.com.firstapp.models.wsModel;

import android.os.Parcel;
import android.os.Parcelable;

import testa.com.firstapp.models.Depot;

/**
 * Created by Phael on 31/03/2021.
 */

public class DepoResponseWs implements Parcelable {
    private  int code;
    private String message;
    private Depot data;
    private boolean error;

    public DepoResponseWs() {
    }

    public DepoResponseWs(int code, String message, Depot data, boolean error) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = error;
    }

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

    public Depot getData() {
        return data;
    }

    public void setData(Depot data) {
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

    protected DepoResponseWs(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(Depot.class.getClassLoader());
        this.error = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DepoResponseWs> CREATOR = new Parcelable.Creator<DepoResponseWs>() {
        @Override
        public DepoResponseWs createFromParcel(Parcel source) {
            return new DepoResponseWs(source);
        }

        @Override
        public DepoResponseWs[] newArray(int size) {
            return new DepoResponseWs[size];
        }
    };
}
