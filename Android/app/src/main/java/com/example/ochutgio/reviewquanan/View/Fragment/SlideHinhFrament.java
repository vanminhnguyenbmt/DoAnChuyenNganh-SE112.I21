package com.example.ochutgio.reviewquanan.View.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.app.Fragment;

import com.example.ochutgio.reviewquanan.R;

public class SlideHinhFrament extends Fragment {
    ImageView imvFullHinh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_slidehinh, container,false);
        imvFullHinh = (ImageView)view.findViewById(R.id.imvFullHinh);

        Bundle bundle = getArguments();
        byte[] byteArray = bundle.getByteArray("byteArray");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        imvFullHinh.setImageBitmap(bitmap);
        return view;
    }
}
