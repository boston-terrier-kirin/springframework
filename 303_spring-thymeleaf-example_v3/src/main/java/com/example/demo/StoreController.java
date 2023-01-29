package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreController {

    private List<Item> items = new ArrayList<>();

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        model.addAttribute("categories", Constants.CATEGORIES);

        var idx =getIndexFromId(id);
        if (idx == Constants.NOT_FOUND) {
            model.addAttribute("item", new Item());
        } else {
            model.addAttribute("item", items.get(idx));
        }

        return "form";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("items", items);
        return "inventory";
    }

    @PostMapping("/submitItem")
    public String handleSubmit(Item item, RedirectAttributes redirectAttributes) {
        int idx = getIndexFromId(item.getId());
        if (idx == Constants.NOT_FOUND) {
            items.add(item);
        } else {
            items.set(idx, item);
        }

        redirectAttributes.addFlashAttribute("status", Constants.SUCCESS_STATUS);
        return "redirect:/inventory";
    }

    private int getIndexFromId(String id) {
        for (int i = 0; i < items.size(); i ++) {
            if (items.get(i).getId().equals(id)) {
                return i;
            }
        }

        return Constants.NOT_FOUND;
    }
}
