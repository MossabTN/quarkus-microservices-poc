package io.maxilog.service.mapper.impl;

import io.maxilog.client.ProductClient;
import io.maxilog.domain.OrderItem;
import io.maxilog.service.dto.OrderItemDTO;
import io.maxilog.service.dto.ProductDTO;
import io.maxilog.service.mapper.OrderItemMapper;
import io.maxilog.service.mapper.OrderMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderItemMapperImpl implements OrderItemMapper {

    private final ProductClient productClient;

    @Inject
    public OrderItemMapperImpl(@RestClient ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public OrderItem toEntity(OrderItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setId( dto.getId() );
        orderItem.setQuantity( dto.getQuantity() );
        orderItem.setProductId(dto.getProduct()!=null?dto.getProduct().getId():null);
        return orderItem;
    }

    @Override
    public OrderItemDTO toDto(OrderItem entity) {
        if ( entity == null ) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId( entity.getId() );
        orderItemDTO.setQuantity( entity.getQuantity() );
        try {
            ProductDTO productDTO = productClient.getProductById(entity.getProductId());
            if(productDTO != null){
                orderItemDTO.setProduct(productDTO);
            }else{
                orderItemDTO.setProduct(new ProductDTO(entity.getProductId()));
            }
        } catch (Exception e) {
            orderItemDTO.setProduct(new ProductDTO(entity.getProductId()));
        }
        return orderItemDTO;
    }

    @Override
    public List<OrderItem> toEntity(List<OrderItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OrderItem> list = new ArrayList<OrderItem>( dtoList.size() );
        for ( OrderItemDTO orderItemDTO : dtoList ) {
            list.add( toEntity( orderItemDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderItemDTO> toDto(List<OrderItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderItemDTO> list = new ArrayList<OrderItemDTO>( entityList.size() );
        for ( OrderItem orderItem : entityList ) {
            list.add( toDto( orderItem ) );
        }

        return list;
    }
}