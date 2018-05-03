package com.bin.lazada.View.TrangChu.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bin.lazada.Adapter.AdapterNoiBat;
import com.bin.lazada.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentNoiBat extends Fragment {
    RecyclerView recyclerView;
    AdapterNoiBat adapterNoiBat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_noibat, container, false);

        recyclerView = view.findViewById(R.id.recyclerNoiBat);

        List<String> dulieu = new ArrayList<>();

        for(int i = 0; i < 50; i ++) {
            String ten = "Noi bat " + i;
            dulieu.add(ten);
        }

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        adapterNoiBat = new AdapterNoiBat(getActivity(), dulieu);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterNoiBat);

        adapterNoiBat.notifyDataSetChanged();

        return view;
    }
}
