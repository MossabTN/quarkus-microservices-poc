package io.maxilog.service.impl;

import io.maxilog.domain.Product;
import io.maxilog.domain.UserHolder;
import io.maxilog.order.OrderAvro;
import io.maxilog.repository.ProductRepository;
import io.maxilog.service.ProductService;
import io.maxilog.service.dto.ProductDTO;
import io.maxilog.service.mapper.ProductMapper;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserHolder userHolder;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper, UserHolder userHolder) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
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
    public Page<ProductDTO> findAllByName(String name, Pageable pageable) {
        LOG.debug("Request to get all Products by name");
        return productRepository.findAllByNameContaining(name, pageable)
                .map(productMapper::toDto);
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
