package pp.model;

import pp.model.enums.TagType;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Tag {
	private int start;
	private int end;
	private TagType type;
	private String page;

	public Tag(final int start, final int end, final TagType type, final String page) {
		this.start = start;
		this.end = end;
		this.type = type;
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public TagType getType() {
		return type;
	}

	public String getPage() {
		return page;
	}

	@Override
	public String toString() {
		return type.toString() + "{" + page.substring(start, end) +"}";
	}
}
