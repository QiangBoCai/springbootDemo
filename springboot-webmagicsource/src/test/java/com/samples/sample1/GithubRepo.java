package com.samples.sample1;

import java.util.List;

public class GithubRepo {

	private String author ;
	private String name ;
	private String readme ;
	private List<String> language;
	private int star;
	private int fork;
	private String url;
	public GithubRepo() {
		super();
	}
	public GithubRepo(String author, String name, String readme,
			List<String> language, int star, int fork, String url) {
		super();
		this.author = author;
		this.name = name;
		this.readme = readme;
		this.language = language;
		this.star = star;
		this.fork = fork;
		this.url = url;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReadme() {
		return readme;
	}
	public void setReadme(String readme) {
		this.readme = readme;
	}
	public List<String> getLanguage() {
		return language;
	}
	public void setLanguage(List<String> language) {
		this.language = language;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getFork() {
		return fork;
	}
	public void setFork(int fork) {
		this.fork = fork;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "GithubRepo [author=" + author + ", name=" + name + ", readme="
				+ readme + ", language=" + language + ", star=" + star
				+ ", fork=" + fork + ", url=" + url + "]";
	}
	
	
	
}
