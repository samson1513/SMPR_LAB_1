package com.example.samson.smpr_lab_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.samson.smpr_lab_1.custom.SquareItem;

import java.util.ArrayList;


public class MatrixAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> list;

    public MatrixAdapter(Context context, ArrayList<Integer> list) {
        this.context = context;
        this.list = list;
    }

    public void updateMatrix(ArrayList<Integer> newList){
        list = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cell_matrix, null);
            holder = new ViewHolder();
            holder.init(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(getItem(position) == 1)
            holder.selectCell();
        else
            holder.unselectCell();

        return convertView;
    }

    class ViewHolder{
        SquareItem button;

        public void init(View view){
            button = (SquareItem) view.findViewById(R.id.btnCell);
        }

        public void selectCell(){
            button.setBackgroundColor(context.getResources().getColor(R.color.bg_selected_cell));
        }

        public void unselectCell(){
            button.setBackgroundColor(context.getResources().getColor(R.color.bg_unselected_cell));
        }
    }
}
