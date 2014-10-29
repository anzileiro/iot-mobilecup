package com.fiap.icinema;

public class Produto {
	
	private int icone;
	private String nome;
	private float preco;
	private int quantidade;
	
	public Produto(int icone, String nome, float preco){
		this.icone = icone;
		this.nome = nome;
		this.preco = preco;
	}
	
	public float calcularSubTotal(){
		return preco * quantidade;
	}

	public int getIcone() {
		return icone;
	}

	public String getNome() {
		return nome;
	}

	public float getPreco() {
		return preco;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
