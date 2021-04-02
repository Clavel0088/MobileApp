package testa.com.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import testa.com.firstapp.R;
import testa.com.firstapp.models.Appel;
import testa.com.firstapp.models.Offre;

/**
 * Created by Phael on 21/03/2021.
 */

public  class OfferAdapter extends BaseAdapter {
    ArrayList<Offre> offers;
    Context context;
    private LayoutInflater inflater;
    int type;
    public OfferAdapter(ArrayList<Offre> offers, Context context,int type) {
        this.offers = offers;
        this.type = type;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.offers.size();
    }

    @Override
    public Offre getItem(int i) {
        return (Offre)this.offers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Offre offre = (Offre) getItem(i);

        if (offre != null) {

            final OfferHolder holder;

            if (view == null) {

                view = inflater.inflate(R.layout.item_offer, null);

                holder = new OfferHolder();
                holder.textOfferName = (TextView) view.findViewById(R.id.textOfferName);
                holder.textViewDuration=(TextView) view.findViewById(R.id.textViewDuration);

                view.setTag(holder);


            } else {

                holder = (OfferHolder) view.getTag();
            }
            //holder.textNumero.setText(appel.getPuce().getNumero());
            holder.textOfferName.setText(offre.getNom());
            if (type==0) {
                holder.textViewDuration.setText(String.valueOf(offre.getCodeOffre()));
            }
            else{
                holder.textViewDuration.setText(String.valueOf(offre.getPrix()));
            }
        }
        return view;

    }

    public static class OfferHolder {
        TextView textOfferName;
        TextView textViewDuration;
    }
}
