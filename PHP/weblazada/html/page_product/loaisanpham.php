
<div class="row">
	<div id="col-left" class="col-sm-4 col-md-5 col-lg-6">
		<div class="page-title form-style">
		    <span class="title">Loại sản phẩm</span>
		    <div class="description">Quản lý nội dung liên quan tới loại sản phẩm</div>

	    	<label for="ip_tenloaisp">Tên loại sản phẩm</label>
	    	<input type="text" class="form-control" id="ip_tenloaisp" placeholder="Nhập tên loại sản phẩm" />
	    	<label for="ip_tenloaisp">Loại cha</label></br>
	    	<select id="sl_cha">
				<optgroup label="Tên loại">
	            	<option value="0">Không</option>
	               <?php
	               	HienThiDanhMucLoaiSanPham();
	               ?>
	            </optgroup>

	        </select>
	       	</br>
	        <input type="button" class="btn btn-success" id="btn-themloaisp" value="Thêm">
	        <div class="thongbaoloi"></div>

	    </div>
	</div>

	<div id="col-right" class="col-sm-8 col-md-7 col-lg-6">
		<div class="card">
			<input type="button" class="btn btn-default" id="btn-xoaloaisanpham" value="Xóa" />

			<div class="col-right">
				<table class="timkiem">
					<tr>
						<td><input type="text" class="form-control" id="txt-timtenloaisp" placeholder="Nhập tên loại sản phẩm cần tìm"/></td>
						<td><button id="btn-timkiemloaisp" class="btn btn-default"><i class="glyphicon glyphicon-search"></i></button></td>
					</tr>
				</table>
			</div>

			<table class="table">
				<thead>
					<tr>
						<th>
							<div class="checkbox3 checkbox-round checkbox-check checkbox-light">
								<input type="checkbox" id="cb-chontatca" value="Thêm"/>
								<label for="cb-chontatca" >Tất cả</label>
							</div>
						</th>

						<th>
							Tên loại
						</th>
					</tr>
				</thead>

				<tbody>
					<?php
						LayDanhSachLoaiSanPhamLimit(0);
					?>
				</tbody>
			</table>
			<?php
				HienThiPhanTrang();
			?>
		</div>
	</div>

</div>

<?php

	function HienThiPhanTrang(){
		global $conn;
		$truyvan = "SELECT * FROM loaisanpham";
		$ketqua = mysqli_query($conn,$truyvan);
		$tongsotrang = ceil(mysqli_num_rows($ketqua)/10);
		echo '<nav>
				<ul class="pagination">
					<li>
						<a href="#" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>'	;
		for($i=1;$i<=$tongsotrang;$i++){
			if($i==1){
				echo '<li class="active"><a href="#">'.$i.'</a></li>';
			}else{
				echo '<li><a href="#">'.$i.'</a></li>';
			}
		}

		echo '<li>
					<a href="#" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
			   </li>
			</ul>
		</nav>';
	}

	function LayDanhSachLoaiSanPhamLimit($limit){
		global $conn;
		$truyvan = "SELECT * FROM loaisanpham LIMIT ".$limit.",10";
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<tr>";
				echo '<th><div class="checkbox3 checkbox-round checkbox-check checkbox-light">
							<input name="cb-mang[]" data-id="'.$dong["MALOAISP"].'" type="checkbox" id="cb-'.$dong["MALOAISP"].'"/>
							<label for="cb-'.$dong["MALOAISP"].'" ></label>
						</div></th>';
				echo '<th>'.$dong["TENLOAISP"].'</th>';
				echo "</tr>";

			}
		}
	}

	function HienThiDanhMucLoaiSanPham()
	{
		global $conn;
		$truyvan = "SELECT * FROM loaisanpham";
		$ketqua = mysqli_query($conn, $truyvan);
		if($ketqua) {
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo "<option value='".$dong["MALOAISP"]."'>".$dong["TENLOAISP"]."</option>";
			}
		}
	}
 ?>
