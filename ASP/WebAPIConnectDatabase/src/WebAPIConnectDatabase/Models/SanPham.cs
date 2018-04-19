using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebAPIConnectDatabase.Models
{
    public class SanPham
    {
        [Key]
        public int MASP { get; set; }
        public string TENSP { get; set; }
        public int MALOAISP { get; set; }

        [ForeignKey("MALOAISP")]
        public LoaiSanPham LoaiSanPham { get; set; }
    }
}
