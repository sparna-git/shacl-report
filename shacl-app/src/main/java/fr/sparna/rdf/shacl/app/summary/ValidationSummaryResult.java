package fr.sparna.rdf.shacl.app.summary;

public class ValidationSummaryResult {

	protected String resultMessage;
	protected String resultPath;
	protected String resultSeverity;
	protected String sourceConstraintComponent;
	protected String sampleValue;
	protected String sampleFocusNode;
	protected String sourceShape;
	
	protected int count;
	
	
	
	public String getSourceShape() {
		return sourceShape;
	}
	public void setSourceShape(String sourceShape) {
		this.sourceShape = sourceShape;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getResultPath() {
		return resultPath;
	}
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}
	public String getResultSeverity() {
		return resultSeverity;
	}
	public void setResultSeverity(String resultSeverity) {
		this.resultSeverity = resultSeverity;
	}
	public String getSourceConstraintComponent() {
		return sourceConstraintComponent;
	}
	public void setSourceConstraintComponent(String sourceConstraintComponent) {
		this.sourceConstraintComponent = sourceConstraintComponent;
	}
	public String getSampleValue() {
		return sampleValue;
	}
	public void setSampleValue(String sampleValue) {
		this.sampleValue = sampleValue;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getSampleFocusNode() {
		return sampleFocusNode;
	}
	public void setSampleFocusNode(String sampleFocusNode) {
		this.sampleFocusNode = sampleFocusNode;
	}
	
}
