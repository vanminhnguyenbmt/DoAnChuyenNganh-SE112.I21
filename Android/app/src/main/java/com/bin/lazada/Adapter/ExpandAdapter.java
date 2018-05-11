package com.bin.lazada.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bin.lazada.Model.TrangChu.XuLyMenu.XuLyJSONMenu;
import com.bin.lazada.ObjectClass.LoaiSanPham;
import com.bin.lazada.R;
import com.bin.lazada.View.HienThiSanPhamTheoDanhMuc.HienThiSanPhamTheoDanhMucActivity;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {

    Context context;
    List<LoaiSanPham> loaiSanPhams;
    ViewHolderMenu viewHolderMenu;

    public ExpandAdapter(Context context, List<LoaiSanPham> loaiSanPhams)
    {
        this.context = context;
        this.loaiSanPhams = loaiSanPhams;
        XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();

        int count = loaiSanPhams.size();
        for(int i = 0; i < count; i++)
        {
            int maloaisp = loaiSanPhams.get(i).getMALOAISP();
            loaiSanPhams.get(i).setListCon(xuLyJSONMenu.LaySanPhamTheoMaLoaiCha(maloaisp));
        }
    }

    @Override
    public int getGroupCount() {
        return loaiSanPhams.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(loaiSanPhams.get(groupPosition).getListCon().size() != 0) {
            return 1;
        }else {
            return  0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return loaiSanPhams.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return loaiSanPhams.get(groupPosition).getListCon().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return loaiSanPhams.get(groupPosition).getMALOAISP();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return loaiSanPhams.get(groupPosition).getListCon().get(childPosition).getMALOAISP();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public class ViewHolderMenu {
        TextView txtTenLoaiSP;
        ImageView hinhMenu;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View viewGroupCha = convertView;
        if(viewGroupCha == null) {
            viewHolderMenu = new ViewHolderMenu();

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewGroupCha = layoutInflater.inflate(R.layout.custom_layout_group_cha, parent, false);

            viewHolderMenu.hinhMenu = viewGroupCha.findViewById(R.id.imMenuPlus);
            viewHolderMenu.txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);

            viewGroupCha.setTag(viewHolderMenu);
        }else {
            viewHolderMenu = (ViewHolderMenu) viewGroupCha.getTag();
        }

        viewHolderMenu.txtTenLoaiSP.setText(loaiSanPhams.get(groupPosition).getTENLOAISP());

        int demsanpham = loaiSanPhams.get(groupPosition).getListCon().size();

        if(demsanpham > 0 ) {
            viewHolderMenu.hinhMenu.setVisibility(View.VISIBLE);
        }else {
            viewHolderMenu.hinhMenu.setVisibility(View.INVISIBLE);
        }

        if(isExpanded) {
            viewHolderMenu.hinhMenu.setImageResource(R.drawable.ic_remove_black_24dp);
            viewGroupCha.setBackgroundResource(R.color.colorGray);
        }else {
            viewHolderMenu.hinhMenu.setImageResource(R.drawable.ic_add_black_24dp);
            viewGroupCha.setBackgroundResource(R.color.colorWhite);
        }

        viewGroupCha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("maloaisp", loaiSanPhams.get(groupPosition).getTENLOAISP() + " - " + loaiSanPhams.get(groupPosition).getMALOAISP());

                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                HienThiSanPhamTheoDanhMucActivity hienThiSanPhamTheoDanhMucActivity = new HienThiSanPhamTheoDanhMucActivity();

                Bundle bundle = new Bundle();
                bundle.putInt("MALOAI", loaiSanPhams.get(groupPosition).getMALOAISP());
                bundle.putBoolean("KIEMTRA", false);
                bundle.putString("TENLOAI", loaiSanPhams.get(groupPosition).getTENLOAISP());

                hienThiSanPhamTheoDanhMucActivity.setArguments(bundle);

                fragmentTransaction.addToBackStack("TrangChuActivity");
                fragmentTransaction.replace(R.id.themFragment, hienThiSanPhamTheoDanhMucActivity);
                fragmentTransaction.commit();

                return false;
            }
        });
        return viewGroupCha;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewGroupCon = layoutInflater.inflate(R.layout.custom_layout_group_con, parent, false);
//
//        ExpandableListView expandableListView = (ExpandableListView) viewGroupCon.findViewById(R.id.epMenuCon);

        SecondExpandalbe secondExpandalbe = new SecondExpandalbe(context);
        //gọi lại phương thức khởi tạo để làm đa cấp menu
        ExpandAdapter secondAdapter = new ExpandAdapter(context, loaiSanPhams.get(groupPosition).getListCon());
        secondExpandalbe.setAdapter(secondAdapter);

        secondExpandalbe.setGroupIndicator(null);
        notifyDataSetChanged();

        return secondExpandalbe;
    }

    public class SecondExpandalbe extends ExpandableListView {

        public SecondExpandalbe(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            int width = size.x;
            int height = size.y;

            Log.d("size",width + " - " + height);

            //widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

//    class xử lý menu 3 cấp
//        public class SecondAdapter extends BaseExpandableListAdapter{
//        List<LoaiSanPham> listCon;
//
//        public SecondAdapter(List<LoaiSanPham> listCon){
//            this.listCon = listCon;
//
//            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
//
//            int count = listCon.size();
//            for(int i = 0; i < count; i++)
//            {
//                int maloaisp = listCon.get(i).getMALOAISP();
//                listCon.get(i).setListCon(xuLyJSONMenu.LaySanPhamTheoMaLoaiCha(maloaisp));
//            }
//        }
//        @Override
//        public int getGroupCount() {
//            return listCon.size();
//        }
//
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return listCon.get(groupPosition).getListCon().size();
//        }
//
//        @Override
//        public Object getGroup(int groupPosition) {
//            return listCon.get(groupPosition);
//        }
//
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            return listCon.get(groupPosition).getListCon().get(childPosition);
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return listCon.get(groupPosition).getMALOAISP();
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return listCon.get(groupPosition).getListCon().get(childPosition).getMALOAISP();
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return false;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View viewGroupCha = layoutInflater.inflate(R.layout.custom_layout_group_cha, parent, false);
//            TextView txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);
//            txtTenLoaiSP.setText(listCon.get(groupPosition).getTENLOAISP());
//
//            return viewGroupCha;
//        }
//
//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//            TextView tv = new TextView(context);
//            tv.setText(listCon.get(groupPosition).getListCon().get(childPosition).getTENLOAISP());
//            tv.setPadding(15, 5 , 5, 5);
//            tv.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//            return tv;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return false;
//        }
//    }
}

