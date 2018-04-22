using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Data.Entity;

namespace WebAPIConnectDatabase.Models
{
    public class DataInit: DropCreateDatabaseIfModelChanges<DbLazadaContext>
    {
        protected override void Seed(DbLazadaContext context)
        {
            var itemSP = new List<SanPham>();
            SanPham sp1 = new SanPham();
            sp1.TENSP = "Máy lạnh SamSung";
            sp1.MOTA = "Máy lạnh được sản xuất bởi SamSung";
            sp1.MALOAISP = 1;

            SanPham sp2 = new SanPham();
            sp2.TENSP = "Máy giặt Sony";
            sp2.MOTA = "Máy lạnh được sản xuất bởi Sony";
            sp2.MALOAISP = 2;

            itemSP.Add(sp1);
            itemSP.Add(sp2);

            var itemLSP = new List<LoaiSanPham>()
            {
                new LoaiSanPham
                {
                    MALOAISP = 1,
                    TENLOAISP = "SamSung",
                    MALOAI_CHA = 0
                },
                new LoaiSanPham
                {
                    MALOAISP = 2,
                    TENLOAISP = "Sony",
                    MALOAI_CHA = 0
                },
            };

            context.tbLoaiSanPham.AddRange(itemLSP);
            context.tbSanPham.AddRange(itemSP);

            context.SaveChanges();
        }
    }
}
