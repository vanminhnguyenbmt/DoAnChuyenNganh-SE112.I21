//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebApiMVC
{
    using System;
    using System.Collections.Generic;
    
    public partial class SanPham
    {
        public int MASP { get; set; }
        public string TENSP { get; set; }
        public string MOTA { get; set; }
        public int MALOAISP { get; set; }
    
        public virtual LoaiSanPham LoaiSanPham { get; set; }
    }
}
