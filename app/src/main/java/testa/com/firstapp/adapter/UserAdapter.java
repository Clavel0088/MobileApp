package testa.com.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import testa.com.firstapp.R;
import testa.com.firstapp.models.Utilisateur;

/**
 * Created by Phael on 02/04/2021.
 */

public class UserAdapter  extends BaseAdapter {
    ArrayList<Utilisateur> calls;
    Context context;
    private LayoutInflater inflater;

    public UserAdapter(Context context,ArrayList<Utilisateur> calls) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.calls = calls;
    }

    @Override
    public int getCount() {
        return this.calls.size();
    }

    @Override
    public Utilisateur getItem(int i) {
        return (Utilisateur) this.calls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Utilisateur utilisateur = (Utilisateur) getItem(i);

        if (utilisateur != null) {

            final CallHolder holder;

            if (view == null) {

                view = inflater.inflate(R.layout.item_utilisateur, null);

                holder = new CallHolder();
                holder.textViewName = (TextView) view.findViewById(R.id.textViewName);
                holder.textNumero = (TextView) view.findViewById(R.id.textNumero);

                view.setTag(holder);

            } else {

                holder = (CallHolder) view.getTag();
            }
            if((utilisateur.getNom()!=null)){
                holder.textViewName.setText(utilisateur.getNom());

            }
            if(utilisateur.getNumero()!=null) {

                holder.textNumero.setText(utilisateur.getNumero());
            }
        }
        return view;

    }

    private static class CallHolder{

        TextView textViewName ;
        TextView textNumero ;
    }

}
