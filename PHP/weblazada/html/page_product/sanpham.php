
<div>
	<a class="btn-hienthithemsanpham btn btn-success">Thêm sản phẩm</a>
	<a class="btn-hienthidanhsachsanpham btn btn-success">Hiển thị danh sách sản phẩm</a>
</div>
<div class="themsanpham anbutton">
	<div class="page-title form-style">
	    <span class="title">Sản phẩm</span>
	    <div class="description">Quản lý nội dung liên quan tới sản phẩm</div>

	   	<table cellspacing="10" cellpadding="10">
	   		<tr>
	   			<th>
	   				<label for="ip_tensanpham">Tên sản phẩm</label>
    				<input type="text" class="form-control" id="ip_tensanpham" placeholder="Nhập tên sản phẩm" />
	   			</th>

	   			<th>
	   				<label for="ip_giasanpham">Giá sản phẩm</label>
    				<input type="number" class="form-control" id="ip_giasanpham" placeholder="Nhập giá sản phẩm" />
	   			</th>
	   		</tr>

	   		<tr>
	   			<th>
	   				<label for="sl_loaisanpham">Loại sản phẩm</label></br>
			    	<select id="sl_loaisanpham">
			            <optgroup label="Tên loại">
			            	<option value="0">Không</option>
			               <?php
			               	HienThiDanhMucLoaiSanPham();
			               ?>
			            </optgroup>

			        </select>

	   			</th>

	   			<th>
	   				<label for="ip_soluong">Số lượng</label>
    				<input type="number" class="form-control" id="ip_soluong" placeholder="Nhập số lượng" />
	   			</th>
	   		</tr>

	   		<tr>
	   			<th>
	   				<label for="sl_thuonghieu">Thương hiệu</label></br>
			    	<select id="sl_thuonghieu">
			            <optgroup label="Tên thương hiệu">
			               <?php
			               	LayDanhSachThuongHieu();
			               ?>
			            </optgroup>

			        </select>

	   			</th>

	   			<th rowspan="2">
	   				<label for="ip_thongtin">Mô tả</label>
    				<textarea rows="10" id="ip_thongtin" class="form-control"></textarea>
	   			</th>
	   		</tr>

	   		<tr>
	   			<th id="khunganhlon">
	   				<label for="ip_anhlon">Ảnh lớn</label>
    				<div class="form-group">
	                    <input id="ip_anhlon" name="ip_anhlon" class="file" type="file" data-preview-file-type="any" data-upload-url="uploadhinh.php">
	                </div>
	   			</th>
	   		</tr>

	   		<tr>
	   			<th id="khunganhnho">
	   				<label for="ip_anhnho">Ảnh nhỏ</label>
    				<div class="form-group">
	                    <input id="ip_anhnho" name="ip_anhnho" class="file" type="file" multiple data-preview-file-type="any" data-upload-url="uploadhinh.php">
	                </div>
	   			</th>
	   		</tr>

	   		<tr>
	   			<th>
	   				Chi tiết sản phẩm
	   				<div id="khungchitietsanpham">
	   					<table>
	   						<tr>
	   							<th>
	   								Tên chi tiết : <input style="margin:5px; padding:5px; width:60%" name="mangtenchitietsanpham[]" type="text"  />
	   							</th>

	   							<th>
	   								Giá trị : <input style="margin:5px; padding:5px; width:60%" name="manggiatrichitietsanpham[]" type="text"  />
	   								<a class="btn btn-primary btnthemchitietsanpham">Thêm</a> <a class="btn btn-danger anbutton btnxoachitietsanpham">Xóa</a>
	   							</th>
	   						</tr>
	   					</table>
	   				</div>
	   			</th>
	   		</tr>
	   	</table>


        <input type="button" class="btn btn-success" id="btn-themsanpham" value="Đồng ý">
        <input type="button" class="btn btn-success" id="btn-capnhatsanpham" value="Cập nhật">

        <div class="thongbaoloi"></div>
        <div class="anchitietsanpham">
        	<table>
				<tr>
					<th>
						Tên chi tiết : <input style="margin:5px; padding:5px; width:60%" name="mangtenchitietsanpham[]" type="text"  />
					</th>

					<th>
						Giá trị : <input style="margin:5px; padding:5px; width:60%" name="manggiatrichitietsanpham[]" type="text"  />
						<a class="btn btn-primary btnthemchitietsanpham">Thêm</a> <a class="btn btn-danger anbutton btnxoachitietsanpham">Xóa</a>
					</th>
				</tr>
			</table>
        </div>

    </div>
