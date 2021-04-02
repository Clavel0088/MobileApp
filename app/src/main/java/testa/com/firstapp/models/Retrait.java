package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phael on 31/03/2021.
 */

public class Retrait implements Parcelable {
    public int id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }

    public Retrait() {
    }

    protected Retrait(Parcel in) {
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Retrait> CREATOR = new Parcelable.Creator<Retrait>() {
        @Override
        public Retrait createFromParcel(Parcel source) {
            return new Retrait(source);
        }

        @Override
        public Retrait[] newArray(int size) {
            return new Retrait[size];
        }
    };
}
