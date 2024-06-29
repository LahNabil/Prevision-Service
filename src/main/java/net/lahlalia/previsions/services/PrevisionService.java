package net.lahlalia.previsions.services;




import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.dtos.EsDto;
import net.lahlalia.previsions.dtos.IsStockDto;
import net.lahlalia.previsions.dtos.PrevisionDto;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.entities.Prevision;
import net.lahlalia.previsions.exceptions.PrevisionNotFoundException;
import net.lahlalia.previsions.mappers.MapperBac;
import net.lahlalia.previsions.mappers.MapperPrevision;
import net.lahlalia.previsions.repositories.BacItemRepository;
import net.lahlalia.previsions.repositories.PrevisionRepository;
import net.lahlalia.previsions.restclients.StockRestClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrevisionService {

    private final PrevisionRepository previsionRepository;
    private final StockRestClient stockRestClient;
    private final MapperPrevision mapperPrevision;
    private final MapperBac mapperBac;
    private final BacItemRepository bacItemRepository;




    public PrevisionDto savePrevision(PrevisionDto previsionDto)throws EntityNotFoundException{
        if(previsionDto == null){
            log.error(" value is null");
            return null;
        }

        Prevision prevision = mapperPrevision.convertToModel(previsionDto);

//        List<BacItem> bacs = stockRestClient.getBacsByProductAndZone(previsionDto.getNameProduct(),previsionDto.getSupplyEnveloppe()).stream().map(mapperBac::convertToModel).toList();
        List<Bac> bacs = stockRestClient.getBacsByProductAndZone(previsionDto.getNameProduct(),previsionDto.getSupplyEnveloppe());
        Prevision savedPrevision = previsionRepository.save(prevision);
        List<BacItem> bacItems = new ArrayList<>();
        bacs.forEach(b->{
            BacItem bacItem = BacItem.builder()
                    .idBac(b.getIdBac())
                    .idDepot(b.getIdDepot())
                    .idProduct(b.getIdProduct())
                    .capacityUsed(b.getCapacityUsed())
                    .capacity(b.getCapacity())
                    .totalImpom(b.getTotalImpom())
                    .prevision(prevision)
                    .build();
            bacItemRepository.save(bacItem);
            bacItems.add(bacItem);
        });
        savedPrevision.setBacItems(bacItems);
        return mapperPrevision.convertToDto(savedPrevision);


    }
    public List<PrevisionDto>getAllPrevision(){
        return previsionRepository.findAll().stream().map(mapperPrevision::convertToDto).toList();
    }
    public PrevisionDto updatePrevision(Long idPrevision, PrevisionDto previsionDto) throws EntityNotFoundException{
        Prevision existingPrevision = previsionRepository.findById(idPrevision)
                .orElseThrow(() -> new EntityNotFoundException("Prevision with ID " + idPrevision + " not found"));

        existingPrevision.setForeCaste(previsionDto.getForeCaste());
        existingPrevision.setDate(previsionDto.getDate());
        existingPrevision.setBusiness(previsionDto.getBusiness());
        existingPrevision.setNameProduct(previsionDto.getNameProduct());
        existingPrevision.setSupplyEnveloppe(previsionDto.getSupplyEnveloppe());

        Prevision updatedPrevision = previsionRepository.save(existingPrevision);

        return mapperPrevision.convertToDto(updatedPrevision);


    }


    public PrevisionDto getPrevisionById(Long idPrevision) throws EntityNotFoundException{
        if(idPrevision == null){
            log.error("Id is null");
            return null;
        }
        Prevision prevision = previsionRepository.findById(idPrevision).get();
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);
        return previsionDto;
    }
    public List<BacItem> getBacsByProdZonePrevision(Long idPrevision) {
        Prevision prevision = previsionRepository.findById(idPrevision)
                .orElseThrow(() -> new EntityNotFoundException("Prevision Not found"));
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);

        // Check for null values
        if (previsionDto.getNameProduct() == null || previsionDto.getSupplyEnveloppe() == null) {
            throw new IllegalArgumentException("NameProduct or SupplyEnveloppe is null");
        }

        String nameProduct = previsionDto.getNameProduct();
        String zoneDepot = previsionDto.getSupplyEnveloppe();
        List<BacItem> bacItems = new ArrayList<>();

        // Call Feign client to get Bacs
        List<Bac> bacs = stockRestClient.getBacsByProductAndZone(nameProduct, zoneDepot);
        bacs.forEach(b->{
            BacItem bacItem = BacItem.builder()
                    .idBac(b.getIdBac())
                    .idDepot(b.getIdDepot())
                    .idProduct(b.getIdProduct())
                    .build();
            bacItems.add(bacItem);
        });
        return bacItems;
    }


    public boolean compareDatesByYearAndMonth(Date date1, Date date2) {
        int year1 = getYearFromDate(date1);
        int year2 = getYearFromDate(date2);
        int month1 = getMonthFromDate(date1);
        int month2 = getMonthFromDate(date2);
        if(year1 == year2 && month1 == month2){
            return true;
        }
        return false;
    }
    public static int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1; // Months are 0-based in Calendar, so add 1
    }
    public static int getYearFromDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.YEAR));
        return calendar.get(Calendar.YEAR);

    }
    public List<EsDto> getEsDtos(){
        return stockRestClient.getEs();

    }
    public double calculerABS(Long idPrevision){
        Prevision prevision = previsionRepository.findById(idPrevision).get();
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);

        double forecaste = previsionDto.getForeCaste();
        double vReel = Math.abs(calculerVreel(previsionDto.getIdPrevision()));
        return Math.abs(forecaste - vReel);


    }
    public List<PrevisionDto> getPrevisionByCityProduit(String ville,String produit)throws EntityNotFoundException{
        if(ville == null ||produit == null){
            log.error("value is null");
            return null;
        }
        List<PrevisionDto> previsionDtos = getAllPrevision();
        return previsionDtos.stream()
                .filter(prevision -> ville.equals(prevision.getSupplyEnveloppe()) && produit.equals(prevision.getNameProduct()))
                .collect(Collectors.toList());




    }
    public double calculerSommePrevisionByCityProduit(String ville, String produit){
        if(ville == null ||produit == null){
            log.error("value is null");
            return 0;
        }
        List<PrevisionDto> previsionDtos = getPrevisionByCityProduit(ville,produit);
        return previsionDtos.stream().mapToDouble(PrevisionDto::getForeCaste).sum();


    }
    public double calculerSommePrevisionByCityProduitDate(String idDepot, String produit, int year, int month) {
        if (idDepot == null || produit == null) {
            log.error("value is null");
            return 0;
        }
        String ville = stockRestClient.getCityDepot(idDepot);
        int adjustedMonth = month - 1;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, adjustedMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = cal.getTime();
        log.info("Start Date: {}", startDate);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();

        List<PrevisionDto> previsionDtos = getPrevisionByCityProduit(ville, produit);

        return previsionDtos.stream()
                .filter(previsionDto -> {
                    Calendar previsionCal = Calendar.getInstance();
                    previsionCal.setTime(previsionDto.getDate());
                    int previsionYear = previsionCal.get(Calendar.YEAR);
                    int previsionMonth = previsionCal.get(Calendar.MONTH);
                    return previsionYear == year && previsionMonth == adjustedMonth;
                })
                .mapToDouble(PrevisionDto::getForeCaste)
                .sum();

    }

    public double calculerAccuracy(Long idPrevision) throws EntityNotFoundException {
        if (idPrevision == null) {
            log.error("value is null");
            return 0;
        }

        Prevision prevision = previsionRepository.findById(idPrevision).get();
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);
        double ABS = calculerABS(previsionDto.getIdPrevision());
        double vreel = Math.abs(calculerVreel(previsionDto.getIdPrevision())); // Use absolute value of vreel
        double accuracy = 0.0;
        double forecast = previsionDto.getForeCaste();
        if (forecast >= vreel) {
            accuracy = vreel / forecast * 100;
        } else if (forecast <= vreel) {
            accuracy = forecast/vreel * 100;

        }
        return Math.round(accuracy * 100.0) / 100.0;

    }

    public double calculerVreel(Long idPrevisionDto)throws EntityNotFoundException{
        if(idPrevisionDto == null){
            log.error("value is null");
            return 0;
        }
        Prevision prevision = previsionRepository.findById(idPrevisionDto).get();
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);

        List<EsDto> esDtoList = getEsDtos();
        log.info("EsDto List: {}", esDtoList);

        double sumOfSorties = 0.0;
        for (EsDto es : esDtoList) {
            boolean businessMatches = previsionDto.getBusiness().equals(es.getBusiness());
            boolean dateMatches = compareDatesByYearAndMonth(es.getDate(), previsionDto.getDate());
            boolean bacMatches = false;

            for (BacItem bacItem : previsionDto.getBacItems()) {
                if (bacItem.getIdBac().equals(es.getIdBac())) {
                    bacMatches = true;
                    break;
                }
            }

            log.info("EsDto ID: {}, Business Matches: {}, Date Matches: {}, Bac Matches: {}",
                    es.getId(), businessMatches, dateMatches, bacMatches);

            if (businessMatches && dateMatches && bacMatches) {
                sumOfSorties += es.getQuantite();
            }
        }

        log.info("Sum of Sorties: {}", sumOfSorties);

        return sumOfSorties;
    }

    public boolean deletePrevisionById(Long idPrevison)throws EntityNotFoundException{
        PrevisionDto dto = getPrevisionById(idPrevison);
        if( dto != null){
            previsionRepository.deleteById(idPrevison);
            return true;
        } else {
            return false;
        }

    }
    public double calculerQuantiteStockProduitVille(Long idPrevision)throws EntityNotFoundException{
        if(idPrevision == null){
            log.error("null values");
        }
        Prevision prevision = previsionRepository.findById(idPrevision).get();
        PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);

        List<BacItem> bacItemList =  previsionDto.getBacItems();
        return bacItemList.stream()
                .mapToDouble(BacItem::getCapacityUsed)
                .sum();

    }

public IsStockDto isStockSufficientForPrevision(Long idPrevision) throws PrevisionNotFoundException {
    Prevision prevision = previsionRepository.findById(idPrevision)
            .orElseThrow(() -> new PrevisionNotFoundException("Prevision with ID " + idPrevision + " not found"));
    PrevisionDto previsionDto = mapperPrevision.convertToDto(prevision);



    double safetyStock = 1130;
    double forecastedQuantity = previsionDto.getForeCaste();
    double currentStock = calculerQuantiteStockProduitVille(idPrevision) - safetyStock;
    List<BacItem> bacItems = previsionDto.getBacItems();
    double creuxBacs = bacItems.stream()
            .mapToDouble(bacItem->bacItem.getCapacity() - bacItem.getCapacityUsed())
            .sum();
    double capacityProduit = bacItems.stream()
            .mapToDouble(BacItem::getCapacity)
            .sum();

    return IsStockDto.builder()
            .stockActuel(currentStock)
            .safetyStock(safetyStock)
            .forecaste(forecastedQuantity)
            .nameProduct(previsionDto.getNameProduct())
            .creux(creuxBacs)
            .ville(previsionDto.getSupplyEnveloppe())
            .productCapacity(capacityProduit)
            .build();


}




}
