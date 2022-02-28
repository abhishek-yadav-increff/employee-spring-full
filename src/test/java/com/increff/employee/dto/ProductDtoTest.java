package com.increff.employee.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.List;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.BrandUtil;
import com.increff.employee.util.ProductTestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ProductDtoTest
 */
public class ProductDtoTest extends AbstractUnitTest {

    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;

    @Test
    public void add() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" branD ", " Category");
        brandDto.add(brandForm);
        ProductForm form =
                ProductTestUtil.getProductForm(" ", " braNd", "categoRy", "Name ", 2.999);
        productDto.add(form);

        // Checks to ensure the product has been added
        // Product is unique for brand, category, name, mrp
        List<ProductData> productDatas = productDto.getAll();
        assertEquals(1, productDatas.size());

        ProductData productData = productDto.getByBarcode(productDatas.get(0).getBarcode());
        assertEquals(productData.getBrand(), "brand");
        assertEquals(productData.getCategory(), "category");
        assertEquals(productData.getName(), "name");
        double precision = 0.001d;
        assertEquals(productData.getMrp(), 3.00, precision);
    }

    @Test(expected = ApiException.class)
    public void addDuplicate() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" branD ", " Category");
        brandDto.add(brandForm);
        ProductForm form =
                ProductTestUtil.getProductForm(" ", " braNd", "categoRy", "Name ", 2.999);
        productDto.add(form);
        form = ProductTestUtil.getProductForm(" ", " bRANd    ", "   categoRY   ", "NAme ", 2.999);
        productDto.add(form);
    }


    @Test
    public void get() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" branD ", " Category");
        brandDto.add(brandForm);
        ProductForm form =
                ProductTestUtil.getProductForm(" ", " braNd", "categoRy", "Name ", 2.999);
        productDto.add(form);

        List<ProductData> productDatas = productDto.getAll();
        assertEquals(1, productDatas.size());
        // Checks if get returns the correctproduct
        ProductData productData = productDto.get(productDatas.get(0).getId());
        assertEquals(productData.getBrand(), "brand");
        assertEquals(productData.getCategory(), "category");
        assertEquals(productData.getName(), "name");
        double precision = 0.001d;
        assertEquals(productData.getMrp(), 3.00, precision);
    }

    // getByBarcode() already gets checked during add()

    @Test
    public void getAll() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" branD ", " Category");
        brandDto.add(brandForm);
        ProductForm form =
                ProductTestUtil.getProductForm(" ", " braNd", "categoRy", "Name ", 2.999);
        productDto.add(form);
        form = ProductTestUtil.getProductForm(" ", " brAnd", "categoRy", "NamEe ", 2.999);
        productDto.add(form);

        // Checks length of returned list
        List<ProductData> productDatas = productDto.getAll();
        assertEquals(2, productDatas.size());

    }


    @Test
    public void update() throws ApiException {
        // Adding data
        BrandForm brandForm = BrandUtil.getBrandFormDto(" branD ", " Category");
        brandDto.add(brandForm);
        ProductForm form =
                ProductTestUtil.getProductForm(" ", " braNd", "categoRy", "Name ", 2.999);
        productDto.add(form);
        List<ProductData> productDatas = productDto.getAll();

        // Updating data
        Integer id = productDatas.get(0).getId();
        String barcode = productDatas.get(0).getBarcode();
        form = ProductTestUtil.getProductForm(barcode, "brand", "category", "   naEme   ", 9.55);
        productDto.update(id, form);

        // Assertions
        form = productDto.get(id);
        assertEquals(form.getMrp(), 9.55, 0.00000d);
        assertEquals(form.getName(), "naeme");
    }

    @Test
    public void updateBlanks() throws ApiException {
        // Adding data
        BrandForm brandForm = BrandUtil.getBrandFormDto(" branD ", " Category");
        brandDto.add(brandForm);
        ProductForm form =
                ProductTestUtil.getProductForm(" ", " braNd", "categoRy", "Name ", 2.999);
        productDto.add(form);
        List<ProductData> productDatas = productDto.getAll();

        // Updating data
        Integer id = productDatas.get(0).getId();
        String barcode = productDatas.get(0).getBarcode();

        // update with blank name
        try {
            form = ProductTestUtil.getProductForm(barcode, "brand", "category", "      ", 9.55);
            productDto.update(id, form);
        } catch (ApiException ee) {

        } catch (Exception e) {
            fail();
        }
        // update with 0 mrp
        try {
            form = ProductTestUtil.getProductForm(barcode, "brand", "category", "      as", 0.);
            productDto.update(id, form);
        } catch (ApiException ee) {

        } catch (Exception e) {
            fail();
        }
        // update with different brand category
        try {
            form = ProductTestUtil.getProductForm(barcode, "brnd", "category", "      ", 9.55);
            productDto.update(id, form);
        } catch (ApiException ee) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = ApiException.class)
    public void updateDuplicate() throws ApiException {
        // Adding data
        BrandForm brandForm = BrandUtil.getBrandFormDto(" branD ", " Category");
        brandDto.add(brandForm);
        ProductForm form =
                ProductTestUtil.getProductForm(" ", " braNd", "categoRy", "Name ", 2.999);
        productDto.add(form);
        form = ProductTestUtil.getProductForm(" ", "brand", "category", "   naEme   ", 9.55);
        productDto.add(form);
        List<ProductData> productDatas = productDto.getAll();

        // Updating data
        Integer id = productDatas.get(0).getId();
        assertEquals(productDatas.get(0).getMrp(), 2.99, 0.01d);
        String barcode = productDatas.get(0).getBarcode();

        form = ProductTestUtil.getProductForm(barcode, "brand", "category", "   naEme   ", 9.55);
        productDto.update(id, form);

    }
}
