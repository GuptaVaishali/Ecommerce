package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.entities.product.Product;
import com.ttn.project.ecommerce.entities.registration.Seller;
import com.ttn.project.ecommerce.exceptions.ProductNotFoundException;
import com.ttn.project.ecommerce.repos.CategoryRepository;
import com.ttn.project.ecommerce.repos.ProductRepository;
import com.ttn.project.ecommerce.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDaoService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EmailSenderService emailSenderService;

    public String addProduct(long sellerId, Product product){

        // checking if category is leaf node or not
        Category category = product.getCategory();
        List<Category> categories = categoryRepository.findByParentCategory(category);
        // if size of categories is 0, then it is leaf node
        if (categories.size() != 0){
            return "please enter leaf node category";
        }

        // Product name should be unique
        Optional<Product> product1 = productRepository.findByName(product.getName());
        if (product1.isPresent())
            return "Product name already exists";

        Seller seller = sellerRepository.findById(sellerId).get();
        product.setSeller(seller);
        productRepository.save(product);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("vaishgupta97@gmail.com");
        mailMessage.setSubject("Activate this product");
        mailMessage.setFrom("vaishgupta97@gmail.com");
        mailMessage.setText("Please activate this product with details " + product.getName());
        emailSenderService.sendEmail(mailMessage);

        return "product added successfully";
    }

    public Product viewProduct(long sellerId, long productId) throws Exception {
        Seller loggedInSeller = sellerRepository.findById(sellerId).get();
        Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent())
            throw new ProductNotFoundException("Product not found with product id = " + productId);
        Product product = productById.get();
        long createrSellerId = product.getSeller().getId();
        if (sellerId != createrSellerId)
            throw new Exception("logged in user is not creator of the product");

        if (product.isDeleted())
            throw new Exception("Product should be non-deleted");
        return product;
    }

    public List<Product> viewAllProducts(){
        return (List<Product>) productRepository.findAll();
    }


    public String updateProduct(long sellerId, long productId, Product product){
        System.out.println("name " + product.getName() + " description " + product.getDescription()
            + "  cancellable " + product.isCancellable());
        Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent())
            throw new ProductNotFoundException("Product not found with productId " + productId);

        Product product1 = productById.get();
        long creatorSellerId = product1.getSeller().getId();
        if (sellerId != creatorSellerId)
            return "logged in user is not creator of the product";

        if (product.getName() != null)
            product1.setName(product.getName());
        if (product.getDescription() !=  null)
            product1.setDescription(product.getDescription());
        if (product.isReturnable())
            product1.setReturnable(product.isReturnable());
        if (product.isCancellable())
            product1.setCancellable(product.isCancellable());
        productRepository.save(product1);
        return "Product updated successfully";
    }

    public String deleteProduct(long sellerId,long productId){
        Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent())
            throw new ProductNotFoundException("Product not found with productId " + productId);
        Product product = productById.get();
        long creatorSellerId = product.getSeller().getId();
        if (sellerId != creatorSellerId)
            return "logged in user is not creator of the product";

        product.setDeleted(true);
     //   productRepository.delete(product);
        return "product deleted successfully";
    }


    public String deActivateProduct(long productId){
        Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent())
            throw new ProductNotFoundException("Product not found with productId " + productId);

        Product product = productById.get();
        if (product.isActive() == true) {
            product.setActive(false);
            productRepository.save(product);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            //    mailMessage.setTo("vaishgupta97@gmail.com");
            mailMessage.setTo(product.getSeller().getEmail());
            mailMessage.setSubject("Product DeActivation Successful");
            mailMessage.setFrom("vaishgupta97@gmail.com");
            mailMessage.setText("Product has been deactivated " + product.getName());
            emailSenderService.sendEmail(mailMessage);

            return "Product deactivated successfully";
        }
        else
            return "Product is deactivated already";
    }


    public String activateProduct(long productId){

        Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent())
            throw new ProductNotFoundException("Product not found with productId " + productId);

        Product product = productById.get();
        if (product.isActive() == false) {
            product.setActive(true);
            productRepository.save(product);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
        //    mailMessage.setTo("vaishgupta97@gmail.com");
            mailMessage.setTo(product.getSeller().getEmail());
            mailMessage.setSubject("Product Activation Successful");
            mailMessage.setFrom("vaishgupta97@gmail.com");
            mailMessage.setText("Product has been activated " + product.getName());
            emailSenderService.sendEmail(mailMessage);

            return "Product activated successfully";
        }
        else
            return "Product is activated already";
    }


    public List<Product> viewSimilarProducts(long productId){
        Optional<Product> productById = productRepository.findById(productId);
        if (!productById.isPresent())
            throw new ProductNotFoundException("Product not found with productId " + productId);

        Product product = productById.get();
        System.out.println(">>>>>> name " + product.getName() +  " brand " + product.getBrand());
        Category category = product.getCategory();
        System.out.println(">>> category " + category.getId());
        List<Product> products = productRepository.findAllByCategory(category);
        return products;
    }
}
