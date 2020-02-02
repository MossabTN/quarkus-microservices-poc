package io.maxilog.service.mapper.impl;

import io.maxilog.client.CustomerClient;
import io.maxilog.domain.Order;
import io.maxilog.domain.OrderItem;
import io.maxilog.service.dto.CustomerDTO;
import io.maxilog.service.dto.OrderDTO;
import io.maxilog.service.dto.OrderItemDTO;
import io.maxilog.service.mapper.AddressMapper;
import io.maxilog.service.mapper.OrderItemMapper;
import io.maxilog.service.mapper.OrderMapper;
import io.maxilog.service.mapper.PaymentMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class OrderMapperImpl implements OrderMapper {

    private final PaymentMapper paymentMapper;
    private final OrderItemMapper orderItemMapper;
    private final AddressMapper addressMapper;
    private final CustomerClient userClient;

    @Inject
    public OrderMapperImpl(PaymentMapper paymentMapper, OrderItemMapper orderItemMapper,
                           AddressMapper addressMapper, @RestClient CustomerClient userClient) {
        this.paymentMapper = paymentMapper;
        this.orderItemMapper = orderItemMapper;
        this.addressMapper = addressMapper;
        this.userClient = userClient;
    }

    @Override
    public Order toEntity(OrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();
        order.setId( dto.getId() );
        order.setTotalPrice( dto.getTotalPrice() );
        order.setStatus( dto.getStatus() );
        order.setShipped( dto.getShipped() );
        order.setPayment( paymentMapper.toEntity( dto.getPayment() ) );
        order.setShipmentAddress( addressMapper.toEntity( dto.getShipmentAddress() ) );
        order.setOrderItems( orderItemDTOSetToOrderItemSet( dto.getOrderItems() ) );
        //order.setCustomer( dto.getCustomer() );
        order.setCustomer(dto.getCustomer()==null?null:dto.getCustomer().getUsername());
        return order;
    }

    @Override
    public OrderDTO toDto(Order entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId( entity.getId() );
        orderDTO.setTotalPrice( entity.getTotalPrice() );
        orderDTO.setStatus( entity.getStatus() );
        orderDTO.setShipped( entity.getShipped() );
        orderDTO.setPayment( paymentMapper.toDto( entity.getPayment() ) );
        orderDTO.setShipmentAddress( addressMapper.toDto( entity.getShipmentAddress() ) );
        orderDTO.setOrderItems( orderItemSetToOrderItemDTOSet( entity.getOrderItems() ) );
        //orderDTO.setCustomer( entity.getCustomer() );
        try {
            CustomerDTO customerDTO = userClient.getUserByUsername(entity.getCustomer());
            if(customerDTO != null){
                orderDTO.setCustomer(customerDTO);
            }else{
                orderDTO.setCustomer(new CustomerDTO(entity.getCustomer()));
            }
        } catch (Exception e) {
            orderDTO.setCustomer(new CustomerDTO(entity.getCustomer()));
        }
        return orderDTO;
    }

    @Override
    public List<Order> toEntity(List<OrderDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( dtoList.size() );
        for ( OrderDTO orderDTO : dtoList ) {
            list.add( toEntity( orderDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderDTO> toDto(List<Order> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( entityList.size() );
        for ( Order order : entityList ) {
            list.add( toDto( order ) );
        }

        return list;
    }

    protected Set<OrderItem> orderItemDTOSetToOrderItemSet(Set<OrderItemDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<OrderItem> set1 = new HashSet<OrderItem>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( OrderItemDTO orderItemDTO : set ) {
            set1.add( orderItemMapper.toEntity( orderItemDTO ) );
        }

        return set1;
    }

    protected Set<OrderItemDTO> orderItemSetToOrderItemDTOSet(Set<OrderItem> set) {
        if ( set == null ) {
            return null;
        }

        Set<OrderItemDTO> set1 = new HashSet<OrderItemDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( OrderItem orderItem : set ) {
            set1.add( orderItemMapper.toDto( orderItem ) );
        }

        return set1;
    }
}
