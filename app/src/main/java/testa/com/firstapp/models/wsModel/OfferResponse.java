package testa.com.firstapp.models.wsModel;

import java.util.ArrayList;

import testa.com.firstapp.models.Offre;

/**
 * Created by Phael on 26/03/2021.
 */

public class OfferResponse {
    int code;
    String message;
    ArrayList<Offre> data;
    String error;

    public OfferResponse() {
    }

    public OfferResponse(int code, String message, ArrayList<Offre> data, String erreur) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = erreur;
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

    public ArrayList<Offre> getData() {
        return data;
    }

    public void setData(ArrayList<Offre> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String erreur) {
        this.error = erreur;
    }
}
