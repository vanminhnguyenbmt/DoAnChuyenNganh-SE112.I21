using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebAPIConnectDatabase.Models;

namespace WebAPIConnectDatabase.Controllers
{
    [Route("api/[controller]")]
    public class ValuesController : Controller
    {
        DbLazadaContext _dbLazada;

        public ValuesController(DbLazadaContext dbLazada)
        {
            _dbLazada = dbLazada;
        }
        // GET api/values
        [HttpGet("LayDanhSachSanPham")]
        public IEnumerable<SanPham> LayDanhSachSanPham()
        {
            return _dbLazada.tbSanPham.ToList();
        }

        [HttpGet("LayDanhSachSanPhamTheoMa/{masp}")]
        public IEnumerable<SanPham> LayDanhSachSanPhamTheoMa(int masp)
        {
            var sanphams = _dbLazada.tbSanPham.Where(s => s.MASP.Equals(masp));
            return sanphams.ToList();
        }

        [HttpGet("CapNhapSanPham/{masp}/{tensp}/{mota}")]
        public IEnumerable<SanPham> CapNhapSanPham(int masp, string tensp, string mota)
        {
            var sanphams = _dbLazada.tbSanPham.Where(s => s.MASP.Equals(masp));
            foreach(var sp in sanphams)
            {
                sp.TENSP = tensp;
                sp.MOTA = mota;      
            }
            _dbLazada.SaveChanges();
            return sanphams.ToList();
        }

        [HttpGet("XoaSanPhamTheoMa/{masp}")]
        public IEnumerable<SanPham> XoaSanPhamTheoMa(int masp)
        {
            var sanphams = _dbLazada.tbSanPham.Where(s => s.MASP.Equals(masp));
            _dbLazada.tbSanPham.RemoveRange(sanphams);

            _dbLazada.SaveChanges();
            return sanphams.ToList();
        }
        // GET api/values/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
