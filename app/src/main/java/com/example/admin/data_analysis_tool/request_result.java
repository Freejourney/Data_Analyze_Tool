package com.example.admin.data_analysis_tool;

public class request_result {

	String log_id;
	String result_inner;
	
	public String getResult_inner() {
		return result_inner;
	}

	public void setResult_inner(String result_inner) {
		this.result_inner = result_inner;
	}

	@Override
	public String toString() {
		return "request_result [log_id=" + log_id + ", result_inner=" + result_inner + "]";
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	
	
}
