package fr.sparna.rdf.shacl.printer.report;

import java.io.OutputStream;

public interface ValidationReportWriter {

	public void write(ValidationReport report, OutputStream out);
	
	public ValidationReportOutputFormat getFormat();
}
