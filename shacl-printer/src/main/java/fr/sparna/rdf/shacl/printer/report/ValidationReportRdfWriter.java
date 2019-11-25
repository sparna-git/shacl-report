package fr.sparna.rdf.shacl.printer.report;

import java.io.OutputStream;

import org.apache.jena.riot.Lang;

public class ValidationReportRdfWriter implements ValidationReportWriter {

	protected Lang lang;
	
	public ValidationReportRdfWriter(Lang lang) {
		super();
		this.lang = lang;
	}

	@Override
	public void write(ValidationReport validationReport, OutputStream out) {
		validationReport.getResultsModel().write(out,this.lang.getName());
	}

	@Override
	public ValidationReportOutputFormat getFormat() {
		return ValidationReportOutputFormat.RDF;
	}
	
}
