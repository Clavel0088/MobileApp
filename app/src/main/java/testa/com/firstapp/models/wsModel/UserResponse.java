package testa.com.firstapp.models.wsModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import testa.com.firstapp.models.Utilisateur;

/**
 * Created by Phael on 02/04/2021.
 */

public class UserResponse implements Parcelable {
    int httpCode;
    String message;
    ArrayList<Utilisateur> data;
    String error;

    public UserResponse(int httpCode, String message, ArrayList<Utilisateur> data, String error) {
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Utilisateur> getData() {
        return data;
    }

    public void setData(ArrayList<Utilisateur> data) {
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
        dest.writeTypedList(this.data);
        dest.writeString(this.error);
    }

    protected UserResponse(Parcel in) {
        this.httpCode = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(Utilisateur.CREATOR);
        this.error = in.readString();
    }

    public static final Parcelable.Creator<UserResponse> CREATOR = new Parcelable.Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel source) {
            return new UserResponse(source);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };
}

