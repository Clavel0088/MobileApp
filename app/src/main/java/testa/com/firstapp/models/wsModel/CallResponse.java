package testa.com.firstapp.models.wsModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import testa.com.firstapp.models.Appel;

/**
 * Created by Phael on 27/03/2021.
 */

public class CallResponse implements Parcelable  {
    //mila hovaina httpCode io code io
    int httpCode;
    String message;
    ArrayList<Appel> data;
    String error;

    public CallResponse() {
    }

    public CallResponse(int code, String message, ArrayList<Appel> data, String error) {
        this.httpCode = code;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public int getCode() {
        return httpCode;
    }

    public void setCode(int code) {
        this.httpCode = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Appel> getData() {
        return data;
    }

    public void setData(ArrayList<Appel> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.httpCode);
        dest.writeString(this.message);
        dest.writeList(this.data);
        dest.writeString(this.error);
    }

    protected CallResponse(Parcel in) {
        this.httpCode = in.readInt();
        this.message = in.readString();
        this.data = new ArrayList<Appel>();
        in.readList(this.data, Appel.class.getClassLoader());
        this.error = in.readString();
    }

    public static final Parcelable.Creator<CallResponse> CREATOR = new Parcelable.Creator<CallResponse>() {
        @Override
        public CallResponse createFromParcel(Parcel source) {
            return new CallResponse(source);
        }

        @Override
        public CallResponse[] newArray(int size) {
            return new CallResponse[size];
        }
    };
}
