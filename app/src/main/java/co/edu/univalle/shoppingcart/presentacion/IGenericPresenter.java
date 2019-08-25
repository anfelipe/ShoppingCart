package co.edu.univalle.shoppingcart.presentacion;

import co.edu.univalle.shoppingcart.dto.DataBaseDTO;

public interface IGenericPresenter {

    void actualizarVista(DataBaseDTO dataBaseDTO);

    //Notificaciones de las operaciones en la BD
    void notificar(DataBaseDTO dataBaseDTO);
}
