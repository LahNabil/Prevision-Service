package net.lahlalia.previsions.restclients;


import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.dtos.EsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "STOCK-SERVICE")
public interface StockRestClient {

    @GetMapping("/api/bac/")
    public List<Bac> getAllBacs();

    @GetMapping("/api/es/")
    public List<EsDto> getEs();

    @GetMapping("/api/bac/{idBac}")
    public Bac getBacById(@PathVariable Long idBac);

    @GetMapping("/api/bac/product/{nameProduct}")
    public List<Bac> getBacsByProductName(@PathVariable String nameProduct);

    @GetMapping("/api/bac/name/{idProduit}")
    public String getProductNameById(Long idProduit);

    @GetMapping("/api/depot/bacs/{nameProduct}/{zoneDepot}")
    public List<Bac> getBacsByProductAndZone(@PathVariable("nameProduct") String nameProduct, @PathVariable("zoneDepot") String zoneDepot);

    @GetMapping("/api/depot/getcitydepot/{idDepot}")
    public String getCityDepot(@PathVariable String idDepot);

}

