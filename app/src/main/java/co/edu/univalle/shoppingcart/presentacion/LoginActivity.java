package co.edu.univalle.shoppingcart.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import co.edu.univalle.shoppingcart.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText txtCorreo;
    private TextInputEditText txtClave;
    private TextInputEditText txtNombre;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtClave = findViewById(R.id.txtClave);
        txtNombre = findViewById(R.id.txtNombre);
        txtNombre.setVisibility(View.GONE);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    }

    private Boolean validarDatos(boolean registro){
        boolean res = false;
        String email = txtCorreo.getText().toString();
        String password = txtClave.getText().toString();
        String name = txtNombre.getText().toString();
        txtNombre.setError(null);

        if(!TextUtils.isEmpty(email)){
            if(Patterns.EMAIL_ADDRESS.matcher(txtCorreo.getText().toString()).matches()){
                txtCorreo.setError(null);
            }else{
                txtCorreo.setError("Correo invalido");
            }
        }else{
            txtCorreo.setError("Campo Obligatorio");
        }

        if(registro){
            if(TextUtils.isEmpty(name)){
                txtNombre.setError("Campo Obligatorio");
            }
        }

        if(TextUtils.isEmpty(password)){
            txtClave.setError("Campo Obligatorio");
        }else{
            txtClave.setError(null);
        }

        if(txtCorreo.getError() == null && txtClave.getError() == null &&
                txtNombre.getError() == null){
            res = true;
        }

        return res;
    }

    public void btnIngresar(View view){
        try{
            txtNombre.setText("");
            txtNombre.setVisibility(View.GONE);

            if(validarDatos(false)){
                iniciarSesion();
            }
        }catch (Exception ex){
            Log.e("Error", "No se pudo autenticar con correo");
        }
    }

    public void registrarUsuario(View view){
        if(txtNombre.getVisibility() == View.VISIBLE){
            if(validarDatos(true)){
                crearUsuario();
            }
        }else{
            txtNombre.setVisibility(View.VISIBLE);
        }
    }

    private void ingresarMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("USER", "acardenas");
        startActivity(intent);
        this.finish();
    }

    private void iniciarSesion(){
        try{
            String email = txtCorreo.getText().toString();
            String password = txtClave.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email, password).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                ingresarMenu();
                            }else {
                                Toast.makeText(LoginActivity.this,
                                        "Credenciales Invalidas", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }catch (Exception ex){
            Log.e("Error", "No se pudo iniciar sesion");
        }
    }

    public void crearUsuario(){
        try{
            String email = txtCorreo.getText().toString();
            String password = txtClave.getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String name = txtNombre.getText().toString();
                        actualizarUsuario(name);
                        ingresarMenu();
                    }else{
                        Toast.makeText(LoginActivity.this,
                                "Usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception ex){
            Log.e("Error", "El usuario no se creó correctamente " + ex.getMessage());
        }
    }

    private void actualizarUsuario(String name){
        try{
            FirebaseUser user = firebaseAuth.getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Info", "User profile updated.");
                            }else{
                                Toast.makeText(LoginActivity.this,
                                        "No se pudo actualizar el usuario",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }catch (Exception ex){
            Log.e("Error", "Usuario no se actualizó " + ex.getMessage());
        }
    }

}
