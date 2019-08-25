package co.edu.univalle.shoppingcart.accesodatos;

import android.arch.persistence.room.Room;

import co.edu.univalle.shoppingcart.presentacion.AppContext;

public class ConexionDB {

    private static AppDataBase dataBaseInstance;

    private ConexionDB(){ }

    public static AppDataBase getInstance(){
        if (dataBaseInstance == null){
            dataBaseInstance = Room.databaseBuilder(AppContext.getContext(), AppDataBase.class,
                    "DataBaseShopping").build();
        }

        return dataBaseInstance;
    }
}
