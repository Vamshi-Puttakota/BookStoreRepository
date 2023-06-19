package com.orderzzz.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.orderzzz.external.entities.Book;

@FeignClient(name = "BOOKSERVICE",  url = "http://localhost:8081/book-service")
public interface BookServiceExternal {
	
	@GetMapping("/order-book/{bookid}/{qty}")
	 Book orderBook(@PathVariable Integer bookid,@PathVariable Integer qty);

}
