package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.service.TourPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tour")
public class TourPackagesController {
    private final TourPackageService tourPackageService;

    @GetMapping
    public String toursPage(@RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size, ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);
        Page<TourPackage> result = tourPackageService.findAllByPageable(pageable);
        List<TourPackage> content = result.getContent();
        int totalPages = result.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("tours", result);
        return "tour";
    }

    @GetMapping("/{id}")
    public String singleTourPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<TourPackage> byId = tourPackageService.findById(id);
        if (byId.isPresent()) {
            TourPackage tourPackage = byId.get();
            modelMap.addAttribute("tour", tourPackage);
            return "singleTour";
        } else {
            return "redirect:/tour";
        }

     }

}
