$(document).ready(function() {
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
