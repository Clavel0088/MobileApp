package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phael on 20/03/2021.
 */

public class Offre implements Parcelable {
    private int id;
    private String nom;
    private double prix;
    private int duree;
    private String codeOffre;

    public Offre() {
    }


    public Offre(int id, String name, double prix, int duree, String codeOffre) {
        this.id = id;
        this.nom = name;
        this.prix = prix;
        this.duree = duree;
        this.codeOffre = codeOffre;
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

    public void setName(String name) {
        this.nom = name;
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

    public String getCodeOffre() {
        return codeOffre;
    }

    public void setCodeOffre(String codeOffre) {
        this.codeOffre = codeOffre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nom);
        dest.writeDouble(this.prix);
        dest.writeInt(this.duree);
        dest.writeString(this.codeOffre);
    }

    protected Offre(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.prix = in.readDouble();
        this.duree = in.readInt();
        this.codeOffre = in.readString();
    }

    public static final Creator<Offre> CREATOR = new Creator<Offre>() {
        @Override
        public Offre createFromParcel(Parcel source) {
            return new Offre(source);
        }

        @Override
        public Offre[] newArray(int size) {
            return new Offre[size];
        }
    };
}
