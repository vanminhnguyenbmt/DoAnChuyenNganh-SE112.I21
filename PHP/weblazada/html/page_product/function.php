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

		case 'TimKiemLoaiSanPhamTheoTen_Ajax':
            $ham();
            break;

		case 'ThemSanPham_Ajax':
            $ham();
            break;

		case 'XoaSanPham_Ajax':
            $ham();
            break;

		case 'LayChiTietSanPhamTheoMa_Ajax':
            $ham();
            break;

		case 'CapNhatSanPhamTheoMaSP_Ajax':
            $ham();
            break;

        case 'LayDanhSachSanPhamLimit_Ajax':
            $ham();
            break;

        case 'LayDanhSachHoaDonLimit_Ajax':
            $ham();
            break;

        case 'KiemTraDangNhap_Ajax':
            $ham();
            break;

        case 'LayChiTietHoaDon_Ajax':
            $ham();
            break;

		case 'CapNhatHoaDonVsChiTietHoaDon_Ajax':
            $ham();
            break;

        case 'TimKiemHoaDon_Ajax':
            $ham();
            break;

        case 'TimKiemSanPham_Ajax':
            $ham();
            break;

        case 'LayDanhSachSanPhamTheoMaLoaiSP_Ajax':
            $ham();
            break;

        case 'ThemKhuyenMaiVaChiTietKhuyenMai_Ajax':
            $ham();
            break;

        case 'LayChiTietKhuyenMai_Ajax':
            $ham();
            break;

        case 'CapNhapKhuyenMaiVaChiTietKhuyenMai_Ajax':
            $ham();
            break;

        case 'TimKiemKhuyenMai_Ajax':
            $ham();
            break;

        default:
            // code...
            break;
    }

    function TimKiemKhuyenMai_Ajax(){
		global $conn;
        $limit = ($_POST["sotrang"]-1)*10;
		$noidungtimkiem = $_POST["noidungtimkiem"];
		$truyvan = "SELECT * FROM khuyenmai km, loaisanpham lsp
                     WHERE km.MALOAISP = lsp.MALOAISP AND km.TENKM LIKE '%".$noidungtimkiem."%'
                    OR km.MALOAISP = lsp.MALOAISP AND km.NGAYBATDAU LIKE '%".$noidungtimkiem."%'
                    OR km.MALOAISP = lsp.MALOAISP AND km.NGAYKETTHUC LIKE '%".$noidungtimkiem."%'
                    ORDER BY km.MAKM DESC
                    LIMIT ".$limit.", 10";
		$ketqua = mysqli_query($conn,$truyvan);

        //Tính tổng số trang
        $truyvantrang = "SELECT * FROM khuyenmai km, loaisanpham lsp
                        WHERE km.MALOAISP = lsp.MALOAISP AND km.TENKM LIKE '%".$noidungtimkiem."%'
                        OR km.MALOAISP = lsp.MALOAISP AND km.NGAYBATDAU LIKE '%".$noidungtimkiem."%'
                        OR km.MALOAISP = lsp.MALOAISP AND km.NGAYKETTHUC LIKE '%".$noidungtimkiem."%'";
        $ketquatrang = mysqli_query($conn,$truyvantrang);
        $tongsotrang = ceil(mysqli_num_rows($ketquatrang)/10);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
                echo '<tr data-tongsotrang="'.$tongsotrang.'">';
				echo '<th data-hinhkm="'.$dong["HINHKHUYENMAI"].'"><img style="width:133px; height:50px" src="..'.$dong["HINHKHUYENMAI"].'" /> </th>';
				echo '<th data-tenkm="'.$dong["TENKM"].'">'.$dong["TENKM"].'</th>';
				echo '<th data-maloaisp="'.$dong["MALOAISP"].'">'.$dong["TENLOAISP"].'</th>';
				echo '<th class="anbutton" data-tenloaisp="'.$dong["TENLOAISP"].'"></th>';
				echo '<th data-ngaybatdau="'.$dong["NGAYBATDAU"].'">'.$dong["NGAYBATDAU"].'</th>';
				echo '<th data-ngayketthuc="'.$dong["NGAYKETTHUC"].'">'.$dong["NGAYKETTHUC"].'</th>';
				echo '<th data-id="'.$dong["MAKM"].'"><a class="btn btn-success btn-suakhuyenmai">Sửa</a></th>';
				echo '</tr>';
			}
		}
    }

    function CapNhapKhuyenMaiVaChiTietKhuyenMai_Ajax()
    {
        global $conn;

        $tenkhuyenmai = $_POST["tenkhuyenmai"];
		$maloaisp = $_POST["maloaisp"];
		$ngaybatdau = $_POST["ngaybatdau"];
		$ngayketthuc = $_POST["ngayketthuc"];
		$hinhkhuyenmai = $_POST["hinhkhuyenmai"];
		$mangmasp = $_POST["mangmasp"];
		$mangphantramkm = $_POST["mangphantramkm"];
        $makm = $_POST["makm"];

        $truyvan = "UPDATE khuyenmai
                    SET MALOAISP = '".$maloaisp."', TENKM = '".$tenkhuyenmai."',
                    NGAYBATDAU = '".$ngaybatdau."', NGAYKETTHUC = '".$ngayketthuc."',
                    HINHKHUYENMAI = '".$hinhkhuyenmai."'
                    WHERE MAKM='".$makm."'";
		$ketqua = mysqli_query($conn, $truyvan);

        if ($ketqua) {
            $truyvanxoachitietkm = "DELETE FROM chitietkhuyenmai WHERE MAKM='".$makm."'";
    		mysqli_query($conn,$truyvanxoachitietkm);

            $dem = count($mangmasp);
            for ($i=0; $i < $dem; $i++) {
                $masp = $mangmasp[$i];
                $phantramkm = $mangphantramkm[$i];

                $truyvanchitiet = "INSERT INTO chitietkhuyenmai(MASP, MAKM, PHANTRAMKM)
                                    VALUES('".$masp."','".$makm."','".$phantramkm."')";
    			mysqli_query($conn, $truyvanchitiet);
            }
            echo "Cập nhập thành công";
        }
    }

    function LayChiTietKhuyenMai_Ajax()
    {
        global $conn;
        $makm = $_POST["makm"];

        $truyvan = "SELECT * FROM chitietkhuyenmai ctkm, sanpham sp
                    WHERE ctkm.MASP = sp.MASP AND MAKM='".$makm."'";
        $ketqua = mysqli_query($conn, $truyvan);

        if($ketqua){
            while ($dong = mysqli_fetch_array($ketqua)) {
                echo '<tr>';
                echo '<th>Tên sản phẩm : <input name="mangsanpham[]" data-masp="'.$dong["MASP"].'" value="'.$dong["TENSP"].'" style="margin:5px; padding:5px; width:60%"  disabled type="text"  />';
                echo '</th>';
                echo '<th>Phần trăm khuyến mãi: <input data-masp="'.$dong["MASP"].'" disabled value="'.$dong["PHANTRAMKM"].'" style="margin:5px; padding:5px; width:60%" name="mangphantramkm[]" type="text"  /><a class="btn btn-danger btnxoachitiethoadon">Xóa</a>';
                echo '</th>';
                echo '</tr>';
            }
        }
    }

    function ThemKhuyenMaiVaChiTietKhuyenMai_Ajax()
    {
        global $conn;

        $tenkhuyenmai = $_POST["tenkhuyenmai"];
		$maloaisp = $_POST["maloaisp"];
		$ngaybatdau = $_POST["ngaybatdau"];
		$ngayketthuc = $_POST["ngayketthuc"];
		$hinhkhuyenmai = $_POST["hinhkhuyenmai"];
		$mangmasp = $_POST["mangmasp"];
		$mangphantramkm = $_POST["mangphantramkm"];

        $truyvan = "INSERT INTO khuyenmai(MALOAISP, TENKM, NGAYBATDAU, NGAYKETTHUC, HINHKHUYENMAI)
                    VALUES('".$maloaisp."','".$tenkhuyenmai."','".$ngaybatdau."','".$ngayketthuc."','".$hinhkhuyenmai."')";
		$ketqua = mysqli_query($conn, $truyvan);

        $makm = mysqli_insert_id($conn);
        $dem = count($mangmasp);

        for($i=0; $i < $dem; $i++){
			$masp = $mangmasp[$i];
            $phantramkm = $mangphantramkm[$i];

			$truyvanchitiet = "INSERT INTO chitietkhuyenmai(MASP, MAKM, PHANTRAMKM)
                                VALUES('".$masp."','".$makm."','".$phantramkm."')";
			mysqli_query($conn, $truyvanchitiet);
		}

        if($ketqua){
			echo "Thêm thành công !";
		}else{
			echo "Thêm thất bại !";
		}
    }
    function LayDanhSachSanPhamTheoMaLoaiSP_Ajax()
    {
        global $conn;
        $maloaisp = $_POST["maloaisp"];

        $truyvan = "SELECT * FROM sanpham
					WHERE MALOAISP = $maloaisp
					ORDER BY MASP DESC";
		$ketqua = mysqli_query($conn,$truyvan);

        if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
                echo "<option value='".$dong["MASP"]."'>".$dong["TENSP"]."</option>";
            }
        }
    }

    function TimKiemSanPham_Ajax(){
		global $conn;
        $limit = ($_POST["sotrang"]-1)*10;
		$noidungtimkiem = $_POST["noidungtimkiem"];

		$truyvan = "SELECT * FROM sanpham sp, loaisanpham lsp, thuonghieu th
					WHERE sp.MALOAISP = lsp.MALOAISP AND sp.MATHUONGHIEU = th.MATHUONGHIEU
                    AND sp.TENSP LIKE '%".$noidungtimkiem."%'
					ORDER BY sp.MASP DESC
					LIMIT ".$limit.",10";
		$ketqua = mysqli_query($conn,$truyvan);

        //Tính tổng số trang
        $truyvantrang = "SELECT * FROM sanpham sp, loaisanpham lsp, thuonghieu th
    					WHERE sp.MALOAISP = lsp.MALOAISP AND sp.MATHUONGHIEU = th.MATHUONGHIEU
                        AND sp.TENSP LIKE '%".$noidungtimkiem."%'";
        $ketquatrang = mysqli_query($conn,$truyvantrang);
        $tongsotrang = ceil(mysqli_num_rows($ketquatrang)/10);

		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo '<tr data-tongsotrang="'.$tongsotrang.'">';
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

    function TimKiemHoaDon_Ajax(){
		global $conn;
        $limit = ($_POST["sotrang"]-1)*10;
		$noidungtimkiem = $_POST["noidungtimkiem"];
		$truyvan = "SELECT * FROM hoadon WHERE TENNGUOINHAN LIKE '%".$noidungtimkiem."%'
                    OR SODT LIKE '%".$noidungtimkiem."%'
                    OR NGAYMUA LIKE '%".$noidungtimkiem."%'
                    OR MACHUYENKHOAN LIKE '%".$noidungtimkiem."%'
                    OR MADOITAC LIKE '%".$noidungtimkiem."%'
                    ORDER BY MAHD DESC
                    LIMIT ".$limit.", 10";
		$ketqua = mysqli_query($conn,$truyvan);

        //Tính tổng số trang
        $truyvantrang = "SELECT * FROM hoadon WHERE TENNGUOINHAN LIKE '%".$noidungtimkiem."%'
                        OR SODT LIKE '%".$noidungtimkiem."%'
                        OR NGAYMUA LIKE '%".$noidungtimkiem."%'
                        OR MACHUYENKHOAN LIKE '%".$noidungtimkiem."%'
                        OR MADOITAC LIKE '%".$noidungtimkiem."%'";
        $ketquatrang = mysqli_query($conn,$truyvantrang);
        $tongsotrang = ceil(mysqli_num_rows($ketquatrang)/10);
		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
                $chuyenkhoan = $dong["CHUYENKHOAN"] !=null && $dong["CHUYENKHOAN"] != '0' ? 'có' : 'không';
                $madoitac = $dong["MADOITAC"] !=null && $dong["MADOITAC"] != '0' ? $dong["MADOITAC"] : 'không có';
                echo '<tr data-tongsotrang="'.$tongsotrang.'">';
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

    function CapNhatHoaDonVsChiTietHoaDon_Ajax(){
		global $conn;

		$mahd = $_POST["mahd"];
		$tennguoinhan = $_POST["tennguoinhan"];
		$sodt = $_POST["sodt"];
		$diachi = $_POST["diachi"];
		$machuyenkhoan = $_POST["machuyenkhoan"];
		$ngaymua = $_POST["ngaymua"];
		$ngaygiao = $_POST["ngaygiao"];
		$tinhtrang = $_POST["tinhtrang"];
		$chuyenkhoan = $_POST["chuyenkhoan"];
		$mangmasp = $_POST["mangmasp"];
		$mangsoluong = $_POST["mangsoluong"];
		$mangtensp = $_POST["mangtensp"];

		$dem = count($mangmasp);
		$kiemtra = false;
		$chuoithongbao = "Các sản phẩm sau không đáp ứng được đơn hàng : ";

        $ngayhientai = date("Y/m/d");
        $tongtien = 0;
		for ($i=0; $i < $dem; $i++) {
			$masp = $mangmasp[$i];
			$soluong = $mangsoluong[$i];
			$tensp = $mangtensp[$i];

			$soluongtonkho = LaySoLuongSanPhamTonKho($masp);
			if($soluongtonkho < $soluong){
				$chuoithongbao .= "Tên sản phẩm : ".$tensp." - Số lượng : ".($soluongtonkho - $soluong);
				$kiemtra = true;
			}

            $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                        FROM khuyenmai km, chitietkhuyenmai ctkm
                        WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$masp."'";
            $ketquakhuyenmai = mysqli_query($conn,$truyvankhuyenmai);

            $phantramkm = 0;
            if($ketquakhuyenmai){
                while ($dongkhuyenmai = mysqli_fetch_array($ketquakhuyenmai)) {
                    $thoigiankm = $dongkhuyenmai["THOIGIANKM"];

                    if($thoigiankm > 0){
                        $phantramkm = $dongkhuyenmai["PHANTRAMKM"];
                    }
                }
            }

            $tongtien += $soluong * LayGiaTienSanPham($masp) * ((100 - $phantramkm) / 100);
		}

		if(!$kiemtra){
			if($tinhtrang=="chờ kiểm duyệt" || $tinhtrang=="hoàn thành"){
			CapNhatHoaDon($ngaymua,$ngaygiao,$tinhtrang,$tennguoinhan,$sodt,$diachi,$chuyenkhoan,$machuyenkhoan,$mahd, $tongtien);
			XoaChiTietHoaDon($mahd);

			for ($i=0; $i < $dem; $i++) {
				$masp = $mangmasp[$i];
				$soluong = $mangsoluong[$i];
				ThemChiTietHoaDon($mahd,$masp,$soluong);
			}
            echo "Cập nhập thành công";

			}else if($tinhtrang == "đã hủy"){
				for ($i=0; $i < $dem; $i++) {
					$masp = $mangmasp[$i];
					$soluong = $mangsoluong[$i];

					CapNhatHoaDon($ngaymua,$ngaygiao,$tinhtrang,$tennguoinhan,$sodt,$diachi,$chuyenkhoan,$machuyenkhoan,$mahd, $tongtien);
					CapNhatSoLuongSanPham($masp,$soluong,true);
				}
                echo "Cập nhập thành công";

			}else if($tinhtrang == "đang giao hàng"){
				for ($i=0; $i < $dem; $i++) {
					$masp = $mangmasp[$i];
					$soluong = $mangsoluong[$i];

					CapNhatHoaDon($ngaymua,$ngaygiao,$tinhtrang,$tennguoinhan,$sodt,$diachi,$chuyenkhoan,$machuyenkhoan,$mahd, $tongtien);
					CapNhatSoLuongSanPham($masp,$soluong,false);
                    TangLuotMuaSanPham($masp);
				}
                echo "Cập nhập thành công";
			}
		}else{
			echo $chuoithongbao;
		}
	}

    function TangLuotMuaSanPham($masp){
        global $conn;

        $truyvan = "SELECT LUOTMUA FROM sanpham WHERE MASP='".$masp."'";
        $ketqua = mysqli_query($conn, $truyvan);
        $luotmua = 0;
        if($ketqua){
            while ($dong = mysqli_fetch_array($ketqua)) {
                $luotmua = $dong["LUOTMUA"];
            }
        }

        $luotmua = $luotmua + 1;

        $truyvancapnhap = "UPDATE sanpham SET LUOTMUA='".$luotmua."' WHERE MASP='".$masp."'";
        mysqli_query($conn, $truyvancapnhap);
    }

    function CapNhatSoLuongSanPham($masp,$soluong,$kiemtra){
        global $conn;
        $soluongtonkho = LaySoLuongSanPhamTonKho($masp);

        if($kiemtra){
            $soluongtonkho += $soluong;
        }else{
            $soluongtonkho -= $soluong;
        }

        $truyvan = "UPDATE sanpham SET SOLUONG='".$soluongtonkho."' WHERE MASP='".$masp."'";
        mysqli_query($conn,$truyvan);
    }

    function LaySoLuongSanPhamTonKho($masp){
        global $conn;
        $truyvan = "SELECT * FROM sanpham WHERE MASP='".$masp."'";
        $ketqua = mysqli_query($conn,$truyvan);
        $soluongtonkho = 0;
        if($ketqua){
            while ($dong = mysqli_fetch_array($ketqua)) {
                $soluongtonkho = $dong["SOLUONG"];
            }
        }

        return $soluongtonkho;
    }

    function LayGiaTienSanPham($masp){
        global $conn;
        $truyvan = "SELECT * FROM sanpham WHERE MASP='".$masp."'";
        $ketqua = mysqli_query($conn,$truyvan);
        $giatien = 0;
        if($ketqua){
            while ($dong = mysqli_fetch_array($ketqua)) {
                $giatien = $dong["GIA"];
            }
        }

        return $giatien;
    }

    function CapNhatHoaDon($ngaymua,$ngaygiao,$tinhtrang,$tennguoinhan,$sodt,$diachi,$chuyenkhoan,$machuyenkhoan,$mahd, $tongtien){
        global $conn;
        $truyvanhoadon = "UPDATE hoadon
                        SET NGAYMUA='".$ngaymua."', NGAYGIAO='".$ngaygiao."',
                            TRANGTHAI='".$tinhtrang."', TENNGUOINHAN='".$tennguoinhan."',
                            SODT='".$sodt."', DIACHI='".$diachi."', CHUYENKHOAN='".$chuyenkhoan."',
                            MACHUYENKHOAN='".$machuyenkhoan."', TONGTIEN='".$tongtien."'
                        WHERE MAHD='".$mahd."'";
        $ketqua = mysqli_query($conn,$truyvanhoadon);
    }

    function XoaChiTietHoaDon($mahd){
		global $conn;
		$truyvanxoachitiethd = "DELETE FROM chitiethoadon WHERE MAHD='".$mahd."'";
		mysqli_query($conn,$truyvanxoachitiethd);
	}

	function ThemChiTietHoaDon($mahd,$masp,$soluong){
		global $conn;
		$truyvanthemchitiethoadon = " INSERT INTO chitiethoadon(MAHD,MASP,SOLUONG) VALUES('".$mahd."','".$masp."','".$soluong."')";
		mysqli_query($conn,$truyvanthemchitiethoadon);
	}

    function LayChiTietHoaDon_Ajax(){
        global $conn;
        $mahd = $_POST["mahd"];

        $truyvan = "SELECT sp.TENSP, cthd.MASP,cthd.MAHD,cthd.SOLUONG FROM chitiethoadon cthd, sanpham sp WHERE cthd.MASP = sp.MASP AND cthd.MAHD='".$mahd."'";
        $ketqua = mysqli_query($conn,$truyvan);

        if($ketqua){
            while ($dong = mysqli_fetch_array($ketqua)) {
                echo '<tr>
                    <th>
                        Tên sản phẩm : <input disabled style="margin:5px; padding:5px; width:60%" data-masp="'.$dong["MASP"].'" name="mangsanpham[]" type="text" value="'.$dong["TENSP"].'" />
                    </th>

                    <th>
                        Số lượng : <input disabled data-masp="'.$dong["MASP"].'" style="margin:5px; padding:5px; width:60%" name="mangsoluong[]" type="text" value="'.$dong["SOLUONG"].'"  />
                        <a class="btn btn-danger  btnxoachitiethoadon">Xóa</a>
                    </th>
                </tr>';
            }

        }
    }

    function KiemTraDangNhap_Ajax(){
        global $conn;
        session_start();
        $tendangnhap = $_POST["tendangnhap"];
        $matkhau = $_POST["matkhau"];
        $nhotaikhoan = $_POST["nhotaikhoan"];

        $truyvan = "SELECT * FROM nhanvien WHERE TENDANGNHAP='".$tendangnhap."' AND MATKHAU='".md5($matkhau)."'";
        $ketqua = mysqli_query($conn, $truyvan);

        if($nhotaikhoan){
            setcookie("tendangnhap",$tendangnhap,time() + (86400 * 7),"/");
            setcookie("matkhau",$matkhau,time() + (86400 * 7),"/");
        }

        if($ketqua){
            $sodong = mysqli_num_rows($ketqua);
            if($sodong > 0){
                while ($dong = mysqli_fetch_array($ketqua)) {
                    $_SESSION["tenv"] = $dong["TENNV"];
                    $_SESSION["email"] = $dong["TENDANGNHAP"];
                    $_SESSION["manv"] = $dong["MANV"];
                    $_SESSION["maloainv"] = $dong["MALOAINV"];
                    echo 1;
                }
            }else{
                echo 0;
            }
        }

    };

    function LayDanhSachHoaDonLimit_Ajax(){
		global $conn;
        $limit = ($_POST["sotrang"]-1)*10;
		$truyvan = "SELECT * FROM hoadon ORDER BY MAHD DESC LIMIT ".$limit.",10";
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

    function LayDanhSachSanPhamLimit_Ajax(){
        global $conn;
        $limit = ($_POST["sotrang"]-1)*10;
        $truyvan = "SELECT * FROM sanpham sp, loaisanpham lsp, thuonghieu th
                    WHERE sp.MALOAISP = lsp.MALOAISP AND sp.MATHUONGHIEU = th.MATHUONGHIEU
                    ORDER BY sp.MASP DESC
                    LIMIT ".$limit.",10";
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

	function CapNhatSanPhamTheoMaSP_Ajax(){
		global $conn;
		$masp = $_POST["masp"];
		$tensp = $_POST["tensanpham"];
		$giasanpham = $_POST["giasanpham"];
		$soluong = $_POST["soluong"];
		$maloaisp = $_POST["maloaisp"];
		$mathuonghieu = $_POST["mathuonghieu"];
		$anhlon = $_POST["anhlon"];
		$anhnho = $_POST["anhnho"];
		$mota = $_POST["mota"];
		$manv = 1;
		$mangtenchitiet = $_POST["mangtenchitiet"];
		$manggiatrichitiet = $_POST["manggiatrichitiet"];
		$mangmachitietsanpham = $_POST["mangmachitietsanpham"];
		$manggiatrichitietsanphambosung = $_POST["manggiatrichitietsanphambosung"];
		$mangtenchitietsanphambosung = $_POST["mangtenchitietsanphambosung"];

		$truyvan = "UPDATE sanpham SET TENSP='".$tensp."', GIA='".$giasanpham."', SOLUONG='".$soluong."', MALOAISP='".$maloaisp."', MATHUONGHIEU='".$mathuonghieu."', ANHLON='".$anhlon."', ANHNHO='".$anhnho."', THONGTIN='".$mota."', MANV='".$manv."' WHERE MASP='".$masp."'";
		$ketqua = mysqli_query($conn,$truyvan);

		if($ketqua){

			$dem = count($mangmachitietsanpham);
			for($i=0;$i<$dem;$i++){
				$tenchitiet = $mangtenchitiet[$i];
				$giatrichitiet = $manggiatrichitiet[$i];
				$machitietsanpham = $mangmachitietsanpham[$i];

				$truyvanchitiet= "UPDATE chitietsanpham SET TENCHITIET='".$tenchitiet."', GIATRI='".$giatrichitiet."' WHERE MACHITIET='".$machitietsanpham."'";

				$ketquachitiet = mysqli_query($conn,$truyvanchitiet);
			}

			$demchitietbosung = count($manggiatrichitietsanphambosung);
			if($demchitietbosung > 0){
				for( $i=0; $i<$demchitietbosung; $i++){
					$truythemvanchitiet = "INSERT INTO chitietsanpham (MASP,TENCHITIET,GIATRI) VALUES('".$masp."','".$mangtenchitietsanphambosung[$i]."','".$manggiatrichitietsanphambosung[$i]."')";
					 mysqli_query($conn,$truythemvanchitiet);
				}

			}

			echo "Cập nhập thành công !";
		}else{
			echo "Cập nhập thất bại !";
		}

	}

	function LayChiTietSanPhamTheoMa_Ajax(){
		global $conn;
		$masp = $_POST["masp"];

		$truyvan = "SELECT * FROM chitietsanpham WHERE MASP='".$masp."'";
		$ketqua = mysqli_query($conn,$truyvan);

		if($ketqua){
			while ($dong = mysqli_fetch_array($ketqua)) {
				echo '<tr>
					<th>
						<input type="text" id="mangmachitietsanpham" name="mangmachitietsanpham[]" class="anbutton" value="'.$dong["MACHITIET"].'"/>
						Tên chi tiết : <input style="margin:5px; padding:5px; width:60%" name="mangtenchitietsanpham[]" type="text" value="'.$dong["TENCHITIET"].'" />
					</th>

					<th>
						Giá trị : <input style="margin:5px; padding:5px; width:60%" name="manggiatrichitietsanpham[]" type="text" value="'.$dong["GIATRI"].'"  />
						<a class="btn btn-primary anbutton btnthemchitietsanpham">Thêm</a> <a class="btn btn-danger  btnxoachitietsanpham">Xóa</a>
					</th>
				</tr>';
			}
		}
	}

	function XoaSanPham_Ajax(){
		global $conn;
		$masp = $_POST["masp"];
		$kiemtra = 0;

		if(XoaChiTietKhuyenMaiTheoMASP($masp)){
			$kiemtra = 1;
		}else{
			$kiemtra = 0;
		}

		if(XoaChiTietSanPhamTheoMASP($masp)){
			$kiemtra = 1;
		}else{
			$kiemtra = 0;
		}

		if(XoaChiTietHoaDonTheoMASP($masp)){
			$kiemtra = 1;
		}else{
			$kiemtra = 0;
		}

		if(XoaDanhGiaTheoMASP($masp)){
			$kiemtra = 1;
		}else{
			$kiemtra = 0;
		}

		$truyvan = "DELETE FROM sanpham WHERE MASP=".$masp;
		$ketqua = mysqli_query($conn,$truyvan);

		if($ketqua){
			$kiemtra = 1;
		}else{
			$kiemtra = 0;
		}
		echo $kiemtra;
	}

    function ThemSanPham_Ajax(){
		global $conn;
		$tensp = $_POST["tensanpham"];
		$giasanpham = $_POST["giasanpham"];
		$soluong = $_POST["soluong"];
		$maloaisp = $_POST["maloaisp"];
		$mathuonghieu = $_POST["mathuonghieu"];
		$anhlon = $_POST["anhlon"];
		$anhnho = $_POST["anhnho"];
		$mota = $_POST["mota"];
		$manv = 1;
		$mangtenchitiet = $_POST["mangtenchitiet"];
		$manggiatrichitiet = $_POST["manggiatrichitiet"];

		$truyvan = "INSERT INTO sanpham(TENSP,GIA,SOLUONG,MALOAISP,MATHUONGHIEU,ANHLON,ANHNHO,THONGTIN,MANV,LUOTMUA) VALUES('".$tensp."','".$giasanpham."','".$soluong."','".$maloaisp."','".$mathuonghieu."','".$anhlon."','".$anhnho."','".$mota."','".$manv."',0)";
		$ketqua = mysqli_query($conn,$truyvan);

		$masp = mysqli_insert_id($conn);

		$dem = count($manggiatrichitiet);
		for($i=0;$i<$dem;$i++){
			$tenchitiet = $mangtenchitiet[$i];
			$giatrichitiet = $manggiatrichitiet[$i];

			$truyvanchitiet = "INSERT INTO chitietsanpham(MASP,TENCHITIET,GIATRI) VALUES('".$masp."','".$tenchitiet."','".$giatrichitiet."')";
			mysqli_query($conn,$truyvanchitiet);
		}

		if($ketqua){
			echo "Thêm thành công !";
		}else{
			echo "Thêm thất bại !";
		}
    }

    function TimKiemLoaiSanPhamTheoTen_Ajax(){
		global $conn;
		$tenloaisp = $_POST["noidungtimkiem"];
		$truyvan = "SELECT * FROM loaisanpham WHERE TENLOAISP LIKE '%".$tenloaisp."%'";
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
