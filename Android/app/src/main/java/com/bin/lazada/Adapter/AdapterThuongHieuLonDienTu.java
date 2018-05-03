package com.bin.lazada.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bin.lazada.ObjectClass.ThuongHieu;
import com.bin.lazada.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterThuongHieuLonDienTu extends RecyclerView.Adapter<AdapterThuongHieuLonDienTu.ViewHolderThuongHieuLon> {

    Context context;
    List<ThuongHieu> thuongHieus;

    public AdapterThuongHieuLonDienTu(Context context, List<ThuongHieu> thuongHieus) {
        this.context = context;
        this.thuongHieus = thuongHieus;
    }

    public class ViewHolderThuongHieuLon extends RecyclerView.ViewHolder {
        ImageView imgLogoThuongHieuLon;

        public ViewHolderThuongHieuLon(View itemView) {
            super(itemView);

            imgLogoThuongHieuLon = (ImageView) itemView.findViewById(R.id.imgLogoTopThuongHieuLon);
        }
    }

    @NonNull
    @Override
    public ViewHolderThuongHieuLon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout_recycler_topthuonghieulon_dientu, parent, false);

        ViewHolderThuongHieuLon viewHolderThuongHieuLon = new ViewHolderThuongHieuLon(view);

        return viewHolderThuongHieuLon;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderThuongHieuLon holder, int position) {
        ThuongHieu thuongHieu = thuongHieus.get(position);

        Picasso.get().load(thuongHieu.getHINHTHUONGHIEU()).resize(120, 60).centerInside().into(holder.imgLogoThuongHieuLon);
    }

    @Override
    public int getItemCount() {
        return thuongHieus.size();
    }
}
