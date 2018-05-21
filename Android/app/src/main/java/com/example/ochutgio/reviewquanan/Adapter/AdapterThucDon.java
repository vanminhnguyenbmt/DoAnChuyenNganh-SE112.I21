package com.example.ochutgio.reviewquanan.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Model.ThucDonModel;
import com.example.ochutgio.reviewquanan.R;

import java.util.List;

/**
 * Created by ochutgio on 5/8/2018.
 */

public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.ViewHolder> {

    Context context;
    List<ThucDonModel> thucDonModelList;

    public AdapterThucDon(Context context, List<ThucDonModel> thucDonModelList){
        this.context = context;
        this.thucDonModelList = thucDonModelList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvMenuPlus;
        ImageView imvMenuDown;
        TextView txtTenThucDon;
        RecyclerView recyclerMonAn;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenThucDon = (TextView) itemView.findViewById(R.id.txtTenThucDon);
            recyclerMonAn = (RecyclerView) itemView.findViewById(R.id.recyclerMonAn);
            imvMenuDown = (ImageView) itemView.findViewById(R.id.imvMenuDown);
            imvMenuPlus = (ImageView) itemView.findViewById(R.id.imvMenuPlus);
        }
    }

    @Override
    public AdapterThucDon.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_thucdon, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterThucDon.ViewHolder holder, int position) {
        ThucDonModel thucDonModel = thucDonModelList.get(position);
        holder.txtTenThucDon.setText(thucDonModel.getTenthucdon());
        holder.recyclerMonAn.setLayoutManager(new LinearLayoutManager(context));
        AdapterMonAn adapterMonAn = new AdapterMonAn(context, thucDonModel.getMonAnModelList());
        holder.recyclerMonAn.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();

        holder.imvMenuPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imvMenuPlus.setVisibility(View.GONE);
                holder.imvMenuDown.setVisibility(View.VISIBLE);
                holder.recyclerMonAn.setVisibility(View.VISIBLE);
            }
        });

        holder.imvMenuDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.recyclerMonAn.setVisibility(View.GONE);
                holder.imvMenuDown.setVisibility(View.GONE);
                holder.imvMenuPlus.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thucDonModelList.size();
    }

}
