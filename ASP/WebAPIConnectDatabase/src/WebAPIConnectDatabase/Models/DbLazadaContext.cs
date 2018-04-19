using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Data.Entity;

namespace WebAPIConnectDatabase.Models
{
    public class DbLazadaContext : DbContext
    {
        public DbLazadaContext(string chuoiketnoi) : base(chuoiketnoi) { }

        public DbSet<LoaiSanPham> tbLoaiSanPham { get; set; }
        public DbSet<SanPham> tbSanPham { get; set; }
    }
}
