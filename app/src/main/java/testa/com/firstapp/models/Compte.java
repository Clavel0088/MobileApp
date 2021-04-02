package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phael on 20/03/2021.
 */

public class Compte implements Parcelable {
    private int id;
    private String code;
    private double solde;
    private Puce puce;

    public Compte() {

    }

    public Compte(int id, String code, double solde, Puce puce) {
        this.id = id;
        this.code = code;
        this.solde = solde;
        this.puce = puce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Puce getPuce() {
        return puce;
    }

    public void setPuce(Puce puce) {
        this.puce = puce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.code);
        dest.writeDouble(this.solde);
        dest.writeParcelable(this.puce, flags);
    }

    protected Compte(Parcel in) {
        this.id = in.readInt();
        this.code = in.readString();
        this.solde = in.readDouble();
        this.puce = in.readParcelable(Puce.class.getClassLoader());
    }

    public static final Parcelable.Creator<Compte> CREATOR = new Parcelable.Creator<Compte>() {
        @Override
        public Compte createFromParcel(Parcel source) {
            return new Compte(source);
        }

        @Override
        public Compte[] newArray(int size) {
            return new Compte[size];
        }
    };
}
