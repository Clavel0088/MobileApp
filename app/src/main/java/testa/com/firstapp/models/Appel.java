package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Phael on 20/03/2021.
 */

public class Appel implements Parcelable {
    private int id;
    private String dateAppel;
    private double prix;
    private int duree;
   private Utilisateur appelant;
   private Utilisateur appeler;
    //private int appeler;

    public Appel() {
    }

    public Appel(int id, String dateAppel, double prix, int duree, Utilisateur appelant, Utilisateur appeler) {
        this.id = id;
        this.dateAppel = dateAppel;
        this.prix = prix;
        this.duree = duree;
        this.appelant = appelant;
        this.appeler = appeler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateAppel() {
        return dateAppel;
    }

    public void setDateAppel(String dateAppel) {
        this.dateAppel = dateAppel;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Utilisateur getAppelant() {
        return appelant;
    }

    public void setAppelant(Utilisateur appelant) {
        this.appelant = appelant;
    }

    public Utilisateur getAppeler() {
        return appeler;
    }

    public void setAppeler(Utilisateur appeler) {
        this.appeler = appeler;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.dateAppel);
        dest.writeDouble(this.prix);
        dest.writeInt(this.duree);
        dest.writeParcelable(this.appelant, flags);
        dest.writeParcelable(this.appeler, flags);
    }

    protected Appel(Parcel in) {
        this.id = in.readInt();
        this.dateAppel = in.readString();
        this.prix = in.readDouble();
        this.duree = in.readInt();
        this.appelant = in.readParcelable(Utilisateur.class.getClassLoader());
        this.appeler = in.readParcelable(Utilisateur.class.getClassLoader());
    }

    public static final Creator<Appel> CREATOR = new Creator<Appel>() {
        @Override
        public Appel createFromParcel(Parcel source) {
            return new Appel(source);
        }

        @Override
        public Appel[] newArray(int size) {
            return new Appel[size];
        }
    };
}
