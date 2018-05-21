package com.example.ochutgio.reviewquanan.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ochutgio.reviewquanan.Controller.OdauController;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;

import java.util.zip.Inflater;

/**
 * Created by ochutgio on 4/18/2018.
 */

public class ODauFragment extends Fragment {

    OdauController odauController;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container,false);
        recyclerOdau = (RecyclerView) view.findViewById(R.id.recyclerOdau);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarODau);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getContext().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        odauController = new OdauController(getContext());
        odauController.getDanhSachQuanAnController(getContext(), recyclerOdau, vitrihientai, progressBar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
