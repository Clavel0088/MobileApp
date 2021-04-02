package testa.com.firstapp.models.wsModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import testa.com.firstapp.models.Offre;

/**
 * Created by Phael on 26/03/2021.
 */

public class DataResponse implements Parcelable {
    int code;
    String message;
    ArrayList<Offre> data;
    String error;

    public DataResponse() {

    }

    public DataResponse(int code, String message, ArrayList<Offre> data, String error) {
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

    public ArrayList<Offre>  getData() {
        return data;
    }

    public void setData(ArrayList<Offre> data) {
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
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
        dest.writeString(this.error);
    }

    protected DataResponse(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(Offre.CREATOR);
        this.error = in.readString();
    }

    public static final Creator<DataResponse> CREATOR = new Creator<DataResponse>() {
        @Override
        public DataResponse createFromParcel(Parcel source) {
            return new DataResponse(source);
        }

        @Override
        public DataResponse[] newArray(int size) {
            return new DataResponse[size];
        }
    };
}
