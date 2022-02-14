# Point of Scale

Abhishek Kumar Yadav

[![Repo](https://badgen.net/badge/icon/GitHub?icon=github&label)/abhishek-yadav-increff/employee-spring-full](https://github.com/abhishek-yadav-increff/employee-spring-full.git)


### Functional Requirements

1.  Upload brand/category details (Brand Master) using TSV, from UI :heavy_check_mark:
    
2.  View + Create + Edit a brand detail using UI :heavy_check_mark:
    
3.  Upload product details (Product Master) using TSV, from UI :heavy_check_mark:
	1.  a product cannot be inserted if its brand-category does not exist in the Brand Master :heavy_check_mark:    

4.  View + Create + Edit a product detail using UI :heavy_check_mark:
5.  Upload product wise inventory using TSV :heavy_check_mark:
    
6.  Edit inventory for a product :heavy_check_mark:
    
7.  Create a customer order 
	1.  Enter barcode, Quantity, MRP (multiple rows) :heavy_check_mark:
    
	2.  On creating order, the inventory should get reduced
    
	3.  An order should cannot be created for a product which does not exist :heavy_check_mark:
    

8.  Edit an existing customer order
    
9.  Download a PDF for a customer invoice (so that it can be printed)
    
10.  Reports
		- Inventory Report
	    - Brand
	    - reportSales report(for a duration)

### Other Checkmarks
1.  All code except Controller must have automated tests
2.  80% code coverage, 90% method coverage, 90% class coverage  
3.  Tests for all layers - dao, api, utilities
4.  Code must be properly layered. For e.g. there should be no business logic in Controller or Repository layer, there should not be any queries in Service layer :heavy_check_mark:
5.  It should be a clean code
6.  No commented out code snippets
7.  Code must be properly packaged :heavy_check_mark:
8.  Be careful about when to use public, private, protected, static modifiers :heavy_check_mark:
9.  Be careful about using @Transactional properly. E.g. if 2-3 entities need to be stored in a transaction, they should be wrapped using @Transactional
10.  UI should be clean and look professional
11.  Limits - A malicious user can potentially upload a file with very large data. All file uploads must be limited to 5000 rows maximum
12.  Error messaging - User should be shown proper error in the UI if there is some issue in the data being submitted via TSV or forms
13.  There should be code comments explaining what the code is doing. This is specially required in your test code
