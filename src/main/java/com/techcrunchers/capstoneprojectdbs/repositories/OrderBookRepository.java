//package com.techcrunchers.capstoneprojectdbs.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.techcrunchers.capstoneprojectdbs.beans.OrderBook;
//import com.techcrunchers.capstoneprojectdbs.models.ClientWiseStats;
//import com.techcrunchers.capstoneprojectdbs.models.CustodianWiseStats;
//import com.techcrunchers.capstoneprojectdbs.models.OrderDirection;
//import com.techcrunchers.capstoneprojectdbs.models.OrderStatus;
//
//import java.util.List;
//@Repository
//public interface OrderBookRepository extends JpaRepository<OrderBook, Integer> {
//
//    List<OrderBook> findAllByOrderDirectionAndOrderStatus(OrderDirection orderDirection, OrderStatus orderStatus);
//    List<OrderBook> findAllByOrderDirection(OrderDirection orderDirection);
//
//    @Query(value="select custodian_id as custodianId, " +
//            "sum(IF(ob.order_direction='BUY' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalBuy, " +
//            "sum(IF(ob.order_direction='SELL' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalSell, " +
//            "from clientdetails c left join order_book ob on c.client_id = ob.client_id " +
//            "group by custodian_id",nativeQuery = true)
//    List<CustodianWiseStats> getCustodianWiseStats();
//
//    @Query(value="select c.client_id as clientId," +
//            "sum(if(ob.order_direction = 'SELL' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalSell," +
//            "sum(if(ob.order_direction = 'BUY' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalBuy, " +
//            "from clientdetails c " + "left join order_book ob on c.client_id = ob.client_id " +
//            "group by c.client_id",nativeQuery = true)
//    List<ClientWiseStats> getClientWiseStats();
//
//}
//
//
//
//
//
//
//








package com.techcrunchers.capstoneprojectdbs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techcrunchers.capstoneprojectdbs.beans.OrderBook;
import com.techcrunchers.capstoneprojectdbs.models.ClientWiseStats;
import com.techcrunchers.capstoneprojectdbs.models.CustodianWiseStats;
import com.techcrunchers.capstoneprojectdbs.models.OrderDirection;
import com.techcrunchers.capstoneprojectdbs.models.OrderStatus;

import java.util.List;
@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Integer> {

    List<OrderBook> findAllByOrderDirectionAndOrderStatus(OrderDirection orderDirection, OrderStatus orderStatus);
    List<OrderBook> findAllByOrderDirection(OrderDirection orderDirection);

    @Query(value="select custodian_id as custodianId," +
            "sum(IF(ob.order_direction='BUY' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalBuy," +
            "sum(IF(ob.order_direction='SELL' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalSell " +
            "from clientdetails c left join order_book ob on c.client_id = ob.client_id " +
            "group by custodian_id",nativeQuery = true)
    List<CustodianWiseStats> getCustodianWiseStats();

    @Query(value="select c.client_id as clientId," +
            "sum(if(ob.order_direction = 'SELL' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalSell," +
            "sum(if(ob.order_direction = 'BUY' and ob.order_status='COMPLETED',ob.price * initial_quantity,0)) as totalBuy " +
            "from clientdetails c " +
            "left join order_book ob on c.client_id = ob.client_id " +
            "group by c.client_id",nativeQuery = true)
    List<ClientWiseStats> getClientWiseStats();

}



