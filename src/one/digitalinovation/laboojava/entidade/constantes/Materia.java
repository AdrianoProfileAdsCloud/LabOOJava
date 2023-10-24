package one.digitalinovation.laboojava.entidade.constantes;

public enum Materia {
	M2(2), M5(5), M10(10);

	private int materia;

	Materia(int materia) {
		this.materia = materia;
	}

	public int getMateria() {
		return materia;
	}
}
