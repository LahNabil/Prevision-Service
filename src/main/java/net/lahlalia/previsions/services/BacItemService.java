package net.lahlalia.previsions.services;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.previsions.dtos.Bac;
import net.lahlalia.previsions.entities.BacItem;
import net.lahlalia.previsions.repositories.BacItemRepository;
import net.lahlalia.previsions.restclients.StockRestClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BacItemService {

    private final BacItemRepository bacItemRepository;
    private final StockRestClient stockRestClient;

    public String getProductNameByIDBac(Long idBac){
        BacItem bacItem = bacItemRepository.findById(idBac).get();
        String nameProduct = stockRestClient.getProductNameById(bacItem.getIdProduct());
        return nameProduct;
    }

    public List<BacItem> getAllBacItems(){
        List<Bac> allBacs = stockRestClient.getAllBacs();
        List<BacItem> bacItems = bacItemRepository.findAll();

        Set<String> bacItemIds = bacItems.stream()
                .map(BacItem::getIdBac)
                .collect(Collectors.toSet());

        for(Bac bac : allBacs){
            if(!bacItemIds.contains(bac.getIdBac())){
                BacItem newBacItem = BacItem.builder()
                        .idBac(bac.getIdBac())
                        .idDepot(bac.getIdDepot())
                        .idProduct(bac.getIdProduct())
                        .totalImpom(bac.getTotalImpom())
                        .capacityUsed(bac.getCapacityUsed())
                        .capacity(bac.getCapacity())
                        .build();
                bacItems.add(newBacItem);
                bacItemRepository.save(newBacItem);
            }
        }
        return bacItems;


    }

}

