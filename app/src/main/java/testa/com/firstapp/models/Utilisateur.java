package testa.com.firstapp.models;

import android.os.Parcel;
import android.os.Parcelable;



public class Utilisateur implements Parcelable {
    private int id;
    private String nom;
    private String login;
    private String mdp;
    private String numero;
    private double credit;
    private Operateur operateur;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String login, String mdp, String numero, double credit, Operateur operateur) {
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.mdp = mdp;
        this.numero = numero;
        this.credit = credit;
        this.operateur = operateur;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nom);
        dest.writeString(this.login);
        dest.writeString(this.mdp);
        dest.writeString(this.numero);
        dest.writeDouble(this.credit);
        dest.writeParcelable(this.operateur, flags);
    }

    protected Utilisateur(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.login = in.readString();
        this.mdp = in.readString();
        this.numero = in.readString();
        this.credit = in.readDouble();
        this.operateur = in.readParcelable(Operateur.class.getClassLoader());
    }

    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel source) {
            return new Utilisateur(source);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };
}
