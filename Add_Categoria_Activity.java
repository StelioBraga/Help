package stelio.braga.steliobrga.MyListe.activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import stelio.braga.steliobrga.MyListe.databasesCategoria.DatabaseCategoria;
import stelio.braga.steliobrga.MyListe.R;
import stelio.braga.steliobrga.MyListe.entities.Categoria;

public class Add_Categoria_Activity extends AppCompatActivity {
    private static final int IMAGE_PICKER_REQUESTCODE = 1;
        TextInputEditText tie_categoria_nome, tie_categoria_descricao;
        ImageView imagem_categoria;
        Button bt_categoria;
        Uri pick_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__categoria);
        tie_categoria_descricao = findViewById(R.id.tie_categoria_descricao);
        tie_categoria_nome = findViewById(R.id.tie_categoria_nome);
        imagem_categoria =findViewById(R.id.imagem_categoria);
        bt_categoria  = findViewById(R.id.bt_categoria);

        bt_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduto();

            }
        });
        imagem_categoria.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhotoIntent, IMAGE_PICKER_REQUESTCODE); //one can be replaced with any action code
                }

        });



    }

    private void changeImage(Uri newImageUri){
        imagem_categoria.setImageURI(newImageUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IMAGE_PICKER_REQUESTCODE:
                if ((resultCode == RESULT_OK) && (data != null)) {
                    pick_categoria = data.getData();
                    changeImage(pick_categoria);

                } else if (resultCode == RESULT_CANCELED) {
                    // the user did not pick any photo.
                    Toast.makeText(this, "Canceled!", Toast.LENGTH_SHORT).show();

                } else {
                    // Some other error occured.
                }
                break;

            default:
                break;
        }

    }


    public  void  saveProduto(){
        final String nome = tie_categoria_nome.getText().toString().trim();
        final Uri   imagem  = pick_categoria;

        Categoria  categoria= new Categoria();
        categoria.setNome(nome);
        categoria.setImagem(imagem.toString());

        DatabaseCategoria.getmInstance(getApplication()).getDatabases()
                .categoriaDAO()
                .getAll();



        class Savarproduto extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Categoria  categoria= new Categoria();
                categoria.setNome(nome);
                categoria.setImagem(imagem.toString());

                DatabaseCategoria.getmInstance(getApplication()).getDatabases()
                        .categoriaDAO()
                        .getAll();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(),"Guardodo com sucesso", Toast.LENGTH_LONG).show();
            }
        }
        Savarproduto salvar = new Savarproduto();
        salvar.execute();
    }


        public static String dataatual(){
        long yourmlliseconds = System.currentTimeMillis();
        @SuppressLint("formatodata")
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYY");
            Date resultedate = new Date (yourmlliseconds);
            return  sdf.format(resultedate);
        }
}
