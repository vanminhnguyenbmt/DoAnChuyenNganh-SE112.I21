package com.bin.lazada.View.TimKiem;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bin.lazada.Adapter.AdapterTopDienThoaiDienTu;
import com.bin.lazada.ObjectClass.ILoadMore;
import com.bin.lazada.ObjectClass.LoadMoreScroll;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.Presenter.TimKiem.PresenterLogicTimKiem;
import com.bin.lazada.R;

import java.util.List;

public class TimKiemActivity extends AppCompatActivity implements ViewTimKiem, ILoadMore, SearchView.OnQueryTextListener{

    Toolbar toolbar;
    RecyclerView recyclerView;
    PresenterLogicTimKiem presenterLogicTimKiem;
    String searchKey;
    List<SanPham> sanphamMore;
    AdapterTopDienThoaiDienTu adapterTopDienThoaiDienTu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timkiem);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerTimKiem);

        toolbar.setTitleTextColor(getIdColor(R.color.colorWhite));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenterLogicTimKiem = new PresenterLogicTimKiem(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private int getIdColor(int idcolor) {
        int color = 0;
        if(Build.VERSION.SDK_INT > 21) {
            color = ContextCompat.getColor(this, idcolor);
        }else {
            color = getResources().getColor(idcolor);
        }
        return color;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timkiem,menu);

        MenuItem itSearch = menu.findItem(R.id.itSearch);
        SearchView searchView = (SearchView) itSearch.getActionView();

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorWhite));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorWhite));

        searchView.setQueryHint("Nhập tên sản phẩm");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public void TimKiemThanhCong(final List<SanPham> sanPhamList) {
        sanphamMore = sanPhamList;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapterTopDienThoaiDienTu = new AdapterTopDienThoaiDienTu(this, R.layout.custom_layout_list_topdienthoaivamaytinhbang, sanphamMore);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterTopDienThoaiDienTu);
        recyclerView.addOnScrollListener(new LoadMoreScroll(layoutManager, this));

        adapterTopDienThoaiDienTu.notifyDataSetChanged();

    }

    @Override
    public void TimKiemThatBai() {
        Toast.makeText(this, "Không tìm thấy sản phẩm này !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoadMore(final int tongitem) {
        List<SanPham> sanPhamsLoadMore = presenterLogicTimKiem.TimKiemSanPhamTheoTenSPLoadMore(searchKey, tongitem);
        sanphamMore.addAll(sanPhamsLoadMore);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapterTopDienThoaiDienTu.notifyItemInserted(tongitem - 1);
                adapterTopDienThoaiDienTu.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchKey = query;
        presenterLogicTimKiem.TimKiemSanPhamTheoTenSP(query, 0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
