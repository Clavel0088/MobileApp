package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phael on 20/03/2021.
 */

public class Operateur implements Parcelable {
    private int id;
    private String nom;
    private String numBasic;

    public Operateur(int id) {
        this.id = id;
    }

    public Operateur(int id, String nom, String numBasic) {
        this.id = id;
        this.nom = nom;
        this.numBasic = numBasic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumBasic() {
        return numBasic;
    }

    public void setNumBasic(String numBasic) {
        this.numBasic = numBasic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nom);
        dest.writeString(this.numBasic);
    }

    protected Operateur(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.numBasic = in.readString();
    }

    public static final Parcelable.Creator<Operateur> CREATOR = new Parcelable.Creator<Operateur>() {
        @Override
        public Operateur createFromParcel(Parcel source) {
            return new Operateur(source);
        }

        @Override
        public Operateur[] newArray(int size) {
            return new Operateur[size];
        }
    };
}
