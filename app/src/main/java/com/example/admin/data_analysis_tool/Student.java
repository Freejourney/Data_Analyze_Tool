package com.example.admin.data_analysis_tool;

public class Student {

	private String name;
	private int chinese;
	private int english;
	private int math;
	private int politic;
	private int history;
	private int pysic;
	private int bio;
	private int geo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getChinese() {
		return chinese;
	}
	public void setChinese(int chinese) {
		this.chinese = chinese;
	}
	public int getEnglish() {
		return english;
	}
	public void setEnglish(int english) {
		this.english = english;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getPolitic() {
		return politic;
	}
	public void setPolitic(int politic) {
		this.politic = politic;
	}
	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}
	public int getPysic() {
		return pysic;
	}
	public void setPysic(int pysic) {
		this.pysic = pysic;
	}
	public int getBio() {
		return bio;
	}
	public void setBio(int bio) {
		this.bio = bio;
	}
	public int getGeo() {
		return geo;
	}
	public void setGeo(int geo) {
		this.geo = geo;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", chinese=" + chinese + ", english=" + english + ", math=" + math
				+ ", politic=" + politic + ", history=" + history + ", pysic=" + pysic + ", bio=" + bio + ", geo=" + geo
				+ "]";
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
