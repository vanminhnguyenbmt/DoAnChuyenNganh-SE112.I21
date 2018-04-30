DROP DATABASE IF EXISTS LAZADA;
CREATE DATABASE LAZADA;
USE LAZADA;

CREATE TABLE LOAISANPHAM(
	MALOAISP INT AUTO_INCREMENT,
    TENLOAISP NVARCHAR(100),
    MALOAI_CHA INT,
    
    CONSTRAINT KHOACHINH_LOAISANPHAM_MALOAISP PRIMARY KEY (MALOAISP)
);

CREATE TABLE THUONGHIEU(
	MATHUONGHIEU INT AUTO_INCREMENT,
    TENTHUONGHIEU NVARCHAR(100),
    HINHTHUONGHIEU TEXT,
	LUOTMUA INT,
    
    CONSTRAINT KHOACHINH_THUONGHIEU_MATHUONGHIEU PRIMARY KEY (MATHUONGHIEU)
);

CREATE TABLE SANPHAM(
	MASP INT AUTO_INCREMENT,
    TENSP NVARCHAR(200),
    GIA DECIMAL,
    ANHLON TEXT,
    ANHNHO TEXT,
    THONGTIN TEXT,
    SOLUONG INT,
    MALOAISP INT,
    MATHUONGHIEU INT,
	LUOTMUA INT,
    
    CONSTRAINT KHOACHINH_SANPHAM_MASP PRIMARY KEY (MASP),
    CONSTRAINT KHOANGOAI_SANPHAM_MALOAISP FOREIGN KEY (MALOAISP) REFERENCES LOAISANPHAM (MALOAISP),
  	CONSTRAINT KHOANGOAI_SANPHAM_MATHUONGHIEU FOREIGN KEY (MATHUONGHIEU) REFERENCES THUONGHIEU (MATHUONGHIEU)
);

CREATE TABLE LOAINHANVIEN(
	MALOAINV INT AUTO_INCREMENT,
    TENLOAINV NVARCHAR(20),
    
    CONSTRAINT KHOACHINH_LOAINHANVIEN_MALOAINV PRIMARY KEY (MALOAINV)
);

CREATE TABLE NHANVIEN(
	MANV INT AUTO_INCREMENT,
    TENNV NVARCHAR(100),
    TENDANGNHAP VARCHAR(100),
    MATKHAU VARCHAR(100),
    DIACHI NVARCHAR(200),
    NGAYSINH VARCHAR(20),
    SODT VARCHAR(20),
    GIOITINH BOOLEAN,
    MALOAINV INT,
	EMAILDOCQUYEN VARCHAR(4) DEFAULT NULL,
    
    CONSTRAINT KHOACHINH_NHANVIEN_MANV PRIMARY KEY (MANV),
    CONSTRAINT KHOANGOAI_NHANVIEN_MALOAINV FOREIGN KEY (MALOAINV) REFERENCES LOAINHANVIEN (MALOAINV)
);

CREATE TABLE KHUYENMAI(
    MAKM INT AUTO_INCREMENT,
	MALOAISP INT,
	TENKM NVARCHAR(200),
	NGAYBATDAU VARCHAR(20),
    NGAYKETTHUC VARCHAR(20),
	HINHKHUYENMAI TEXT,
    
    CONSTRAINT KHOACHINH_KHUYENMAI_MAKM PRIMARY KEY (MAKM),
	CONSTRAINT KHOANGOAI_KHUYENMAI_MALOAISP FOREIGN KEY (MALOAISP) REFERENCES LOAISANPHAM (MALOAISP)
);

CREATE TABLE CHITIETKHUYENMAI(
    MASP INT,
	MAKM INT,
    PHANTRAMKM INT(2),
    
    CONSTRAINT KHOACHINH_CHITIETKHUYENMAI_MASP_MAKM PRIMARY KEY (MASP, MAKM),
    CONSTRAINT KHOANGOAI_CHIETIETKUYENMAI_MASP FOREIGN KEY (MASP) REFERENCES SANPHAM (MASP),
    CONSTRAINT KHOANGOAI_CHITIETKHUYENMAI_MAKM FOREIGN KEY (MAKM) REFERENCES KHUYENMAI (MAKM)
);

