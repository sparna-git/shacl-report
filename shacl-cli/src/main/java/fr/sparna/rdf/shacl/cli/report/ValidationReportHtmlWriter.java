package fr.sparna.rdf.shacl.cli.report;

import java.io.OutputStream;

import org.apache.jena.rdf.model.Model;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import fr.sparna.rdf.shacl.result.ValidationReportFormat;
import fr.sparna.rdf.shacl.result.ValidationReportWriter;

public class ValidationReportHtmlWriter implements ValidationReportWriter {

	@Override
	public void write(Model validationReport, OutputStream out) {
		JenaReportParser parser= new JenaReportParser(validationReport);
		JtwigModel model = JtwigModel.newModel();
		model.with("data", parser.getResults());
		JtwigTemplate template = JtwigTemplate.classpathTemplate("views/report.twig");
		
		template.render(model, out);
	}

	@Override
	public ValidationReportFormat getFormat() {
		return ValidationReportFormat.HTML;
	}

	
	
}
