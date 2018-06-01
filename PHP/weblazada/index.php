<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>Bin.UIT</title>

	<!-- Google Fonts -->
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>

	<link rel="stylesheet" href="css/animate1.css">
	<!-- Custom Stylesheet -->
	<link rel="stylesheet" href="css/style1.css">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>

<body>
	<div class="container">
		<div class="login-box animated fadeInUp">
			<div class="box-header">
				<h2>Đăng Nhập</h2>
			</div>
			<label for="tendangnhap">Tên đăng nhập</label>
			<br/>
			<input type="text" name="tendangnhap" id="tendangnhap" value=<?php echo isset($_COOKIE["tendangnhap"])?  $_COOKIE["tendangnhap"] : "" ?>>
			<br/>
			<label for="matkhau">Mật khẩu</label>
			<br/>
			<input type="password" id="matkhau" value=<?php echo isset($_COOKIE["matkhau"])?  $_COOKIE["matkhau"] : "" ?>>
			<br/>
			<button id="dongy" type="submit">Đồng ý</button>
			<br/>
			<label for="nhotaikhoan">Nhớ tài khoản</label>
			<input type="checkbox" name="nhotaikhoan" id="nhotaikhoan" />
			<input type="hidden" id="url" value=<?php echo "http://$_SERVER[HTTP_HOST]/weblazada/" ?>>
		</div>
	</div>
</body>

<script>
	$(document).ready(function () {
		$("body").delegate("#dongy","click",function(){
			tendangnhap = $("#tendangnhap").val();
			matkhau = $("#matkhau").val();
			nhotaikhoan = $("#nhotaikhoan").is(":checked");
			duongdan = $("#url").val();

			 $.ajax({
				url : "html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
				type : "POST",
				// datatype: ""
				data : {
					action : "KiemTraDangNhap_Ajax",
					tendangnhap : tendangnhap,
					matkhau: matkhau,
					nhotaikhoan: nhotaikhoan

				},
				success:function(data){
					if(data !=0){
						window.location.replace(duongdan+"html/index.php");
					}
				}
			});
		});

    	$('#logo').addClass('animated fadeInDown');
    	$("input:text:visible:first").focus();
		});
		$('#tendangnhap').focus(function() {
			$('label[for="tendangnhap"]').addClass('selected');
		});
		$('#tendangnhap').blur(function() {
			$('label[for="tendangnhap"]').removeClass('selected');
		});
		$('#matkhau').focus(function() {
			$('label[for="matkhau"]').addClass('selected');
		});
		$('#matkhau').blur(function() {
			$('label[for="matkhau"]').removeClass('selected');
		});
</script>

</html>
