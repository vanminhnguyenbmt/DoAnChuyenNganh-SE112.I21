<div>
	<a class="btn-hienthithemhoadon btn btn-success">Thêm hóa đơn</a>
	<a class="btn-hienthidanhsachhoadon btn btn-success">Hiển thị danh sách hóa đơn</a>
</div>

<div class="themsanpham anbutton">
	<div class="page-title form-style">
	    <span class="title">Hóa đơn</span>
	    <div class="description">Quản lý nội dung liên tới hóa đơn</div>
	   	<table cellspacing="10" cellpadding="10">
	   		<tr>
	   			<th>
	   				<label for="ip_chudonhang">Chủ đơn hàng</label>
    				<input type="text" class="form-control" id="ip_chudonhang" placeholder="Nhập tên chủ đơn hàng" />
	   			</th>

	   			<th>
	   				<label for="ip_sodtdonhang">Số điện thoại</label>
    				<input type="number" class="form-control" id="ip_sodtdonhang" placeholder="Nhập số điện thoại" />
	   			</th>
	   		</tr>

	   		<tr>
	   			<th>
	   				<label for="ip_diachi">Địa chỉ</label>
    				<input type="text" class="form-control" id="ip_diachi" placeholder="Nhập địa chỉ" />
	   			</th>

	   			<th>
	   				<label for="ip_machuyenkhoan">Mã chuyển khoản (nếu có)</label>
    				<input type="text" class="form-control" id="ip_machuyenkhoan" placeholder="Nhập mã chuyển khoản (nếu có)" />
	   			</th>
	   		</tr>

   			<tr>
	   			<th>
	   				<label for="ip_ngaymua">Ngày mua</label>
    				<input type="text" class="form-control" id="ip_ngaymua" placeholder="Nhập ngày mua" />
	   			</th>

	   			<th>
	   				<label for="ip_ngaygiao">Ngày giao</label>
    				<input type="text" class="form-control" id="ip_ngaygiao" placeholder="Nhập ngày giao" />
	   			</th>
	   		</tr>

	   		<tr>
	   			<th>
	   				<label for="sl_tinhtranghoadon">Trạng thái</label>
	   				<select id="sl_tinhtranghoadon">
	   					<optgroup label="Tình trạng">
	   						<option value="chờ kiểm duyệt">chờ kiểm duyệt</option>
	   						<option value="đã hủy">đã hủy</option>
	   						<option value="đang giao hàng">đang giao hàng</option>
	   						<option value="hoàn thành">hoàn thành</option>
	   					</optgroup>
	   				</select>
	   			</th>

	   			<th>
	   				<label for="sl_chuyenkhoan">Chuyển khoản</label>
	   				<select id="sl_chuyenkhoan">
	   					<option value="1">có</option>
	   					<option value="0">không</option>
	   				</select>
	   			</th>
	   		</tr>

	   		<tr>
	   			<th colspan="2">
	   				<span>Chi tiết hóa đơn</span>
	   				<div>
	   						<select id="sl_tenspchitiethd">
			   					<?php LayDanhSachSanPham() ?>
			   				</select>

			   				<input id="ip_soluongsanphamhd" type="number" min="1" value="1"  />

			   				<a class="btn btn-default btn-themchitiethoadon">Thêm vào chi tiết hóa đơn</a>
	   				</div>

	   				<div id="khungchitiethoadon">
	   					<table>
	   						<tbody>

	   						</tbody>
	   					</table>
	   				</div>
	   			</th>
	   		</tr>
	   	</table>


        <input type="button" class="btn btn-success" id="btn-themhoadon" value="Đồng ý">
        <input type="button" class="btn btn-success anbutton" id="btn-dongycapnhathoadon" value="Cập nhật">

        <div class="thongbaoloi"></div>

    </div>
</div>

