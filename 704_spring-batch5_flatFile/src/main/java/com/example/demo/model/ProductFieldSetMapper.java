package com.example.demo.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ProductFieldSetMapper implements FieldSetMapper<ProductCsv> {

    @Override
    public ProductCsv mapFieldSet(FieldSet fieldSet) throws BindException {
        ProductCsv productCsv = new ProductCsv();
        productCsv.setProductId(fieldSet.readLong("product_id"));
        productCsv.setProductName(fieldSet.readString("product_name"));
        productCsv.setProductCategory(fieldSet.readString("product_category"));
        productCsv.setProductPrice(fieldSet.readLong("product_price"));

        return productCsv;
    }
}
