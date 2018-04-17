<?php
    $sanpham = array(array('empid' => "SJ011MS", "personal" => array('name' => "SmithJon" )), array('empid' => "SJ012MS", "personal" => array('name' => "SmithJon" )));
    echo "{ sanpham: ";
    echo json_encode($sanpham); //xử lý dữ liệu dạng mảng
    echo "}";
?>
