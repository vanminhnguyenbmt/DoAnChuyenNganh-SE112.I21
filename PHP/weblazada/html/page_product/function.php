<?php
    include '../../config.php';

    $ham = $_POST["action"];

    switch ($ham) {
        case 'ThemLoaiSanPham_Ajax':
            $ham();
            break;

		case 'LayDanhSachLoaiSanPhamLimit_Ajax':
            $ham();
            break;

        case 'XoaLoaiSanPhamTheoMa_Ajax':
            $ham();
            break;

		case 'HienThiPhanTrang_Ajax':
            $ham();
            break;

        default:
            // code...
            break;
    }

    function HienThiPhanTrang_Ajax(){
		global $conn;
		$truyvan = "SELECT * FROM loaisanpham";
		$ketqua = mysqli_query($conn,$truyvan);
		$tongsotrang = ceil(mysqli_num_rows($ketqua)/10);
		echo '
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
            ';
	}

    function XoaLoaiSanPhamTheoMa_Ajax(){
        global $conn;
		$mangmaloaisp = $_POST["mangmaloaisp"];

		for($i=0; $i < count($mangmaloaisp); $i++){
			DeQuyLayMaLoaiSPTheoMaLoaiCha($mangmaloaisp[$i]);
			XoaBangLienQuanToiBangLoaiSanPham($mangmaloaisp[$i]);
		}
    }

    function DeQuyLayMaLoaiSPTheoMaLoaiCha($maloaisp){
		global $conn;
		$truyvan = "SELECT * FROM loaisanpham WHERE MALOAI_CHA=".$maloaisp;
		$ketqua = mysqli_query($conn,$truyvan);
		$maloai = 0;
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				$maloai = $dong["MALOAISP"];
				XoaBangLienQuanToiBangLoaiSanPham($maloai);
				DeQuyLayMaLoaiSPTheoMaLoaiCha($maloai);
			}
		}
	}

    function XoaBangLienQuanToiBangLoaiSanPham($maloaisp){

		LayMaSPVaXoaSanPhamTheoMaLoaiSP($maloaisp);

		XoaChiTietThuongHieuTheoMaLoaiSP($maloaisp);
		LayMaKMVaXoaChiTietKhuyenMaiTheoMaKhuyenMai($maloaisp);
		XoaKhuyenMaiTheoMaLoaiSP($maloaisp);
		XoaBangLoaiSanPhamTheoMaLoai($maloaisp);
	}

    function XoaChiTietThuongHieuTheoMaLoaiSP($maloaisp){
		global $conn;
		$truyvan = "DELETE FROM chitietthuonghieu WHERE MALOAISP=".$maloaisp;
		mysqli_query($conn,$truyvan);
	}

    function LayMaKMVaXoaChiTietKhuyenMaiTheoMaKhuyenMai($maloaisp){
        global $conn;
        $truyvan = "SELECT * FROM khuyenmai WHERE MALOAISP=".$maloaisp;
        $ketqua = mysqli_query($conn,$truyvan);
        $makm = 0;
        if($ketqua){
            while ($dong = mysqli_fetch_array($ketqua)) {
                $makm = $dong["MAKM"];
                XoaChiTietKhuyenMaiTheoMaKM($makm);
            }
        }else{

        }
    }

    function XoaChiTietKhuyenMaiTheoMaKM($makm){
        global $conn;
        $truyvan = "DELETE FROM chitietkhuyenmai WHERE MAKM=".$makm;
        mysqli_query($conn,$truyvan);
    }

    function XoaKhuyenMaiTheoMaLoaiSP($maloaisp){
        global $conn;
        $truyvan = "DELETE FROM khuyenmai WHERE MALOAISP=".$maloaisp;
        mysqli_query($conn,$truyvan);
    }

    function XoaBangLoaiSanPhamTheoMaLoai($maloaisp){
        global $conn;
        $truyvan = "DELETE FROM loaisanpham WHERE MALOAISP=".$maloaisp;
        mysqli_query($conn,$truyvan);
    }

    function LayMaSPVaXoaSanPhamTheoMaLoaiSP($maloaisp){
		global $conn;
		$truyvan = "SELECT * FROM sanpham WHERE MALOAISP=".$maloaisp;
		$ketqua = mysqli_query($conn,$truyvan);
		$masp = 0;
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				$masp = $dong["MASP"];

				XoaChiTietKhuyenMaiTheoMASP($masp);
				XoaChiTietSanPhamTheoMASP($masp);
				XoaChiTietHoaDonTheoMASP($masp);
				XoaDanhGiaTheoMASP($masp);
			}

			$truyvan = "DELETE FROM sanpham WHERE MALOAISP=".$maloaisp;
			mysqli_query($conn,$truyvan);

		}else{

		}
	}

    function XoaChiTietKhuyenMaiTheoMASP($masp){
		global $conn;
		$truyvan = "DELETE FROM chitietkhuyenmai WHERE MASP=".$masp;
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			return true;
		}else{
			return false;
		}
	}

    function XoaChiTietSanPhamTheoMASP($masp){
		global $conn;
		$truyvan = "DELETE FROM chitietsanpham WHERE MASP=".$masp;
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			return true;
		}else{
			return false;
		}
	}

    function XoaChiTietHoaDonTheoMASP($masp){
		global $conn;
		$truyvan = "DELETE FROM chitiethoadon WHERE MASP=".$masp;
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			return true;
		}else{
			return false;
		}
	}

    function XoaDanhGiaTheoMASP($masp){
		global $conn;
		$truyvan = "DELETE FROM danhgia WHERE MASP=".$masp;
		$ketqua = mysqli_query($conn,$truyvan);
		if($ketqua){
			return true;
		}else{
			return false;
		}
	}

    function LayDanhSachLoaiSanPhamLimit_Ajax(){
        global $conn;
        $sotrang = $_POST["sotrang"];
        $limit = ($sotrang-1)*10;
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

    function ThemLoaiSanPham_Ajax()
    {
        global $conn;
		$tenloaisp = $_POST["tenloaisanpham"];
		$maloaisp = $_POST["maloaisp"];
		$loi = "Đã xảy ra lỗi trong quá trình thêm dữ liệu";
		if($tenloaisp == ""){
			echo $loi;
		}else{
			$truyvan = "INSERT INTO loaisanpham(TENLOAISP,MALOAI_CHA) VALUES('".$tenloaisp."','".$maloaisp."')";
			$ketqua = mysqli_query($conn,$truyvan);
			if($ketqua){
				echo "<h4 style='color:red'>Thêm thành công !<h4>";
			}else{
				echo $loi;
			}
		}
    }
 ?>
