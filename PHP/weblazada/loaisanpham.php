<?php
    include_once 'config.php';

    $truyvan = "SELECT * FROM LOAISANPHAM";
    $ketqua = mysqli_query($conn, $truyvan);
    $chuoijson = array();
    echo "{\"LOAISANPHAM\":[";
    if($ketqua) {
        while ($dong=mysqli_fetch_array($ketqua)) {
            /*cách 1: thêm tất cả giá trị vào chuỗi json
            $chuoijson[] = $dong;*/

            // cách 2: đưa vào chuỗi json những giá trị mong muốn
            array_push($chuoijson, array("TENLOAISP"=>$dong["TENLOAISP"], "MALOAISP"=>$dong["MALOAISP"]));
        }
        echo json_encode($chuoijson);
    }
    echo "]}";
 ?>
