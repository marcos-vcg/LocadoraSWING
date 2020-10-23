package model;

public enum Grau {

	Selecione(""), AVO("Avo"), PAI("Pai"), FILHO("Filho"), TIO("Tio"), SOBRINHO("Sobrinho"), CONJUJE("Cônjuje");
	
	private String descricao;
	
	Grau(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() { return descricao; }
	
}
