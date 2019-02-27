package com.intrasoft.intl.demomicrosvc.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import com.intrasoft.intl.demomicrosvc.model.PrepayProducts;
import com.intrasoft.intl.demomicrosvc.service.PrepayProductsService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class PrepayProductsController {
    final Log logger = LogFactory.getLog(PrepayProductsController.class);

    PrepayProductsService prepayProductsService;

    @Autowired
    public PrepayProductsController(PrepayProductsService prepayProductsService) {
        this.prepayProductsService = prepayProductsService;
    }

    @GetMapping("/prepayProductsNonDeferred")
    public ResponseEntity<PrepayProducts> prepayProductsForMsisdnNonDeferred(
            @RequestParam(name = "msisdn") String msisdn
    ) {
        PrepayProducts prepayProducts = prepayProductsService.prepayProductsForMsisdn(msisdn);
        prepayProducts.add(linkTo(methodOn(PrepayProductsController.class).prepayProductsForMsisdnNonDeferred(msisdn)).withSelfRel());
        return new ResponseEntity<>(prepayProducts, HttpStatus.OK);
    }

    @GetMapping("/prepayProducts")
    @ApiOperation(value = "Get the PrepayProducts for an MSISDN", response = PrepayProducts.class)
    public DeferredResult<ResponseEntity<PrepayProducts>> prepayProductsForMsisdnDeferred(
            @RequestParam(name = "msisdn") String msisdn
    ) {
        DeferredResult<ResponseEntity<PrepayProducts>> result = new DeferredResult<>(1100L);

        result.onTimeout(() -> {
            logger.info("A timeout occured");
            PrepayProducts prepayProducts = new PrepayProducts(0L, "Timeout");
            prepayProducts.add(selfRelForMsisdn(msisdn));
            result.setResult(ResponseEntity.ok(prepayProducts));
        });
        result.onError((throwable) -> {
            logger.info("An error occured");
            PrepayProducts prepayProducts = new PrepayProducts(0L, throwable.getMessage());
            prepayProducts.add(selfRelForMsisdn(msisdn));
            result.setResult(ResponseEntity.ok(prepayProducts));
        });

        prepayProductsService.prepayProductsForMsisdnDeferred(msisdn, result);
        return result;
    }

    public static Link selfRelForMsisdn(String msisdn) {
        return linkTo(methodOn(PrepayProductsController.class).prepayProductsForMsisdnDeferred(msisdn))
                .withSelfRel();
    }

}