<div class="hienthisanpham">
	<div class="card">

		<div class="col-right">
			<table class="timkiem">
				<tr>
					<td><input type="text" class="form-control" id="txt-timkiemhoadon" placeholder="Nhập tên hóa đơn cần tìm"/></td>
					<td><button id="btn-timkiemhoadon" class="btn btn-default"><i class="glyphicon glyphicon-search"></i></button></td>
				</tr>
			</table>


		</div>

		<table class="table">
			<thead>
				<tr>

					<th>
						Chủ đơn hàng
					</th>

					<th>
						Số điện thoại
					</th>

					<th>
						Địa chỉ
					</th>

					<th>
						Tình trạng
					</th>

					<th>
						Ngày mua
					</th>

					<th>
						Ngày giao
					</th>

					<th>
						Chuyển khoản
					</th>

					<th>
						Mã chuyển khoản
					</th>

					<th>
						Mã đối tác
					</th>

					<th>
						Tổng tiền
					</th>
				</tr>
			</thead>

			<tbody>
				<?php LayDanhSachHoaDon() ?>
			</tbody>
		</table>

		<div id="phantranghoadon" data-tongsotrang=<?php LayTongSoTrang() ?>>

		</div>

		<div id="phantranghoadontimkiem">

		</div>
	</div>
</div>

<?php

	function LayTongSoTrang(){
		global $conn;
		$truyvan = "SELECT * FROM hoadon";
		$ketqua = mysqli_query($conn,$truyvan);
		$tongsotrang = ceil(mysqli_num_rows($ketqua)/10);
		echo $tongsotrang;
	}

	function LayDanhSachSanPham(){
		global $conn;
		$truyvan = "SELECT * FROM sanpham";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<option value='".$dong["MASP"]."'>".$dong["TENSP"]."</option>";
			}
		}
	}

	function LayDanhSachHoaDon(){
		global $conn;
		$truyvan = "SELECT * FROM hoadon ORDER BY MAHD DESC LIMIT 0,10";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				$chuyenkhoan = $dong["CHUYENKHOAN"] !=null && $dong["CHUYENKHOAN"] != '0' ? 'có' : 'không';
				$madoitac = $dong["MADOITAC"] !=null && $dong["MADOITAC"] != '0' ? $dong["MADOITAC"] : 'không có';
				echo '<tr>';
				echo '<th data-tennguoinhan="'.$dong['TENNGUOINHAN'].'">'.$dong['TENNGUOINHAN'].'</th>';
				echo '<th data-sodt="'.$dong['SODT'].'">'.$dong['SODT'].'</th>';
				echo '<th data-diachi="'.$dong['DIACHI'].'">'.$dong['DIACHI'].'</th>';
				echo '<th data-tinhtrang="'.$dong['TRANGTHAI'].'">'.$dong['TRANGTHAI'].'</th>';
				echo '<th data-ngaymua="'.$dong['NGAYMUA'].'">'.$dong['NGAYMUA'].'</th>';
				echo '<th data-ngaygiao="'.$dong['NGAYGIAO'].'">'.$dong['NGAYGIAO'].'</th>';
				echo '<th data-chuyenkhoan="'.$dong['CHUYENKHOAN'].'">'.$chuyenkhoan.'</th>';
				echo '<th class="" data-machuyenkhoan="'.$dong['MACHUYENKHOAN'].'">'.$dong['MACHUYENKHOAN'].'</th>';
				echo '<th class="" data-madoitac="'.$dong['MADOITAC'].'">'.$madoitac.'</th>';
				echo '<th class="" data-tongtien="'.$dong['TONGTIEN'].'">'.$dong['TONGTIEN'].'</th>';
				echo '<th data-id="'.$dong['MAHD'].'"><a class="btn btn-success btn-capnhathoadon">Cập nhật</a></th>';
				echo '</tr>';
			}
		}
	}

	function HienThiDanhMucLoaiSanPham(){
		global $conn;
		$truyvan = "SELECT * FROM hoadon";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<option value='".$dong["MALOAISP"]."'>".$dong["TENLOAISP"]."</option>";
			}
		}
	}
?>
