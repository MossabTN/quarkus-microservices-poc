package io.maxilog.service.impl;

import io.maxilog.order.OrderAvro;
import io.maxilog.domain.Product;
import io.maxilog.domain.UserHolder;
import io.maxilog.repository.ProductRepository;
import io.maxilog.service.ProductService;
import io.maxilog.service.dto.ProductDTO;
import io.maxilog.service.mapper.ProductMapper;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.search.mapper.orm.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Singleton
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final EntityManager entityManager;
    private final UserHolder userHolder;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, EntityManager entityManager, UserHolder userHolder) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.entityManager = entityManager;
        this.userHolder = userHolder;
    }


    @Override
    public ProductDTO save(ProductDTO productDTO) {
        LOG.debug("Request to save Users : {}", productDTO);
        Product user = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDto(user);
    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Users");
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    @Transactional
    public List<ProductDTO> findAllByName(String name) {
        LOG.debug("Request to get all Products by name");
        return Search.session(entityManager)
                .search(Product.class)
                .predicate(f ->
                        name == null || name.trim().isEmpty() ?
                                f.matchAll() :
                                f.simpleQueryString()
                                        .fields("name").matching(name)
                )
                .sort(f -> f.field("name_sort"))
                .fetchAllHits()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Incoming("input")
    public CompletionStage consumeKafkaOrder(KafkaMessage<String, OrderAvro> message) {
        LOG.info("receiving order from kafka");
        CompletableFuture.runAsync(()->{
            message.getPayload().getOrderItems().forEach(orderItem -> {
                productRepository.decreaseProductQuantity(Long.valueOf(orderItem.getProductId()), Integer.parseInt(orderItem.getQuantity()));
            });
        });
        return message.ack();
    }

    @Override
    public ProductDTO findOne(long id) {
        LOG.debug("Request to get User : {}", id);
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElse(null);
    }

    @Override
    public void delete(long id) {
        LOG.debug("Request to delete User : {}", id);
        productRepository.deleteById(id);
    }
}
