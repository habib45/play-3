error id: file://<WORKSPACE>/app/models/Product.java
file://<WORKSPACE>/app/models/Product.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[165,1]

error in qdox parser
file content:
```java
offset: 3585
uri: file://<WORKSPACE>/app/models/Product.java
text:
```scala
// package models;

// import io.ebean.Model;
// import io.ebean.Finder;
// import jakarta.persistence.*;
// import javax.validation.constraints.NotNull;

// @Entity
// @Table(name = "products")
// public class Product extends Model {

//     @Id
//     private Long id;

//     @NotNull
//     @Column(name = "Product_Name")
//     private String productName;

//     private String category;
//     private String subCategory;
//     private String brand;
//     private String unit;
//     private String sku;

//     @Column(name = "Minimum_Qty")
//     private Integer minimumQty;

//     private Integer quantity;
//     private String description;
//     private Double tax;

//     @Column(name = "Discount_Type")
//     private String discountType;

//     private Double price;
//     private Boolean status;

//     @Column(name = "Product_Image")
//     private String productImage;

//     // ----- GETTERS AND SETTERS -----
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getProductName() {
//         return productName;
//     }

//     public void setProductName(String productName) {
//         this.productName = productName;
//     }

//     public String getCategory() {
//         return category;
//     }

//     public void setCategory(String category) {
//         this.category = category;
//     }

//     public String getSubCategory() {
//         return subCategory;
//     }

//     public void setSubCategory(String subCategory) {
//         this.subCategory = subCategory;
//     }

//     public String getBrand() {
//         return brand;
//     }

//     public void setBrand(String brand) {
//         this.brand = brand;
//     }

//     public String getUnit() {
//         return unit;
//     }

//     public void setUnit(String unit) {
//         this.unit = unit;
//     }

//     public String getSku() {
//         return sku;
//     }

//     public void setSku(String sku) {
//         this.sku = sku;
//     }

//     public Integer getMinimumQty() {
//         return minimumQty;
//     }

//     public void setMinimumQty(Integer minimumQty) {
//         this.minimumQty = minimumQty;
//     }

//     public Integer getQuantity() {
//         return quantity;
//     }

//     public void setQuantity(Integer quantity) {
//         this.quantity = quantity;
//     }

//     public String getDescription() {
//         return description;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }

//     public Double getTax() {
//         return tax;
//     }

//     public void setTax(Double tax) {
//         this.tax = tax;
//     }

//     public String getDiscountType() {
//         return discountType;
//     }

//     public void setDiscountType(String discountType) {
//         this.discountType = discountType;
//     }

//     public Double getPrice() {
//         return price;
//     }

//     public void setPrice(Double price) {
//         this.price = price;
//     }

//     public Boolean getStatus() {
//         return status;
//     }

//     public void setStatus(Boolean status) {
//         this.status = status;
//     }

//     public String getProductImage() {
//         return productImage;
//     }

//     public void setProductImage(String productImage) {
//         this.productImage = productImage;
//     }

//     // ----- FINDER -----
//     public static final Finder<Long, Product> find = new Finder<>(Product.class);
// }
@@
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:489)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:587)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:584)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:619)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:617)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1306)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:584)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:904)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:687)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:467)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:840)
```
#### Short summary: 

QDox parse error in file://<WORKSPACE>/app/models/Product.java