package co.edu.univalle.shoppingcart.accesodatos;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

public abstract class GenericDAO <T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void Crear(T entity);

    @Update
    public abstract void Actualizar(T entity);

    @Delete
    public abstract void Eliminar(T entity);

}
