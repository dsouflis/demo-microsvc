package com.intrasoft.intl.demomicrosvc.service;

import com.intrasoft.intl.demomicrosvc.controller.PrepayProductsController;
import com.intrasoft.intl.demomicrosvc.model.PrepayProducts;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Random;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class PrepayProductsService {
    final Log logger = LogFactory.getLog(PrepayProductsService.class);

    public PrepayProducts prepayProductsForMsisdn(String msisdn) {
        PrepayProducts prepayProducts = new PrepayProducts(999, "Ok");
        return prepayProducts;
    }

    @Async
    public void prepayProductsForMsisdnDeferred(String msisdn, DeferredResult<ResponseEntity<PrepayProducts>> result) {
        try {
            int rand = Math.abs(new Random().nextInt()) % 10;
            if(rand < 2) {
                logger.info("Sleeping for too long");
                Thread.sleep(2000);
            } else if(rand < 8) {
                logger.info("Returning something");
                if(!result.hasResult()) {
                    logger.info("Setting prepayProducts");
                    PrepayProducts prepayProducts = prepayProductsForMsisdn(msisdn);
                    prepayProducts.add(PrepayProductsController.selfRelForMsisdn(msisdn));
                    result.setResult(ResponseEntity.ok(prepayProducts));
                }
            } else {
                logger.info("Returning some error");
                if(!result.hasResult()) {
                    logger.info("Setting prepayProducts with error");
                    PrepayProducts prepayProducts = new PrepayProducts(0, "Some error");
                    prepayProducts.add(PrepayProductsController.selfRelForMsisdn(msisdn));
                    result.setResult(ResponseEntity.ok(prepayProducts));
                }
            }
        } catch (Throwable e) {
            logger.info(String.format("Returning: %s", e.getMessage()));
            if (!result.hasResult()) {
                logger.info("Setting prepayProducts with exception");
                PrepayProducts prepayProducts = new PrepayProducts(0, e.getMessage());
                prepayProducts.add(PrepayProductsController.selfRelForMsisdn(msisdn));
                result.setResult(ResponseEntity.ok(prepayProducts));
            }
        }
    }
}
