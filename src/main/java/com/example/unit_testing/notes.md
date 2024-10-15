## createProduct
String productJson = """
{
"name": "Test Product",
"description": "A test product",
"price": 99.99,
"stockQuantity": 50
}
""";

## testGetAllProducts
Product product1 = new Product();
product1.setName("Product A");
product1.setDescription("Description A");
product1.setPrice(10.99);
product1.setStockQuantity(100);

Product product2 = new Product();
product2.setName("Product B");
product2.setDescription("Description B");
product2.setPrice(20.99);
product2.setStockQuantity(200);

## testGetProductsByName
Product product = new Product();
product.setName("Test Product");
product.setDescription("Description for Test Product");
product.setPrice(99.99);
product.setStockQuantity(50);

## testGetProductsByPriceRange
Product product1 = new Product();
product1.setName("Product A");
product1.setDescription("Description A");
product1.setPrice(15.99);
product1.setStockQuantity(100);

Product product2 = new Product();
product2.setName("Product B");
product2.setDescription("Description B");
product2.setPrice(25.99);
product2.setStockQuantity(200);

## testGetProductsByColor
Product product1 = new Product();
product1.setName("Product A");
product1.setDescription("Description A");
product1.setColor("Red");
product1.setPrice(15.99);
product1.setStockQuantity(100);

Product product2 = new Product();
product2.setName("Product B");
product2.setDescription("Description B");
product2.setColor("Red");
product2.setPrice(25.99);
product2.setStockQuantity(200);

## testDeleteProduct
Product product = new Product();
product.setName("Product to Delete");
product.setDescription("Will be deleted");
product.setPrice(9.99);
product.setStockQuantity(50);
