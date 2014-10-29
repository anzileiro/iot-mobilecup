package com.fiap.icinema;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class PedidoActivity extends ListActivity implements OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        
        new ServiceAPI((AudioManager) getSystemService(AUDIO_SERVICE)).resolverLuz(this);
        
        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(new Produto(R.drawable.ic_launcher, "Pipoca", 10.5f));
        produtos.add(new Produto(R.drawable.ic_launcher, "Refrigerante", 6.75f));
        
        ProdutoAdapter adapter = new ProdutoAdapter(this, R.layout.row_produtos, produtos);
        setListAdapter(adapter);
        inicializarTela();
    }


    private void inicializarTela() {
		TextView total = (TextView) findViewById(R.id.txtTotal);
		total.setText(String.format("Total: R$ %.2f", 0f));
		
		View trocoPara = findViewById(R.id.layTrocoPara);
		trocoPara.setVisibility(View.INVISIBLE);
		
		RadioGroup formasPagamento = (RadioGroup) findViewById(R.id.rgrFormaPagamento);
		formasPagamento.setOnCheckedChangeListener(this);
	}

	
	public void enviar(View view){
		try{
			validarPedido();
			
			enviarPedido();
		}catch(Exception e){
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("Atenção!")
			.setMessage(e.getMessage())
			.setCancelable(true)
			.show();
		}
	}
	
	private void enviarPedido(){
		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("Confirmação")
		.setMessage("Tem certeza de que deseja enviar o pedido?")
		.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				agradecer();			
			}
		})
		.setNegativeButton("Não", null)
		.show();
	}
	
	private void agradecer(){
		new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("Obrigado!")
			.setMessage("Pedido enviado, obrigado! :D")
			.setCancelable(true)
			.show();
	}
	
	private void validarPedido(){		
		float total = obterTotalPedido();
		
		if(total == 0)
			throw new RuntimeException("Nenhum produto selecionado! D:");
		
		if(formaPagamentoNaoInformada())
			throw new RuntimeException("Nenhuma forma de pagamento selecionada! D:");
		
		if(trocoParaMenorQueOuIgualValorTotal())
			throw new RuntimeException("O valor do 'troco para' precisa ser maior que o valor total! D:");
	}


	private boolean formaPagamentoNaoInformada() {
		RadioGroup formasPagamento = (RadioGroup) findViewById(R.id.rgrFormaPagamento);
		int checkedId = formasPagamento.getCheckedRadioButtonId();
		
		return checkedId == -1;
	}


	private boolean trocoParaMenorQueOuIgualValorTotal() {
		boolean retorno = false;
		
		if(formaPagamentoEhDinheiro()){
			float total = obterTotalPedido();
			float trocoPara = obterTrocoPara();
			
			retorno = trocoPara != 0 && trocoPara <= total;
		}
			
		
		return retorno;
	}
	
	private boolean formaPagamentoEhDinheiro() {
		RadioGroup formasPagamento = (RadioGroup) findViewById(R.id.rgrFormaPagamento);
		int checkedId = formasPagamento.getCheckedRadioButtonId();
		
		View trocoPara = findViewById(R.id.layTrocoPara);
		
		return checkedId == R.id.rbuDinheiro && trocoPara.getVisibility() == View.VISIBLE;
	}


	private float obterTrocoPara() {
		TextView trocoPara = (TextView) findViewById(R.id.eteTrocoPara);
		
		float valor = 0;
		String texto = trocoPara.getText().toString();
		
		if(!texto.equals(""))
			valor = Float.parseFloat(texto);
		
		return valor;
	}


	private float obterTotalPedido(){
		ProdutoAdapter adapter = (ProdutoAdapter) getListAdapter();
		
		return adapter.obterTotal();
	}
	
	private void iniciarIntent(Context contexto, Class<?> classe) {
		Intent i = new Intent(contexto, classe);
		startActivity(i);
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		View trocoPara = findViewById(R.id.layTrocoPara);
		
		int visibility = R.id.rbuCartao == checkedId ? View.INVISIBLE : View.VISIBLE;
		
		trocoPara.setVisibility(visibility);
	}
}