</div>

<div class="hienthisanpham">
	<div class="card">

		<div class="col-right">
			<table class="timkiem">
				<tr>
					<td><input type="text" class="form-control" id="txt-timkiemsanpham" placeholder="Nhập tên sản phẩm cần tìm"/></td>
					<td><button id="btn-timkiemsanpham" class="btn btn-default"><i class="glyphicon glyphicon-search"></i></button></td>
				</tr>
			</table>


		</div>

		<table class="table">
			<thead>
				<tr>

					<th>
						Hình
					</th>

					<th>
						Tên sản phẩm
					</th>

					<th>
						Loại sản phẩm
					</th>

					<th>
						Thương hiệu
					</th>

					<th>
						Giá
					</th>

					<th>
						Số lượng
					</th>

					<th>
						#
					</th>

				</tr>
			</thead>

			<tbody>
				<?php LayDanhSachLoaiSanPhamLimit(0); ?>
			</tbody>
		</table>

		<div id="phantrangsanpham" data-tongsotrang=<?php LayTongSoTrang() ?>>

		</div>
	</div>
</div>

<?php

	function LayDanhSachLoaiSanPhamLimit($limit){
		global $conn;
		$truyvan = "SELECT * FROM sanpham sp, loaisanpham lsp, thuonghieu th WHERE sp.MALOAISP = lsp.MALOAISP AND sp.MATHUONGHIEU = th.MATHUONGHIEU LIMIT ".$limit.",10";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<tr>";
				echo '<th class="anbutton" data-anhnho="'.$dong["ANHNHO"].'"</th>';
				echo '<th class="anbutton" data-mota="'.$dong["THONGTIN"].'"></th>';
				echo '<th data-anhlon="'.$dong["ANHLON"].'"><img style="width:50px; height:50px" src="..'.$dong["ANHLON"].'" /> </th>';
				echo '<th data-tensp="'.$dong["TENSP"].'">'.$dong["TENSP"].'</th>';
				echo '<th data-maloaisp="'.$dong["MALOAISP"].'">'.$dong["TENLOAISP"].'</th>';
				echo '<th data-math="'.$dong["MATHUONGHIEU"].'">'.$dong["TENTHUONGHIEU"].'</th>';
				echo '<th data-gia="'.$dong["GIA"].'">'.$dong["GIA"].'</th>';
				echo '<th data-soluong="'.$dong["SOLUONG"].'">'.$dong["SOLUONG"].'</th>';
				echo '<th data-id="'.$dong["MASP"].'"><a class="btn btn-success btn-suasanpham">Sửa</a> <a class="btn btn-danger btn-xoasanpham">Xóa</a></th>';


				echo "</tr>";

			}
		}
	}

	function LayDanhSachThuongHieu(){
		global $conn;
		$truyvan = "SELECT * FROM thuonghieu";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<option value='".$dong["MATHUONGHIEU"]."'>".$dong["TENTHUONGHIEU"]."</option>";
			}
		}
	}

	function LayTongSoTrang(){
		global $conn;
		$truyvan = "SELECT * FROM sanpham";
		$ketqua = mysqli_query($conn,$truyvan);
		$tongsotrang = ceil(mysqli_num_rows($ketqua)/10);
		echo $tongsotrang;
	}



	function HienThiDanhMucLoaiSanPham(){
		global $conn;
		$truyvan = "SELECT * FROM loaisanpham";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<option value='".$dong["MALOAISP"]."'>".$dong["TENLOAISP"]."</option>";
			}
		}
	}
?>
