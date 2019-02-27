package com.intrasoft.intl.demomicrosvc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class PrepayProducts extends ResourceSupport {
    private long productId;
    private String status;

    public PrepayProducts(long productId, String status) {
        this.productId = productId;
        this.status = status;
    }

    public long getProductId() {
        return productId;
    }

    public String getStatus() {
        return status;
    }
}