CREATE TABLE BINHLUAN(
	MABL INT AUTO_INCREMENT,
	TIEUDE NVARCHAR(200),
   
    CONSTRAINT KHOACHINH_BINHLUAN_MABL PRIMARY KEY (MABL)  
);

CREATE TABLE CHITIETBINHLUAN(
	MABL INT,
	MANV INT,
    MASP INT,
    NOIDUNG TEXT,
    NGAYBL VARCHAR(20),
	
	CONSTRAINT KHOACHINH_CHITIETBINHLUAN_MABL_MANV_MASP PRIMARY KEY (MABL, MANV, MASP),
	CONSTRAINT KHOANGOAI_CHITIETBINHLUAN_MABL FOREIGN KEY (MABL) REFERENCES BINHLUAN (MABL),
	CONSTRAINT KHOANGOAI_CHITIETBINHLUAN_MANV FOREIGN KEY (MANV) REFERENCES NHANVIEN (MANV),
    CONSTRAINT KHOANGOAI_CHITIETBINHLUAN_MASP FOREIGN KEY (MASP) REFERENCES SANPHAM (MASP)
);

CREATE TABLE DANHGIA(
	MADG VARCHAR(200),
	MASP INT,
	TENTHIETBI TEXT,
	TIEUDE TEXT,
	NOIDUNG TEXT,
	SOSAO INT(1),
	NGAYDANHGIA VARCHAR(50),
    
    CONSTRAINT KHOACHINH_DANHGIA_MADG PRIMARY KEY (MADG),
	CONSTRAINT KHOANGOAI_DANHGIA_MASP FOREIGN KEY (MASP) REFERENCES SANPHAM (MASP)
);

CREATE TABLE CHITIETTHUONGHIEU(
    MATHUONGHIEU INT,
    MALOAISP INT,
    HINHTHUONGHIEU TEXT,
    
    CONSTRAINT KHOACHINH_CHITIETTHUONGHIEU_MATHUONGHIEU_MALOAISP PRIMARY KEY (MATHUONGHIEU, MALOAISP),
    CONSTRAINT KHOANGOAI_CHITIETTHUONGHIEU_MATHUONGHIEU FOREIGN KEY (MATHUONGHIEU) REFERENCES THUONGHIEU (MATHUONGHIEU),
    CONSTRAINT KHOANGOAI_CHITIETTHUONGHIEU_MALOAISP FOREIGN KEY (MALOAISP) REFERENCES LOAISANPHAM (MALOAISP)
);

CREATE TABLE CHITIETSANPHAM(
    MASP INT,
    TENCHITIET INT,
    GIATRI TEXT,
    
    CONSTRAINT KHOACHINH_CHITIETSANPHAM_MASP PRIMARY KEY (MASP),
    CONSTRAINT KHOANGOAI_CHITIETSANPHAM_MASP FOREIGN KEY (MASP) REFERENCES SANPHAM (MASP)
);

CREATE TABLE HOADON(
	MAHD INT AUTO_INCREMENT,
	NGAYMUA TEXT,
	NGAYGIAO TEXT,
	TRANGTHAI VARCHAR(20),
	TENNGUOINHAN VARCHAR(50),
	SODT TEXT,
	DIACHI TEXT,
	CHUYENKHOAN BOOLEAN,
	MACHUYENKHOAN TEXT,

	CONSTRAINT KHOACHINH_HOADON_MAHD PRIMARY KEY (MAHD)
);

CREATE TABLE CHITIETHOADON(
	MAHD INT,
	MASP INT,
	SOLUONG INT, 

	CONSTRAINT KHOACHINH_CHITIETHOADON_MAHD_MASP PRIMARY KEY (MAHD,MASP),
	CONSTRAINT KHOANGOAI_CHITIETHOADON_MAHD FOREIGN KEY (MAHD) REFERENCES HOADON (MAHD),
	CONSTRAINT KHOANGOAI_CHITIETHOADON_MASP FOREIGN KEY (MASP) REFERENCES SANPHAM (MASP)
);