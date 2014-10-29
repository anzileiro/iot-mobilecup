package com.fiap.icinema;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProdutoAdapter extends ArrayAdapter<Produto> implements TextWatcher  {
	
	private Context context;
	private int resourceId;
	private List<Produto> data;

	public ProdutoAdapter(Context context, int resource, List<Produto> objects) {
		super(context, resource, objects);
		this.context = context;
		resourceId = resource;
		data = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ProdutoHolder holder = null;
		View row = convertView;
		
		if(row == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resourceId, parent, false);
			
			holder = new ProdutoHolder();
			holder.nome = (TextView) row.findViewById(R.id.txtProdutoNome);
			holder.preco = (TextView) row.findViewById(R.id.txtProdutoPreco);
			holder.imagem = (ImageView) row.findViewById(R.id.imgProduto);
			holder.quantidade = (EditText) row.findViewById(R.id.edtProdutoQuantidade);
			holder.subTotal = (TextView) row.findViewById(R.id.txtProdutoSubTotal);
			holder.quantidade.addTextChangedListener(this);
			holder.position = position;
			
			row.setTag(holder);
		}else{
			holder = (ProdutoHolder)row.getTag();
		}
		
		Produto produto = data.get(position);
		holder.nome.setText(produto.getNome());
		holder.imagem.setImageResource(produto.getIcone());
		holder.preco.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
		holder.subTotal.setText(String.format("Sub-total: R$ %.2f", 0f));
		
		return row;
	}
	
	static class ProdutoHolder{
		TextView nome;
		TextView preco;
		ImageView imagem;
		EditText quantidade;
		TextView subTotal;
		int position;
	}

	@Override
	public void afterTextChanged(Editable s) {
		EditText quantidade = (EditText) ((Activity)context).getCurrentFocus();
		String texto = quantidade.getText().toString();
		float subTotal = 0;
		View parent = (View) quantidade.getParent();
		ProdutoHolder holder = (ProdutoHolder) parent.getTag();
		Produto p = data.get(holder.position);
		
		if(!texto.equals("")){
			int valor = Integer.parseInt(texto);			
			p.setQuantidade(valor);
			subTotal = p.calcularSubTotal();
		}else{
			p.setQuantidade(0);
		}
		
		holder.subTotal.setText(String.format("Sub-total: R$ %.2f", subTotal));
		calcularTotal();
	}
	
	private void calcularTotal(){
		View decorView = ((Activity) context).getWindow().getDecorView();
		TextView total = (TextView) decorView.findViewById(R.id.txtTotal);
		
		float valor = obterTotal();
		total.setText(String.format("Total: R$ %.2f", valor));
	}

	public float obterTotal() {
		float total = 0f;
		
		for(Produto produto : data)
			total += produto.calcularSubTotal();
			
		return total;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

}
