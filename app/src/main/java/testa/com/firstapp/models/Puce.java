package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phael on 20/03/2021.
 */

public class Puce implements Parcelable {
    private int id;
    private String numero;
    private double credit;
    private Operateur operateur;
    private Utilisateur utilisateur;

    public Puce() {
    }

    public Puce(int id, String numero, double credit, Operateur operateur, Utilisateur utilisateur) {
        this.id = id;
        this.numero = numero;
        this.credit = credit;
        this.operateur = operateur;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public Operateur getOperateur() {
        return operateur;
    }

    public void setOperateur(Operateur operateur) {
        this.operateur = operateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.numero);
        dest.writeDouble(this.credit);
        dest.writeParcelable(this.operateur, flags);
        dest.writeParcelable(this.utilisateur, flags);
    }

    protected Puce(Parcel in) {
        this.id = in.readInt();
        this.numero = in.readString();
        this.credit = in.readDouble();
        this.operateur = in.readParcelable(Operateur.class.getClassLoader());
        this.utilisateur = in.readParcelable(Utilisateur.class.getClassLoader());
    }

    public static final Parcelable.Creator<Puce> CREATOR = new Parcelable.Creator<Puce>() {
        @Override
        public Puce createFromParcel(Parcel source) {
            return new Puce(source);
        }

        @Override
        public Puce[] newArray(int size) {
            return new Puce[size];
        }
    };
}
