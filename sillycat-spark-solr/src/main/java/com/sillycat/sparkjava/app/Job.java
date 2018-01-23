package com.sillycat.sparkjava.app;

import java.io.Serializable;
import java.math.BigDecimal;

public class Job implements Serializable
{

  private static final long serialVersionUID = 6989138985207683092L;

  private String id;

  private String title;

  private BigDecimal price;

  public BigDecimal getPrice()
  {
    return price;
  }

  public void setPrice( BigDecimal price )
  {
    this.price = price;
  }

  public String getId()
  {
    return id;
  }

  public void setId( String id )
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  @Override
  public String toString()
  {
    return "Job [id=" + id + ", title=" + title + ", price=" + price + "]";
  }

}
