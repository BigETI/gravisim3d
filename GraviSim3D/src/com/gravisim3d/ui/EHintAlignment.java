package com.gravisim3d.ui;

public enum EHintAlignment {

	TOP_LEFT(EHorizontalAlignment.LEFT, EVerticalAlignment.TOP),

	TOP_RIGHT(EHorizontalAlignment.RIGHT, EVerticalAlignment.TOP),

	BOTTOM_LEFT(EHorizontalAlignment.LEFT, EVerticalAlignment.BOTTOM),

	BOTTOM_RIGHT(EHorizontalAlignment.RIGHT, EVerticalAlignment.BOTTOM);

	private EHorizontalAlignment horizontal_alignment;

	private EVerticalAlignment vertical_alignment;

	EHintAlignment(EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment) {
		this.horizontal_alignment = horizontal_alignment;
		this.vertical_alignment = vertical_alignment;
	}

	public EHorizontalAlignment getHorizontalAlignment() {
		return horizontal_alignment;
	}

	public EVerticalAlignment getVerticalAlignment() {
		return vertical_alignment;
	}
}