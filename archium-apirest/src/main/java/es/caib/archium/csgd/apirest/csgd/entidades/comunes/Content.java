package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class Content {

	private String mimetype;
	private String content;
	private String encoding;

	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String toString() {
		return "Content{" +
				"mimetype='" + mimetype + '\'' +
				", content='" + content + '\'' +
				", encoding='" + encoding + '\'' +
				'}';
	}
}
