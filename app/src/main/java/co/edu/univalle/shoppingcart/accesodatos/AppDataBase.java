package co.edu.univalle.shoppingcart.accesodatos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import co.edu.univalle.shoppingcart.dto.ProductoDTO;

@Database(entities = {ProductoDTO.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ProductDAO getProductDAO();
}
