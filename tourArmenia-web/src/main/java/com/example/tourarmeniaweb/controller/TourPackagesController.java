package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.*;
import com.example.tourarmeniacommon.service.*;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tour")
public class TourPackagesController {
    private final TourPackageService tourPackageService;
    private final RegionService regionService;
    private final CarService carService;
    private final ItemService itemService;
    private final CommentService commentService;

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
    @GetMapping("/index")
    public String toursPageIndex(ModelMap modelMap) {
        modelMap.addAttribute("tours", tourPackageService.findAll());
       return "index";
    }

    @GetMapping("/search")
    public String searchByRegion(@RequestParam(value = "regionId", required = false) Integer regionId, ModelMap modelMap) {

        String msg;
        List<TourPackage> tourPackages;

        if (regionId != null) {
            Region region = regionService.findById(regionId).orElse(null);

            if (region != null) {
                tourPackages = tourPackageService.findByRegion(region);
                if (tourPackages.isEmpty()) {
                    msg = "At present, we do not have any scheduled tours in this region.";
                } else {
                    msg = null;
                }
            } else {
                tourPackages = tourPackageService.findAll();
                msg = "invalid region";
            }
        } else {
            msg = null;
            tourPackages = tourPackageService.findAll();
        }

        modelMap.addAttribute("tourPackages", tourPackages);
        modelMap.addAttribute("regions", regionService.findAll());
        modelMap.addAttribute("msg", msg);
        return "tour";
    }



    @GetMapping("/{id}")
    public String singleTourPage(@PathVariable("id") int id,
                                 @AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap) {
        Optional<TourPackage> byId = tourPackageService.findById(id);
        if (byId.isPresent()) {
            TourPackage tourPackage = byId.get();
            User user = currentUser.getUser();
            List<Comment> comments = commentService.findAllByTourId(id);
            modelMap.addAttribute("tour", tourPackage);
            modelMap.addAttribute("comments", comments);
            modelMap.addAttribute("user", user);

            return "singleTour";
        } else {
            return "redirect:/tour";
        }

    }

    @PostMapping("/comment/add")
    public String addComment(@ModelAttribute Comment comment, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        comment.setUser(user);
        comment.setDate(new Date());
        commentService.save(comment);

        return "redirect:/tour/" + comment.getTour().getId();
}

}
