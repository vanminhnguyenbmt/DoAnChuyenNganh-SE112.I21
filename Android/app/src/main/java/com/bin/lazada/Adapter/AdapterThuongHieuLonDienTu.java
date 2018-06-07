package com.bin.lazada.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bin.lazada.ObjectClass.ThuongHieu;
import com.bin.lazada.R;
import com.bin.lazada.View.HienThiSanPhamTheoDanhMuc.HienThiSanPhamTheoDanhMucActivity;
import com.squareup.picasso.Callback;
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
        ProgressBar progressBar;

        public ViewHolderThuongHieuLon(View itemView) {
            super(itemView);

            imgLogoThuongHieuLon = (ImageView) itemView.findViewById(R.id.imgLogoTopThuongHieuLon);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_download);
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
    public void onBindViewHolder(@NonNull final ViewHolderThuongHieuLon holder, int position) {
        final ThuongHieu thuongHieu = thuongHieus.get(position);
        Picasso.get().load(thuongHieu.getHINHTHUONGHIEU()).resize(120, 60).centerInside().into(holder.imgLogoThuongHieuLon, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        holder.imgLogoThuongHieuLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HienThiSanPhamTheoDanhMucActivity hienThiSanPhamTheoDanhMucActivity = new HienThiSanPhamTheoDanhMucActivity();

                Bundle bundle = new Bundle();
                bundle.putInt("MALOAI", thuongHieu.getMATHUONGHIEU());
                bundle.putInt("MALOAISP", thuongHieu.getChiTietThuongHieu().getMALOAISP());
                bundle.putBoolean("KIEMTRA", true);
                bundle.putString("TENLOAI", thuongHieu.getTENTHUONGHIEU());
                bundle.putString("CHECKADAPTER", "thuonghieulondientu");
                hienThiSanPhamTheoDanhMucActivity.setArguments(bundle);

                fragmentTransaction.addToBackStack("TrangChuActivity");
                fragmentTransaction.replace(R.id.themFragment, hienThiSanPhamTheoDanhMucActivity);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return thuongHieus.size();
    }
}
