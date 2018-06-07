<?php
    include_once 'config.php';

    // $ham = $_GET["ham"];
    $ham = $_POST["ham"];

    switch ($ham) {
        case "LayDanhSachMenu":
            $ham(); //LayDanhSachMenu()
            break;

        case"DangKyThanhVien":
            $ham();
            break;

        case"KiemTraDangNhap":
            $ham();
            break;

        case"LayDanhSachCacThuongHieuLon":
            $ham();
            break;

        case"LayDanhSachTopDienThoaiVaMayTinhBang":
            $ham();
            break;

        case"LayDanhSachTopPhuKien":
            $ham();
            break;

        case"LayDanhSachPhuKien":
            $ham();
            break;

        case"LayDanhSachTienIch":
            $ham();
            break;

        case"LayTopTienIch":
            $ham();
            break;

        case"LayLogoThuongHieuLon":
            $ham();
            break;

        case"LayDanhSachSanPhamMoiVe":
            $ham();
            break;

        case"LayDanhSachSanPhamTheoMaThuongHieu":
            $ham();
            break;

        case"LayDanhSachSanPhamTheoMaLoaiDanhMuc":
            $ham();
            break;

        case"LaySanPhamVaChiTietTheoMaSP":
            $ham();
            break;

        case"ThemDanhGia":
            $ham();
            break;

        case"LayDanhSachDanhGiaTheoMaSP":
            $ham();
            break;

        case"ThemHoaDon":
            $ham();
            break;

        case"LayDanhSachKhuyenMai":
            $ham();
            break;

        case"TimKiemSanPhamTheoTenSP":
            $ham();
            break;

		case"LayDanhSachSanPhamTheoThuongHieu":
            $ham();
            break;

        case"LayTopSanPhamTrongThang":
            $ham();
            break;

        case"LayDanhSachSmartPhoneGiaRe":
            $ham();
            break;

        case"LayTopDanhSachThietBiLuuTru":
            $ham();
            break;

        case"LayTopDanhSachTiViManHinhLon":
            $ham();
            break;
    }

    function LayTopDanhSachTiViManHinhLon()
    {
        global $conn;
        $ngayhientai = date("Y/m/d");
        $chuoijson = array();

        if(isset($_POST["maloaisp"]) || isset($_POST["limit"])) {
            $maloaisp = $_POST["maloaisp"];
            $limit = $_POST["limit"];
        }

        $truyvan = "SELECT * FROM sanpham
                    WHERE MALOAISP IN(
                        SELECT MALOAISP FROM loaisanpham
                        WHERE MALOAI_CHA = $maloaisp OR MALOAISP = $maloaisp)
                    ORDER BY MALOAISP DESC, LUOTMUA DESC
                    LIMIT ".$limit.", 20";
        $ketqua = mysqli_query($conn, $truyvan);

        echo "{";
        echo "\"DANHSACHSANPHAM\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {

                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                           FROM khuyenmai km, chitietkhuyenmai ctkm
                           WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHNHO"],
                                            "PHANTRAMKM"=>$phantramkm,
                                            "MANV"=>$dong["MANV"]
                                        ));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

    }

    function LayTopDanhSachThietBiLuuTru()
    {
        global $conn;
        $ngayhientai = date("Y/m/d");
        $chuoijson = array();

        if(isset($_POST["maloaisp"]) || isset($_POST["limit"])) {
            $maloaisp = $_POST["maloaisp"];
            $limit = $_POST["limit"];
        }

        $truyvan = "SELECT * FROM sanpham
                    WHERE MALOAISP IN(
                        SELECT MALOAISP FROM loaisanpham
                        WHERE MALOAI_CHA = $maloaisp OR MALOAISP = $maloaisp)
                    ORDER BY LUOTMUA DESC
                    LIMIT ".$limit.", 20";
        $ketqua = mysqli_query($conn, $truyvan);

        echo "{";
        echo "\"DANHSACHSANPHAM\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {

                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                           FROM khuyenmai km, chitietkhuyenmai ctkm
                           WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHNHO"],
                                            "PHANTRAMKM"=>$phantramkm,
                                            "MANV"=>$dong["MANV"]
                                        ));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

    }

    function LayDanhSachSmartPhoneGiaRe()
    {
        global $conn;
        $ngayhientai = date("Y/m/d");
        $chuoijson = array();

        if(isset($_POST["maloaisp"]) || isset($_POST["limit"])) {
            $maloaisp = $_POST["maloaisp"];
            $limit = $_POST["limit"];
        }

        $truyvan = "SELECT *, AVG(SOSAO) FROM sanpham sp, danhgia dg
                    WHERE sp.MASP = dg.MASP AND sp.MALOAISP = $maloaisp
                    GROUP BY sp.MASP
                    HAVING AVG(SOSAO) > 3
                    LIMIT $limit, 20";
        $ketqua = mysqli_query($conn, $truyvan);

        echo "{";
        echo "\"DANHSACHSANPHAM\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {

                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                           FROM khuyenmai km, chitietkhuyenmai ctkm
                           WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHNHO"],
                                            "PHANTRAMKM"=>$phantramkm,
                                            "MANV"=>$dong["MANV"]
                                        ));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

    }

    function LayTopSanPhamTrongThang()
    {
        global $conn;
        $chuoijson = array();
        $truyvan = 0;

        $ngayhientai = date("Y/m/d");
        $thanghientai = date("m");
        $namhientai = date("Y");
        $namtruocdo = 0;
        if($thanghientai - 1 <= 0) {

            $truyvan = "SELECT * FROM
                        (SELECT * FROM SANPHAM
                            WHERE MASP IN(SELECT DISTINCT cthd.MASP
                                            FROM chitiethoadon cthd, hoadon hd
                                            WHERE hd.MAHD = cthd.MAHD
                                            AND MONTH(hd.NGAYMUA) = $thanghientai
                                            AND YEAR(hd.NGAYMUA) = $namhientai
                                            UNION
                                            SELECT DISTINCT cthd.MASP
                                            FROM chitiethoadon cthd, hoadon hd
                                            WHERE hd.MAHD = cthd.MAHD
                                            AND MONTH(hd.NGAYMUA) = 12
                                            AND YEAR(hd.NGAYMUA) = $namhientai - 1)
                            ORDER BY LUOTMUA DESC LIMIT 150) AS SANPHAM
                            LIMIT 0, 20";
        }else {
            $truyvan = "SELECT * FROM
                        (SELECT * FROM SANPHAM
                            WHERE MASP IN(SELECT DISTINCT cthd.MASP
                                            FROM chitiethoadon cthd, hoadon hd
                                            WHERE hd.MAHD = cthd.MAHD
                                            AND MONTH(hd.NGAYMUA) IN($thanghientai, $thanghientai - 1)
                                            AND YEAR(hd.NGAYMUA) = $namhientai)
                            ORDER BY LUOTMUA DESC LIMIT 150) AS SANPHAM
                            LIMIT 0, 20";
        }


		$ketqua = mysqli_query($conn, $truyvan);

        echo "{";
        echo "\"DANHSACHSANPHAM\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {

                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                           FROM khuyenmai km, chitietkhuyenmai ctkm
                           WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHNHO"],
                                            "PHANTRAMKM"=>$phantramkm,
                                            "MANV"=>$dong["MANV"]
                                        ));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";
    }

    function TimKiemSanPhamTheoTenSP() {
        global $conn;
        $chuoijson = array();
        $ngayhientai = date("Y/m/d");

        if(isset($_POST["tensp"]) || isset($_POST["limit"])) {
            $tensp = $_POST["tensp"];
            $limit = $_POST["limit"];
        }

        $truyvan = " SELECT * FROM sanpham sp
                    WHERE sp.TENSP like '%".$tensp."%' ORDER BY sp.MASP LIMIT ".$limit.",10";
		$ketqua = mysqli_query($conn, $truyvan);

        echo "{";
        echo "\"DANHSACHSANPHAM\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "HINHSANPHAM"=>$dong["ANHLON"],
                                            "HINHSANPHAMNHO"=>$dong["ANHNHO"],
                                            "PHANTRAMKM"=>$phantramkm,
                                            "MANV"=>$dong["MANV"]
                                        ));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";
    }

    function LayDanhSachKhuyenMai() {
        global $conn;
        $chuoijson = array();

        $ngayhientai = date("Y/m/d");
        $truyvan = "SELECT * FROM khuyenmai km, loaisanpham lsp
                    WHERE DATEDIFF(km.NGAYKETTHUC, '".$ngayhientai."') >= 0
                    AND km.MALOAISP = lsp.MALOAISP";
        $ketqua = mysqli_query($conn, $truyvan);

        echo "{";
        echo "\"DANHSACHKHUYENMAI\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {
                $truyvanchitietkhuyenmai = "SELECT * FROM chitietkhuyenmai ctkm, sanpham sp
                            WHERE ctkm.MAKM = '".$dong["MAKM"]."'
                            AND ctkm.MASP = sp.MASP";
                $ketquakhuyenmai = mysqli_query($conn, $truyvanchitietkhuyenmai);

                $chuoijsondanhsachsanpham = array();

                if($ketquakhuyenmai) {
                    while ($dongkhuyenmai = mysqli_fetch_array($ketquakhuyenmai)) {
                        $chuoijsondanhsachsanpham[] = $dongkhuyenmai;
                    }
                }

                array_push($chuoijson, array("MAKM"=>$dong["MAKM"],
                                        "TENLOAISP"=>$dong["TENLOAISP"],
                                        "TENKM"=>$dong["TENKM"],
                                        "HINHKHUYENMAI"=>$dong["HINHKHUYENMAI"],
                                        "DANHSACHSANPHAM"=>$chuoijsondanhsachsanpham));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";
    }

    function ThemHoaDon() {
        global $conn;

        if(isset($_POST["danhsachsanpham"]) || isset($_POST["tennguoinhan"]) || isset($_POST["sodt"]) || isset($_POST["diachi"]) || isset($_POST["chuyenkhoan"])) {
            $danhsachsanpham = $_POST["danhsachsanpham"];
            $tennguoinhan = $_POST["tennguoinhan"];
            $sodt = $_POST["sodt"];
            $diachi = $_POST["diachi"];
            $chuyenkhoan = $_POST["chuyenkhoan"];
        }

        $ngaymua = date("d/m/Y");
        $ngayhientai = date("Y/m/d");
        $ngaygiao = date_create($ngayhientai);
        $ngaygiao = date_modify($ngaygiao, "+3 days");
        $ngaygiao = date_format($ngaygiao, "d/m/Y");

        $trangthai = "chờ kiểm duyệt";

        $truyvan = "INSERT INTO hoadon (NGAYMUA, NGAYGIAO, TRANGTHAI, TENNGUOINHAN, SODT, DIACHI, CHUYENKHOAN)
                    VALUES ('".$ngaymua."', '".$ngaygiao."', '".$trangthai."', '".$tennguoinhan."', '".$sodt."', '".$diachi."', '".$chuyenkhoan."')";
        $ketqua = mysqli_query($conn, $truyvan);

        if ($ketqua) {
            $mahd = mysqli_insert_id($conn);
            $chuoijsonandroid = json_decode($danhsachsanpham);
            $arrayDanhSachSanPham = $chuoijsonandroid->DANHSACHSANPHAM;
            $dem = count($arrayDanhSachSanPham);

            for($i = 0; $i < $dem; $i++) {
                $jsonObject = $arrayDanhSachSanPham[$i];
                $masp = $jsonObject->masp;
                $soluong = $jsonObject->soluong;

                $truyvan = "INSERT INTO chitiethoadon (MAHD, MASP, SOLUONG)
                            VALUES ('".$mahd."', '".$masp."', '".$soluong."')";
                $ketqua1 = mysqli_query($conn, $truyvan);
            }

            echo "{ketqua:true}";

        } else {
            echo "{ketqua:false}".mysqli_error($conn);
        }

    }

    function LayDanhSachDanhGiaTheoMaSP()
    {
        global $conn;
        $chuoijson = array();

        if(isset($_POST["masp"]) || isset($_POST["limit"])) {
            $masp = $_POST["masp"];
            $limit = $_POST["limit"];
        }

        $truyvan = "SELECT * FROM danhgia
                    WHERE MASP = ".$masp."
                    ORDER BY NGAYDANHGIA
                    LIMIT ".$limit." , 20";
        $ketqua = mysqli_query($conn, $truyvan);
        echo "{";
        echo "\"DANHSACHDANHGIA\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {
                $chuoijson[] = $dong;
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";
    }

    function ThemDanhGia() {
        global $conn;

        if(isset($_POST["madg"]) || isset($_POST["masp"]) || isset($_POST["tenthietbi"]) || isset($_POST["tieude"]) || isset($_POST["noidung"]) || isset($_POST["sosao"])) {
            $madg = $_POST["madg"];
            $masp = $_POST["masp"];
            $tenthietbi = $_POST["tenthietbi"];
            $tieude = $_POST["tieude"];
            $noidung = $_POST["noidung"];
            $sosao = $_POST["sosao"];
        }

        $ngaydang = date("d/m/Y");

        $truyvan = "INSERT INTO danhgia (MADG, MASP, TENTHIETBI, TIEUDE, NOIDUNG, SOSAO, NGAYDANHGIA)
                    VALUES ('".$madg."', '".$masp."', '".$tenthietbi."', '".$tieude."', '".$noidung."', '".$sosao."', '".$ngaydang."')";
        $ketqua = mysqli_query($conn, $truyvan);

        if ($ketqua) {
            echo "{ketqua:true}";
        } else {
            echo "{ketqua:false}".mysqli_error($conn);
        }

    }

    function LaySanPhamVaChiTietTheoMaSP()
    {
        global $conn;
        $chuoijson = array();
        $chuoijsonchitiet = array();
        if(isset($_POST["masp"])) {
            $masp = $_POST["masp"];
        }

        $ngayhientai = date("Y/m/d");

        $truyvan = "SELECT * FROM sanpham sp, nhanvien nv
                    WHERE sp.MASP=".$masp." AND sp.MANV = nv.MANV";
        $ketqua = mysqli_query($conn,$truyvan);

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

        echo "{";
        echo "\"CHITIETSANPHAM\":";

        $truyvanchitiet = "SELECT * FROM chitietsanpham WHERE MASP = ".$masp;
        $ketquachitiet = mysqli_query($conn, $truyvanchitiet);

        if($ketquachitiet) {
            while ($dongchitiet = mysqli_fetch_array($ketquachitiet)) {
                array_push($chuoijsonchitiet, array($dongchitiet["TENCHITIET"]=>$dongchitiet["GIATRI"]
                                                    ));
            }
        }

        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {

                array_push($chuoijson,array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "SOLUONG"=>$dong["SOLUONG"],
	                                        "ANHNHO"=>$dong["ANHNHO"],
                                            "THONGTIN"=>$dong["THONGTIN"],
                                            "MALOAISP"=>$dong["MALOAISP"],
                                            "MATHUONGHIEU"=>$dong["MATHUONGHIEU"],
	                                        "MANV"=>$dong["MANV"],
                                            "TENNV"=>$dong["TENNV"],
                                            "LUOTMUA"=>$dong["LUOTMUA"],
                                            "PHANTRAMKM"=>$phantramkm,
                                            "THONGSOKYTHUAT"=>$chuoijsonchitiet
                                        ));
            }
        }

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachSanPhamTheoMaThuongHieu()
    {
        global $conn;
        $chuoijson = array();
        if(isset($_POST["maloaisp"]) || isset($_POST["maloaisanpham"]) || isset($_POST["limit"])) {
            $maloaith = $_POST["maloaisp"];
            $maloaisanpham = $_POST["maloaisanpham"];
            $limit = $_POST["limit"];
        }

        echo "{";
        echo "\"DANHSACHSANPHAM\":";

        $chuoijson = LayDanhSachSanPhamTheoMaLoaiThuongHieu($conn, $maloaith, $maloaisanpham, $chuoijson, $limit);

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachSanPhamTheoThuongHieu()
    {
        global $conn;
        if(isset($_POST["math"]) || isset($_POST["limit"])) {
            $math = $_POST["math"];
            $limit = $_POST["limit"];
        }

        $ngayhientai = date("Y/m/d");
        $truyvanmaloai = "SELECT * FROM sanpham
                    WHERE MATHUONGHIEU = ".$math."
                    ORDER BY LUOTMUA DESC LIMIT ".$limit.", 20";
        $ketqua = mysqli_query($conn, $truyvanmaloai);
        $chuoijson = array();

        echo "{";
        echo "\"DANHSACHSANPHAM\":";

        if($ketqua) {
            while ($dongmaloai= mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dongmaloai["MASP"]."'";
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
                array_push($chuoijson, array("MASP"=>$dongmaloai["MASP"],
                                            "TENSP"=>$dongmaloai["TENSP"],
                                            "GIATIEN"=>$dongmaloai["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHNHO"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
        }

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachSanPhamTheoMaLoaiDanhMuc()
    {
        global $conn;
        $chuoijson = array();
        if(isset($_POST["maloaisp"]) || isset($_POST["limit"])) {
            $maloai = $_POST["maloaisp"];
            $limit = $_POST["limit"];
        }

        $chuoijson = LayDanhSachSanPhamDanhMucTheoMaLoai($conn, $maloai, $chuoijson, $limit);
        echo "{";
        echo "\"DANHSACHSANPHAM\":";

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachSanPhamMoiVe()
    {
        global $conn;

        $truyvan = "SELECT * FROM sanpham
                    ORDER BY MASP DESC LIMIT 20";
        $ketqua = mysqli_query($conn, $truyvan);
        $chuoijson = array();
        $ngayhientai = date("Y/m/d");

        echo "{";
        echo "\"DANHSACHSANPHAMMOIVE\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array('MASP' => $dong["MASP"],
                                            'TENSP' => $dong["TENSP"],
                                            'GIATIEN' => $dong["GIA"],
                                            'HINHSANPHAM'=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHLON"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayLogoThuongHieuLon()
    {
        global $conn;

        $truyvan = "SELECT * FROM thuonghieu";
        $ketqua = mysqli_query($conn, $truyvan);
        $chuoijson = array();

        echo "{";
        echo "\"LOGOTHUONGHIEULON\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {
                array_push($chuoijson, array('MASP' => $dong["MATHUONGHIEU"],
                                            'TENSP' => $dong["TENTHUONGHIEU"],
                                            'HINHSANPHAM'=>"http://".$_SERVER['SERVER_NAME']."/weblazada/".$dong["HINHTHUONGHIEU"]
                                        ));
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayTopTienIch()
    {
        global $conn;

        $ketqua = LayDanhSachLoaiSanPhamTheoMaLoai($conn, 82);
        $chuoijson = array();

        echo "{";
        echo "\"TOPTIENICH\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {

                $ketquacon = LayDanhSachLoaiSanPhamTheoMaLoai($conn, $dong["MALOAISP"]);

                if($ketquacon) {
                    while ($dongcon = mysqli_fetch_array($ketquacon)) {
                        $chuoijson = LayTopDanhSachSanPhamTheoMaLoai($conn, $dongcon["MALOAISP"], $chuoijson, 10);
                    }
                }

            }
        }

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachTienIch()
    {
        global $conn;

        $ketqua = LayDanhSachLoaiSanPhamTheoMaLoai($conn, 82);
        $chuoijson = array();

        echo "{";
        echo "\"DANHSACHTIENICH\":";
        if($ketqua) {
            while ($dong = mysqli_fetch_array($ketqua)) {

                $ketquacon = LayDanhSachLoaiSanPhamTheoMaLoai($conn, $dong["MALOAISP"]);

                if($ketquacon) {
                    while ($dongcon = mysqli_fetch_array($ketquacon)) {
                        $chuoijson = LayDanhSachSanPhamTheoMaLoai($conn, $dongcon["MALOAISP"], $chuoijson, 1);
                    }
                }

            }
        }

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachLoaiSanPhamTheoMaLoai($conn, $maloaisp) {
        $truyvanmaloai = "SELECT * FROM loaisanpham lsp
                        WHERE lsp.MALOAI_CHA = ".$maloaisp;
        $ketqua = mysqli_query($conn, $truyvanmaloai);

        return $ketqua;
    }

    function LayDanhSachSanPhamDanhMucTheoMaLoai($conn, $maloaisp, $chuoijson, $limit) {
        $ngayhientai = date("Y/m/d");
        $truyvanmaloai = "SELECT * FROM loaisanpham lsp, sanpham sp
                    WHERE lsp.MALOAISP = ".$maloaisp." AND lsp.MALOAISP = sp.MALOAISP
                    ORDER BY sp.LUOTMUA DESC LIMIT ".$limit.", 20";
        $ketqua = mysqli_query($conn, $truyvanmaloai);

        if($ketqua) {
            while ($dongmaloai= mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dongmaloai["MASP"]."'";
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
                array_push($chuoijson, array("MASP"=>$dongmaloai["MASP"],
                                            "TENSP"=>$dongmaloai["TENSP"],
                                            "GIATIEN"=>$dongmaloai["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHNHO"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
        }

        return $chuoijson;
    }

    function LayDanhSachSanPhamTheoMaLoaiThuongHieu($conn, $maloaith, $maloaisp, $chuoijson, $limit) {
        $ngayhientai = date("Y/m/d");
        $truyvanmaloai = "SELECT * FROM thuonghieu th, sanpham sp
                    WHERE th.MATHUONGHIEU = ".$maloaith." AND sp.MALOAISP = ".$maloaisp." AND th.MATHUONGHIEU = sp.MATHUONGHIEU
                    ORDER BY sp.LUOTMUA DESC LIMIT ".$limit.", 20";
        $ketqua = mysqli_query($conn, $truyvanmaloai);

        if($ketqua) {
            while ($dongmaloai= mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dongmaloai["MASP"]."'";
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
                array_push($chuoijson, array("MASP"=>$dongmaloai["MASP"],
                                            "TENSP"=>$dongmaloai["TENSP"],
                                            "GIATIEN"=>$dongmaloai["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHNHO"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
        }

        return $chuoijson;
    }

    function LayDanhSachSanPhamTheoMaLoai($conn, $maloaisp, $chuoijson, $limit) {
        $ngayhientai = date("Y/m/d");

        // $truyvanmaloai = "SELECT * FROM loaisanpham lsp, sanpham sp
        //             WHERE lsp.MALOAISP = ".$maloaisp." AND lsp.MALOAISP = sp.MALOAISP
        //             ORDER BY sp.LUOTMUA DESC LIMIT ".$limit;
        $truyvanmaloai = "SELECT min(lsp.MALOAISP) as MALOAISP, sp.MASP, lsp.TENLOAISP, sp.GIA, sp.ANHLON, sp.ANHNHO
                        FROM loaisanpham lsp, sanpham sp
                        WHERE lsp.MALOAISP = ".$maloaisp." AND lsp.MALOAISP = sp.MALOAISP
                        GROUP BY lsp.MALOAISP
                        ORDER BY sp.LUOTMUA DESC LIMIT ".$limit;
        $ketqua = mysqli_query($conn, $truyvanmaloai);

        if($ketqua) {
            while ($dongmaloai= mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dongmaloai["MASP"]."'";
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
                array_push($chuoijson, array("MASP"=>$dongmaloai["MALOAISP"],
                                            "TENSP"=>$dongmaloai["TENLOAISP"],
                                            "GIATIEN"=>$dongmaloai["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHNHO"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
        }

        return $chuoijson;
    }

    function LayTopDanhSachSanPhamTheoMaLoai($conn, $maloaisp, $chuoijson, $limit) {
        $ngayhientai = date("Y/m/d");

        $truyvanmaloai = "SELECT * FROM loaisanpham lsp, sanpham sp
                    WHERE lsp.MALOAISP = ".$maloaisp." AND lsp.MALOAISP = sp.MALOAISP
                    ORDER BY sp.LUOTMUA DESC LIMIT ".$limit;
        $ketqua = mysqli_query($conn, $truyvanmaloai);

        if($ketqua) {
            while ($dongmaloai= mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dongmaloai["MASP"]."'";
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
                array_push($chuoijson, array("MASP"=>$dongmaloai["MASP"],
                                            "TENSP"=>$dongmaloai["TENSP"],
                                            "GIATIEN"=>$dongmaloai["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHLON"],
                                            "HINHSANPHAMNHO"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmaloai["ANHNHO"],
                                            "MALOAISP"=>$dongmaloai["MALOAISP"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
        }

        return $chuoijson;
    }

    function LayDanhSachPhuKien()
    {
        global $conn;

        //lấy danh sách phụ kiện cha
        $truyvan = "SELECT * FROM loaisanpham lsp
                    WHERE lsp.TENLOAISP LIKE 'phụ kiện điện thoại%'";
        $ketqua = mysqli_query($conn, $truyvan);
        $chuoijson = array();

        echo "{";
        echo "\"DANHSACHPHUKIEN\":";
        if($ketqua) {
            while ($dong=mysqli_fetch_array($ketqua)) {

                // lấy danh sách phụ kiện con
                // $truyvanphukiencon = "SELECT * FROM loaisanpham lsp, sanpham sp
                //             WHERE lsp.MALOAI_CHA = ".$dong["MALOAISP"]." AND lsp.MALOAISP = sp.MALOAISP
                //             ORDER BY sp.LUOTMUA DESC LIMIT 20";
                $truyvanphukiencon = "SELECT min(lsp.MALOAISP) as MALOAISP, lsp.TENLOAISP, sp.GIA, sp.ANHLON
                                    FROM loaisanpham lsp, sanpham sp
                                    WHERE lsp.MALOAI_CHA = ".$dong["MALOAISP"]." AND lsp.MALOAISP = sp.MALOAISP
                                    GROUP BY lsp.MALOAISP
                                    ORDER BY sp.LUOTMUA DESC LIMIT 20";
                $ketquacon = mysqli_query($conn, $truyvanphukiencon);

                if($ketquacon) {
                    while ($dongphukiencon = mysqli_fetch_array($ketquacon)) {
                        array_push($chuoijson, array("MASP"=>$dongphukiencon["MALOAISP"],
                                                    "TENSP"=>$dongphukiencon["TENLOAISP"],
                                                    "GIATIEN"=>$dongphukiencon["GIA"],
                                                    "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongphukiencon["ANHLON"]
                                                ));
                    }
                }
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachTopPhuKien()
    {
        global $conn;

        //lấy danh sách phụ kiện cha
        $truyvan = "SELECT * FROM loaisanpham lsp
                    WHERE lsp.TENLOAISP LIKE 'phụ kiện điện thoại%'";
        $ketqua = mysqli_query($conn, $truyvan);
        $chuoijson = array();
        $ngayhientai = date("Y/m/d");

        echo "{";
        echo "\"TOPPHUKIEN\":";
        if($ketqua) {
            while ($dong=mysqli_fetch_array($ketqua)) {

                //lấy danh sách phụ kiện con
                $truyvanphukiencon = "SELECT * FROM loaisanpham lsp, sanpham sp
                            WHERE lsp.MALOAI_CHA = ".$dong["MALOAISP"]." AND lsp.MALOAISP = sp.MALOAISP
                            ORDER BY sp.LUOTMUA DESC LIMIT 10";
                $ketquacon = mysqli_query($conn, $truyvanphukiencon);

                if($ketquacon) {
                    while ($dongphukiencon = mysqli_fetch_array($ketquacon)) {
                        $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                                    FROM khuyenmai km, chitietkhuyenmai ctkm
                                    WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dongphukiencon["MASP"]."'";
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

                        array_push($chuoijson, array("MASP"=>$dongphukiencon["MASP"],
                                                    "TENSP"=>$dongphukiencon["TENSP"],
                                                    "GIATIEN"=>$dongphukiencon["GIA"],
                                                    "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongphukiencon["ANHLON"],
                                                    "MALOAISP"=>$dongphukiencon["MALOAISP"],
                                                    'PHANTRAMKM'=>$phantramkm
                                                ));
                    }
                }
            }
        }
        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachTopDienThoaiVaMayTinhBang()
    {
        global $conn;
        $ngayhientai = date("Y/m/d");

        $truyvan = "SELECT * FROM loaisanpham lsp, sanpham sp
                    WHERE lsp.MALOAISP = sp.MALOAISP AND lsp.TENLOAISP LIKE 'điện thoại%'
                    ORDER BY sp.LUOTMUA DESC LIMIT 10";
        $ketqua = mysqli_query($conn, $truyvan);
        $chuoijson = array();

        echo "{";
        echo "\"TOPDIENTHOAI&MAYTINHBANG\":";
        if($ketqua) {
            while ($dong=mysqli_fetch_array($ketqua)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHLON"],
                                            "MALOAISP"=>$dong["MALOAISP"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
            // echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        }

        //truy vấn các sản phẩm là máy tính bảng
        $truyvan = "SELECT * FROM loaisanpham lsp, sanpham sp
                    WHERE lsp.MALOAISP = sp.MALOAISP AND lsp.TENLOAISP LIKE 'máy tính bảng%'
                    ORDER BY sp.LUOTMUA DESC LIMIT 10";
        $ketquamtb = mysqli_query($conn, $truyvan);

        if($ketquamtb) {
            while ($dongmtb=mysqli_fetch_array($ketquamtb)) {
                $truyvankhuyenmai = "SELECT *, DATEDIFF(km.NGAYKETTHUC,'".$ngayhientai."') as THOIGIANKM
                            FROM khuyenmai km, chitietkhuyenmai ctkm
                            WHERE km.MAKM = ctkm.MAKM AND ctkm.MASP='".$dong["MASP"]."'";
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

                array_push($chuoijson, array("MASP"=>$dongmtb["MASP"],
                                            "TENSP"=>$dongmtb["TENSP"],
                                            "GIATIEN"=>$dongmtb["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmtb["ANHLON"],
                                            "MALOAISP"=>$dong["MALOAISP"],
                                            'PHANTRAMKM'=>$phantramkm
                                        ));
            }
        }

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }

    function LayDanhSachCacThuongHieuLon()
    {
        global $conn;

        $truyvan = "SELECT * FROM thuonghieu th, chitietthuonghieu ctth
                    WHERE th.MATHUONGHIEU = ctth.MATHUONGHIEU";
        $ketqua = mysqli_query($conn, $truyvan);
        $chuoijson = array();

        echo "{";
        echo "\"DANHSACHTHUONGHIEU\":";
        if($ketqua) {
            while ($dong=mysqli_fetch_array($ketqua)) {
                array_push($chuoijson, array("MASP"=>$dong["MATHUONGHIEU"],
                                            "TENSP"=>$dong["TENTHUONGHIEU"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["HINHLOAISPTH"],
                                            "MALOAISP"=>$dong["MALOAISP"]
                                        ));
            }
            echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        }
        echo "}";

        mysqli_close($conn);
    }

    function KiemTraDangNhap()
    {
        global $conn;
        if(isset($_POST["tendangnhap"]) || isset($_POST["matkhau"])) {
            $tendangnhap = $_POST["tendangnhap"];
            $matkhau = $_POST["matkhau"];
        }

        $truyvan = "SELECT * FROM nhanvien WHERE TENDANGNHAP='".$tendangnhap."' AND MATKHAU='".md5($matkhau)."'";
        $ketqua = mysqli_query($conn, $truyvan);
        $demdong = mysqli_num_rows($ketqua);

        if ($demdong >= 1) {
            $tennv = "";
            while ($dong = mysqli_fetch_array($ketqua)) {
                $tennv = $dong["TENNV"];
            }
            echo "{ ketqua: true, tennv: \"".$tennv."\" }";
        }else {
            echo "{ ketqua: false }";
        }
    }

    function DangKyThanhVien()
    {
        global $conn;
        if(isset($_POST["tennv"]) || isset($_POST["tendangnhap"])
        || isset($_POST["matkhau"]) || isset($_POST["maloainv"]) || isset($_POST["emaildocquyen"])) {
            $tennv = $_POST["tennv"];
            $tendangnhap = $_POST["tendangnhap"];
            $matkhau = $_POST["matkhau"];
            $maloainv = $_POST["maloainv"];
            $emaildocquyen = $_POST["emaildocquyen"];
        }

        $truyvan = "INSERT INTO nhanvien (TENNV, TENDANGNHAP, MATKHAU, MALOAINV, EMAILDOCQUYEN)
                    VALUES ('".$tennv."', '".$tendangnhap."', '".md5($matkhau)."', '".$maloainv."', '".$emaildocquyen."')";

        if(mysqli_query($conn, $truyvan)) {
            echo "{ ketqua: true }";
        }else {
            echo "{ ketqua: false }".mysqli_error($conn);
        }

        mysqli_close($conn);
    }

    function LayDanhSachMenu()
    {
        global $conn;
        if(isset($_POST["maloaicha"]))
        {
            $maloaicha = $_POST["maloaicha"];
        }

        $truyvan = "SELECT * FROM LOAISANPHAM WHERE MALOAI_CHA = ".$maloaicha;
        $ketqua = mysqli_query($conn, $truyvan);
        $chuoijson = array();
        echo "{\"LOAISANPHAM\":";
        if($ketqua) {
            while ($dong=mysqli_fetch_array($ketqua)) {
                // cách 1: thêm tất cả giá trị vào chuỗi json
                $chuoijson[] = $dong;

                // cách 2: đưa vào chuỗi json những giá trị mong muốn
                // array_push($chuoijson, array("TENLOAISP"=>$dong["TENLOAISP"], "MALOAISP"=>$dong["MALOAISP"]));
            }
            echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        }
        echo "}";

        mysqli_close($conn);
    }
 ?>
