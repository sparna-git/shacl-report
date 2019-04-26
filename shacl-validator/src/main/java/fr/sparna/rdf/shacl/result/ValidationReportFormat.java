package fr.sparna.rdf.shacl.result;

import java.util.Arrays;
import java.util.List;

public enum ValidationReportFormat {

	RDF(Arrays.asList(new String[] { "ttl", "rdf", "jsonld" })),
	CSV(Arrays.asList(new String[] { "csv" })),
	HTML(Arrays.asList(new String[] { "html" }));
	
	protected List<String> fileExtensions;
	
	private ValidationReportFormat(List<String> fileExtensions) {
		this.fileExtensions = fileExtensions;
	}

	public List<String> getFileExtensions() {
		return fileExtensions;
	}
	
	public static ValidationReportFormat forFileName(String filename) {
		String extension = filename.substring(filename.lastIndexOf('.')+1);
		for (ValidationReportFormat f : ValidationReportFormat.values()) {
			if(f.getFileExtensions().contains(extension)) {
				return f;
			}
		}
		
		return null;
	}
	
}
