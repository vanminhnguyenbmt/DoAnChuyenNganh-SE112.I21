package com.example.ochutgio.reviewquanan.Controller;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.ochutgio.reviewquanan.Model.BinhLuanModel;

import java.util.List;

/**
 * Created by ochutgio on 5/7/2018.
 */

public class BinhLuanController {

    BinhLuanModel binhLuanModel;

    public BinhLuanController(){
        binhLuanModel = new BinhLuanModel();
    }

    public  void ThemBinhLuan(BinhLuanModel binhLuanModel, List<String> listHinh, String maquanan, Context context){
        binhLuanModel.ThemBinhLuan(binhLuanModel, listHinh, maquanan, context);
    }
}
