package fr.sparna.rdf.shacl.cli.validate;

import java.io.File;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Validate an input RDF data against the provided SHACL file, and writes the output in the given output file")
public class ArgumentsValidate {

	@Parameter(
			names = { "-i", "--input" },
			description = "Path to a local RDF file",
			required = true,
			variableArity = true
	)
	private List<File> input;
	
	@Parameter(
			names = { "-s", "--shapes" },
			description = "Path to an RDF file containing the shapes definitions to use",
			required = true
	)
	private File shapes;
	
	@Parameter(
			names = { "-x", "--extra" },
			description = "Extra data to use for validation - but not part of validated data itself. Typically ontology file with subClassOf predicates",
			required = false
	)
	private File extra;

	@Parameter(
			names = { "-o", "--output" },
			description = "Path to the output file",
			required = true,
			variableArity = true
	)
	private List<File> output;

	public List<File> getInput() {
		return input;
	}

	public void setInput(List<File> input) {
		this.input = input;
	}

	public List<File> getOutput() {
		return output;
	}

	public void setOutput(List<File> output) {
		this.output = output;
	}

	public File getShapes() {
		return shapes;
	}

	public void setShapes(File shapes) {
		this.shapes = shapes;
	}

	public File getExtra() {
		return extra;
	}

	public void setExtra(File extra) {
		this.extra = extra;
	}
	
}
