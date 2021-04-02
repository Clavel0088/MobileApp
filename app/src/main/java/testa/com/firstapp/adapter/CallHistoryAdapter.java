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

/**
 * Created by Phael on 20/03/2021.
 */

public class CallHistoryAdapter extends BaseAdapter {
    ArrayList<Appel> calls;
    Context context;
    private LayoutInflater inflater;

    public CallHistoryAdapter(Context context,ArrayList<Appel> calls) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.calls = calls;
    }

    @Override
    public int getCount() {
        return this.calls.size();
    }

    @Override
    public Appel getItem(int i) {
        return (Appel) this.calls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Appel appel = (Appel) getItem(i);

        if (appel != null) {

            final CallHolder holder;

            if (view == null) {

                view = inflater.inflate(R.layout.item_call, null);

                holder = new CallHolder();
                holder.textViewDate = (TextView) view.findViewById(R.id.textViewDate);
                holder.textNumero = (TextView) view.findViewById(R.id.textNumero);

                view.setTag(holder);

            } else {

                holder = (CallHolder) view.getTag();
            }
            if(appel.getDateAppel()!=null){
                holder.textViewDate.setText(appel.getDateAppel());

            }
            if(appel.getAppeler()!=null && appel.getAppeler().getNumero()!=null) {

                holder.textNumero.setText(appel.getAppeler().getNumero());
            }
        }
        return view;

    }

        private static class CallHolder{

            TextView textViewDate ;
            TextView textNumero ;
        }

}
