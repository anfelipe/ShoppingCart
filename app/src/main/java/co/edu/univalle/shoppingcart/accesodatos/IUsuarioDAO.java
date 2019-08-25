package co.edu.univalle.shoppingcart.accesodatos;

import co.edu.univalle.shoppingcart.dto.ProductoDTO;

public interface IUsuarioDAO {

    void insertarCompra(ProductoDTO productoDTO) throws Exception;

}
