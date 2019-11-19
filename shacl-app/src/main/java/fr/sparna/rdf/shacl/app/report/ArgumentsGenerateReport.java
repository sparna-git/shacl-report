package fr.sparna.rdf.shacl.app.report;

import com.beust.jcommander.Parameter;

public class ArgumentsGenerateReport {

	@Parameter(
			names = { "-i", "--input" },
			description = "Chemin du fichier RDF à valider",
			required = true,
			variableArity = true
	)
	private String input;
	
	@Parameter(
			names = { "-o", "--output" },
			description = "Chemin vers le fichier où sera enregistré la sortie",
			required = true
	)
	private String output;
	

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
