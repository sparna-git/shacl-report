package fr.sparna.rdf.shacl.printer.report;

public class PrintableSHResultSummaryEntry {

	protected SHResultSummaryEntry shResultSummaryEntry;

	public PrintableSHResultSummaryEntry(SHResultSummaryEntry shResultSummaryEntry) {
		super();
		this.shResultSummaryEntry = shResultSummaryEntry;
	}
	
	public String getSourceShape() {
		return RDFRenderer.renderResource(shResultSummaryEntry.getSourceShape());
	}

	public String getMessage() {
		return shResultSummaryEntry.getMessage();
	}

	public String getResultPath() {
		return RDFRenderer.renderResource(shResultSummaryEntry.getResultPath());
	}

	public String getResultSeverity() {
		return RDFRenderer.renderResource(shResultSummaryEntry.getResultSeverity());
	}

	public String getSourceConstraintComponent() {
		return RDFRenderer.renderResource(shResultSummaryEntry.getSourceConstraintComponent());
	}
	
	public int getCount() {
		return shResultSummaryEntry.getCount();
	}
	
	public String getSampleFocusNode() {
		return RDFRenderer.renderResource(shResultSummaryEntry.getSampleFocusNode());
	}
	
	public String getSampleValue() {
		return RDFRenderer.renderRDFNode(shResultSummaryEntry.getSampleValue());
	}
	
}
