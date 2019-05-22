package stelio.braga.steliobrga.MyListe.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import stelio.braga.steliobrga.MyListe.databasesProdutos.DatabasesProdutos;
import stelio.braga.steliobrga.MyListe.R;
import stelio.braga.steliobrga.MyListe.entities.Produtos;

public class ProdutoActivity extends AppCompatActivity {

    private static final int IMAGE_PICKER_REQUESTCODE = 1;
    TextInputEditText tie_produto_quantidade, tie_produto_nome,  tie_produto_preco;
    Button bt_produto;
    Uri imagem_uri;
    Spinner  spr_produto_unidade;
    MaterialSpinner spinner;
    ImageView image_picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        image_picker = findViewById(R.id.image_picker);
        tie_produto_nome = findViewById(R.id.tie_produto_nome);
        tie_produto_preco = findViewById(R.id.tie_produto_preco);
        tie_produto_quantidade = findViewById(R.id.tie_produto_quantidade);
        bt_produto =  findViewById(R.id.bt_produto);
        spinner = findViewById(R.id.spinner);
        spr_produto_unidade = findViewById(R.id.spr_produto_unidade);


        spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        bt_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduto();
            }
        });
        image_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhotoIntent, IMAGE_PICKER_REQUESTCODE); //one can be replaced with any action code
            }
        });
    }

    // add items into spinner dynamically
//    public void categoria_add_sp() {
//
//
//        List<String> list = new ArrayList<String>();
//        list.add("list 1");
//        list.add("list 2");
//        list.add("list 3");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spr_categoria.setAdapter(dataAdapter);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IMAGE_PICKER_REQUESTCODE:
                if ((resultCode == RESULT_OK) && (data != null)) {
                    imagem_uri = data.getData();
                    changeImage(imagem_uri);

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


    // Methods
    private void changeImage(Uri newImageUri){
        image_picker.setImageURI(newImageUri);
    }

    public  void  saveProduto(){
         final String nome = tie_produto_nome.getText().toString().trim();
         final String preco = tie_produto_preco.getText().toString().trim();
         final String quantidade = tie_produto_quantidade.getText().toString().trim();
        // final String categoria = spr_categoria.getSelectedItem().toString().trim();
       //  final String unidade   = spr_produto_unidade.getSelectedItem().toString().trim();
         final Uri   imagem  = imagem_uri;

         if (nome.isEmpty()){
             tie_produto_nome.requestFocus();
             return;
         }
         if(preco.isEmpty()){
             tie_produto_preco.requestFocus();
         }
         if (quantidade.isEmpty()){
             tie_produto_quantidade.requestFocus();
         }

        Produtos produtos = new Produtos();
        produtos.setNome(nome);
        produtos.setValor(preco);
        produtos.setQuantidade(quantidade);
        //   produtos.setCategoria(categoria);
        //  produtos.setUnidade(unidade);
        produtos.setImagem(imagem.toString());

        DatabasesProdutos.getmInstance(getApplication()).getDatabases()
                .produtoDoa()
                .insert(produtos);

        class Savarproduto extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
//                Produtos produtos = new Produtos();
//                produtos.setNome(nome);
//                produtos.setValor(preco);
//                produtos.setQuantidade(quantidade);
//             //   produtos.setCategoria(categoria);
//              //  produtos.setUnidade(unidade);
//                produtos.setImagem(imagem.toString());
//
//                DatabasesProdutos.getmInstance(getApplication()).getDatabases()
//                        .produtoDoa()
//                        .insert(produtos);

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


}
