package com.wbz.wbzapi.controller;

import com.wbz.wbzapi.entity.Item;
import com.wbz.wbzapi.service.AuthorizationService;
import com.wbz.wbzapi.service.Impl.ItemServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Tag(name = "Итем контроллер", description = "API для работы со стороны администрации")
public class ItemController {
    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());

    private final ItemServiceImpl itemService;
    private final AuthorizationService authorizationService;

    @GetMapping("/")
    @Operation(summary = "Поиск всех товаров")
    public List<Item> getItem() {
        return itemService.getAllItems();
    }

    @PostMapping("/edit")
    @Operation(summary = "Изменение товара по id")
    public List<Item> editItem(@RequestHeader HttpHeaders httpHeaders, @RequestBody Item item) {
        authorizationService.validateToken(httpHeaders);
        return itemService.editItem(item);
    }

    @PostMapping("/add")
    public List<Item> addItem(@RequestHeader HttpHeaders httpHeaders, @RequestBody Item item) {
        authorizationService.validateToken(httpHeaders);
        return itemService.addItem(item);
    }

    @PostMapping("/delete/{id}")
    public List<Item> deleteItem(@PathVariable long id) {
        LOG.info("IN deleteItem - query success / Прилетел запрос из метода deleteItem: - " + id);
        itemService.deleteItem(id);
        return itemService.getAllItems();
    }

    @PostMapping("/search/{name}")
    public Item searchItem(@PathVariable String name) {
        LOG.info("IN searchItem - query success / Прилетел запрос из метода searchItem: - " + name);
        return itemService.findItemByName(name);
    }
}

