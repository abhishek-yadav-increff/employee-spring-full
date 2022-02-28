package com.increff.employee.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.util.List;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.BrandUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto("     nestLE        ", "ChocolatE ");
        // add
        brandDto.add(brandForm);
        BrandPojo brandMasterPojo = brandDto.getByBrandAndCategory(brandForm);
        // test added data
        assertEquals("nestle", brandMasterPojo.getBrand());
        assertEquals("chocolate", brandMasterPojo.getCategory());
    }

    @Test
    public void testGet() throws ApiException {
        // add
        BrandForm brandForm = BrandUtil.getBrandFormDto(" nestLE ", "ChocolatE ");
        brandDto.add(brandForm);
        BrandPojo brandMasterPojo = brandDto.getByBrandAndCategory(brandForm);
        // get data
        BrandData brandData = brandDto.get(brandMasterPojo.getId());
        assertEquals("nestle", brandData.getBrand());
        assertEquals("chocolate", brandData.getCategory());
    }

    @Test
    public void testGetAll() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" nestLE ", "DairY ");
        brandDto.add(brandForm);
        brandForm = BrandUtil.getBrandFormDto(" nestLE ", "ChOcolAte ");
        brandDto.add(brandForm);
        // get all data
        List<BrandData> brandDatas = brandDto.getAll();
        assertEquals(2, brandDatas.size());
    }

    @Test
    public void testGetByCategory() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" nestLE ", "DairY ");
        brandDto.add(brandForm);
        brandForm = BrandUtil.getBrandFormDto(" CADbury ", "DairY ");
        brandDto.add(brandForm);
        brandForm = BrandUtil.getBrandFormDto(" nestLE ", "Shoes ");
        brandDto.add(brandForm);

        List<BrandData> brandDatas = brandDto.getByCategory("dairy");
        assertEquals(2, brandDatas.size());
    }

    @Test
    public void testGetByBrand() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" nestLE ", "DairY ");
        brandDto.add(brandForm);
        brandForm = BrandUtil.getBrandFormDto(" CADbury ", "DairY ");
        brandDto.add(brandForm);
        brandForm = BrandUtil.getBrandFormDto(" nestLE ", "Shoes ");
        brandDto.add(brandForm);

        List<BrandData> brandDatas = brandDto.getByBrand("nestle");
        assertEquals(2, brandDatas.size());
    }

    @Test
    public void testGetListByBrandAndCategory() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" nestLE ", "DairY ");
        brandDto.add(brandForm);
        brandForm = BrandUtil.getBrandFormDto(" CADbury ", "DairY ");
        brandDto.add(brandForm);
        brandForm = BrandUtil.getBrandFormDto(" nestLE ", "Shoes ");
        brandDto.add(brandForm);

        List<BrandData> brandDatas = brandDto.getListByBrandAndCategory("nestle", "");
        assertEquals(2, brandDatas.size());
        brandDatas = brandDto.getListByBrandAndCategory("", "shoes");
        assertEquals(1, brandDatas.size());
        brandDatas = brandDto.getListByBrandAndCategory("", "");
        assertEquals(3, brandDatas.size());
        brandDatas = brandDto.getListByBrandAndCategory("nestle", "dairy");
        assertEquals(1, brandDatas.size());

        try {
            brandDatas = brandDto.getListByBrandAndCategory("cadbury", "shoes");
        } catch (ApiException apiException) {
            assertNotNull(apiException);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetByBrandAndCategory() throws ApiException {
        BrandForm brandForm = BrandUtil.getBrandFormDto(" nestLE ", "ChocolatE ");
        brandDto.add(brandForm);
        BrandPojo brandPojo = brandDto.getByBrandAndCategory(brandForm);
        // get data
        assertEquals("nestle", brandPojo.getBrand());
        assertEquals("chocolate", brandPojo.getCategory());
    }

    @Test
    public void testUpdateBrand() throws ApiException {
        // add
        BrandForm brandForm = BrandUtil.getBrandFormDto(" AmuL ", "DairY ");
        brandDto.add(brandForm);
        BrandPojo brandPojo = brandDto.getByBrandAndCategory(brandForm);
        // create update form
        BrandForm brandFormUpdate = BrandUtil.getBrandFormDto("amul", "FOOd ");
        brandDto.update(brandPojo.getId(), brandFormUpdate);
        BrandPojo brandPojoNew = brandDto.getByBrandAndCategory(brandFormUpdate);
        // test update
        assertEquals("amul", brandPojoNew.getBrand());
        assertEquals("food", brandPojoNew.getCategory());
    }

}
