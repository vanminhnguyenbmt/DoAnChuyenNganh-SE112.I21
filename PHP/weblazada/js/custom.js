$(document).ready(function() {

	//thực hiện chức năng tìm kiếm khuyến mãi
	$("#btn-timkiemkhuyenmai").click(function(){
		var noidungtimkiem = $("#txt-timkiemkhuyenmai").val();
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "TimKiemKhuyenMai_Ajax",
				noidungtimkiem : noidungtimkiem,
				sotrang: 1
			},
			success:function(data){
				$("table.table").find("tbody").empty();
				$("table.table").find("tbody").append(data);
				$("#phantrangkhuyenmai").addClass('anbutton');
				$('#phantrangkhuyenmaitimkiem').bootpag({
				    total: $("table.table").find("tbody").find("tr").attr("data-tongsotrang"),
				    maxVisible: 10,
				    page: 1
				}).on("page", function(event, trang){
				    $.ajax({
						url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
						type : "POST",
						// datatype: ""
						data : {
							action : "TimKiemKhuyenMai_Ajax",
							noidungtimkiem: noidungtimkiem,
							sotrang : trang
						},
						success:function(data){
							$("table.table").find("tbody").empty();
							$("table.table").find("tbody").append(data);
						}
					});
				});
			}
		});
	});

	//đồng ý cập nhập khuyến mãi
	$("#btn-dongycapnhatkhuyenmai").click(function(){
		makm = $(this).attr('data-id');
		tenkhuyenmai = $("#ip_tenkhuyenmai").val();
		maloaisp = $("#sl_loaisanpham").val();
		ngaybatdau = $("#ip_ngaybatdau").val();
		ngayketthuc = $("#ip_ngayketthuc").val();
		hinhkhuyenmai = "/hinhkhuyenmai/" + $("#khunganhkhuyenmai").find(".file-footer-caption").attr("title");
		for(i = 0; i < 10; i++) {
			ngaybatdau = ngaybatdau.replace("-", "/");
			ngayketthuc = ngayketthuc.replace("-", "/");
		}

		mangmasp = [];
		$("input[name='mangsanpham[]']").each(function(){
			var value = $.trim($(this).attr("data-masp"));
			if(value.length > 0){
				mangmasp.push(value);
			}
		});

		mangphantramkm = [];
		$("input[name='mangphantramkm[]']").each(function(){
			var value = $.trim($(this).val());
			if(value.length > 0){
				mangphantramkm.push(value);
			}
		});

		kiemtra = false;
		if(mangmasp.length == "" && mangphantramkm.length == "")
		{
			kiemtra = false;
			alert('Vui lòng chọn sản phẩm và thêm chi tiết khuyến mãi');
		}else {
			kiemtra = true;
		}

		if(kiemtra) {
			$.ajax({
				url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
				type : "POST",
				// datatype: ""
				data : {
					action : "CapNhapKhuyenMaiVaChiTietKhuyenMai_Ajax",
					tenkhuyenmai : tenkhuyenmai,
					maloaisp: maloaisp,
					ngaybatdau: ngaybatdau,
					ngayketthuc: ngayketthuc,
					hinhkhuyenmai: hinhkhuyenmai,
					mangmasp: mangmasp,
					mangphantramkm: mangphantramkm,
					makm: makm
				},
				success:function(data){
					alert(data);
				}
			});
		}
	});

	//xử lý nút cập nhập khuyến mãi
	$("body").delegate(".btn-suakhuyenmai","click",function(){
		HienThiThemKhuyenMai();
		$("#btn-dongycapnhatkhuyenmai").removeClass("anbutton");
		$("#btn-themkhuyenmai").addClass("anbutton");

		makm = $(this).parent().attr('data-id');
		$("#btn-dongycapnhatkhuyenmai").attr("data-id", makm);

		$(this).closest('tr').find('th').each(function(){
			if($(this).attr("data-hinhkm")){
				hinhkm = $(this).attr("data-hinhkm");
			}else if($(this).attr("data-tenkm")){
				tenkm = $(this).attr("data-tenkm");
			}else if($(this).attr("data-maloaisp")){
				maloaisp = $(this).attr("data-maloaisp");
			}else if($(this).attr("data-tenloaisp")){
				tenloaisp = $(this).attr("data-tenloaisp");
			}else if($(this).attr("data-ngaybatdau")){
				ngaybatdau = $(this).attr("data-ngaybatdau");
			}else if($(this).attr("data-ngayketthuc")){
				ngayketthuc = $(this).attr("data-ngayketthuc");
			}
		});

		$("#sl_loaisanpham").val(maloaisp).change();

		//xử lý load ngày bắt đầu và kết thúc
		for(i = 0; i < 10; i++) {
			ngaybatdau = ngaybatdau.replace("/", "-");
			ngayketthuc = ngayketthuc.replace("/", "-");
		}

		$('#ip_tenkhuyenmai').val(tenkm);
		$('#ip_ngaybatdau').val(ngaybatdau);
		$('#ip_ngayketthuc').val(ngayketthuc);

		//xử lý hiển thị hình ra krajee
		htmlAnhKM = '<label for="ip_anhkhuyenmai">Hình khuyến mãi</label> <div class="form-group"><input id="ip_anhkhuyenmai" name="ip_anhkhuyenmai" class="file-loading" type="file" data-preview-file-type="any" data-upload-url="page_product/uploadhinhkhuyenmai.php"></div>';
		$("#khunganhkhuyenmai").empty();
		$("#khunganhkhuyenmai").append(htmlAnhKM);

		//xử lý load hình đã có vào ảnh lớn
		vitricat = hinhkm.lastIndexOf("/");
		tenanhkm = hinhkm.substring(vitricat+1);

		$("#ip_anhkhuyenmai").fileinput({
		    overwriteInitial: true,
		    initialPreview: [
		        ".."+hinhkm
		    ],
		    initialPreviewAsData: true, // identify if you are sending preview data only and not the raw markup
		    initialPreviewFileType: 'image', // image is the default and can be overridden in config below
		    initialPreviewConfig: [
		        {caption: tenanhkm },
		    ],
		});

		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "LayChiTietKhuyenMai_Ajax",
				makm : makm,
			},
			success:function(data){
				$("#khungchitietkm").find("tbody").empty();
				$("#khungchitietkm").find("tbody").prepend(data);
			}
		});
	});

	$(".btn-hienthithemkhuyenmai").click(function(){
		$("#btn-dongycapnhatkhuyenmai").addClass("anbutton");
		$("#btn-themkhuyenmai").removeClass("anbutton");
		HienThiThemKhuyenMai();
	});

	$(".btn-hienthidanhsachkhuyenmai").click(function(){
		HienThiDanhSachKhuyenMai();
	});

	function HienThiThemKhuyenMai(){
		$(".hienthisanpham").removeClass("anbutton");
		$(".hienthisanpham").fadeOut();
		$(".themsanpham").fadeIn();
	}

	function HienThiDanhSachKhuyenMai(){
		$(".hienthisanpham").fadeIn();
		$(".themsanpham").fadeOut();
	}

	//Xử lý sự kiện click button thêm khuyến mãi
	$("#btn-themkhuyenmai").click(function(){
		tenkhuyenmai = $("#ip_tenkhuyenmai").val();
		maloaisp = $("#sl_loaisanpham").val();
		ngaybatdau = $("#ip_ngaybatdau").val();
		ngayketthuc = $("#ip_ngayketthuc").val();
		hinhkhuyenmai = "/hinhkhuyenmai/" + $("#khunganhkhuyenmai").find(".file-footer-caption").attr("title");
		for(i = 0; i < 10; i++) {
			ngaybatdau = ngaybatdau.replace("-", "/");
			ngayketthuc = ngayketthuc.replace("-", "/");
		}

		mangmasp = [];
		$("input[name='mangsanpham[]']").each(function(){
			var value = $.trim($(this).attr("data-masp"));
			if(value.length > 0){
				mangmasp.push(value);
			}
		});

		mangphantramkm = [];
		$("input[name='mangphantramkm[]']").each(function(){
			var value = $.trim($(this).val());
			if(value.length > 0){
				mangphantramkm.push(value);
			}
		});

		kiemtra = false;
		if(mangmasp.length == "" && mangphantramkm.length == "")
		{
			kiemtra = false;
			alert('Vui lòng chọn sản phẩm và thêm chi tiết khuyến mãi');
		}else {
			kiemtra = true;
		}

		if(kiemtra) {
			$.ajax({
				url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
				type : "POST",
				// datatype: ""
				data : {
					action : "ThemKhuyenMaiVaChiTietKhuyenMai_Ajax",
					tenkhuyenmai : tenkhuyenmai,
					maloaisp: maloaisp,
					ngaybatdau: ngaybatdau,
					ngayketthuc: ngayketthuc,
					hinhkhuyenmai: hinhkhuyenmai,
					mangmasp: mangmasp,
					mangphantramkm: mangphantramkm
				},
				success:function(data){
					alert(data);
				}
			});
		}
	});
	//chọn loại sản phẩm ở trang khuyến mãi sẽ load lên sản phẩm của loại đó
	$("#sl_loaisanpham").change(function(){
		maloaisp = $("#sl_loaisanpham :selected").val();
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "LayDanhSachSanPhamTheoMaLoaiSP_Ajax",
				maloaisp: maloaisp
			},
			success:function(data){
				$("#sl_tenspchitietkm").empty();
				$("#sl_tenspchitietkm").append(data);
				$("#sl_tenspchitietkm").val($("#sl_tenspchitietkm").find('option').first().val()).change();
			}
		});
	});

	//Xử lý nút thêm chi tiết khuyến mãi
	$(".btn-themchitietkm").click(function(){
		tensp = $("#sl_tenspchitietkm :selected").text();
		masp = $("#sl_tenspchitietkm").val();
		phantramkm = $("#ip_phantramkm").val();
		kiemtra= false;

		if($(this).parent().find("#ip_phantramkm").val() == "" || $("#ip_ngaybatdau").val() == ""
		|| $("#ip_ngayketthuc").val() == "" || $("#ip_tenkhuyenmai").val() == "")
		{
			alert('Vui lòng nhập đủ thông tin');
			kiemtra = true;
		}

		$("input[name='mangphantramkm[]']").each(function(){
			if($(this).attr("data-masp")==masp){
				phantramkm = parseInt(phantramkm);
				$(this).val(phantramkm);
				kiemtra = true;
			}
		});

		var content = '<tr><th>Tên sản phẩm : <input name="mangsanpham[]" data-masp="'+masp+'" value="'+tensp+'" style="margin:5px; padding:5px; width:60%"  disabled type="text"  /></th><th>Phần trăm khuyến mãi: <input data-masp="'+masp+'" disabled value="'+phantramkm+'" style="margin:5px; padding:5px; width:60%" name="mangphantramkm[]" type="text"  /><a class="btn btn-danger btnxoachitiethoadon">Xóa</a></th></tr>';

		if(!kiemtra){
			$("#khungchitietkm").find("tbody").append(content);
		}
	});

	//thực hiện chức năng tìm kiếm sản phẩm
	$("#btn-timkiemsanpham").click(function(){
		var noidungtimkiem = $("#txt-timkiemsanpham").val();
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "TimKiemSanPham_Ajax",
				noidungtimkiem : noidungtimkiem,
				sotrang: 1
			},
			success:function(data){
				$("table.table").find("tbody").empty();
				$("table.table").find("tbody").append(data);
				$("#phantrangsanpham").addClass('anbutton');
				$('#phantrangsanphamtimkiem').bootpag({
				    total: $("table.table").find("tbody").find("tr").attr("data-tongsotrang"),
				    maxVisible: 10,
				    page: 1
				}).on("page", function(event, trang){
				    $.ajax({
						url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
						type : "POST",
						// datatype: ""
						data : {
							action : "TimKiemSanPham_Ajax",
							noidungtimkiem: noidungtimkiem,
							sotrang : trang
						},
						success:function(data){
							$("table.table").find("tbody").empty();
							$("table.table").find("tbody").append(data);
						}
					});
				});
			}
		});
	});

	//thực hiện chức năng tìm kiếm hóa đơn
	$("#btn-timkiemhoadon").click(function(){
		var noidungtimkiem = $("#txt-timkiemhoadon").val();
		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "TimKiemHoaDon_Ajax",
				noidungtimkiem : noidungtimkiem,
				sotrang: 1
			},
			success:function(data){
				$("table.table").find("tbody").empty();
				$("table.table").find("tbody").append(data);
				$("#phantranghoadon").addClass('anbutton');
				$('#phantranghoadontimkiem').bootpag({
				    total: $("table.table").find("tbody").find("tr").attr("data-tongsotrang"),
				    maxVisible: 10,
				    page: 1
				}).on("page", function(event, trang){
				    $.ajax({
						url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
						type : "POST",
						// datatype: ""
						data : {
							action : "TimKiemHoaDon_Ajax",
							noidungtimkiem: noidungtimkiem,
							sotrang : trang
						},
						success:function(data){
							$("table.table").find("tbody").empty();
							$("table.table").find("tbody").append(data);
						}
					});
				});
			}
		});
	});

	//Xử lý khi đồng ý cập nhập hóa đơn
	$("#btn-dongycapnhathoadon").click(function(){
		mahd = $(this).attr("data-id");
		tennguoinhan = $("#ip_chudonhang").val();
		sodt = $("#ip_sodtdonhang").val();
		diachi = $("#ip_diachi").val();
		machuyenkhoan = $("#ip_machuyenkhoan").val();
		ngaymua = $("#ip_ngaymua").val();
		ngaygiao = $("#ip_ngaygiao").val();
		tinhtrang = $("#sl_tinhtranghoadon").val();
		chuyenkhoan = $("#sl_chuyenkhoan").val();

		var mangmasp = [];
		var mangtensp = [];
		$("input[name='mangsanpham[]']").each(function(){
			var value = $.trim($(this).attr("data-masp"));
			var tensp = $.trim($(this).val());
			if(value.length > 0){
				mangmasp.push(value);
				mangtensp.push(tensp);
			}

		});

		var mangsoluong = [];
		$("input[name='mangsoluong[]']").each(function(){
			var value = $.trim($(this).val());

			if(value.length > 0){
				mangsoluong.push(value);

			}

		});

		$.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "CapNhatHoaDonVsChiTietHoaDon_Ajax",
				mahd : mahd,
				tennguoinhan: tennguoinhan,
				sodt: sodt,
				diachi: diachi,
				machuyenkhoan: machuyenkhoan,
				ngaymua: ngaymua,
				ngaygiao: ngaygiao,
				tinhtrang: tinhtrang,
				chuyenkhoan: chuyenkhoan,
				mangmasp: mangmasp,
				mangsoluong: mangsoluong,
				mangtensp: mangtensp
			},
			success:function(data){
				alert(data);
			}
		});


	});

	//xóa chi tiết hóa đơn
	$("body").delegate(".btnxoachitiethoadon","click",function(){
		$(this).closest("tr").remove();
	});

	//Xử lý nút thêm chi tiết hóa đơn
	$(".btn-themchitiethoadon").click(function(){

		tensp = $("#sl_tenspchitiethd :selected").text();
		masp = $("#sl_tenspchitiethd").val();
		soluong = $("#ip_soluongsanphamhd").val();
		kiemtra= false;

		$("input[name='mangsoluong[]']").each(function(){
			if($(this).attr("data-masp")==masp){
				soluong = parseInt(soluong) + parseInt($(this).val());
				$(this).val(soluong);
				kiemtra = true;
			}
		});

		var content = '<tr><th>Tên sản phẩm : <input name="mangsanpham[]" data-masp="'+masp+'" value="'+tensp+'" style="margin:5px; padding:5px; width:60%"  disabled type="text"  /></th><th>Số lượng : <input data-masp="'+masp+'" disabled value="'+soluong+'" style="margin:5px; padding:5px; width:60%" name="mangsoluong[]" type="text"  /><a class="btn btn-danger btnxoachitiethoadon">Xóa</a></th></tr>';

		if(!kiemtra){
			$("#khungchitiethoadon").find("tbody").append(content);
		}
	});

	//xử lý nút cập nhật hóa đơn
	$("body").delegate(".btn-capnhathoadon","click",function(){

		HienThiThemHoaDon();
		$("#btn-dongycapnhathoadon").removeClass("anbutton");
		$("#btn-themhoadon").addClass("anbutton");

		chuyenkhoan = 0;
		machuyenkhoan = "không có";
		mahd = $(this).parent().attr("data-id");

		$("#btn-dongycapnhathoadon").attr("data-id",mahd);

		$(this).closest("tr").find("th").each(function(){
			if($(this).attr("data-tennguoinhan")){
				tennguoinhan = $(this).attr("data-tennguoinhan");
			}else if($(this).attr("data-sodt")){
				sodt = $(this).attr("data-sodt");
			}else if($(this).attr("data-diachi")){
				diachi = $(this).attr("data-diachi");
			}else if($(this).attr("data-tinhtrang")){
				tinhtrang = $(this).attr("data-tinhtrang");
			}else if($(this).attr("data-ngaymua")){
				ngaymua = $(this).attr("data-ngaymua");
			}else if($(this).attr("data-ngaygiao")){
				ngaygiao = $(this).attr("data-ngaygiao");
			}else if($(this).attr("data-chuyenkhoan")){
				chuyenkhoan = $(this).attr("data-chuyenkhoan");
			}else if($(this).attr("data-machuyenkhoan")){
				machuyenkhoan = $(this).attr("data-machuyenkhoan");
			}

		});

		$("#ip_chudonhang").val(tennguoinhan);
		$("#ip_sodtdonhang").val(sodt);
		$("#ip_diachi").val(diachi);
		$("#ip_machuyenkhoan").val(machuyenkhoan);
		$("#ip_ngaymua").val(ngaymua);
		$("#ip_ngaygiao").val(ngaygiao);
		$("#sl_tinhtranghoadon").val(tinhtrang).trigger("change");
		$("#sl_chuyenkhoan").val(chuyenkhoan).trigger("change");

		 $.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "LayChiTietHoaDon_Ajax",
				mahd : mahd,
			},
			success:function(data){
				$("#khungchitiethoadon").find("tbody").empty();
				$("#khungchitiethoadon").find("tbody").prepend(data);
			}
		});

	});

	$(".btn-hienthithemhoadon").click(function(){
		$("#btn-dongycapnhathoadon").addClass("anbutton");
		$("#btn-themhoadon").removeClass("anbutton");
		HienThiThemHoaDon();
	});

	$(".btn-hienthidanhsachhoadon").click(function(){
		HienThiDanhSachHoaDon();
	});

	function HienThiThemHoaDon(){
		$(".hienthisanpham").removeClass("anbutton");
		$(".hienthisanpham").fadeOut();
		$(".themsanpham").fadeIn();
	}

	function HienThiDanhSachHoaDon(){
		$(".hienthisanpham").fadeIn();
		$(".themsanpham").fadeOut();
	}

	//Phân trang cho hóa đơn
	$('#phantranghoadon').bootpag({
	    total: $("#phantranghoadon").attr("data-tongsotrang"),
	    maxVisible: 10,
	    page: 1
	}).on("page", function(event, trang){
	    $.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "LayDanhSachHoaDonLimit_Ajax",
				sotrang : trang
			},
			success:function(data){
				$("table.table").find("tbody").empty();
				$("table.table").find("tbody").append(data);
			}
		});
	});

	//Phân trang cho sản phẩm
	$('#phantrangsanpham').bootpag({
	    total: $("#phantrangsanpham").attr("data-tongsotrang"),
	    maxVisible: 10,
	    page: 1
	}).on("page", function(event, trang){
	    $.ajax({
			url : "../html/page_product/function.php", //đường dẫn của trang xử lý code gữi qua
			type : "POST",
			// datatype: ""
			data : {
				action : "LayDanhSachSanPhamLimit_Ajax",
				sotrang : trang
			},
			success:function(data){
				$("table.table").find("tbody").empty();
				$("table.table").find("tbody").append(data);
			}
		});
	});

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

		HienThiThemSanPham();
		$("#btn-capnhatsanpham").removeClass("anbutton");
		$("#btn-themsanpham").addClass("anbutton");

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
					alert("Xóa thành công !");
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
		$("#btn-capnhatsanpham").addClass("anbutton");
		$("#btn-themsanpham").removeClass("anbutton");
		HienThiThemSanPham();
	});

	$(".btn-hienthidanhsachsanpham").click(function(){
		HienThiDanhSachSanPham();
	});

	function HienThiThemSanPham(){
		$(".hienthisanpham").removeClass("anbutton");
		$(".themsanpham").fadeIn(300,"swing");
		$(".hienthisanpham").fadeOut(300,"swing");
	}

	function HienThiDanhSachSanPham(){
		$(".hienthisanpham").fadeIn(300,"swing");
		$(".themsanpham").fadeOut(300,"swing");
	}

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
