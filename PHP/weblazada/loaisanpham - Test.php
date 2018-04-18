<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
        <!-- <form action="index.php" method="post" name="dangky">
            <input type="input" name="tendangnhap" value=""/>
            <input type="input" name="matkhau" value=""/>
            <input type="submit" name="submit" value="Đăng nhập"/>
        </form> -->
        <?php
            include_once 'config.php';

            // if (isset($_GET["tendangnhap"]) && isset($_GET["matkhau"])) {
            //     echo $_GET["tendangnhap"];
            //     echo $_GET["matkhau"];
            // }

            $maloaicha = $_GET["maloaicha"];

            $truyvan = "SELECT * FROM LOAISANPHAM WHERE MALOAI_CHA = ".$maloaicha;
            $ketqua = mysqli_query($conn, $truyvan);
            $chuoijson = array();
            echo "{\"LOAISANPHAM\":[";
            if($ketqua) {
                while ($dong=mysqli_fetch_array($ketqua)) {
                    // cách 1: thêm tất cả giá trị vào chuỗi json
                    $chuoijson[] = $dong;

                    // cách 2: đưa vào chuỗi json những giá trị mong muốn
                    // array_push($chuoijson, array("TENLOAISP"=>$dong["TENLOAISP"], "MALOAISP"=>$dong["MALOAISP"]));
                }
                echo json_encode($chuoijson);
            }
            echo "]}";
         ?>
    </body>
</html>
