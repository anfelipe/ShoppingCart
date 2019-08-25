package co.edu.univalle.shoppingcart.accesodatos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import co.edu.univalle.shoppingcart.dto.ProductoDTO;

@Dao
public abstract class ProductDAO extends GenericDAO<ProductoDTO> {

    @Query("Select * From products Where uidusuario = :uidusuario")
    public abstract List<ProductoDTO> ConsultarProductoXusuario(String uidusuario);

    @Query("Select * From products")
    public abstract List<ProductoDTO> ConsultarProductoTodos();

    @Query("Select * From products Where idProducto = :idProducto")
    public abstract ProductoDTO ConsultarProductoXid(String idProducto);
}
