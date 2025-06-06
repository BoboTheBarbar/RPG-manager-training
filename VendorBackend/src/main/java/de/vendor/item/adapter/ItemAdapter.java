package de.vendor.item.adapter;

import de.vendor.item.domain.DomainItem;
import de.vendor.item.model.ItemCreate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter zur Übersetzung zwischen Domain-Items und API-Items
 */
@Component
public class ItemAdapter {

    /**
     * Konvertiert ein Domain-Item zu einem API-Item
     *
     * @param domainItem Das Domain-Item
     * @return Das API-Item
     */
    public de.vendor.item.model.Item toApiItem(DomainItem domainItem) {
        if (domainItem == null) {
            return null;
        }
        
        return new de.vendor.item.model.Item()
                .id(domainItem.getId())
                .name(domainItem.getName())
                .description(domainItem.getDescription())
                .price(domainItem.getPrice());
    }

    /**
     * Konvertiert ein API-Item zu einem Domain-Item
     *
     * @param apiItem Das API-Item
     * @return Das Domain-Item
     */
    public DomainItem toDomainItem(de.vendor.item.model.Item apiItem) {
        if (apiItem == null) {
            return null;
        }
        
        DomainItem domainItem = new DomainItem();
        domainItem.setId(apiItem.getId());
        domainItem.setName(apiItem.getName());
        domainItem.setDescription(apiItem.getDescription());
        domainItem.setPrice(apiItem.getPrice());
        
        return domainItem;
    }

    /**
     * Konvertiert ein ItemCreate zu einem Domain-Item
     *
     * @param itemCreate Das ItemCreate-Objekt
     * @return Das Domain-Item
     */
    public DomainItem toDomainItem(ItemCreate itemCreate) {
        if (itemCreate == null) {
            return null;
        }
        
        DomainItem domainItem = new DomainItem();
        domainItem.setName(itemCreate.getName());
        domainItem.setDescription(itemCreate.getDescription());
        domainItem.setPrice(itemCreate.getPrice());
        // ID wird nicht gesetzt, da sie automatisch generiert wird
        
        return domainItem;
    }

    /**
     * Konvertiert eine Liste von Domain-Items zu einer Liste von API-Items
     *
     * @param domainItems Die Liste der Domain-Items
     * @return Die Liste der API-Items
     */
    public List<de.vendor.item.model.Item> toApiItems(List<DomainItem> domainItems) {
        if (domainItems == null) {
            return null;
        }
        
        return domainItems.stream()
                .map(this::toApiItem)
                .collect(Collectors.toList());
    }

    /**
     * Konvertiert eine Liste von API-Items zu einer Liste von Domain-Items
     *
     * @param apiItems Die Liste der API-Items
     * @return Die Liste der Domain-Items
     */
    public List<DomainItem> toDomainItems(List<de.vendor.item.model.Item> apiItems) {
        if (apiItems == null) {
            return null;
        }
        
        return apiItems.stream()
                .map(this::toDomainItem)
                .collect(Collectors.toList());
    }
}
