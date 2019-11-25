package fr.sparna.rdf.shacl.printer.report;

import java.util.Arrays;
import java.util.List;

public enum ValidationReportOutputFormat {

	RDF(Arrays.asList(new String[] { "ttl", "rdf", "jsonld" })),
	CSV(Arrays.asList(new String[] { "csv" })),
	HTML(Arrays.asList(new String[] { "html" })),
	HTML_SUMMARY(Arrays.asList(new String[] { "summary.html" }));
	
	protected List<String> fileExtensions;
	
	private ValidationReportOutputFormat(List<String> fileExtensions) {
		this.fileExtensions = fileExtensions;
	}

	public List<String> getFileExtensions() {
		return fileExtensions;
	}
	
	public static ValidationReportOutputFormat forFileName(String filename) {
		String extension = filename.substring(filename.lastIndexOf('.')+1);
		for (ValidationReportOutputFormat f : ValidationReportOutputFormat.values()) {
			if(f.getFileExtensions().contains(extension)) {
				return f;
			}
		}
		
		return null;
	}
	
}
