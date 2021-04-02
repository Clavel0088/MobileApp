package testa.com.firstapp.models.wsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import testa.com.firstapp.models.Utilisateur;


public class LoginResponseWs implements Parcelable {
    int code;
    String message;
    Utilisateur data;
    boolean error;

    public LoginResponseWs(int code, String message, Utilisateur data, boolean error) {
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

    public Utilisateur getData() {
        return data;
    }

    public void setData(Utilisateur data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public static Creator<LoginResponseWs> getCREATOR() {
        return CREATOR;
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

    protected LoginResponseWs(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(Utilisateur.class.getClassLoader());
        this.error = in.readByte() != 0;
    }

    public static final Creator<LoginResponseWs> CREATOR = new Creator<LoginResponseWs>() {
        @Override
        public LoginResponseWs createFromParcel(Parcel source) {
            return new LoginResponseWs(source);
        }

        @Override
        public LoginResponseWs[] newArray(int size) {
            return new LoginResponseWs[size];
        }
    };
}
