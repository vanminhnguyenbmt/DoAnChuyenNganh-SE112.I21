using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LazadaWebAPI.Models
{
    public interface IDanhBa
    {
        IEnumerable<DanhBa> LayDanhBa();
        void CapNhapDanhBa(DanhBa danhba);
        void XoaDanhBa(int id);
        DanhBa TimKiem(int id);
        void ThemDanhBa(DanhBa danhba);
    }
}
