package one.digitalinovation.laboojava.entidade;

import java.util.Objects;

import one.digitalinovation.laboojava.entidade.constantes.Materia;

public class Caderno extends Produto {

	private Materia materia;

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	@Override
	public double calcularFrete() {
		return (getPreco() * getQuantidade()) + materia.getMateria();
	}

	@Override
	public String toString() {
		return "Caderno [Matéria=" + materia + ","
				+ "" + "Código=" + getCodigo() + ","
						+ "" + "Preco=" + getPreco()+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(materia);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caderno other = (Caderno) obj;
		return materia == other.materia;
	}

}
