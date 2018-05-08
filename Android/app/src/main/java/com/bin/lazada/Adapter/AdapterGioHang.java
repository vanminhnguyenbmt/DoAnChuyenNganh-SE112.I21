package com.bin.lazada.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.lazada.Model.GioHang.ModelGioHang;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterGioHang extends RecyclerView.Adapter<AdapterGioHang.ViewHolderGioHang> {

    Context context;
    List<SanPham> sanPhamList;
    ModelGioHang modelGioHang;

    public AdapterGioHang(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        modelGioHang = new ModelGioHang();
        modelGioHang.MoKetNoiSQL(context);
    }

    public class ViewHolderGioHang extends RecyclerView.ViewHolder {

        TextView txtTenTieuDeGioHang, txtGiaTienGioHang, txtSoLuongSPGioHang;
        ImageView imgHinhGioHang, imgXoaSanPhamGioHang;
        ImageButton imgTangSoLuongSPGioHang, imgGiamSoLuongSPGioHang;

        public ViewHolderGioHang(View itemView) {
            super(itemView);

            txtTenTieuDeGioHang = (TextView)itemView.findViewById(R.id.txtTieuDeGioHang);
            txtGiaTienGioHang = (TextView)itemView.findViewById(R.id.txtGiaGioHang);
            imgHinhGioHang = (ImageView) itemView.findViewById(R.id.imgHinhGioHang);
            imgXoaSanPhamGioHang = (ImageView) itemView.findViewById(R.id.imgXoaSanPham);
            txtSoLuongSPGioHang = (TextView) itemView.findViewById(R.id.txtSoLuongSPTrongGioHang);
            imgTangSoLuongSPGioHang = (ImageButton) itemView.findViewById(R.id.imgTangSoLuongSPTrongGioHang);
            imgGiamSoLuongSPGioHang = (ImageButton) itemView.findViewById(R.id.imgGiamSoLuongSPTrongGioHang);
        }
    }

    @NonNull
    @Override
    public ViewHolderGioHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout_giohang, parent, false);

        ViewHolderGioHang viewHolderGioHang = new ViewHolderGioHang(view);

        return viewHolderGioHang;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderGioHang holder, final int position) {
        final SanPham sanPham = sanPhamList.get(position);

        holder.txtTenTieuDeGioHang.setText(sanPham.getTENSP());

        NumberFormat numberFormat = new DecimalFormat("###,###");
        String gia = numberFormat.format(sanPham.getGIA()).toString();
        holder.txtGiaTienGioHang.setText(gia + " VNĐ ");

        byte[] hinhsanpham = sanPham.getHinhgiohang();
        Bitmap bitmapHinhGioHang = BitmapFactory.decodeByteArray(hinhsanpham, 0, hinhsanpham.length);
        holder.imgHinhGioHang.setImageBitmap(bitmapHinhGioHang);

        holder.imgXoaSanPhamGioHang.setTag(sanPham.getMASP());
        holder.imgXoaSanPhamGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelGioHang modelGioHang = new ModelGioHang();
                modelGioHang.MoKetNoiSQL(context);
                modelGioHang.XoaSanPhamTrongGioHang((int) v.getTag());
                sanPhamList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.txtSoLuongSPGioHang.setText(String.valueOf(sanPham.getSOLUONG()));

        holder.imgTangSoLuongSPGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(holder.txtSoLuongSPGioHang.getText().toString());
                int soluongtonkho = sanPham.getSOLUONGTONKHO();

                if(soluong < soluongtonkho) {
                    soluong++;
                }else {
                    Toast.makeText(context, "Đã quá số lượng tồn kho trong cửa hàng !", Toast.LENGTH_SHORT).show();
                }
                modelGioHang.CapNhapSoLuongSanPhamGioHang(sanPham.getMASP(),soluong);
                holder.txtSoLuongSPGioHang.setText(String.valueOf(soluong));
            }
        });

        holder.imgGiamSoLuongSPGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(holder.txtSoLuongSPGioHang.getText().toString());
                if(soluong > 1) {
                    soluong--;
                }
                modelGioHang.CapNhapSoLuongSanPhamGioHang(sanPham.getMASP(),soluong);
                holder.txtSoLuongSPGioHang.setText(String.valueOf(soluong));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }
}
