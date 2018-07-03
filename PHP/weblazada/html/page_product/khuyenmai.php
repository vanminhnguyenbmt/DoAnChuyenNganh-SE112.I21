<div>
	<a class="btn-hienthithemkhuyenmai btn btn-success">Thêm khuyến mãi</a>
	<a class="btn-hienthidanhsachkhuyenmai btn btn-success">Hiển thị danh sách khuyến mãi</a>
</div>

<div class="themsanpham anbutton">
	<div class="page-title form-style">
	    <span class="title">Khuyến mãi</span>
	    <div class="description">Quản lý nội dung liên tới khuyến mãi</div>

	   	<table cellspacing="10" cellpadding="10">
			<tr>
				<th>
	   				<label for="ip_tenkhuyenmai">Tên khuyến mãi</label>
    				<input type="text" class="form-control" id="ip_tenkhuyenmai" placeholder="Nhập tên khuyến mãi" />
	   			</th>

	   			<th>
	   				<label for="sl_loaisanpham">Loại sản phẩm khuyến mãi</label></br>
	   				<select id="sl_loaisanpham" style="width: 100%">
	   					<optgroup label="Chọn loại sản phẩm">
							<?php LayDanhSachLoaiSanPham() ?>
	   					</optgroup>
	   				</select>
	   			</th>
	   		</tr>

			<tr>
	   			<th id="khunganhkhuyenmai">
	   				<label for="ip_anhkhuyenmai">Hình khuyến mãi</label>
    				<div class="form-group">
	                    <input id="ip_anhkhuyenmai" name="ip_anhkhuyenmai" class="file" type="file" data-preview-file-type="any" data-upload-url="page_product/uploadhinhkhuyenmai.php">
	                </div>
	   			</th>
	   		</tr>

			<tr>
	   			<th>
	   				<label for="ip_ngaybatdau">Ngày bắt đầu</label>
    				<input type="date" class="form-control" id="ip_ngaybatdau" placeholder="Nhập ngày bắt đầu" />
	   			</th>

	   			<th>
	   				<label for="ip_ngayketthuc">Ngày kết thúc</label>
    				<input type="date" class="form-control" id="ip_ngayketthuc" placeholder="Nhập ngày kết thúc" />
	   			</th>
	   		</tr>

			<tr>
	   			<th colspan="2">
	   				<span>Chi tiết khuyến mãi</span>
	   				<div>
   						<select id="sl_tenspchitietkm" style="width: 50%">

		   				</select>

		   				<input id="ip_phantramkm" type="number" min="1" value="" placeholder="Phần trăm khuyến mãi"/>
		   				<a class="btn btn-default btn-themchitietkm">Thêm vào chi tiết khuyến mãi</a>
	   				</div>

	   				<div id="khungchitietkm">
	   					<table>
	   						<tbody>

	   						</tbody>
	   					</table>
	   				</div>
	   			</th>
	   		</tr>
		</table>

		<input type="button" class="btn btn-success" id="btn-themkhuyenmai" value="Đồng ý">
        <input type="button" class="btn btn-success anbutton" id="btn-dongycapnhatkhuyenmai" value="Cập nhật">

        <div class="thongbaoloi"></div>
	</div>
</div>

<div class="hienthisanpham">
	<div class="card">

		<div class="col-right">
			<table class="timkiem">
				<tr>
					<td><input type="text" class="form-control" id="txt-timkiemkhuyenmai" placeholder="Nhập khuyến mãi cần tìm"/></td>
					<td><button id="btn-timkiemkhuyenmai" class="btn btn-default"><i class="glyphicon glyphicon-search"></i></button></td>
				</tr>
			</table>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th>
						Hình khuyến mãi
					</th>

					<th>
						Tên khuyến mãi
					</th>

					<th>
						Loại sản phẩm khuyến mãi
					</th>

					<th>
						Ngày bắt đầu
					</th>

					<th>
						Ngày kết thúc
					</th>

					<th>
						#
					</th>

				</tr>
			</thead>

			<tbody>
				<?php LayDanhSachKhuyenMaiLimit(0) ?>
			</tbody>
		</table>

		<div id="phantrangkhuyenmai" data-tongsotrang=<?php LayTongSoTrang() ?>>

		</div>

		<div id="phantrangkhuyenmaitimkiem">

		</div>
	</div>
</div>

<?php
	function LayDanhSachLoaiSanPham(){
		global $conn;
		$truyvan = "SELECT * FROM loaisanpham";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<option value='".$dong["MALOAISP"]."'>".$dong["TENLOAISP"]."</option>";
			}
		}
	}

	function LayTongSoTrang(){
		global $conn;
		$truyvan = "SELECT * FROM khuyenmai";
		$ketqua = mysqli_query($conn,$truyvan);
		$tongsotrang = ceil(mysqli_num_rows($ketqua)/10);
		echo $tongsotrang;
	}

	function LayDanhSachKhuyenMaiLimit($limit){
		global $conn;
		$truyvan = "SELECT * FROM khuyenmai km, loaisanpham lsp
					WHERE km.MALOAISP = lsp.MALOAISP
					ORDER BY MAKM DESC
					LIMIT ".$limit.",10";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<tr>";
				echo '<th data-hinhkm="'.$dong["HINHKHUYENMAI"].'"><img style="width:133px; height:50px" src="..'.$dong["HINHKHUYENMAI"].'" /> </th>';
				echo '<th data-tenkm="'.$dong["TENKM"].'">'.$dong["TENKM"].'</th>';
				echo '<th data-maloaisp="'.$dong["MALOAISP"].'">'.$dong["TENLOAISP"].'</th>';
				echo '<th class="anbutton" data-tenloaisp="'.$dong["TENLOAISP"].'"></th>';
				echo '<th data-ngaybatdau="'.$dong["NGAYBATDAU"].'">'.$dong["NGAYBATDAU"].'</th>';
				echo '<th data-ngayketthuc="'.$dong["NGAYKETTHUC"].'">'.$dong["NGAYKETTHUC"].'</th>';
				echo '<th data-id="'.$dong["MAKM"].'"><a class="btn btn-success btn-suakhuyenmai">Sửa</a></th>';
				echo "</tr>";
			}
		}
	}

 ?>
