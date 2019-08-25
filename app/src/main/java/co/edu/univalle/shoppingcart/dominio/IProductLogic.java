package co.edu.univalle.shoppingcart.dominio;

import java.util.List;

import co.edu.univalle.shoppingcart.dto.ProductoDTO;
import co.edu.univalle.shoppingcart.presentacion.IGenericPresenter;

public interface IProductLogic {

    void agegarProducto(IGenericPresenter iGenericPresenter, ProductoDTO productoDTO) throws Exception;

    void eliminarProducto(IGenericPresenter iGenericPresenter, ProductoDTO productoDTO) throws Exception;

    List<ProductoDTO> consultarProductosTodos(IGenericPresenter iGenericPresenter,
                                              ProductoDTO productoDTO) throws Exception;

    List<ProductoDTO> consultarProductosXusuario(IGenericPresenter iGenericPresenter,
                                                 ProductoDTO productoDTO) throws Exception;

    void insertarCompra(IGenericPresenter iGenericPresenter, ProductoDTO productoDTO) throws Exception;

    List<ProductoDTO> ConsultarProductosCategoria(IGenericPresenter iGenericPresenter,
                                                  String uidUsuario) throws Exception;

    void ConsultarProductosCategoria1(IGenericPresenter iGenericPresenter,
                                      String uidUsuario) throws Exception;

    List<ProductoDTO> ListaProductosInicio(String respuesta, String uidUsuario) throws Exception;
}
