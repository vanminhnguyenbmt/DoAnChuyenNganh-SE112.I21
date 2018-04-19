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
        [HttpGet]
        public IEnumerable<LoaiSanPham> Get()
        {
            return _dbLazada.tbLoaiSanPham.ToList();
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
