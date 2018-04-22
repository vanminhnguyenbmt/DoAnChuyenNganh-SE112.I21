using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using LazadaWebAPI.Models;

// For more information on enabling Web API for empty projects, visit http://go.microsoft.com/fwlink/?LinkID=397860

namespace LazadaWebAPI.Controllers
{
    [Route("api/[controller]")]
    public class DanhBaController : Controller
    {
        public IDanhBa iDanhBa { get; set; }

        public DanhBaController(IDanhBa idb)
        {
            iDanhBa = idb;
        }

        [HttpGet("LayDanhBa")]
        public IEnumerable<DanhBa> DanhSachDanhBa()
        {
            return iDanhBa.LayDanhBa();
        }

        [HttpGet("AddDanhBa")]
        public void AddDanhBa(DanhBa danhba)
        {
            iDanhBa.ThemDanhBa(danhba);
        }
    }
}
