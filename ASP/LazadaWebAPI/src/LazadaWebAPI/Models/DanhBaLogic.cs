using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LazadaWebAPI.Models
{
    public class DanhBaLogic : IDanhBa
    {
        List<DanhBa> listDanhBa = new List<DanhBa>();
        public void CapNhapDanhBa(DanhBa danhba)
        {
            
            
        }

        public IEnumerable<DanhBa> LayDanhBa()
        {
            return listDanhBa;
        }

        public void ThemDanhBa(DanhBa danhba)
        {
            DanhBa db = new DanhBa();
            db.hoten = "Văn Minh Nguyên";
            db.sodt = "01672983683";
            db.diachi = "Ký túc xá khu B";

            DanhBa db1 = new DanhBa();
            db1.hoten = "Văn Minh Nguyên";
            db1.sodt = "01672983683";
            db1.diachi = "Ký túc xá khu B";

            DanhBa db2 = new DanhBa();
            db2.hoten = "Văn Minh Nguyên";
            db2.sodt = "01672983683";
            db2.diachi = "Ký túc xá khu B";

            listDanhBa.Add(db);
            listDanhBa.Add(db1);
            listDanhBa.Add(db2);
        }

        public DanhBa TimKiem(int id)
        {
            return null;
        }

        public void XoaDanhBa(int id)
        {
            
        }
    }
}
