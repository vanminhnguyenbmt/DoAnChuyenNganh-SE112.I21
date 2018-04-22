using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApiMVC.Models;

namespace WebApiMVC.Controllers
{
    public class LoaiSanPhamController : ApiController
    {
        LAZADAEntities _dblazada = new LAZADAEntities();
        [HttpGet]
        [Route("api/loaisanpham/getall")]
        public IEnumerable<loaisanpham> GetAll(string name, int number)
        {
            var loaisanpham = new List<loaisanpham>();
            foreach (var loaisp in _dblazada.LoaiSanPhams.ToList())
            {
                loaisanpham loaisanp = new loaisanpham();
                loaisanp.MALOAICHA = loaisp.MALOAI_CHA;
                loaisanp.TENLOAISP = loaisp.TENLOAISP;
                loaisanp.MALOAISP = loaisp.MALOAISP;
                loaisanpham.Add(loaisanp);
            }
            return loaisanpham;

        }

        [HttpPost]
        [Route("api/loaisanpham/getsanpham")]
        public IEnumerable<string> GetSanPham(string name)
        {
            return new string[] { "Loaisanpham1", "Loaisanpham2" };
        }
    }
}
