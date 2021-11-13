package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.dto.ProductVariationUpdateDto;
import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataFieldRelation;
import com.ttn.project.ecommerce.entities.product.Product;
import com.ttn.project.ecommerce.entities.product.ProductVariation;
import com.ttn.project.ecommerce.exceptions.ProductNotFoundException;
import com.ttn.project.ecommerce.exceptions.ProductVariationNotFoundException;
import com.ttn.project.ecommerce.repos.MetadataFieldValuesRepository;
import com.ttn.project.ecommerce.repos.ProductRepository;
import com.ttn.project.ecommerce.repos.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

@Service
public class ProductVariationService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

    public String createProductVariation(ProductVariation productVariation){
        Product product = productVariation.getProduct();
        Optional<Product> productById = productRepository.findById(product.getId());
        if (!productById.isPresent())
            return "product id is not valid";
        Product product1 = productById.get();
        productVariation.setProduct(product1);

        if (!product1.isActive() || product1.isDeleted())
            return "product should be active and non-deleted";


        Category category = product1.getCategory();
        long catId = category.getId();      // list of category id aayegi till parent categoryid

        List<CategoryMetadataFieldRelation> categoryMetadataFieldValuesList
                = metadataFieldValuesRepository.findByCategoryId(catId);

        Map<Object,String> meta = new LinkedHashMap<>();

        for (CategoryMetadataFieldRelation categoryMetadataFieldRelation : categoryMetadataFieldValuesList) {
//            System.out.println("fieldId >> " + categoryMetadataFieldRelation.getCategoryMetadataField().getId()
//                    + " fieldname >> " + categoryMetadataFieldRelation.getCategoryMetadataField().getName()
//            + " values>>> " + categoryMetadataFieldRelation.getValue());

            meta.put(categoryMetadataFieldRelation.getCategoryMetadataField().getName(),
                    categoryMetadataFieldRelation.getValue());
        }

        String metadata = productVariation.getMetadata();
    //    System.out.println("metadata string >>> " + metadata);

        System.out.println(meta);

        JSONObject jsonObj = new JSONObject(metadata);
        Iterator keys = jsonObj.keys();
        while(keys.hasNext()){
            String currentKey = (String)keys.next();

            if (meta.get(currentKey) == null){
                return "meta value mismatch";
            }

            if (!meta.get(currentKey).contains(jsonObj.getString(currentKey))){
                return "invalid value in meta field";
            }

        //    String currentValue = jsonObj.getString(currentKey);    // the data type would not always string object
        //    System.out.println("Key values obtained>>>>> " + currentKey +  " " + currentValue);
        }

        productVariationRepository.save(productVariation);
        return "product variation created successfully";
    }

    public ProductVariation viewProductVariation(long sellerId,long productVariationId){

        Optional<ProductVariation> productVariationById =
                productVariationRepository.findById(productVariationId);
        if (!productVariationById.isPresent())
            throw new ProductVariationNotFoundException("Product variation not found with id = " +
                    productVariationId);

        ProductVariation productVariation = productVariationById.get();
        long creatorSellerId = productVariation.getProduct().getSeller().getId();
        if (sellerId != creatorSellerId)
            try {
                throw new Exception("logged in seller is not creator of product");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return productVariation;
    }

    public List<ProductVariation> viewAllProductVariations(long productId){

        Optional<Product> productById = productRepository.findById(productId);
        if(!productById.isPresent())
            throw new ProductNotFoundException("Product id is not valid");
        List<ProductVariation> allById =
                (List<ProductVariation>) productVariationRepository.findAllByProduct(productById.get());

        return allById;
    }

    public String updateProductVariation(long productVariationId,
                                         ProductVariationUpdateDto productVariation){
        Optional<ProductVariation> productVariationById =
                productVariationRepository.findById(productVariationId);
        if (!productVariationById.isPresent())
            throw new ProductVariationNotFoundException("Product variation not found with id " +
                    productVariationId);
        ProductVariation productVariation1 = productVariationById.get();

        if (productVariation.getQuantityAvailable() != 0)
            productVariation1.setQuantityAvailable(productVariation.getQuantityAvailable());

        if (productVariation.getPrice() != 0)
            productVariation1.setPrice(productVariation.getPrice());

        if (productVariation.isActive() != false)
            productVariation1.setActive(productVariation.isActive());

        productVariationRepository.save(productVariation1);
        return "Product variation updated successfully";
    }

}
