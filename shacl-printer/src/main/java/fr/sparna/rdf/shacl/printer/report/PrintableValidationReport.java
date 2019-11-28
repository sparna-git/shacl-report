package fr.sparna.rdf.shacl.printer.report;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrintableValidationReport {

	protected ValidationReport validationReport;
	protected List<PrintableSHResultSummaryEntry> resultsSummary;
	protected List<PrintableSHResult> results;

	public PrintableValidationReport(ValidationReport validationReport) {
		this.validationReport = validationReport;
	}
	
	public synchronized List<PrintableSHResultSummaryEntry> getResultsSummary() {
		if(resultsSummary == null) {
			this.resultsSummary = this.validationReport.getResultsSummary().stream()
					.map(e -> new PrintableSHResultSummaryEntry(e))
					.collect(Collectors.toList());
			
			Collections.sort(this.resultsSummary, (e1, e2) -> { return e2.getCount() - e1.getCount() ;});
			
		}
		return this.resultsSummary;		
	}
	
	public synchronized List<PrintableSHResult> getResultsFor(PrintableSHResultSummaryEntry entry) {
		return this.validationReport.getResultsFor(entry.getShResultSummaryEntry()).stream().map(r -> new PrintableSHResult(r)).collect(Collectors.toList());
	}	

	
	
	
}
