package com.gravisim3d.ui;

import processing.core.PGraphics;

public class Hint extends Panel {
  
  private Text hint_text;
  
  private double padding = 5.0;
  
  public Hint(String hint_text, double size_x, double size_y, double size_z, int color) {
    super(0.0, 0.0, 0.0, size_x, size_y, size_z, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, color);
    this.hint_text = new Text(hint_text, 0.0, 0.0, 0.0, 12.0, EHorizontalAlignment.LEFT, EVerticalAlignment.TOP, 0xFFFFFFFF);
  }
  
  public Text getHintText() {
    return hint_text;
  }
  
  public void setPadding(double padding) {
    this.padding = padding;
    hint_text.setPos(padding, padding, hint_text.getPos().z);
    setSize(hint_text.getSize().x + (padding * 2.0), hint_text.getSize().y + (padding * 2.0), getSize().z);
  }
  
  public double getPadding() {
    return padding;
  }
  
  @Override
  protected void drawSiblings(PGraphics graphics) {
    super.drawSiblings(graphics);
    hint_text.drawComponent(graphics, getSize());
  }
  
  @Override
  public void setHorizontalAlignment(EHorizontalAlignment horizontal_alignment) {
    super.setHorizontalAlignment(horizontal_alignment);
    if (hint_text != null)
      hint_text.setHorizontalAlignment(horizontal_alignment);
  }
  
  @Override
  public void setVerticalAlignment(EVerticalAlignment vertical_alignment) {
    super.setVerticalAlignment(vertical_alignment);
    if (hint_text != null)
      hint_text.setVerticalAlignment(vertical_alignment);
  }
  
  @Override
  public void dispose() {
    super.dispose();
    hint_text.dispose();
    hint_text = null;
 }
}