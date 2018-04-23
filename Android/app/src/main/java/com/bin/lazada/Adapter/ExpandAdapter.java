package com.bin.lazada.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.bin.lazada.Model.TrangChu.XuLyMenu.XuLyJSONMenu;
import com.bin.lazada.ObjectClass.LoaiSanPham;
import com.bin.lazada.R;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {

    Context context;
    List<LoaiSanPham> loaiSanPhams;

    public ExpandAdapter(Context context, List<LoaiSanPham> loaiSanPhams)
    {
        this.context = context;
        this.loaiSanPhams = loaiSanPhams;
        XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();

        int count = loaiSanPhams.size();
        for(int i = 0; i < count; i++)
        {
            int maloaisp = loaiSanPhams.get(i).getMALOAISP();
            loaiSanPhams.get(i).setListCon(xuLyJSONMenu.LaySanPhamTheoMaLoai(maloaisp));
        }
    }
    @Override
    public int getGroupCount() {
        return loaiSanPhams.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewGroupCha = layoutInflater.inflate(R.layout.custom_layout_group_cha, parent, false);
        TextView txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);
        txtTenLoaiSP.setText(loaiSanPhams.get(groupPosition).getTENLOAISP());

        return viewGroupCha;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewGroupCon = layoutInflater.inflate(R.layout.custom_layout_group_con, parent, false);
//
//        ExpandableListView expandableListView = (ExpandableListView) viewGroupCon.findViewById(R.id.epMenuCon);

        SecondExpandalbe secondExpandalbe = new SecondExpandalbe(context);
        SecondAdapter secondAdapter = new SecondAdapter(loaiSanPhams.get(groupPosition).getListCon());
        secondExpandalbe.setAdapter(secondAdapter);
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

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //class xử lý menu 3 cấp
    public class SecondAdapter extends BaseExpandableListAdapter{
        List<LoaiSanPham> listCon;

        public SecondAdapter(List<LoaiSanPham> listCon){
            this.listCon = listCon;

            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();

            int count = listCon.size();
            for(int i = 0; i < count; i++)
            {
                int maloaisp = listCon.get(i).getMALOAISP();
                listCon.get(i).setListCon(xuLyJSONMenu.LaySanPhamTheoMaLoai(maloaisp));
            }
        }
        @Override
        public int getGroupCount() {
            return listCon.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return listCon.get(groupPosition).getListCon().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return listCon.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return listCon.get(groupPosition).getListCon().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return listCon.get(groupPosition).getMALOAISP();
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return listCon.get(groupPosition).getListCon().get(childPosition).getMALOAISP();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewGroupCha = layoutInflater.inflate(R.layout.custom_layout_group_cha, parent, false);
            TextView txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);
            txtTenLoaiSP.setText(listCon.get(groupPosition).getTENLOAISP());

            return viewGroupCha;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView tv = new TextView(context);
            tv.setText(listCon.get(groupPosition).getListCon().get(childPosition).getTENLOAISP());
            tv.setPadding(15, 5 , 5, 5);
            tv.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return tv;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}

