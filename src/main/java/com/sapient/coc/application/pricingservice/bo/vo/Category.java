package com.sapient.coc.application.pricingservice.bo.vo;

/**
 * Created by Tridib Chakraborty on 7/3/17.
 */
public class Category {

  private String id;
  private String name;
  private String href;
  private Category parent;

  public Category() {
  }

  public Category(String id){
    this.id = id;
  }

  public Category(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public Category(String id, String name, Category parent) {
    this.id = id;
    this.name = name;
    this.parent = parent;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Category getParent() {
    return parent;
  }

  public void setParent(Category parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
    return "Category{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", href='" + href + '\'' +
            ", parent=" + parent +
            '}';
  }
}
