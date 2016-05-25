package com.gravisim3d.ui;

/**
 * Hint alignment enumerator
 * 
 * @author Ethem Kurt
 *
 */
public enum EHintAlignment {

	/**
	 * Top-Left
	 */
	TOP_LEFT(EHorizontalAlignment.LEFT, EVerticalAlignment.TOP),

	/**
	 * Top-Right
	 */
	TOP_RIGHT(EHorizontalAlignment.RIGHT, EVerticalAlignment.TOP),

	/**
	 * Bottom-Left
	 */
	BOTTOM_LEFT(EHorizontalAlignment.LEFT, EVerticalAlignment.BOTTOM),

	/**
	 * Bottom-Right
	 */
	BOTTOM_RIGHT(EHorizontalAlignment.RIGHT, EVerticalAlignment.BOTTOM);

	/**
	 * Horizontal alignment
	 */
	private EHorizontalAlignment horizontal_alignment;

	/**
	 * Vertical alignment
	 */
	private EVerticalAlignment vertical_alignment;

	/**
	 * Constructor
	 * 
	 * @param horizontal_alignment
	 *            Horizontal alignment
	 * @param vertical_alignment
	 *            Vertical alignment
	 */
	EHintAlignment(EHorizontalAlignment horizontal_alignment, EVerticalAlignment vertical_alignment) {
		this.horizontal_alignment = horizontal_alignment;
		this.vertical_alignment = vertical_alignment;
	}

	/**
	 * Get horizontal alignment
	 * 
	 * @return Horizontal alignment
	 */
	public EHorizontalAlignment getHorizontalAlignment() {
		return horizontal_alignment;
	}

	/**
	 * Get vertical alignment
	 * 
	 * @return Vertical alignment
	 */
	public EVerticalAlignment getVerticalAlignment() {
		return vertical_alignment;
	}
}