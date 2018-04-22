using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebAPIConnectDatabase.Models
{
    public class LoaiSanPham
    {
        [Key]
        public int MALOAISP { get; set; }
        public string TENLOAISP { get; set; }
        public int MALOAI_CHA { get; set; }

        public ICollection<SanPham> SanPham { get; set; }
    }
}
