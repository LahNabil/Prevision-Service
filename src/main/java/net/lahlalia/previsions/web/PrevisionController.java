package net.lahlalia.previsions.web;


import lombok.RequiredArgsConstructor;
import net.lahlalia.previsions.dtos.EsDto;
import net.lahlalia.previsions.dtos.IsStockDto;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.services.BacItemService;
import net.lahlalia.previsions.services.PrevisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/prevision")
@RequiredArgsConstructor
public class PrevisionController {

    private final BacItemService bacItemService;
    private final PrevisionService previsionService;


    @GetMapping("/bac")
    public ResponseEntity<List<BacItem>>getAllBacs(){
        List<BacItem> bacItems = bacItemService.getAllBacItems();
        return ResponseEntity.ok(bacItems);
    }
    @GetMapping("/bac/name/{idBac}")
    public ResponseEntity<String> getProductNameById(@PathVariable Long idBac){
        String nameProduct = bacItemService.getProductNameByIDBac(idBac);
        return ResponseEntity.ok(nameProduct);
    }
    @GetMapping("/{idPrevision}")
    public ResponseEntity<PrevisionDto> getPrevisionById(@PathVariable Long idPrevision){
        PrevisionDto previsionDto = previsionService.getPrevisionById(idPrevision);
        return ResponseEntity.ok(previsionDto);
    }
    @PostMapping("/")
    public ResponseEntity<PrevisionDto> savePrevision(@RequestBody PrevisionDto previsionDto){
        PrevisionDto savedPrevision = previsionService.savePrevision(previsionDto);
        return new ResponseEntity<>(savedPrevision, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{idPrevision}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrevisionDto>updatePrevision(@PathVariable Long idPrevision, @RequestBody PrevisionDto previsionDto){
        PrevisionDto updatedPrevision = previsionService.updatePrevision(idPrevision,previsionDto);
        return ResponseEntity.ok(updatedPrevision);
    }
    @GetMapping("/")
    public ResponseEntity<List<PrevisionDto>>getPrevisions(){
        List<PrevisionDto> previsionDtos = previsionService.getAllPrevision();
        return ResponseEntity.ok(previsionDtos);
    }
    @GetMapping("/vreel/{idPrevisionDto}")
    public ResponseEntity<Double>getVreel(@PathVariable Long idPrevisionDto){
        double quantite = previsionService.calculerVreel(idPrevisionDto);
        return ResponseEntity.ok(quantite);
    }
    @GetMapping("/es")
    public ResponseEntity<List<EsDto>> getEs(){
        List<EsDto> esDtoList = previsionService.getEsDtos();
        return ResponseEntity.ok(esDtoList);
    }
    @GetMapping("/cdate/{date1}")
    public ResponseEntity<Integer> checkSameMonthAndYear(@PathVariable Date date1) {
        Integer result = previsionService.getYearFromDate(date1);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/ABS/{idPrevision}")
    public ResponseEntity<Double> calculerABS(@PathVariable Long idPrevision){
        double ABS = previsionService.calculerABS(idPrevision);
        return ResponseEntity.ok(ABS);
    }
    @GetMapping("/accuracy/{idPrevision}")
    public ResponseEntity<Double>calculerAccuracy(@PathVariable Long idPrevision){
        double accuracy = previsionService.calculerAccuracy(idPrevision);
        return ResponseEntity.ok(accuracy);
    }
    @DeleteMapping("/{idPrevision}")
    public ResponseEntity<Void> deletePrevisionById(@PathVariable Long idPrevision){
        Boolean deletedPrevision = previsionService.deletePrevisionById(idPrevision);
        return deletedPrevision ? ResponseEntity.noContent().build() :ResponseEntity.notFound().build();

    }
    @GetMapping("/sommestockvilleproduit/{idPrevision}")
    public ResponseEntity<Double>calculerQuantiteStockProduitVille(@PathVariable Long idPrevision){
        double somme = previsionService.calculerQuantiteStockProduitVille(idPrevision);
        return ResponseEntity.ok(somme);

    }
    @GetMapping("/suffisant/{idPrevision}")
    public ResponseEntity<IsStockDto>isStockSufficientForPrevision(@PathVariable Long idPrevision){
        IsStockDto isStockDto = previsionService.isStockSufficientForPrevision(idPrevision);
        return ResponseEntity.ok(isStockDto);
    }
    @GetMapping("/{city}/{product}")
    public ResponseEntity<List<PrevisionDto>>getPrevisionsByCityProduct(@PathVariable String city,@PathVariable String product){
        List<PrevisionDto> previsionDtos = previsionService.getPrevisionByCityProduit(city,product);
        return ResponseEntity.ok(previsionDtos);
    }
    @GetMapping("/somme/{city}/{product}")
    public ResponseEntity<Double> getSommePrevisionByProduitCity(@PathVariable String city,@PathVariable String product){
        double somme = previsionService.calculerSommePrevisionByCityProduit(city,product);
        return ResponseEntity.ok(somme);
    }
    @GetMapping("/sommes/{idDepot}/{product}/{year}/{month}")
    public ResponseEntity<Double>calculerSommePByCityProduitDate(@PathVariable String idDepot,
                                                                 @PathVariable String product,
                                                                 @PathVariable int year,
                                                                 @PathVariable int month){
        double somme = previsionService.calculerSommePrevisionByCityProduitDate(idDepot,product,year,month);
        return ResponseEntity.ok(somme);
    }
    

}

