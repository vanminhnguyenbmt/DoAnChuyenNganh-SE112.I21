<?php
    include_once 'config.php';

    $ham = $_GET["ham"];
    // $ham = $_POST["ham"];

    switch ($ham) {
        case 'LayDanhSachTopDienThoaiVaMayTinhBang':
            $ham();
            break;
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
                array_push($chuoijson, array("MASP"=>$dong["MASP"],
                                            "TENSP"=>$dong["TENSP"],
                                            "GIATIEN"=>$dong["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dong["ANHLON"]
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
                array_push($chuoijson, array("MASP"=>$dongmtb["MASP"],
                                            "TENSP"=>$dongmtb["TENSP"],
                                            "GIATIEN"=>$dongmtb["GIA"],
                                            "HINHSANPHAM"=>"http://".$_SERVER['SERVER_NAME']."/weblazada".$dongmtb["ANHLON"]
                                        ));
            }
        }

        echo json_encode($chuoijson, JSON_UNESCAPED_UNICODE);
        echo "}";

        mysqli_close($conn);
    }


 ?>
