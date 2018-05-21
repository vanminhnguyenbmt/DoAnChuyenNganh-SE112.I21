package com.example.ochutgio.reviewquanan.Controller;

import com.example.ochutgio.reviewquanan.Model.ThanhVienModel;

/**
 * Created by ochutgio on 4/23/2018.
 */

public class DangKyController {
    ThanhVienModel thanhVienModel;

    public DangKyController(){
        thanhVienModel = new ThanhVienModel();
    }

    public void ThemThanhVienController(ThanhVienModel thanhVienModel, String uid){
        thanhVienModel.ThemThanhVienModel(thanhVienModel, uid);
    }

}
