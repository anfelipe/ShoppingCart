package co.edu.univalle.shoppingcart.dominio;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import co.edu.univalle.shoppingcart.R;
import co.edu.univalle.shoppingcart.dto.DataBaseDTO;
import co.edu.univalle.shoppingcart.enumeraciones.EnumUrl;
import co.edu.univalle.shoppingcart.presentacion.AppContext;

public class AccesoConexionRestLogic extends AsyncTask<DataBaseDTO, Integer, DataBaseDTO> {

    @Override
    protected DataBaseDTO doInBackground(DataBaseDTO... dataBaseDTOS) {
        StringBuffer respuesta = new StringBuffer();

        try{

            StringBuffer peticion = new StringBuffer();
            peticion.append(dataBaseDTOS[0].getParametros());
            peticion.append("?size=").append("MEDIUM");
            peticion.append("&color=").append("red");
            peticion.append("&maxprice=").append("250");
            peticion.append("&category=").append("women-new-arrivals");
            peticion.append("&page=").append("1");
            peticion.append("&pagesize=").append("10");

            Log.i("Info-Url", peticion.toString());

            URL url = new URL(peticion.toString());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("X-RapidAPI-Host", EnumUrl.HOST.getValor());
            httpsURLConnection.setRequestProperty("X-RapidAPI-Key",
                    AppContext.getContext().getString(R.string.keyForever21));

            httpsURLConnection.setDoOutput(false);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.connect();

            Log.i("Info-Header", httpsURLConnection.getRequestProperty("X-RapidAPI-Key"));
            Log.i("Info-Header", httpsURLConnection.getRequestProperty("X-RapidAPI-Host"));

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpsURLConnection.getInputStream()));

            String linealeida;

            while ((linealeida = bufferedReader.readLine()) != null){
                respuesta.append(linealeida);
            }

            dataBaseDTOS[0].setRespuesta(respuesta.toString());

            bufferedReader.close();
            httpsURLConnection.disconnect();

        }catch (Exception e){
            Log.e("Error", "Error consultando productos" + e.getMessage());
            dataBaseDTOS[0].setException(e);
        }

        Log.i("Info-respuesta", respuesta.toString());

        //return respuesta.toString();
        return dataBaseDTOS[0];
    }

    @Override
    protected void onPostExecute(DataBaseDTO dataBaseDTO) {
        if (dataBaseDTO.getException() != null){
            Log.i("Info", "Excepcion: " + dataBaseDTO.getException());
            dataBaseDTO.getGenericPresenter().notificar(dataBaseDTO);
        }else{
            Log.i("Info", "Actualizar");
            dataBaseDTO.getGenericPresenter().actualizarVista(dataBaseDTO);
        }
    }
}
