package entelect.training.incubator.srping.booking.models;

import lombok.Data;

@Data
public class BookingSearchRequest {
     private SearchType searchType;
     private Integer customerId;
     private String referenceNumber;
}
