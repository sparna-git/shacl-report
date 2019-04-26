package fr.sparna.rdf.shacl.result;

import java.io.OutputStream;

import org.apache.jena.rdf.model.Model;

public interface ValidationReportWriter {

	public void write(Model validationReport, OutputStream out);
	
	public ValidationReportFormat getFormat();
}
