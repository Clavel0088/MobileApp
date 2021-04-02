package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phael on 20/03/2021.
 */

public class Depot implements Parcelable {
    private int id;
    private double frais;
    private Compte compte;
    private String date;
    private double montant;

    public Depot() {
    }

    public Depot(int id, double frais, Compte compte, String date, double montant) {
        this.id = id;
        this.frais = frais;
        this.compte = compte;
        this.date = date;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.frais);
        dest.writeParcelable(this.compte, flags);
        dest.writeString(this.date);
        dest.writeDouble(this.montant);
    }

    protected Depot(Parcel in) {
        this.id = in.readInt();
        this.frais = in.readDouble();
        this.compte = in.readParcelable(Compte.class.getClassLoader());
        this.date = in.readString();
        this.montant = in.readDouble();
    }

    public static final Parcelable.Creator<Depot> CREATOR = new Parcelable.Creator<Depot>() {
        @Override
        public Depot createFromParcel(Parcel source) {
            return new Depot(source);
        }

        @Override
        public Depot[] newArray(int size) {
            return new Depot[size];
        }
    };
}
