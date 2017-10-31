package com.yyoo.model;

public class Greeting {
	private final long id;
	private final String content;
	public Greeting(long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Greeting other = (Greeting) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Greeting [id=" + id + ", content=" + content + "]";
	}
	
	
}
