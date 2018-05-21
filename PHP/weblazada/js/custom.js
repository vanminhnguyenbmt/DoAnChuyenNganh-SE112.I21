$(document).ready(function() {

	//đồng ý cập nhật sản phẩm
	$("#btn-capnhatsanpham").click(function(){
		var masp = $(this).attr("data-id");
		var tensanpham = $("#ip_tensanpham").val();
		var giasanpham = $("#ip_giasanpham").val();
		var soluong = $("#ip_soluong").val();
		var maloaisp = $("#sl_loaisanpham").val();
		var mathuonghieu = $("#sl_thuonghieu").val();
		var anhlon = "/hinhsanpham/" + $("#khunganhlon").find(".file-footer-caption").attr("title");
		var anhnho = "";
		var demanhnho = $("#khunganhnho").find(".file-footer-caption").length;
		$("#khunganhnho").find(".file-footer-caption").each(function(index){
			if(demanhnho-1 == index){
				anhnho += "/hinhsanpham/" + $(this).attr("title");
			}else{
				anhnho += "/hinhsanpham/" + $(this).attr("title") + ",";
			}


		});

		var mota = tinymce.get("ip_thongtin").getContent();

		var mangtenchitiet = [];
		$("input[name='mangtenchitietsanpham[]']").each(function(){
			var value = $.trim($(this).val());
			if(value.length > 0){
				mangtenchitiet.push(value);
			}
		});

		var manggiatrichitiet = [];
		$("input[name='manggiatrichitietsanpham[]']").each(function(){
			var value = $.trim($(this).val());

			if(value.length > 0){
				manggiatrichitiet.push(value);
			}
		});

		var mangmachitietsanpham = [];
		$("input[name='mangmachitietsanpham[]']").each(function(){
			var value = $.trim($(this).val());

			if(value.length > 0){
				mangmachitietsanpham.push(value);
			}
		});

		var mangtenchitietsanphambosung = [];
		$("input[name='mangtenchitietsanpham[]'][disabled]").each(function(){
			var value = $.trim($(this).val());
			if(value.length > 0){
				mangtenchitietsanphambosung.push(value);
			}
		});

		var manggiatrichitietsanphambosung = [];
		$("input[name='manggiatrichitietsanpham[]'][disabled]").each(function(){
			var value = $.trim($(this).val());
			if(value.length > 0){
				manggiatrichitietsanphambosung.push(value);
			}
		});


		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "CapNhatSanPhamTheoMaSP_Ajax",
				masp: masp,
				tensanpham : tensanpham,
				giasanpham : giasanpham,
				soluong : soluong,
				maloaisp : maloaisp,
				mathuonghieu : mathuonghieu,
				anhlon: anhlon,
				anhnho: anhnho,
				mota: mota,
				mangtenchitiet: mangtenchitiet,
				manggiatrichitiet: manggiatrichitiet,
				mangmachitietsanpham: mangmachitietsanpham,
				mangtenchitietsanphambosung: mangtenchitietsanphambosung,
				manggiatrichitietsanphambosung: manggiatrichitietsanphambosung
			},
			success:function(data){
				alert(data);
			}
		});
	});

	//xử lý sửa sản phẩm
	$("body").delegate(".btn-suasanpham","click",function(){
		htmlAnhLon = '<label for="ip_anhlon">Ảnh lớn</label> <div class="form-group"><input id="ip_anhlon" name="ip_anhlon" class="file-loading" type="file" data-preview-file-type="any" data-upload-url="uploadhinh.php"></div>';
		htmlAnhNho = '<label for="ip_anhnho">Ảnh nhỏ</label> <div class="form-group"><input id="ip_anhnho" name="ip_anhnho" class="file-loading" type="file" multiple data-preview-file-type="any" data-upload-url="uploadhinh.php"></div>';

		$("#khunganhlon").empty();
		$("#khunganhlon").append(htmlAnhLon);

		$("#khunganhnho").empty();
		$("#khunganhnho").append(htmlAnhNho);

		anhlon = "";
		anhnho = "";
		tensp = "";
		mota = "";
		maloaisp = 0;
		math = 0;
		gia = 0;
		soluong = 0;

		masp = $(this).parent().attr("data-id");
		//truyền mã sản phẩm qua cho view thêm sản phẩm
		$("#btn-capnhatsanpham").attr("data-id",masp);

		$(this).closest("tr").find("th").each(function(){
			if($(this).attr("data-anhnho")){
				anhnho = $(this).attr("data-anhnho");
			}else if($(this).attr("data-anhlon")){
				anhlon = $(this).attr("data-anhlon")
			}else if($(this).attr("data-tensp")){
				tensp = $(this).attr("data-tensp");
			}else if($(this).attr("data-mota")){
				mota = $(this).attr("data-mota");
			}else if($(this).attr("data-maloaisp")){
				maloaisp = $(this).attr("data-maloaisp");
			}else if($(this).attr("data-math")){
				math = $(this).attr("data-math");
			}else if($(this).attr("data-gia")){
				gia= $(this).attr("data-gia");
			}else if($(this).attr("data-soluong")){
				soluong = $(this).attr("data-soluong");
			}
		});

		//gán giá trị vào field tương ứng
		$("#ip_tensanpham").val(tensp);
		$("#ip_giasanpham").val(gia);
		$("#ip_soluong").val(soluong);
		$("#sl_loaisanpham").val(maloaisp).trigger("change");
		$("#sl_thuonghieu").val(math).trigger("change");
		tinymce.get("ip_thongtin").setContent(mota);

		//xử lý load hình đã có vào ảnh lớn
		vitricat = anhlon.lastIndexOf("/");
		tenhinhanhlon = anhlon.substring(vitricat+1);

		$("#ip_anhlon").fileinput({
		    overwriteInitial: true,
		    initialPreview: [
		        ".."+anhlon
		    ],
		    initialPreviewAsData: true, // identify if you are sending preview data only and not the raw markup
		    initialPreviewFileType: 'image', // image is the default and can be overridden in config below
		    initialPreviewConfig: [
		        {caption: tenhinhanhlon },
		    ],
		});

		//xử lý load nhiều hình vào ảnh nhỏ
		arrayanhnhosplit = anhnho.split(",");
		arrayanhnho = [];
		arraytenanhnho = [];
		for(i=0;i<arrayanhnhosplit.length;i++){
			arrayanhnho.push(".."+arrayanhnhosplit[i]);

			vitricat = arrayanhnhosplit[i].lastIndexOf("/");
			tenhinhanhnho = arrayanhnhosplit[i].substring(vitricat+1);

			arraytenanhnho.push({caption: tenhinhanhnho});
		}

		$("#ip_anhnho").fileinput({

		    overwriteInitial: false,
		    initialPreview: arrayanhnho,
		    initialPreviewAsData: true, // identify if you are sending preview data only and not the raw markup
		    initialPreviewFileType: 'image', // image is the default and can be overridden in config below
		    initialPreviewConfig: arraytenanhnho
		});

		//load chi tiết sản phẩm
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "LayChiTietSanPhamTheoMa_Ajax",
				masp: masp
			},
			success:function(data){
				$("#khungchitietsanpham").empty();
				$("#khungchitietsanpham").append($(".anchitietsanpham").clone().removeClass("anchitietsanpham"));
				$("#khungchitietsanpham").find("tbody").prepend(data);
			}
		});
	});

	//xử lý xóa sản phẩm
	$("body").delegate(".btn-xoasanpham","click",function(){
		masp = $(this).parent().attr("data-id");
		This = $(this);
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "XoaSanPham_Ajax",
				masp : masp,
			},
			success:function(data){
				if(data == 1){
					This.closest('tr').remove();
				}else{
					alert("Xóa thất bại !");
				}
			}
		});
	});

	//Xử lý động thêm chi tiết sản phẩm
	$("body").delegate(".btnthemchitietsanpham","click",function(){
		var khungchitietsanpham = $(".anchitietsanpham").clone().removeClass("anchitietsanpham");
		$("#khungchitietsanpham").append(khungchitietsanpham);

		$(this).parent().find(".btnxoachitietsanpham").removeClass("anbutton");
		$(this).closest("tr").find("input").attr("disabled",true);
		$(this).remove();
	});

	// Xóa xử lý động thêm chi tiết sản phẩm
	$("body").delegate(".btnxoachitietsanpham","click",function(){
		$(this).closest("tr").remove();
	});

	//Xử lý sự kiện click button thêm sản phẩm
	$("#btn-themsanpham").click(function(){
		var tensanpham = $("#ip_tensanpham").val();
		var giasanpham = $("#ip_giasanpham").val();
		var soluong = $("#ip_soluong").val();
		var maloaisp = $("#sl_loaisanpham").val();
		var mathuonghieu = $("#sl_thuonghieu").val();
		var anhlon = "/hinhsanpham/" + $("#khunganhlon").find(".file-footer-caption").attr("title");
		var anhnho = "";
		var demanhnho = $("#khunganhnho").find(".file-footer-caption").length;
		$("#khunganhnho").find(".file-footer-caption").each(function(index){
			if(demanhnho-1 == index){
				anhnho += "/hinhsanpham/" + $(this).attr("title");
			}else{
				anhnho += "/hinhsanpham/" + $(this).attr("title") + ",";
			}


		});

		var mota = tinymce.get("ip_thongtin").getContent();

		var mangtenchitiet = [];
		$("input[name='mangtenchitietsanpham[]']").each(function(){
			var value = $.trim($(this).val());
			if(value.length > 0){
				mangtenchitiet.push(value);
			}
		});

		var manggiatrichitiet = [];
		$("input[name='manggiatrichitietsanpham[]']").each(function(){
			var value = $.trim($(this).val());
			if(value.length > 0){
				manggiatrichitiet.push(value);
			}
		});

		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gửi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "ThemSanPham_Ajax",
				tensanpham : tensanpham,
				giasanpham : giasanpham,
				soluong : soluong,
				maloaisp : maloaisp,
				mathuonghieu : mathuonghieu,
				anhlon: anhlon,
				anhnho: anhnho,
				mota: mota,
				mangtenchitiet: mangtenchitiet,
				manggiatrichitiet: manggiatrichitiet
			},
			success:function(data){
				alert(data);
			}
		});
	});

	//tinymce của mô tả sản phẩm
	tinymce.init({
		selector: "textarea#ip_thongtin",
		height: 250,
		plugins: [
				    'advlist autolink lists link image charmap print preview anchor',
				    'searchreplace visualblocks code fullscreen',
				    'insertdatetime media table contextmenu paste code'
				  ],
		 toolbar: 'insertfile undo redo  | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
	});

	$(".btn-hienthithemsanpham").click(function(){
		$(".themsanpham").fadeIn(300,"swing");
		$(".hienthisanpham").fadeOut(300,"swing");
	});

	$(".btn-hienthidanhsachsanpham").click(function(){
		$(".hienthisanpham").fadeIn(300,"swing");
		$(".themsanpham").fadeOut(300,"swing");
	});

	//thực hiện chức năng tìm kiếm loại sản phẩm
	$("#btn-timkiemloaisp").click(function(){
		var noidungtimkiem = $("#txt-timtenloaisp").val();
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "TimKiemLoaiSanPhamTheoTen_Ajax",
				noidungtimkiem : noidungtimkiem,

			},
			success:function(data){
				$("table.table").find("tbody").empty();
				$("table.table").find("tbody").append(data);
				$("ul.pagination").remove();
			}
		});
	});

    // Thực hiện chức năng thêm loại sản phẩm
    $("#btn-themloaisp").click(function() {
        var tenloaisanpham = $("#ip_tenloaisp").val();
        var maloaisp = $("#sl_cha").val();
        This = $(this);

        $.ajax({
            url: '../html/page_product/function.php',
            type: 'POST',
            // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
            data: {
                action: "ThemLoaiSanPham_Ajax",
                tenloaisanpham: tenloaisanpham,
                maloaisp: maloaisp
            },
            success:function(data) {
                $(".thongbaoloi").empty();
				$(".thongbaoloi").append(data);
            }
        });
    });

    // Thực hiện chức năng phân trang
	$("nav").delegate("ul.pagination>li","click",function(){
		var sotrang = $(this).text();
		$("ul.pagination>li").removeClass("active");
		$(this).addClass("active");
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gửi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "LayDanhSachLoaiSanPhamLimit_Ajax",
				sotrang : sotrang,
			},
			success:function(data){
				$("table.table").find("tbody").empty();
				$("table.table").find("tbody").append(data);
			}
		});
	});

	// Xử lý check tất cả checkbox
	$("#cb-chontatca").change(function(){
		var allcheckbox = $(this).closest("table").find("tbody input:checkbox");
		if($(this).is(":checked")){
			allcheckbox.prop("checked",true);
		}else{
			allcheckbox.prop("checked",false);
		}
	});

	//Xử lý xóa loại sản phẩm
	$("#btn-xoaloaisanpham").click(function() {
		var mangcheckbox = [];
		$("input[name='cb-mang[]']:checked").each(function() {
			var maloai = $(this).attr('data-id');
			mangcheckbox.push(maloai);
		});

		$.ajax({
			url: '../html/page_product/function.php',
			type: 'POST',
			// dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
			data: {
				action : "XoaLoaiSanPhamTheoMa_Ajax",
				mangmaloaisp : mangcheckbox,
			},
			success:function(data) {
				//load lại nội dung của loại sản phẩm khi xóa
				$.ajax({
					url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
					type : "POST",
					// datatype: ""
					data : {
						action : "LayDanhSachLoaiSanPhamLimit_Ajax",
						sotrang : 1,

					},
					success:function(dulieu){
						$("table.table").find("tbody").empty();
						$("table.table").find("tbody").append(dulieu);
					}
				});

				$.ajax({
					url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
					type : "POST",
					// datatype: ""
					data : {
						action : "HienThiPhanTrang_Ajax",

					},
					success:function(dulieuphantrang){
						$("ul.pagination").empty();
						$("ul.pagination").append(dulieuphantrang);
					}
				});
			}
		});
	});
});
